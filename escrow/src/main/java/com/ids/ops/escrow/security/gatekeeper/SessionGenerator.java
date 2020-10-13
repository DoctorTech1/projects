/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.gatekeeper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;

/**
 *
 * @author paul20
 */
public class SessionGenerator {
    
    private static final SecureRandom randomizer = new SecureRandom();
    private static final int SECURE_TOKEN_LENGTH = 256;
    private static String DICTIONARY_DEFS;
    
    private static final String getDictionary(){
        String prop = "src/main/resources/controls/defaultinfo.properties";
        File file = new File(prop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }catch(IOException e){
            System.out.println("Unable to find or read property file " + e.getMessage());
        }
        String UPC = (String)properties.get("sessionid.dictionary.uppercase");
        String LWC = (String)properties.get("sessionid.dictionary.lowercase");
        String NUM = (String)properties.get("sessionid.dictionary.numerics");       
        DICTIONARY_DEFS = UPC+LWC+NUM;
        return DICTIONARY_DEFS;
    }
    
    private static final char[] symbols = SessionGenerator.getDictionary().toCharArray();
    private static final char[] buffer = new char[SECURE_TOKEN_LENGTH];
    
    public static String generateTokenID(){
        for(int idx = 0; idx < buffer.length; idx++){
            buffer[idx] = symbols[randomizer.nextInt(symbols.length)];           
        }
        return new String(buffer);
    }
}
