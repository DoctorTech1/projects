/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.xlsxparse;

import com.ids.ops.escrow.security.connections.MSSQLConnector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author paul20
 */
public class ImportCustDataToDB {
    private static final String prpfile = "src/main/resources/fileinfo/directories.properties";
    private static Map<String, Integer> map = new HashMap<String, Integer>();
    
    private static String sysNum;
    
    
    public String getSysNum(){
        return sysNum;
    }
    
    private static String getCIFile(){
        File propfile = new File(prpfile);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(ImportCustDataToDB.class.getName()).log(Level.SEVERE, null, ex);
	}
        String filename = (String)properties.get("xlsx.input.custdata.test");
        return filename;
    }
    
    public static void parseExcel(){
        File cdimport = new File(ImportCustDataToDB.getCIFile());
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(cdimport)); //Creates the workbook instance of the XLSX file
            DataFormatter formatter = new DataFormatter(Locale.US); //Instantiates a data formatter to handle translation
            Sheet sheet = workbook.getSheetAt(0); //Returns the first sheet from the XLSX Workbook. May also be "RWCustomersByProductResults"
            ImportDataFormatter fmt = new ImportDataFormatter();
            FilterCustomerInfo msn = new FilterCustomerInfo();
            msn.parseXLSX(sheet, workbook, formatter, fmt, cdimport);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportCustDataToDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InvalidFormatException | EncryptedDocumentException ex) {
            Logger.getLogger(ImportCustDataToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void readAndImportExcelFile(){
        File cdimport = new File(ImportCustDataToDB.getCIFile());        
        String sysNum,address,escrow,escType,il9,il10,rap,ia,acctNum, company;
        try{
            ImportCustDataToDB.dropTableData();
            ImportCustDataToDB.dropNonEscrowTableData();
            FileInputStream fis = new FileInputStream(cdimport);
            XSSFWorkbook workbook = new XSSFWorkbook(fis); //Creates the workbook instance of the XLSX file
            DataFormatter formatter = new DataFormatter(Locale.US); //Instantiates a data formatter to handle translation
            XSSFSheet sheet = workbook.getSheetAt(0); //Returns the first sheet from the XLSX Workbook
            XSSFRow row;
            ImportDataFormatter fmt = new ImportDataFormatter();
            for(int i = 0; i <= sheet.getLastRowNum(); i++){               
                row = sheet.getRow(i);
                String trigger = formatter.formatCellValue(row.getCell(4));
                if(trigger.equals("Yes")){
                    String an = formatter.formatCellValue(row.getCell(0));
                    String cm = formatter.formatCellValue(row.getCell(1));
                    String sn = formatter.formatCellValue(row.getCell(2));
                    String sr = formatter.formatCellValue(row.getCell(3));
                    String ce = formatter.formatCellValue(row.getCell(4));
                    String i10 = formatter.formatCellValue(row.getCell(5));
                    String i9 = formatter.formatCellValue(row.getCell(6));
                    String ina = formatter.formatCellValue(row.getCell(7));
                    String rp = formatter.formatCellValue(row.getCell(8));
                    fmt.setAcctNum(an);
                    fmt.setCmpName(cm);
                    fmt.setSysNum(sn);
                    fmt.setAddress(sr);
                    fmt.setEscrow(ce);
                    fmt.setEscrowType(i9,i10,rp,ina);
                    acctNum = fmt.getAcctNum();
                    company = fmt.getCmpName();
                    sysNum = fmt.getSysNum();
                    address = fmt.getAddress();
                    escrow = fmt.getEscrow();
                    escType = fmt.getEscType();                
                    ImportCustDataToDB.insertDataIntoDB(company, sysNum, address, escrow, escType, acctNum);
                    }else if(trigger.equals("No")){
                    String an = formatter.formatCellValue(row.getCell(0));
                    String cm = formatter.formatCellValue(row.getCell(1));
                    String sn = formatter.formatCellValue(row.getCell(2));
                    String sr = formatter.formatCellValue(row.getCell(3));
                    String ce = formatter.formatCellValue(row.getCell(4));
                    String i10 = formatter.formatCellValue(row.getCell(5));
                    String i9 = formatter.formatCellValue(row.getCell(6));
                    String ina = formatter.formatCellValue(row.getCell(7));
                    String rp = formatter.formatCellValue(row.getCell(8));
                    fmt.setAcctNum(an);
                    fmt.setCmpName(cm);
                    fmt.setSysNum(sn);
                    fmt.setAddress(sr);
                    fmt.setEscrow(ce);
                    fmt.setEscrowType(i9,i10,rp,ina);
                    acctNum = fmt.getAcctNum();
                    company = fmt.getCmpName();
                    sysNum = fmt.getSysNum();
                    address = fmt.getAddress();
                    escrow = fmt.getEscrow();
                    escType = fmt.getEscType();
                    ImportCustDataToDB.insertNonEscrowCustToDB(company, sysNum, address, escrow, escType, acctNum);
                    }
            }

        }catch(FileNotFoundException ex){
            Logger.getLogger(ImportCustDataToDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportCustDataToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void insertDataIntoDB(String company, String sysNum, String address, String escrow, String escType, String acctNum){
        
        try{
            PreparedStatement ps;
            String query = "INSERT INTO escrw_cust_dta (cst_acct_num,cst_cmp_nme,cst_sys_num,cst_bus_adr,cst_esc_typ,cst_dep_typ) VALUES (?,?,?,?,?,?) OPTION (QUERYTRACEON 460)";
            ps = MSSQLConnector.getConnection().prepareStatement(query);
            ps.setString(1,acctNum);
            ps.setString(2,company);
            ps.setString(3,sysNum);
            ps.setString(4,address);
            ps.setString(5,escType);
            ps.setString(6,escrow);
            ps.executeUpdate();        
        }catch(SQLException | ClassNotFoundException se){
            Logger.getLogger(ImportCustDataToDB.class.getName()).log(Level.SEVERE, null, se);
        }
    }
    
    private static void dropTableData(){
        try{
            PreparedStatement ps;
            String query = "DELETE escrw_cust_dta";
            ps = MSSQLConnector.getConnection().prepareStatement(query);
            ps.executeUpdate();
        }catch(SQLException | ClassNotFoundException se){
            Logger.getLogger(ImportCustDataToDB.class.getName()).log(Level.SEVERE, null, se);
        }
    }   
    
    private static void insertNonEscrowCustToDB(String company, String sysNum, String address, String escrow, String escType, String acctNum){        
        try{
            PreparedStatement ps;
            String query = "INSERT INTO cust_dta_non_escrw (cst_acct_num,cst_cmp_nme,cst_sys_num,cst_bus_adr,cst_esc_typ,cst_dep_typ) VALUES (?,?,?,?,?,?) OPTION (QUERYTRACEON 460)";
            ps = MSSQLConnector.getConnection().prepareStatement(query);
            ps.setString(1,acctNum);
            ps.setString(2,company);
            ps.setString(3,sysNum);
            ps.setString(4,address);
            ps.setString(5,escType);
            ps.setString(6,escrow);
            ps.executeUpdate();        
        }catch(SQLException | ClassNotFoundException se){
            Logger.getLogger(ImportCustDataToDB.class.getName()).log(Level.SEVERE, null, se);
        }
    }
    
    private static void dropNonEscrowTableData(){
        try{
            PreparedStatement ps;
            String query = "DELETE cust_dta_non_escrw";
            ps = MSSQLConnector.getConnection().prepareStatement(query);
            ps.executeUpdate();
        }catch(SQLException | ClassNotFoundException se){
            Logger.getLogger(ImportCustDataToDB.class.getName()).log(Level.SEVERE, null, se);
        }
    }
    
}
