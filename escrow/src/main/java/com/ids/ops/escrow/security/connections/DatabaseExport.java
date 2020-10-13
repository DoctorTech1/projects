/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.connections;

import com.ids.ops.escrow.uix.modules.search.CustomerSearch;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
/**
 *
 * @author paul20
 */
public class DatabaseExport {
    
    private static final List<String> results = new ArrayList<>();
    private static final ArrayList<String> columns = new ArrayList<>();
    private static String[] headers;
    private static String query;
    private static PreparedStatement ps;
    private static ResultSet rs;
    
    private static void retrieveResults(int search, int comp, String value) throws IOException{
        int sql_param = comp;       
        /*
         * Check if search terms are null or not
         */
        if(value != null){            
            /*
             * Retrieve SQL query
             */                      
            CustomerSearch genquery = new CustomerSearch();
            query = genquery.getBasicSQLString(search,comp);           
            switch (sql_param){
                case 0: //BEGINS WITH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value+"%");
                        rs = ps.executeQuery();
                        ResultSetMetaData meta = rs.getMetaData();
                        int count = meta.getColumnCount();
                        for(int i = 1; i < count; i++){
                            String name = meta.getColumnName(i);
                            columns.add(name);
                        }                                                                
                    }catch(SQLException | ClassNotFoundException ex){
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                case 1: //CONTAINS
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value+"%");
                        rs = ps.executeQuery();
                        ResultSetMetaData meta = rs.getMetaData();
                        int count = meta.getColumnCount();
                        for(int i = 1; i < count; i++){
                            String name = meta.getColumnName(i);
                            columns.add(name);
                        }                                             
                    }catch(SQLException | ClassNotFoundException ex){
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 2: //ENDS WITH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value);
                        rs = ps.executeQuery();
                        ResultSetMetaData meta = rs.getMetaData();
                        int count = meta.getColumnCount();
                        for(int i = 1; i < count; i++){
                            String name = meta.getColumnName(i);
                            columns.add(name);
                        }                                             
                    }catch(SQLException | ClassNotFoundException ex){
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 3: //EXACT MATCH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value);
                        rs = ps.executeQuery();
                        ResultSetMetaData meta = rs.getMetaData();
                        int count = meta.getColumnCount();
                        for(int i = 1; i < count; i++){
                            String name = meta.getColumnName(i);
                            columns.add(name);
                        }                                             
                    }catch(SQLException | ClassNotFoundException ex){
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }else{
            java.util.logging.Logger.getLogger(DatabaseOperations.class.getName());
        }
    }
    private static String[] getRSHeaders(ArrayList<String> columnNames){
        String[] str = new String[columnNames.size()];
        for (int i = 0; i < columnNames.size(); i++){
            str[i] = columnNames.get(i);
        }
        return str;
    }
    public void writeResultSetArrayToCsv(int search, int comp, String value) throws IOException, SQLException{             
        String prop = "src/main/resources/filefolder/filedirectory.properties";        
        File file = new File(prop);
        Properties properties = new Properties();       
        try(FileInputStream fis = new FileInputStream(file)){           
            properties.load(fis);
        }
        catch(IOException e){
            System.out.println("Unable to find or read property file " + e.getMessage());
        }
        String csvout = (String)properties.get("csv.out.testfile");
        DatabaseExport.retrieveResults(search, comp, value);
        
        CSVWriter writer = new CSVWriter(new FileWriter(csvout));
        headers = DatabaseExport.getRSHeaders(columns);
        writer.writeNext(headers);
        writer.writeAll(rs,false);
        writer.close();
    }
}
