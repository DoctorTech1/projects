/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 *
 * @author paul20
 */
public class TarballTest {
    private static final String p4prop = "src/main/resources/p4v/helix.properties";
    private static final String logprop = "src/main/resources/fileinfo/directories.properties";
    private static final int product_name = 0;
    private static final String branch = "il.10.6.2";
    private static final String sysnum = "6896";
    
    private static String getLogDir(){
        File propfile = new File(logprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(TarballTest.class.getName()).log(Level.SEVERE, null, ex);
	}
	String logfile = (String)properties.get("logger.outFile");
	return logfile;
    }
    private static String getTempDirectory(){
        File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(TarballTest.class.getName()).log(Level.SEVERE, null, ex);
	}
	String work = (String)properties.get("artifact.working.tempDir");
	return work;
    }
    
    
	   
    public static void main(String[] args){
 
    }
}
