/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter;

import com.ids.ops.escrow.security.encryption.EncodePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;



/**
 *
 * @author paul20
 */
public class ArrayMapping {
    
    private static String codedperm = "30313131";
    private static String products;
    
    
    
    public static void main(String[] args){       
        StringBuilder sb = new StringBuilder();
        EncodePermissions coding = new EncodePermissions();
        coding.decodePermissionData(codedperm);
        String decoded = coding.returnDecodedPermissions();                     
        int dec = Integer.parseInt(decoded,2);
        System.out.println(dec);
        
        switch(dec){
            case 0:
                sb.append("No Current Escrow Contract");
                break;
            case 1:
                sb.append("Contracted for: InfoLease 9");
                break;
            case 2:
                sb.append("Contracted for: InfoLease 10");
                break;
            case 3:
                sb.append("Contracted for: InfoLease 9 & 10");
                break;
            case 4:
                sb.append("Contracted for: Rapport");
                break;
            case 5:
                sb.append("Contracted for: Rapport, IL9");
                break;
            case 6:
                sb.append("Contracted for: Rapport, IL10");
                break;
            case 7:
                sb.append("Contracted for: Rapport, IL9 & IL10");
                break;
            case 8:
                sb.append("Contracted for: InfoAnalysis");
                break;
            case 9:
                sb.append("Contracted for: InfoAnalysis & IL9");
                break;
            case 10:
                sb.append("Contracted for: InfoAnalysis & IL10");
                break;
            case 11:
                sb.append("Contracted for: InfoAnalysis, IL9 & IL10");
                break;
            case 12:
                sb.append("Contracted for: InfoAnalysis & Rapport");
                break;
            case 13:
                sb.append("Contracted for: InfoAnalysis, Rapport, IL9");
                break;
            case 14:
                sb.append("Contracted for: InfoAnalysis, Rapport, IL10");
                break;
            case 15:
                sb.append("Contracted for: All Products");
                break;
        }
        products = sb.toString();
        System.out.println(products);
    }
}
