/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.fileops;

import java.io.File;
import java.io.FileInputStream; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author paul20
 */
public class GetFilePaths {
    
    private static String il10source, il10archive, rapsource, raparchive, iasource,
            iaarchive, exhbsource, exhbarchive, exhboutput, enhancement;
    private static String[] enhList;
    private static List<String> enhPaths = new ArrayList<>();
    
    private static void getIL10SourcePath(String branch){
        String p4prop = "src/main/resources/p4v/helix.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        String source = (String)properties.get("artifact.il10.dirPath");
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(branch).append("\\");
        il10source = sb.toString();
        
    }
    
    private static void getIL10ArchivePath(){
        String p4prop = "src/main/resources/fileinfo/directories.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        il10archive = (String)properties.get("artifact.archive.il10.output");
    }
    
    private static void getIL10EnhancePath(String branch){
        String p4prop = "src/main/resources/p4v/helix.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        String source = (String)properties.get("artifact.il10.enhancement");
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(branch).append("\\enhancements\\");               
        enhancement = sb.toString().trim();
    }
    
    private static void getRapSourcePath(String branch){
        String p4prop = "src/main/resources/p4v/helix.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        String source = (String)properties.get("artifact.rap.dirPath");
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(branch);
        rapsource = sb.toString();
    }
    
    private static void getRapArchivePath(){
        String p4prop = "src/main/resources/fileinfo/directories.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        raparchive = (String)properties.get("artifact.archive.rap.output");
    }
    
    private static void getIASourcePath(String branch){
        String p4prop = "src/main/resources/p4v/helix.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        String source = (String)properties.get("artifact.ia.dirPath");
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(branch);
        iasource = sb.toString();
    }
    
    private static void getIAArchivePath(){
        String p4prop = "src/main/resources/fileinfo/directories.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        iaarchive = (String)properties.get("artifact.archive.ia.output");
    }
            
    private static void getExhBSourcePath(int product_name, int deposit_type){
        String p4prop = "src/main/resources/fileinfo/directories.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        switch(product_name){
            case 0: //InfoLease10
                if(deposit_type == 0){ //Digital Deposit
                    exhbsource = (String)properties.get("exhb.digitemplate.il10.dirPath");
                }else{ //Physical Deposit
                    exhbsource = (String)properties.get("exhb.phystemplate.il10.dirPath");
                }
            break;
            case 1: //Rapport
                if(deposit_type == 0){ //Digital Deposit
                    exhbsource = (String)properties.get("exhb.digitemplate.rap.dirPath");
                }else{ //Physical Deposit
                    exhbsource = (String)properties.get("exhb.phystemplate.rap.dirPath");
                }
            break;
            case 2: //InfoAnalysis
                if(deposit_type == 0){ //Digital Deposit
                    exhbsource = (String)properties.get("exhb.digitemplate.ia.dirPath");
                }else{ //Physical Deposit
                    exhbsource = (String)properties.get("exhb.phystemplate.ia.dirPath");
                }
            break;
            case 3: //InfoLease9
                if(deposit_type == 0){ //Digital Deposit
                    exhbsource = (String)properties.get("exhb.digitemplate.il9.dirPath");
                }else{ //Physical Deposit
                    exhbsource = (String)properties.get("exhb.phystemplate.il9.dirPath");
                }
            break;
        }
        
    }
    
    private static void getExhbOutputPath(int product_name){
        String p4prop = "src/main/resources/fileinfo/directories.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        switch(product_name){
            case 0: //InfoLease10
                exhboutput = (String)properties.get("exhb.out.il10.dirPath");
            break;
            case 1: //Rapport
                exhboutput = (String)properties.get("exhb.out.rap.dirPath");
            break;
            case 2: //InfoAnalysis
                exhboutput = (String)properties.get("exhb.out.ia.dirPath");
            break;
            case 3: //InfoLease9
                exhboutput = (String)properties.get("exhb.out.il9.dirPath");
            break;
        }
        
    }
    
    private static void getExhBArchivePath(int product_name){
        String p4prop = "src/main/resources/fileinfo/directories.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(GetFilePaths.class.getName()).log(Level.SEVERE, null, ex);
	}
        switch(product_name){
            case 0:
                exhbarchive = (String)properties.get("exhb.hist.il10.dirPath");
            break;
            case 1:
                exhbarchive = (String)properties.get("exhb.hist.rap.dirPath");
            break;
            case 2:
                exhbarchive = (String)properties.get("exhb.hist.ia.dirPath");
            break;
            case 3:
                exhbarchive = (String)properties.get("exhb.hist.il9.dirPath");
            break;
        }
    }
    
    private static String retrieveIL10SourcePath(String branch){
        GetFilePaths.getIL10SourcePath(branch);
        return il10source;
    }
    
    private static String retrieveIL10ArchivePath(){
        GetFilePaths.getIL10ArchivePath();
        return il10archive;
    }
    
    private static String retrieveRapSourcePath(String branch){
        GetFilePaths.getRapSourcePath(branch);
        return rapsource;
    }
    
    private static String retrieveRapArchivePath(){
        GetFilePaths.getRapArchivePath();
        return raparchive;
    }
    
    private static String retrieveIASourcePath(String branch){
        GetFilePaths.getIASourcePath(branch);
        return iasource;
    }
    
    private static String retrieveIAArchivePath(){
        GetFilePaths.getIAArchivePath();
        return iaarchive;
    }
    
    public static String retrieveIL10EnhancementPath(String branch){
        GetFilePaths.getIL10EnhancePath(branch);
        return enhancement;
    }
        
    public static String setExhBSourcePath(int product_name, int deposit_type){
        GetFilePaths.getExhBSourcePath(product_name, deposit_type);
        return exhbsource;
    }
    
    public static String setExhBArchivePath(int product_name){
        GetFilePaths.getExhBArchivePath(product_name);
        return exhbarchive;
    }
    
    public static String setArtifactSourcePaths(int product_name, String branch){
        String filepath = null;
        switch(product_name){
            case 0: //InfoLease10
                filepath = GetFilePaths.retrieveIL10SourcePath(branch);
                break;
            case 1: //Rapport
                filepath = GetFilePaths.retrieveRapSourcePath(branch);
                break;
            case 2: //InfoAnalysis
                filepath = GetFilePaths.retrieveIASourcePath(branch);
                break;
            case 3: //InfoLease9
                break;
        }
        return filepath;
    }
    
    public static String setArtifactArchivePaths(int product_name){
        String filepath = null;
        switch(product_name){
            case 0: //InfoLease10
                filepath = GetFilePaths.retrieveIL10ArchivePath();
                break;
            case 1: //Rapport
                filepath = GetFilePaths.retrieveRapArchivePath();
                break;
            case 2: //InfoAnalysis
                filepath = GetFilePaths.retrieveIAArchivePath();
                break;
            case 3: //InfoLease9
                filepath = "Not implemented in current release.";
                break;
        }
        return filepath;
    }
    
    public static String setExhBOutputPaths(int product_name){
        GetFilePaths.getExhbOutputPath(product_name);
        return exhboutput;
    }     
}
