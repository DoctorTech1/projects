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
public class CustomerPermissions {
    
    /**
     * Initialize TreeMaps containing customer permissions
     */
    private final TreeMap<String,String> escrowtype = new TreeMap<>(); //Determines the products to be escrowed
    private final TreeMap<String,String> specialtype = new TreeMap<>(); //Determines any special legacy products to be included.
    
    /**
     * Assign property file paths to appropriate strings
     */
    private final String escrctrl = "src/main/resources/controls/prodcontrol.properties";
    private final String legacyctrl = "src/main/resources/controls/legprodcontrol.properties";
    
    
    /**
     * Method to populate the escrow type tree with permissions data
     */
    public void loadEscrowTypesToTable(){
        File file = new File(escrctrl);
        Properties controls = new Properties();
        try{
            FileInputStream readprop = new FileInputStream(file);
            controls.load(readprop);
        }catch(IOException ex){
            System.out.println("Unable to find or read the property file: " + escrctrl + " " + ex.getMessage());
        }
        controls.entrySet().forEach((entry) -> {
            escrowtype.put((String) entry.getKey(), (String) entry.getValue());
        });
    }
    
    /**
     * API Method for accessing the contents of the escrow type tree elsewhere
     * @return
     */
    public TreeMap<String,String> getEscrowType(){
        return escrowtype;
    }
    
    /**
     * Method to populate the legacy type tree with permissions data
     */
    public void loadLegacyTypesToTable(){
        File file = new File(legacyctrl);
        Properties controls = new Properties();
        try{
            FileInputStream readprop = new FileInputStream(file);
            controls.load(readprop);
        }catch(IOException ex){
            System.out.println("Unable to find or read the property file: " + legacyctrl + " " + ex.getMessage());
        }
        controls.entrySet().forEach((entry) -> {
            specialtype.put((String) entry.getKey(), (String) entry.getValue());
        });
    }
    
    /**
     * API method for access the contents of the legacy type tree elsewhere
     * @return
     */
    public TreeMap<String,String> getLegacyEscrow(){
        return specialtype;
    }
}
