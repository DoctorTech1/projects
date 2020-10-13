/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter;

import com.ids.ops.escrow.security.encryption.EncodePermissions;
import java.util.ArrayList;

/**
 *
 * @author paul20
 */
public class ArrayConverter {
    
    private static String account;
    private static String name;
    private static String sys_num;
    private static String address;
    private static String products;
    private static String deposit;
    
    
    private static String getEscrowAccountNumber(ArrayList<ArrayList<String>> mdarray,int y){       
        StringBuilder sb = new StringBuilder();
        sb.append("Account No: ").append(mdarray.get(y).get(0));
        account = sb.toString();
        return account;
    }
    
    private static String getCustomerBusinessName(ArrayList<ArrayList<String>> mdarray,int y){
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(mdarray.get(y).get(1));
        name = sb.toString();
        return name;
    }
    
    private static String getCustomerSystemNumber(ArrayList<ArrayList<String>> mdarray,int y){
        StringBuilder sb = new StringBuilder();
        sb.append("System No: ").append(mdarray.get(y).get(2));
        sys_num = sb.toString();
        return sys_num;
    }
    
    private static String getCustomerBusinessAddress(ArrayList<ArrayList<String>> mdarray,int y){
        StringBuilder sb = new StringBuilder();        
        String adr = mdarray.get(y).get(3);
        String[] add = adr.split(";");
        for(int z = 0; z < add.length; z++){
            if(z == 0){
                sb.append(add[z]).append("\n");
            }else if(z > 0){
                String str = add[z].trim();
                sb.append(str).append("\n");
            }else if(z == add.length){
                sb.append(add[z]).trimToSize();
            }
        }
        address = sb.toString();
        return address;
    }
    
    private static String getContractedEscrow(ArrayList<ArrayList<String>> mdarray,int y){
        StringBuilder sb = new StringBuilder();
        EncodePermissions coding = new EncodePermissions();
        coding.decodePermissionData(mdarray.get(y).get(4));
        String decoded = coding.returnDecodedPermissions();
        int dec = Integer.parseInt(decoded,2);
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
        return products;
    }
    
    private static String getArtifactDepositType(ArrayList<ArrayList<String>> mdarray,int y){
        StringBuilder sb = new StringBuilder();
        String dep = mdarray.get(y).get(5);
        if(dep.equals("1")){
            sb.append("Deposit Type: Iron Mountain");
        }else{
            sb.append("Deposit Type: Other");
        }
        deposit = sb.toString();
        
        return deposit;
    }

//Publically accessible methods for displaying results in the textfields
    public static void displayCustomerInformation(javax.swing.JTextField cusacct,
            javax.swing.JTextField cusname,
            javax.swing.JTextField cusnum,
            javax.swing.JTextArea cusaddr,
            javax.swing.JTextField cusescrow,
            javax.swing.JTextField cusdep,
            ArrayList<ArrayList<String>> mdarray, int y){    
        cusacct.setText(ArrayConverter.getEscrowAccountNumber(mdarray,y));
        cusname.setText(ArrayConverter.getCustomerBusinessName(mdarray, y));
        cusnum.setText(ArrayConverter.getCustomerSystemNumber(mdarray, y));
        cusaddr.setText(ArrayConverter.getCustomerBusinessAddress(mdarray, y));
        cusescrow.setText(ArrayConverter.getContractedEscrow(mdarray, y));
        cusdep.setText(ArrayConverter.getArtifactDepositType(mdarray, y));
    }
    
    /*public static void displayBusinessName(javax.swing.JTextField cusname, ArrayList<ArrayList<String>> mdarray, int clicked, int count){
    }    
    public static void displaySystemNumber(javax.swing.JTextField cusnum, ArrayList<ArrayList<String>> mdarray, int clicked, int count){    
    }
    public static void displayBusinessAddress(javax.swing.JTextField cusaddr, ArrayList<ArrayList<String>> mdarray, int clicked, int count){    
    }    
    public static void displayEscrowProducts(javax.swing.JTextField cusescrow, ArrayList<ArrayList<String>> mdarray, int clicked, int count){   
    }    
    public static void displayDepositType(javax.swing.JTextField cusdep, ArrayList<ArrayList<String>> mdarray, int clicked, int count){    
    }*/
}
