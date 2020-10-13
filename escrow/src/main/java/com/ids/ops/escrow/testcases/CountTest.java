/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.security.connections.DatabaseOperations;
import com.ids.ops.escrow.security.connections.MSSQLConnector;
import com.ids.ops.escrow.uix.modules.search.CustomerSearch;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul20
 */
public class CountTest {
    private static int numrow;
    
    private static void countSilent(int search, int comp, String value) throws InterruptedException{
        final String query;
        /**
         * Check if search terms are null or not
         */
        if(value != null){
            /**
            * Retrieve customer search query & associated count query
            */
            CustomerSearch genstring = new CustomerSearch();
            query = genstring.getSQLRowCount(search,comp);
            System.out.println(query);
            /**
             * Query DB and return total result count
             */           
            int row_count = comp;
            int count;
            int res;
            PreparedStatement ps;
            ResultSet rs;
            switch (row_count){ 
                case 0: //BEGINS WITH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value+"%");
                        rs = ps.executeQuery();                                
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
                case 1: //CONTAINS
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value+"%");
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }                            
                break;
                case 2: //ENDS WITH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value);
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }                                
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
                case 3: //EXACT MATCH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value);
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }                               
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
            }
         }else{
            javax.swing.JOptionPane.showMessageDialog(null,"Please enter valid search terms.","Search Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger(DatabaseOperations.class.getName());
        }
    }

    
    public static void main(String[] args) throws InterruptedException{
        int search = 2;
        int comp = 0;
        String value = "6";
        DatabaseOperations dbops = new DatabaseOperations();
        
        Thread countrow = new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    CountTest.countSilent(search, comp, value);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CountTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        countrow.start();
        countrow.join();
        System.out.println(numrow);
    }   
}
