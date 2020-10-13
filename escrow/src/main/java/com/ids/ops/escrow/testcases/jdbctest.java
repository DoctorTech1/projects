/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.security.connections.MSSQLConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul20
 */
public class jdbctest {
    public static void main(String args[]) throws ClassNotFoundException {
        
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM app_version";
        String vers;
        try{
            ps = MSSQLConnector.getConnection().prepareStatement(query);       
            rs = ps.executeQuery();
            if(rs.next()){
                vers = rs.getString(1);
                System.out.println("Current App Version: "+vers);
            }
            else{
                System.out.println("Connection to Database failed! Please check the JDBC properties and try again.");
            }
        }catch (SQLException ex){
            Logger.getLogger(MSSQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
}
