/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.connections;

import java.io.File;
import java.io.FileInputStream; 
import java.io.IOException;
import java.sql.Connection; 
import java.sql.DriverManager;  
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author paul20
 */
public class MSSQLConnector {
    
    public static Connection getConnection() throws ClassNotFoundException {
        String prop = "src/main/resources/jdbc/jdbc.properties";
        File file = new File(prop);
        Properties properties = new Properties();
        
        try{
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }
        catch(IOException e){
            System.out.println("Unable to find or read property file " + e.getMessage());
        }
        String url = (String) properties.get("sql.datasource.url");
        String driver = (String) properties.get("sql.datasource.driverClassName");
        String username = (String) properties.get("sql.datasource.username");
        String password = (String) properties.get("sql.datasource.password");
        
        Class.forName(driver);
        
        
        Connection con = null;
        try{
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(MSSQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    } 
}
