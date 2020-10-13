/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.validation;

import com.ids.ops.escrow.security.connections.MSSQLConnector;
import com.ids.ops.escrow.uix.OperationsMenu;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author paul20
 */
public class ValidateConnections extends Thread{
    
    public void testJDBCConnection(){
        new Thread(() -> {
            PreparedStatement ps;
            ResultSet rs;
            String db_query = "SELECT * FROM app_version";
            String version;
            try{
                ps = MSSQLConnector.getConnection().prepareStatement(db_query);
                rs = ps.executeQuery();
                if(rs.next()){
                    version = rs.getString(1);
                    JOptionPane.showMessageDialog(null,"Database connection test successful. \nCurrent application version: "+version,"JDBC Test Successful",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,"Database connection test failed. Please check JDBC Settings and try again","JDBC Test Failed",JOptionPane.WARNING_MESSAGE);
                }
            }catch(SQLException ex){
                Logger.getLogger(MSSQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }catch (ClassNotFoundException ex) {
                Logger.getLogger(OperationsMenu.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }).start();       
    }
    public void testFTPConnection(){
        new Thread(() -> {
            String prop = "src/main/resources/ftp/remote.properties";
            File file = new File(prop);
            Properties properties = new Properties();
        
            try{
                FileInputStream fis = new FileInputStream(file);
                properties.load(fis);
            }catch(IOException e){
                Logger.getLogger(OperationsMenu.class.getName()).log(Level.SEVERE, null, e);
            }
            String server = (String)properties.get("connection.ftp.server");
            int port = Integer.parseInt((String)properties.get("connection.ftp.port"));
            String user = (String)properties.get("connection.ftp.user");
            String pwd = (String)properties.get("connection.ftp.password");
        
            JSch ftpClient = new JSch();
            Session session = null;
            try{
                session = ftpClient.getSession(user,server,port);
                session.setConfig("StrictHostKeyChecking","no");
                session.setPassword(pwd);
                session.connect();           
                boolean logon = session.isConnected();
                if(logon == true){
                    javax.swing.JOptionPane.showMessageDialog(null,"FTP Connection to Iron Mountain Successful","FTP Connection Successful ",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    session.disconnect();
                }else{
                    javax.swing.JOptionPane.showMessageDialog(null,"FTP Connection to Iron Mountain Failed!","FTP Connection Failed",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    session.disconnect();
                }
            }catch(JSchException e){
                Logger.getLogger(OperationsMenu.class.getName()).log(Level.SEVERE, null, e);
            }
        }).start();
    }
}
