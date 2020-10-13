/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.TreeMap;
/**
 *
 * @author paul20
 */
public class UserPermissions {
    
    /**
     * Initialize trees to contain permission data 
     */
    private final TreeMap<String,String> accountstatus = new TreeMap<>();
    private final TreeMap<String,String> accountcontrol = new TreeMap<>();
    private final TreeMap<String,String> accountflags = new TreeMap<>();
    private final TreeMap<String,String> accountinfo = new TreeMap<>();
    
   
    /**
     * Assign property file paths to appropriate strings
     */
    private final String statusfile = "src/main/resources/controls/acctstatus.properties";
    private final String controlfile = "src/main/resources/controls/acctcontrol.properties";
    private final String flagsfile = "src/main/resources/controls/acctflags.properties";
    private final String infofile = "src/main/resources/controls/acctinfo.properties";
    
    /**
     * Load Account Status property file and parse data to tree
     */
    public void loadAccountStatusToTable(){
        File acctstatfile = new File(statusfile);
        Properties acctstatus = new Properties();
        try{
            FileInputStream fis = new FileInputStream(acctstatfile);
            acctstatus.load(fis);
        }catch(IOException e){
            System.out.println("Unable to find or read the property file: " + statusfile + " " + e.getMessage());
        }
        acctstatus.entrySet().forEach((entry) -> {
            accountstatus.put((String) entry.getKey(), (String) entry.getValue());
        });
    }
    public TreeMap<String,String> getAcctStatusMap(){
        return accountstatus;
    }
    
    /**
     * Load Account Controls property file and parse data to tree
     */
    public void loadAccountControlsToTable(){
        File acctcontrolprop = new File(controlfile);
        Properties acctcontrol = new Properties();
        try{
            FileInputStream fis = new FileInputStream(acctcontrolprop);
            acctcontrol.load(fis);
        }catch(IOException e){
            System.out.println("Unable to find or read the property file: " + controlfile + " " + e.getMessage());
        }
        acctcontrol.entrySet().forEach((entry) -> {
            accountcontrol.put((String) entry.getKey(), (String) entry.getValue());
        });
    }
    public TreeMap<String,String> getAcctCtrlMap(){
        return accountcontrol;
    }
    
    /**
     * Load Account flags property file and parse data to tree
     */
    public void loadAccountFlagsToTable(){
        File acctprop = new File(flagsfile);
        Properties acctflags = new Properties();
        try{
            FileInputStream fis = new FileInputStream(acctprop);
            acctflags.load(fis);
        }catch(IOException e){
            System.out.println("Unable to find or read the property file: " + flagsfile + " " + e.getMessage());
        }
        acctflags.entrySet().forEach((entry) -> {
            accountflags.put((String) entry.getKey(), (String) entry.getValue());
        });
    }
    public TreeMap<String,String> getAcctFlagsMap(){
        return accountflags;
    }
    
    /**
     * Load Account information property file and parse data to tree
     */
    public void loadAccountInformationToTable(){
        File acctinfoprop = new File(infofile);
        Properties acctinfo = new Properties();
        try{
            FileInputStream fis = new FileInputStream(acctinfoprop);
            acctinfo.load(fis);
        }catch(IOException e){
            System.out.println("Unable to find or read the property file: " + infofile + " " + e.getMessage());
        }
        acctinfo.entrySet().forEach((entry) -> {
            accountinfo.put((String) entry.getKey(), (String) entry.getValue());
        });
    }
    public TreeMap<String,String> getAcctInfoMap() {
        return accountinfo;
    }
}
