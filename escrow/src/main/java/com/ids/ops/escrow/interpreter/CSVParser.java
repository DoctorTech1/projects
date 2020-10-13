/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter;

//Standard Java Libraries
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.HashMap;
import java.util.Map;

//External Dependencies
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import java.util.ArrayList;


/**
 *
 * @author paul20
 */
public class CSVParser {
    
    private static Map<String,String> parsedata = new HashMap<String,String>();
    /**
     * Method to write the output of a customer search to a CSV File.
     * This method takes the contents of the results ArrayList and parses it 
     * for export to a CSV file. That CSV file will then be read and parsed for
     * display/viewing in the JTextArea
     * @param results The array list containing the ResultSet of the SQL query
     */
    private static void outputSearchToCSV(List<String> results) throws IOException{
        String prop = "src/main/resources/filefolder/filedirectory.properties";
        File file = new File(prop);
        Properties properties = new Properties();       
        try{
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }
        catch(IOException e){
            System.out.println("Unable to find or read property file " + e.getMessage());
        }
        String outfile = (String)properties.get("csv.out.workingfile");    
        File csvOutputFile = new File(outfile);
        try(FileWriter writer = new FileWriter(csvOutputFile, false)){
            /*writer.append("Account No.");
            writer.append(",");
            writer.append("Company Name");
            writer.append(",");
            writer.append("System No.");
            writer.append(",");
            writer.append("Business Address");
            writer.append(",");
            writer.append("Escrow Type(s)");
            writer.append(",");
            writer.append("Iron Mountain Deposit");
            writer.append(",");
            writer.append("Deposit Artifact Name");
            writer.append(",");
            writer.append("Deposit Artifact Version");
            writer.append(",");
            writer.append("Deposit Artifact Size (bytes)");
            writer.append(",");
            writer.append("Date/Time of Last Escrow");
            writer.append(",");
            writer.append("Initiating User");
            writer.append(",\n");*/
            for(String mapping : results){
                writer.write(mapping+"\n");
            }
            writer.flush();
            writer.close();
        }catch(IOException ex){
            java.util.logging.Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    private static void parseCSVInput(){
        String prop = "src/main/resources/filefolder/filedirectory.properties";
        File file = new File(prop);
        Properties properties = new Properties();       
        try(FileInputStream fis = new FileInputStream(file)){           
            properties.load(fis);
        }
        catch(IOException e){
            System.out.println("Unable to find or read property file " + e.getMessage());
        }
        String infile = (String)properties.get("csv.out.workingfile");
        try{
           parsedata = new CSVReaderHeaderAware(new FileReader(infile)).readMap();
            
        }catch(IOException ex){
            java.util.logging.Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public Map<String,String> getCSVInputData(){
        CSVParser.parseCSVInput();
        return parsedata;
    }
    
    /**
     * Publicly accessible method to invoke the CSV Output Writer.
     * @param results 
     */
    public static void writeCSV(List<String> results) throws IOException{
        CSVParser.outputSearchToCSV(results);
    }
}
