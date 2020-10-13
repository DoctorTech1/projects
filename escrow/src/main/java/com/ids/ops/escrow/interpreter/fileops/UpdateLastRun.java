/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.fileops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul20
 */
public class UpdateLastRun {
    private static final String propfile = "src/main/resources/fileinfo/directories.properties";
    private static String lastpath;
    private static String lastdate;
    private static String currdate;
    
    /**
     * Gets the path to the file containing the last-run information.
     * @return The path containing the fully-qualified name of the file.
     */
    private static String getLastRunFile(){
        File lastfile = new File(propfile);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(lastfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(UpdateLastRun.class.getName()).log(Level.SEVERE, null, ex);
	}
        String path = (String)properties.get("lastrun.touchFile");
        return path;
    }
    
    /**
     * Gets the content of the last-run file.
     * @return The date-stamp contents of the last-run file
     */
    private static String getLastRun(){
        lastpath = UpdateLastRun.getLastRunFile();
        StringBuilder reader = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(lastpath))){
           String curLine;
           while((curLine = br.readLine()) != null){
               reader.append(curLine);
           }
        }catch(FileNotFoundException ex){
            Logger.getLogger(UpdateLastRun.class.getName()).log(Level.SEVERE, null, ex);
        }catch(IOException ix){
            Logger.getLogger(UpdateLastRun.class.getName()).log(Level.SEVERE, null, ix);
        }
        return reader.toString();
    }
    
    /**
     * Gets the current date on the system.
     * Gets the current date of the system the application is running on and
     * formats it as YYMMDD for easier parsing.
     * @return The date as formatted.
     */
    private static String getCurrentDate(){
        SimpleDateFormat dateformat = new SimpleDateFormat("YYMMdd");
        Date date = new Date(System.currentTimeMillis());
        currdate = dateformat.format(date);        
        return currdate;
    }
    
    /**
     * Compares the current system-date with that of the last-run file.
     * Gets the current system date and compares it with that in the last-run
     * file to determine when the last time an escrow was run.
     * @return true or false based on the comparison information
     */
    public static Boolean compareLastRun(){
        String date = UpdateLastRun.getCurrentDate();
        String lastrun = UpdateLastRun.parseLastRun();
        if(date.equals(lastrun)){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Method to get the contents of the last-run file.
     * A publicly accessible method to get the contents of the last-run file
     * for parsing by other classes.
     * @return The file contents.
     */
    public static String parseLastRun(){
        lastdate = UpdateLastRun.getLastRun();
        return lastdate;
    }
    
    /**
     * Method to populate the file with data.
     * A publicly accessible method to populate the last-run file with data on
     * the current escrow run.
     */
    public static void writeLastRun(){
        String last = UpdateLastRun.getLastRunFile();               
        try{
            FileWriter fw = new FileWriter(last);
            fw.write(UpdateLastRun.getCurrentDate());
            fw.close();            
        }catch(IOException ex){
            Logger.getLogger(UpdateLastRun.class.getName()).log(Level.SEVERE, null, ex);            
        }        
    }
    
    /**
     * Clears the last-run file contents.
     * Public method to clear the contents of the last-run file so that it can
     * be updated with new information.
     */
    public static void clearLastRun(){
        String last = UpdateLastRun.getLastRunFile();
        try{
            PrintWriter pw = new PrintWriter(last);
            pw.close();
        }catch(IOException ex){
            Logger.getLogger(UpdateLastRun.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
}
