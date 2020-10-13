/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 *
 * @author paul20
 */
public class MappingsBean {
    
    @CsvBindByName(column = "Account_Num", required = true)   
    @CsvBindByPosition(position = 0)
    private int acct;
    
    @CsvBindByName(column = "Company_Name", required = true)
    @CsvBindByPosition(position = 1)
    private String name;
    
    @CsvBindByName(column = "System_Num", required = true)
    @CsvBindByPosition(position = 2)
    private int sysnum;
    
    @CsvBindByName(column = "Business_Addr", required = true)
    @CsvBindByPosition(position = 3)
    private String address;
    
    @CsvBindByName(column = "Escrow_Type", required = true)
    @CsvBindByPosition(position = 4)
    private int esctype;
    
    @CsvBindByName(column = "IM_Deposit", required = true)
    @CsvBindByPosition(position = 5)
    private int deptype;
    
    @CsvBindByName(column = "Deposit_Name")
    @CsvBindByPosition(position = 6)
    private String depname;
    
    @CsvBindByName(column = "Deposit_Version")
    @CsvBindByPosition(position = 7)
    private int depversion;
    
    @CsvBindByName(column = "Deposit_Size")
    @CsvBindByPosition(position = 8)
    private int depsize;
    
    @CsvBindByName(column = "Last_Escrow")
    @CsvBindByPosition(position = 9)
    private String lastrun;
    
    @CsvBindByName(column = "Initiating_User")
    @CsvBindByPosition(position = 10)
    private String userrun;
    
}
