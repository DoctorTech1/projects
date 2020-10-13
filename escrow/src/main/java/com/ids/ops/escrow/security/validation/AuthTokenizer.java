/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.validation;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.util.Properties;
/**
 *
 * @author paul20
 */
public class AuthTokenizer {
    
    private static final Key signkey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final String tokenIssuer = getTokenIssuer();
    private static final String tokenHeader = null;
    private static final String tokenPayload = null;
    private static final String tokenSignature = null;
    
    
    
    
    
    
    
    private static String getTokenIssuer(){
        String prop = "src/main/resources/tokenization/tokendefaults.properties";
        File file = new File(prop);
        Properties properties = new Properties();       
        try{
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }
        catch(IOException e){
            System.out.println("Unable to find or read property file " + e.getMessage());
        }
        String data = (String)properties.get("jwt.tokenizer.issuer");
        return data;
    }
   
    private static void getPermissionInformation(){
        
    }
    private static void generatePayloadDigest(String unm, String fnm, String perms){
    }  

}
