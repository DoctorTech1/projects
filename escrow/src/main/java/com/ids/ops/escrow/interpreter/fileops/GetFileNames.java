/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.fileops;

import java.io.File;
import java.io.FileInputStream; 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author paul20
 */
public class GetFileNames {
    private static String datetime;
    private static String fileprefix;
    private static final String fileprop = "src/main/resources/fileinfo/directories.properties";
    private static String exhfullname;
    private static String artfullname;
    private static String artfilename;
    private static String exhbhistname;
    private static String legartname;
    private static String enhanceBundle;
    
    
    private static String getExhBFileName(int product_name){
        String filename = null;
        File propfile = new File(fileprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
                Logger.getLogger(GetFileNames.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        switch(product_name){
            case 0: //IL10
                filename = (String)properties.get("exhb.out.il10.fileName");
            break;
            case 1: //Rap 
                filename = (String)properties.get("exhb.out.rap.fileName");
            break;
            case 2: //IA
                filename = (String)properties.get("exhb.out.ia.fileName");
            break;
            case 3: //IL9
                filename = (String)properties.get("exhb.out.il9.fileName");
            break;   
        }
        return filename;
    }
    
    private static String getExhBHistName(){
        String filename = null;
        File propfile = new File(fileprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
                Logger.getLogger(GetFileNames.class.getName()).log(Level.SEVERE, null, ex);
        }
        filename = (String)properties.get("exhb.hist.filePrefix");
        return filename;
    }
    
    private static String setExhBFilePrefix(int product_name){
        fileprefix = GetFileNames.getExhBFileName(product_name);
        return fileprefix;
    }
    
    private static String getArtifactFileName(int product_name){
        String filename = null;
        File propfile = new File(fileprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
                Logger.getLogger(GetFileNames.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch(product_name){
            case 0: //IL10
                filename = (String)properties.get("artifact.archive.il10.filename");
            break;
            case 1: //Rap 
                filename = (String)properties.get("artifact.archive.rap.filename");
            break;
            case 2: //IA
                filename = (String)properties.get("artifact.archive.ia.filename");
            break;
            case 3: //IL9
                filename = (String)properties.get("artifact.archive.il9.filename");
            break;   
        }
        return filename;
    }
    
    private static String getEnhanceBundleName(String branch,String sysnumber){
        String test = "";
        return null;
    }
    
    private static String getCurrentDateAndTime(){
        SimpleDateFormat dateformat = new SimpleDateFormat("YYMMdd'-'HHmm");
        Date date = new Date(System.currentTimeMillis());
        String suffix = dateformat.format(date);        
        return suffix;
    }
    
    private static String setCurrentDateAndTime(){
        datetime = GetFileNames.getCurrentDateAndTime();
        return datetime;
    }
    
    private static String getFullExhBName(int product_name, String sysnum){
        StringBuilder sb = new StringBuilder();
        fileprefix = GetFileNames.setExhBFilePrefix(product_name);       
        String name;
        sb.append(fileprefix).append(sysnum);
        name = sb.toString();        
        return name;
    }
    
    private static String getLegacyArtifactName(int product_name,String progVersion){
        String filename = null;
        String tempname = null;
        File prop = new File(fileprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(prop);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(GetFileNames.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder sb = new StringBuilder();
        switch(product_name){
            case 0: //Pentaho
                tempname = (String)properties.get("artifact.archive.pent.filename");
                sb.append(tempname).append(progVersion).append(".tar.gz");
                filename = sb.toString();
                break;
            case 1: //IDS Reporting
                tempname = (String)properties.get("artifact.archive.idsrp.filename");
                sb.append(tempname).append(progVersion).append(".tar.gz");
                filename = sb.toString();
                break;
            case 2: //UniData
                tempname = (String)properties.get("artifact.archive.und.filename");
                sb.append(tempname).append(progVersion).append(".tar.gz");
                filename = sb.toString();                
                break;
        }
        return filename;
    }
    
    public static String setLegacyArtifactName(int product_name,String progVersion){
        legartname = GetFileNames.getLegacyArtifactName(product_name, progVersion);
        return legartname;
    }
    
    public static String setFullExhBName(int product_name, String sysnum){
        exhfullname = GetFileNames.getFullExhBName(product_name, sysnum);
        return exhfullname;
    }
    
    private static String getFullArtifactName(int product_name, String sysnum){
        String artname;
        StringBuilder sb = new StringBuilder();
        artfilename = GetFileNames.getArtifactFileName(product_name);
        sb.append(artfilename).append(sysnum);
        artname = sb.toString();
        return artname;
    }
    
    public static String setFullArtifactName(int product_name, String sysnum){
        artfullname = GetFileNames.getFullArtifactName(product_name, sysnum);
        return artfullname;
    }
    
    public static String setExhBHistName(){       
        String prefix = GetFileNames.getExhBHistName();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(GetFileNames.setCurrentDateAndTime());

        exhbhistname = sb.toString();
        return exhbhistname;
    }
    
}
