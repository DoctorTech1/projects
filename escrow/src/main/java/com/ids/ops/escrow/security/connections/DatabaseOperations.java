/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.connections;

import com.google.common.collect.ImmutableList;
import com.ids.ops.escrow.security.encryption.EncryptPassword;
import com.ids.ops.escrow.security.gatekeeper.MailerSubsystem;
import com.ids.ops.escrow.uix.modules.AddUser;
import com.ids.ops.escrow.security.encryption.GenerateRandom;
import com.ids.ops.escrow.security.login.UserLogin;
import com.ids.ops.escrow.uix.OperationsMenu;
import com.ids.ops.escrow.uix.modules.search.CustomerSearch;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
/**
 *
 * @author paul20
 */
public class DatabaseOperations {
    
    private static String username = null;
    private static String bcryptHashString = null;
    Boolean pwdCheck;
    private static final ArrayList<String> results = new ArrayList<>();
    private static final ArrayList<String> columns = new ArrayList<>();
    private static ArrayList<ArrayList<String>> mdresults = new ArrayList<ArrayList<String>>();
    private static ArrayList<String> fwdresults = null;
    private static int res;
    
    /**
 * Method to submit new or modified user information into the database.
 * Takes the information from the user information form and puts it into the 
 * database. Once the information is submitted, it asks the logged in user if
 * they want to add more users or return to main page (this is done through the
 * use of a JOptionPane Question Message with custom options.
 * 
 * @param fnm The user's given (first) name
 * @param lnm The user's surname (last name)
 * @param unm The requested user's requested username
 * @param email The email address of the user to communicate info through
 * @param bcryptHashString The hash of the user's entered password
 * @param hexString The hexadecimal string of the user's permissions.
 */    
    private static void commitUserInfoToDB(String fnm, String lnm, String unm, String email, String bcryptHashString, String hexString){
        PreparedStatement ps;
        String db_query = "INSERT INTO escrw_app_ud (u_unm,u_fnm,u_lnm,u_eml,u_pd_h,u_prm) VALUES (?,?,?,?,?,?)";
        int gateway;
        Object[] options = {"Yes","No"};
        
        try{
            ps = MSSQLConnector.getConnection().prepareStatement(db_query);
            ps.setString(1,unm);
            ps.setString(2,fnm);
            ps.setString(3,lnm);
            ps.setString(4,email);
            ps.setString(5,bcryptHashString);
            ps.setString(6,hexString);
             
            if(ps.executeUpdate() > 0){
                gateway = JOptionPane.showOptionDialog(null,"User successfully added. Do you wish to add another?","Registration Successful",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
                if(gateway == 1){
                        OperationsMenu mainmenu = new OperationsMenu();
                        mainmenu.setLocationRelativeTo(null);
                        mainmenu.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                        mainmenu.pack();
                        mainmenu.setVisible(true);                          
                }else{
                    //Logic to return to user add screen.
                }
            }else{
                JOptionPane.showMessageDialog(null,"User failed to be added.","Registration failed",JOptionPane.WARNING_MESSAGE);
            }
               
        }catch(SQLException ex){
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method to check whether or not the username exists in the DB.
     * This method first tests the username string for a null value. If the
     * string is non-null, it then proceeds to create and execute a DB query to
     * check whether the username exists. Whatever data it finds, it will write
     * to a string for access in a checkUsername API.
     * @param unm The username of the person trying to login
     */
    private static void queryUsername(String unm){
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT u_unm FROM escrw_app_ud WHERE u_unm = ?";
            
        if((unm != null)){
            try{
                ps = MSSQLConnector.getConnection().prepareStatement(query);
                ps.setString(1, unm);
                rs = ps.executeQuery();
                if(rs.next()){
                   username = rs.getString("u_unm");
                }
            }catch(SQLException | ClassNotFoundException ex){
                java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            javax.swing.JOptionPane.showMessageDialog(null,"Username cannot be blank. Please enter a username","Username error",JOptionPane.ERROR_MESSAGE);
        }              
    }

    /**
     * Method to query the user's password from the DB.
     * This method uses the username of the person logging in to retrieve the
     * hashed and salted password from the database and assign said hash to a
     * string for comparison later.
     * @param unm The username of the person logging in.
     * @param password The password the user logging in enters into the form.
     */
    private static void userLogin(String unm, String password,javax.swing.JProgressBar statbar, javax.swing.JLabel statlabel){
        Boolean pwdCheck;
	PreparedStatement ps;
	ResultSet rs;
	String query = "SELECT u_pd_h FROM escrw_app_ud WHERE u_unm = ?";
	if((password != null) || (unm != null)){
            statbar.setForeground(java.awt.Color.BLUE);
            statbar.setStringPainted(true);
            statbar.setValue(33);
            statbar.setString("33%");
            statlabel.setText("Working...");
            try{
                ps = MSSQLConnector.getConnection().prepareStatement(query);
                ps.setString(1, unm);
                rs = ps.executeQuery();
                    if(rs.next()){
                        statbar.setValue(66);
                        statbar.setString("66%");
                        statlabel.setText("Checking Password...");
                        bcryptHashString = rs.getString("u_pd_h");
                        EncryptPassword cipher = new EncryptPassword();
                        cipher.verifyPassword(bcryptHashString, password);
                        pwdCheck = cipher.getVerificationStatus();
                        if(pwdCheck == true){
                            String time = String.valueOf(java.time.LocalTime.now());
                            String date = String.valueOf(java.time.LocalDate.now());                   
                            PreparedStatement ps1;
                            String query1 = "UPDATE escrw_app_ud SET u_lst_lgd = ?,u_lst_lgt = ? WHERE u_unm =?";
                            try{
                                ps1 = MSSQLConnector.getConnection().prepareStatement(query1);
                                ps1.setString(1,date);
                                ps1.setString(2,time);
                                ps1.setString(3,unm);
                                int rowcount = ps1.executeUpdate();
                                if(rowcount > 0){
                                    statbar.setValue(90);
                                    statbar.setString("90%");
                                    statlabel.setText("Updating Date/Time...");
                                }
                            }catch(SQLException | ClassNotFoundException ex){
                                    java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            statbar.setValue(100);
                            statbar.setString("100%");
                            statlabel.setText("Logging in...");                            
                            if(statbar.getString().equals("100%")){
                                Thread mainthread = new Thread(() -> {
                                    OperationsMenu main = new OperationsMenu();
                                    main.setLocationRelativeTo(null);
                                    main.setVisible(true);
                                    main.dispUsername.setText("Current User: "+unm);                
                                    main.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                    main.pack();
                                });
                                mainthread.start();
                            }  
                        }else{
                            statbar.setValue(0);
                            statbar.setString("0%");
                            statlabel.setText("Login Failure!");
                            javax.swing.JOptionPane.showMessageDialog(null,"User authentication failed","Login Failure",JOptionPane.ERROR_MESSAGE);
                        }
                    }
            }catch(SQLException | ClassNotFoundException ex){
                java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }else{
            statbar.setValue(0);
            statbar.setString("0%");
            statlabel.setText("Login Failure!");
            javax.swing.JOptionPane.showMessageDialog(null,"Password and/or username cannot be blank.","Authentication Error",JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Method to retrieve the username of a person in the database.
     * This method queries the user information table to see if
     * the user exists. If the user exists in the table, retrieve the
     * information and email it to the address on file. In order to prevent
     * brute-force attempts to crack the username, no indication will be given
     * to the user if the requested username does not exist in the table.
     * 
     * @param firstname The given (first) name of the requesting user
     * @param lastname The surname (last name) of the requesting user
     * @param email The email address of the requesting user.
     */    
    private static void userRecovery(String firstname,String lastname,String email){
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT u_unm FROM escrw_app_ud WHERE CONVERT(NVARCHAR(MAX),u_fnm) = ? AND CONVERT(NVARCHAR(MAX),u_lnm) = ? AND u_eml = ?";
        
        if((firstname != null) && (lastname != null) && (email != null)){
            try{
                ps = MSSQLConnector.getConnection().prepareStatement(query);
                ps.setString(1,firstname);
                ps.setString(2,lastname);
                ps.setString(3, email);
                rs = ps.executeQuery();
                if(rs.next()){
                   username = rs.getString("u_unm");
                   MailerSubsystem mailer = new MailerSubsystem();
                   mailer.sendUsernameRecoveryEmail(firstname, lastname, email, username);
                   JOptionPane.showMessageDialog(null,"The information you requested will be sent to the email address on file.","Recovery Complete",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,"The information you requested will be sent to the email address on file.","Recovery Complete",JOptionPane.INFORMATION_MESSAGE);
                }
            }catch(SQLException | ClassNotFoundException ex){
                java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            javax.swing.JOptionPane.showMessageDialog(null,"Given name, surname, and/or email are required fields. Please enter values and try again.","Error",JOptionPane.ERROR_MESSAGE);
        }              
    }
    
    /**
     * Method to reset the password of a user.
     * This method resets the password of a user in the database. To verify the
     * user requesting a password reset is genuine, a number of questions are
     * asked of the user for verification. Upon verification of the user, and
     * to prevent default passwords leaking, a secure-random password string is
     * generated, from outside the class and passed to this method. The secure
     * string is then encoded and hashed for storage and the plain-text of the
     * password is emailed to the user so that they can login. 
     * 
     * @param username The username of the person requesting a reset
     * @param email The email address on file of the user
     * @param lastname The surname (last name) of the user
     * @param fnm The given (first) name of the user
     * @param statbar The progress bar indicating the status of the request
     * @param statlabel The progress bar's label displaying information
     * @throws InterruptedException 
     */   
    private static void forgotPassword(String username,String email,String lastname,String fnm, javax.swing.JProgressBar statbar, javax.swing.JLabel statlabel) throws InterruptedException{                
        statbar.setForeground(java.awt.Color.BLUE);
        statbar.setStringPainted(true);
        statbar.setValue(0);
        statbar.setString("0%");
        statlabel.setText("Working...");
        
        String passwd = "";
        int len = 16;
        
        GenerateRandom pwdgen = new GenerateRandom();
        passwd = pwdgen.getNewPassword(len);
        
        statbar.setValue(25);
        statbar.setString("25%");
        statlabel.setText("Retrieving Data...");
        
        EncryptPassword encipher = new EncryptPassword();
        encipher.hashPassword(passwd);
        String password = encipher.getBCryptHashString();
        encipher.verifyPassword(password,passwd);
        Boolean pwdCheck = encipher.getVerificationStatus();
        
        statbar.setValue(50);
        statbar.setString("50%");
        statlabel.setText("Validating Inputs...");
        if(pwdCheck == true){
            PreparedStatement ps;
            String query = "UPDATE escrw_app_ud SET u_pd_h = ? WHERE u_unm =?";
            if((username == null) || (email == null) || (lastname == null) || (fnm == null)){
                statbar.setValue(0);
                statbar.setString("0%");
                statlabel.setText("Data Error! Aborting...");
                javax.swing.JOptionPane.showMessageDialog(null,"Username, email address, first and last name are required fields. Please enter the requested information try again.","Form Field Error",JOptionPane.ERROR_MESSAGE);
            }else{
                try{
                    ps = MSSQLConnector.getConnection().prepareStatement(query);
                    ps.setString(1,password);
                    ps.setString(2,username);
                    int rowcount = ps.executeUpdate();
                    if(rowcount > 0){
                        statbar.setValue(75);
                        statbar.setString("75%");
                        statlabel.setText("Processing Data...");                                                                                                
                        MailerSubsystem sendmail = new MailerSubsystem();
                        sendmail.sendPasswordResetEmail(lastname, fnm, passwd, email);
                        statbar.setValue(100);
                        statbar.setString("100%");
                        statlabel.setText("Reset Complete...");
                        javax.swing.JOptionPane.showMessageDialog(null,"If the specified information exists in the database, you will receive a confirmation email shortly.","Reset Complete",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        statbar.setValue(75);
                        statbar.setString("75%");
                        statlabel.setText("Processing Data...");
                        Thread.sleep(1500);                        
                        statbar.setValue(100);
                        statbar.setString("100%");
                        statlabel.setText("Reset Complete...");
                        javax.swing.JOptionPane.showMessageDialog(null,"If the specified information exists in the database, you will receive a confirmation email shortly.","Reset Complete",JOptionPane.INFORMATION_MESSAGE);
                    }
                    if(statbar.getString().equals("100%")){
                        Thread lgnthread = new Thread(() -> {
                            UserLogin main = new UserLogin();
                            main.setLocationRelativeTo(null);
                            main.setVisible(true);                
                            main.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            main.pack();
                        });
                        lgnthread.start();
                    }
                }catch(SQLException | ClassNotFoundException ex){
                    java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }  
    }
    
    /**
     * Method to retrieve the SQL query results.
     * This method takes the values entered into the form and generates a SQL
     * query that will be run against the customer table. The resulting
     * ResultSet is then written to an ArrayList for parsing by other methods.
     * 
     * @param search The selected index value of the search-by JComboBox
     * @param comp The selected index value of the comparator JComboBox
     * @param value The string value of the search-value JTextField
     * @param statbar The progress bar displaying the search progress
     * @param statlabel The label displaying search messages
     */    
    private static void retrieveResults1(int search, int comp, String value, javax.swing.JProgressBar statbar, javax.swing.JLabel statlabel){
        statbar.setForeground(Color.BLUE);
        statbar.setIndeterminate(true);
        statlabel.setText("Initializing...");
        int sql_param = comp;
        String query;
        PreparedStatement ps;
        ResultSet rs;       
        /*
         * Check if search terms are null or not
         */
        if(value != null){
            statbar.setIndeterminate(false);
            statbar.setStringPainted(true);
            statbar.setValue(15);
            statbar.setString("Search Progress: 15%");
            statlabel.setText("Building Query...");
            /*
             * Retrieve SQL query
             */
            CustomerSearch genquery = new CustomerSearch();
            query = genquery.getBasicSQLString(search,comp);
            statbar.setValue(50);
            statbar.setString("Search Progress: 50%");
            statlabel.setText("Executing Query...");
            switch (sql_param){
                case 0: //BEGINS WITH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value+"%");
                        rs = ps.executeQuery(); 
                        int numCol = rs.getMetaData().getColumnCount();
                        while(rs.next()){
                            StringBuilder sb = new StringBuilder();
                            for(int i = 1; i <= numCol; i++){
                                sb.append(String.format(String.valueOf(rs.getObject(i)))).append(",");
                            }
                            results.add(sb.toString().replaceAll("(?m)^\\s*$[\n\r]{1,}", ""));               
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        statbar.setValue(0);
                        statbar.setString("Search Progress: 0%");
                        statlabel.setText("Query Error");
                        statlabel.setText("Search returned an error");
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 1: //CONTAINS
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value+"%");
                        rs = ps.executeQuery();                       
                        int numCol = rs.getMetaData().getColumnCount();
                        while(rs.next()){
                            StringBuilder sb = new StringBuilder();
                            for(int i = 1; i <= numCol; i++){
                                sb.append(String.format(String.valueOf(rs.getObject(i)))).append(",");
                            }
                            results.add(sb.toString().replaceAll("(?m)^\\s*$[\n\r]{1,}", ""));
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        statbar.setValue(0);
                        statbar.setString("Search Progress: 0%");
                        statlabel.setText("Query Error");
                        statlabel.setText("Search returned an error");
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 2: //ENDS WITH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value);
                        rs = ps.executeQuery();
                        int numCol = rs.getMetaData().getColumnCount();
                        while(rs.next()){
                            StringBuilder sb = new StringBuilder();
                            for(int i = 1; i <= numCol; i++){
                                sb.append(String.format(String.valueOf(rs.getObject(i)))).append(",");
                            }
                            results.add(sb.toString().replaceAll("(?m)^\\s*$[\n\r]{1,}", ""));
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        statbar.setValue(0);
                        statbar.setString("Search Progress: 0%");
                        statlabel.setText("Query Error");
                        statlabel.setText("Search returned an error");
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 3: //EXACT MATCH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value);
                        rs = ps.executeQuery();
                        int numCol = rs.getMetaData().getColumnCount();
                        while(rs.next()){
                            StringBuilder sb = new StringBuilder();
                            for(int i = 1; i <= numCol; i++){
                                sb.append(String.format(String.valueOf(rs.getObject(i)))).append(",");
                            }
                            results.add(sb.toString().replaceAll("(?m)^\\s*$[\n\r]{1,}", ""));
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        statbar.setValue(0);
                        statbar.setString("Search Progress: 0%");
                        statlabel.setText("Query Error");
                        statlabel.setText("Search returned an error");
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            statbar.setValue(100);
            statbar.setString("Search Progress: 100%");
            statlabel.setText("Query Complete");
            }else{
                statbar.setValue(0);
                statbar.setString("Search Progress: 0%");
                statlabel.setText("Terms cannot be null");
                java.util.logging.Logger.getLogger(DatabaseOperations.class.getName());
        }
    }
    
    private static void retrieveResults(int search, int comp, String value, javax.swing.JProgressBar statbar, javax.swing.JLabel statlabel, javax.swing.JTextField terms){
        statbar.setForeground(Color.BLUE);
	statbar.setStringPainted(true);
	new Thread(() -> {
            for(int i = statbar.getValue(); i <= statbar.getMaximum(); i++){
                try {
                    Thread.sleep(11);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
                statbar.setValue(i);
                statbar.setString("Search Progress ("+i+"%)");
                switch (statbar.getValue()) {
                    case 0:
                        statlabel.setText("Initializing Search");
                        break;
                    case 10:
                        statlabel.setText("Generating Search Pattern");
                        break;
                    case 20:
                        statlabel.setText("Executing Search");
                        break;
                    case 100:
                        statlabel.setText("Search Complete");
                        break;
                    default:
                        break;
                }
            }
	}).start();	
	int sql_param = comp;
	String query;
	PreparedStatement ps;
	ResultSet rs;
	
	//Checks if the search terms are null
	if(value != null){
            query = CustomerSearch.getBasicSQLString(search, comp);
            //Query the DB for the requested information
            switch(sql_param){
                case 0:				
                    try{				
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value+"%");
                        rs = ps.executeQuery();
                        ResultSetMetaData meta = rs.getMetaData();
                        fwdresults = new ArrayList<>();				
                        for(int i = 1; i <= meta.getColumnCount(); i++){
                            fwdresults.add(meta.getColumnName(i));
                        }
                        mdresults.add(fwdresults);
                        while(rs.next()){
                            fwdresults = new ArrayList<>();
                            for(int i = 1; i <= meta.getColumnCount(); i++){
                                fwdresults.add(rs.getString(i));
                            }
                            mdresults.add(fwdresults);
                        }
                    }catch(SQLException | ClassNotFoundException ex){				
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }               				
                break;
                case 1:
                    try{				
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value+"%");
                        rs = ps.executeQuery();
                        ResultSetMetaData meta = rs.getMetaData();
                        fwdresults = new ArrayList<>();				
                        for(int i = 1; i <= meta.getColumnCount(); i++){
                            fwdresults.add(meta.getColumnName(i));
                        }
                        mdresults.add(fwdresults);
                        while(rs.next()){
                            fwdresults = new ArrayList<>();
                            for(int i = 1; i <= meta.getColumnCount(); i++){
                                fwdresults.add(rs.getString(i));
                            }
                            mdresults.add(fwdresults);
                        }
                    }catch(SQLException | ClassNotFoundException ex){				
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }               				
                break;
                case 2:
                    try{				
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value);
                        rs = ps.executeQuery();
                        ResultSetMetaData meta = rs.getMetaData();
                        fwdresults = new ArrayList<>();				
                        for(int i = 1; i <= meta.getColumnCount(); i++){
                            fwdresults.add(meta.getColumnName(i));
                        }
                        mdresults.add(fwdresults);
                        while(rs.next()){
                            fwdresults = new ArrayList<>();
                            for(int i = 1; i <= meta.getColumnCount(); i++){
                                fwdresults.add(rs.getString(i));
                            }
                            mdresults.add(fwdresults);
                        }
                    }catch(SQLException | ClassNotFoundException ex){				
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }               				
                break;
                case 3:
                    try{				
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value);
                        rs = ps.executeQuery();
                        ResultSetMetaData meta = rs.getMetaData();
                        fwdresults = new ArrayList<>();				
                        for(int i = 1; i <= meta.getColumnCount(); i++){
                            fwdresults.add(meta.getColumnName(i));
                        }
                        mdresults.add(fwdresults);
                        while(rs.next()){
                            fwdresults = new ArrayList<>();
                            for(int i = 1; i <= meta.getColumnCount(); i++){
                                fwdresults.add(rs.getString(i));
                            }
                            mdresults.add(fwdresults);
                        }
                    }catch(SQLException | ClassNotFoundException ex){				
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }               				
                break;
            }
        }
    }
    
    private static void retrieveCustomerTableMetadata(){
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM escrw_cust_dta";
        try{
            ps = MSSQLConnector.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            if(rs != null){
                ResultSetMetaData meta = rs.getMetaData();
                for(int i = 1; i < meta.getColumnCount(); i++){
                    columns.add(meta.getColumnName(i));
                }             
            }     
        }catch(SQLException | ClassNotFoundException ex){				
            java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<List<Object>> resultsToArray(int search, int comp, String value) throws SQLException, ClassNotFoundException {
        PreparedStatement ps;
        ResultSet rs;
        String query;
        
        CustomerSearch getquery = new CustomerSearch();
        query = getquery.getBasicSQLString(search, comp);
        ImmutableList.Builder<List<Object>> rows = ImmutableList.builder();
        ps = MSSQLConnector.getConnection().prepareStatement(query);
        ps.setString(1,value+"%");
        rs = ps.executeQuery();
        int columnCount = rs.getMetaData().getColumnCount();
        while(rs.next()){
            List<Object> row = new ArrayList<>();
            for(int i = 1; i <= columnCount; i++){
                row.add(rs.getObject(i));
            }
            rows.add(row);
        }
        return rows.build();
    }
    
    
    /*
     * Publicly accessible methods for use outside of the main class
     */
    public static ArrayList<String> getCustomerColumns(){
        DatabaseOperations.retrieveCustomerTableMetadata();
        return columns;
    }
    
    public static ArrayList<ArrayList<String>> getMDResults(){       
        return mdresults;
    }
    
    public static ArrayList<String> getFWDResults(){
        return fwdresults;
    }
    
    public void getResults(int search, int comp, String value, javax.swing.JProgressBar statbar, javax.swing.JLabel statlabel, javax.swing.JTextField terms){
        DatabaseOperations.retrieveResults(search, comp, value, statbar, statlabel, terms);
    }
            
    public void commitUserInfo(String fnm, String lnm, String unm, String email, String bcryptHashString, String hexString){
        DatabaseOperations.commitUserInfoToDB(fnm,lnm,unm,email,bcryptHashString,hexString);
    }
    
    public String getUsername(String unm){
        DatabaseOperations.queryUsername(unm);
        return username;
    }
    
    public void mainLogin(String unm, String password,javax.swing.JProgressBar statbar, javax.swing.JLabel statlabel){
        DatabaseOperations.userLogin(unm, password, statbar, statlabel);
    }
    
    public String getPassHash(){
        return bcryptHashString;
    }
    
    public void recoverUsername(String firstname,String lastname,String email){
        DatabaseOperations.userRecovery(firstname, lastname, email);
    }
    
    public void resetPassword(String username,String email,String lastname,String fnm, javax.swing.JProgressBar statbar, javax.swing.JLabel statlabel) throws InterruptedException{
        DatabaseOperations.forgotPassword(username, email, lastname, fnm, statbar, statlabel);
    }
    
    public ArrayList<String> getResultSetArray(int search, int comp, String value, javax.swing.JProgressBar statbar, javax.swing.JLabel statlabel){
        DatabaseOperations.retrieveResults1(search, comp, value, statbar, statlabel);
        return results;
    }
}
