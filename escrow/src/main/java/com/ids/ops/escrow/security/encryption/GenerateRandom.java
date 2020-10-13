/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;

/**
 *
 * @author paul20
 */
public class GenerateRandom {
    
    private static final SecureRandom randomizer = new SecureRandom();
      
    private static String generatePassword(int len){
        String prop = "src/main/resources/controls/defaultinfo.properties";
        File file = new File(prop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }catch(IOException e){
            System.out.println("Unable to find or read property file " + e.getMessage());
        }
        String UPC = (String)properties.get("defaults.dictionary.uppercase");
        String LWC = (String)properties.get("defaults.dictionary.lowercase");
        String NUM = (String)properties.get("defaults.dictionary.numerics");
        String SPC = (String)properties.get("defaults.dictionary.specials");
        String dictionary = UPC+LWC+NUM+SPC;
        String result = "";
            
        for(int i = 0; i < len; i++){
            int index = randomizer.nextInt(dictionary.length());
            result += dictionary.charAt(index);
        }
        return result;
    }
    
    public String getNewPassword(int len){
        String newpass = GenerateRandom.generatePassword(len);
        return newpass;
    }
}
