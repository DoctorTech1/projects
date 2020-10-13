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
public class LastDBUpdate {
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
		Logger.getLogger(LastDBUpdate.class.getName()).log(Level.SEVERE, null, ex);
	}
        String path = (String)properties.get("dbupdate.touchFile");
        return path;
    }
    
    
    /**
     * Gets the content of the last-run file.
     * @return The date-stamp contents of the last-run file
     */
    private static long getLastRun(){       
        lastpath = LastDBUpdate.getLastRunFile();
        StringBuilder reader = new StringBuilder();
        long last = 0;
            try{
               BufferedReader br = new BufferedReader(new FileReader(lastpath));
               String curLine;
               
               while((curLine = br.readLine()) != null){
                   reader.append(curLine);
               }
               br.close();
               String info = reader.toString();
               last = Long.parseLong(info);
            }catch(FileNotFoundException ex){
                Logger.getLogger(LastDBUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }catch(IOException ix){
                Logger.getLogger(LastDBUpdate.class.getName()).log(Level.SEVERE, null, ix);
            }
        return last;
    }
    
    /**
     * Gets the path to the location of the customer data XLSX file.
     * Reads the properties file and extracts the location (full file-path) of 
     * the XLSX file to be read.
     * @return The fully-qualified path to the Excel file
     */
    private static String getCustDataFile(){
        File lastfile = new File(propfile);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(lastfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(LastDBUpdate.class.getName()).log(Level.SEVERE, null, ex);
	}
        String path = (String)properties.get("xlsx.input.custdata");
        return path;
    }
    
    private static long getCustDataInfo(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");
        String path = LastDBUpdate.getCustDataFile();
        File file = new File(path);
        String info = sdf.format(file.lastModified());
        long date = 0;
        try{
           date = Long.parseLong(info); 
        }catch(NumberFormatException nfe){
            Logger.getLogger(LastDBUpdate.class.getName()).log(Level.SEVERE, null, nfe);
        } 
        return date;
    }
    /**
     * Gets the current date on the system.
     * Gets the current date of the system the application is running on and
     * formats it as YYMMDD for easier parsing.
     * @return The date as formatted.
     */
    private static String getCurrentDate(){
        SimpleDateFormat dateformat = new SimpleDateFormat("YYMMddHHmmss");
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
        long lastupdate = LastDBUpdate.getLastRun();
        long xlsxupdate = LastDBUpdate.getCustDataInfo();
        boolean update = false;
        if(xlsxupdate > lastupdate){
            update = true;
        }else if(xlsxupdate <= lastupdate){
            update = false;
        }
        return update;
    }
    
    /**
     * Method to get the contents of the last-run file.
     * A publicly accessible method to get the contents of the last-run file
     * for parsing by other classes.
     * @return The file contents.
     */
    public static long parseLastRun(){
        long lstdte = LastDBUpdate.getLastRun();
        return lstdte;
    }
    
    /**
     * Method to populate the file with data.
     * A publicly accessible method to populate the last-run file with data on
     * the current escrow run.
     */
    public static void writeLastRun(){
        String last = LastDBUpdate.getLastRunFile();               
        try{
            FileWriter fw = new FileWriter(last);
            fw.write(LastDBUpdate.getCurrentDate());
            fw.close();            
        }catch(IOException ex){
            Logger.getLogger(LastDBUpdate.class.getName()).log(Level.SEVERE, null, ex);            
        }        
    }
    
    /**
     * Clears the last-run file contents.
     * Public method to clear the contents of the last-run file so that it can
     * be updated with new information.
     */
    public static void clearLastRun(){
        String last = LastDBUpdate.getLastRunFile();
        try{
            PrintWriter pw = new PrintWriter(last);
            pw.close();
        }catch(IOException ex){
            Logger.getLogger(LastDBUpdate.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
    public static void main(String[] args){
        boolean test = LastDBUpdate.compareLastRun();
        System.out.println(test);
    }
    
}
