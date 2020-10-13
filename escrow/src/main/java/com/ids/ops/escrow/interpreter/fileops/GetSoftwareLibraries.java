/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.fileops;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author paul20
 */
public class GetSoftwareLibraries {
    private static final String fileprop = "src/main/resources/fileinfo/directories.properties";
    private String deproot;
    private String uniroot;
    private String udpath;
    private String pentroot;
    private String penpath;
    private String reportroot;
    private String reportpath;
    
    /**
     * Gets the path to the Java Software Folder.
     * @return Java software folder path.
     */
    public String getThirdPartyPath(){
        return deproot;
    }
   
    /**
     * Gets the Pentaho Software Folder
     * @return Pentaho software folder path
     */
    public String getPentahoPath(){        
        return penpath;
    }
    
    /**
     * Gets the UniData Software Folder.
     * @return UniData software folder path
     */
    public String getUniDataPath(){        
        return udpath;
    }
    
    /**
     * Gets the IDS Reporting Software Folder
     * @return IDS Reporting software folder path
     */
    public String getReportingPath(){
        return reportpath;
    }
    
    /**
     * Sets the path to the Third Party Library Folder
     */
    public void setThirdPartyPath(){
        File propfile = new File(fileprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
	}
	String path = (String)properties.get("artifact.3party.filePath");
        this.deproot = path;
    }
    
    /**
     * Sets the path to the appropriate OS version of UniData
     * @param operating_system 
     */
    public void setUniDataPath(int operating_system){
        File propfile = new File(fileprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
	}
	uniroot = (String)properties.get("artifact.ud.srcPath");
        String uniwin = (String)properties.get("artifact.ud.win.folderName");
        String uninix = (String)properties.get("artifact.ud.lnx.folderName");
        String unidual = (String)properties.get("artifact.ud.unknown.folderName");        
        int os = operating_system;
        String ur;
        StringBuilder sb = new StringBuilder();
        switch(os){
            default: //OS Not Listed
                sb.append(uniroot).append(unidual);
                ur = sb.toString().trim();
                this.udpath = ur;
            break;
            case 0: //Linux
                sb.append(uniroot).append(uninix);
                ur = sb.toString().trim();
                this.udpath = ur;
            break;
            case 1: //Windows
                sb.append(uniroot).append(uniwin);
                ur = sb.toString().trim();
                this.udpath = ur;
            break;
        }
    }
    
    /**
     * Sets the path to the appropriate OS version of Pentaho.
     * @param operating_system The integer representation of the customer's
     * production OS (Zero: Linux;One: Windows;Unknown: both Linux and Windows)
     */
    public void setPentahoPath(int operating_system){
        File propfile = new File(fileprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
	}
        pentroot = (String)properties.get("artifact.pent.srcPath");
        String nixpent = (String)properties.get("artifact.pent.lnx.folderName");
        String winpent = (String)properties.get("artifact.pent.win.folderName");
        String dualpent = (String)properties.get("artifact.pent.unknown.folderName");
        
        int os = operating_system;
        String path;
        StringBuilder sb = new StringBuilder();
        switch(os){
            default: //No OS Listed
                sb.append(pentroot).append(dualpent);
                path = sb.toString().trim();
                this.penpath = path;
            break;
            case 0: //Linux
                sb.append(pentroot).append(nixpent);
                path = sb.toString().trim();
                this.penpath = path;
            break;
            case 1: //Windows
                sb.append(pentroot).append(winpent);
                path = sb.toString().trim();
                this.penpath = path;
             break;
        }        
    }
    
    public void setReportingPath(){
        File propfile = new File(fileprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
	}
	String path = (String)properties.get("artifact.idsrp.srcPath");
        this.reportpath = path;
    }
}
