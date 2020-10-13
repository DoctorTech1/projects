/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.xlsxparse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author paul20
 */
public class FilterCustomerInfo {
    private static String sysNum;
    
    
    public String getSysNum(){
        return sysNum;
    }
    
    public void parseXLSX(Sheet sheet,Workbook workbook,DataFormatter formatter, ImportDataFormatter fmt, File file){
        //Creates a new StringBuilder() instance.
        StringBuilder sb = new StringBuilder();
        
        //Create the array which holds all the entries from a cell that contains multiple entries
        String[] sysNumber;
        
        //The number of the last row in the sheet.
        int lastRow = sheet.getLastRowNum();
        //final int rowNum = sheet.getPhysicalNumberOfRows();
        
        //Instantiates an integer that will be assigned the length of the sysNumber array later
        int arrayLength;
        
        //Creates a new sheet to hold the filtered values.
        Sheet sheet2 = workbook.createSheet("CustomerDataImport");
        
        //Loops through the each row in the sheet 
        for(int r = 1; r < lastRow; r++){
            Row row = sheet.getRow(r);
            String systemNumber = formatter.formatCellValue(row.getCell(2));            
            String activeEscrow = formatter.formatCellValue(row.getCell(4));
            fmt.setAddress(formatter.formatCellValue(row.getCell(3)));
            if(fmt.getAddress().equals("Unknown")){
                sb = new StringBuilder();
                sb.append("No Address Provided");
                String add = sb.toString();
                row.getCell(3).setCellValue(add);
            }else{
                row.getCell(3).setCellValue(fmt.getAddress());
            }
          
            if((systemNumber.length() >= 0) && ((activeEscrow.equals("Yes")) | (activeEscrow.equals("No")) | (activeEscrow.equals("Unknown")))){
                 /** Checks whether or not we are on the cell containing the
                 * system numbers and whether or not they are currently active.
                 * If we are, get values for all cells in the row
                 */
                String an = formatter.formatCellValue(row.getCell(0));
                String cn = formatter.formatCellValue(row.getCell(1));
                String ca = formatter.formatCellValue(row.getCell(3));
                String es = formatter.formatCellValue(row.getCell(4));
                String i10 = formatter.formatCellValue(row.getCell(5));
                String i9 = formatter.formatCellValue(row.getCell(6));
                String ia = formatter.formatCellValue(row.getCell(7));
                String rp = formatter.formatCellValue(row.getCell(8));              
                /**
                 * Checks the contents of the cell for more than one entry
                 * If the cell contains more than one system number, process
                 * the data accordingly
                 */

                fmt.setSysNum(systemNumber);
                sysNumber = String.valueOf(fmt.getSysNum()).split(",");
                /**
                 * Assign the length value of the 'sysNumber' array to
                 * the integer 'arrayLength'
                 */
                arrayLength = sysNumber.length;               
                /**
                 * Loop through each entry in the string array, creating
                 * a new row on each iteration and pasting the data from
                 * the old cells to the new ones
                 */
                for(int n = 0; n < arrayLength; n++){
                    Row nRow = sheet2.createRow(sheet2.getPhysicalNumberOfRows());
                    nRow.createCell(0).setCellValue(an);
                    nRow.createCell(1).setCellValue(cn);
                    nRow.createCell(2).setCellValue(sysNumber[n]);
                    nRow.createCell(3).setCellValue(ca);
                    nRow.createCell(4).setCellValue(es);
                    nRow.createCell(5).setCellValue(i10);
                    nRow.createCell(6).setCellValue(i9);
                    nRow.createCell(7).setCellValue(ia);
                    nRow.createCell(8).setCellValue(rp);
                }                
            }
        }
        workbook.removeSheetAt(0);
        try {
            workbook.write(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilterCustomerInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FilterCustomerInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}