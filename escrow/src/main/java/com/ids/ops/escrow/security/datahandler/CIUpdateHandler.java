/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.datahandler;

import com.ids.ops.escrow.interpreter.fileops.LastDBUpdate;
import com.ids.ops.escrow.interpreter.xlsxparse.ImportCustDataToDB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author paul20
 */
public class CIUpdateHandler {
    
    private static String getLogger(){
        String propfile = "src/main/resources/messages/logging.properties";
        File lastfile = new File(propfile);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(lastfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(CIUpdateHandler.class.getName()).log(Level.SEVERE, null, ex);
	}
        String path = (String)properties.get("console.import.logging.output");
        return path;
    }
    
    public static void main(String[] args) throws FileNotFoundException{
        LastDBUpdate last = new LastDBUpdate();
        ImportCustDataToDB data = new ImportCustDataToDB();
        String log = getLogger();
        PrintStream ps = new PrintStream(new File(log));
        System.setOut(ps);
        Boolean isCurrent = last.compareLastRun();
        if(isCurrent.equals(false)){
            System.out.println("Refresh of customer data is not necessary. Information is up-to-date as of: "+last.parseLastRun()+". Sleeping...");
        }else if(isCurrent.equals(true)){
            System.out.println("Customer information is not up-to-date. Refresh of customer data is required. Last update was performed on: "+last.parseLastRun()+" Running Update...");
            data.parseExcel();
            data.readAndImportExcelFile();
            last.clearLastRun();
            last.writeLastRun();
            System.out.println("Customer Update has completed. Sleeping...");
        }
    }
}
