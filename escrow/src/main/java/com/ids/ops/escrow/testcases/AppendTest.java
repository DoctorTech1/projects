/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.interpreter.xlsxparse.ImportDataFormatter;
import com.ids.ops.escrow.interpreter.xlsxparse.FilterCustomerInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author paul20
 */
public class AppendTest {
    private static final String prpfile = "src/main/resources/fileinfo/directories.properties";
    
    private static String getCIFile(){
        File propfile = new File(prpfile);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(AppendTest.class.getName()).log(Level.SEVERE, null, ex);
	}
        String filename = (String)properties.get("xlsx.input.custdata.test");
        return filename;
    }
    
    private static void readExcelFile(){
        File cdimport = new File(AppendTest.getCIFile());
        try{           
            Workbook workbook = WorkbookFactory.create(new FileInputStream(cdimport)); //Creates the workbook instance of the XLSX file
            DataFormatter formatter = new DataFormatter(Locale.US); //Instantiates a data formatter to handle translation
            Sheet sheet = workbook.getSheetAt(0); //Returns the first sheet from the XLSX Workbook. May also be "RWCustomersByProductResults"
            ImportDataFormatter fmt = new ImportDataFormatter();
            FilterCustomerInfo msn = new FilterCustomerInfo();
            msn.parseXLSX(sheet, workbook, formatter, fmt, cdimport);
        }catch(FileNotFoundException ex){
            Logger.getLogger(AppendTest.class.getName()).log(Level.SEVERE, null, ex);
        }catch(IOException | InvalidFormatException | EncryptedDocumentException ix){
            Logger.getLogger(AppendTest.class.getName()).log(Level.SEVERE, null, ix);
        }
    }
    
    public static void main(String[] args){
        AppendTest.readExcelFile();
    }
}
