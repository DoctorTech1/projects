/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.fileops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul20
 */
public class LegacyIntegration {
    private static String cpCommand;
    private static String srcPath;
    private static String dstPath;
    private static String output;
    private static final String propfile = "src/main/resources/fileinfo/directories.properties";
    
    /**
     * Generates the complete source path of the legacy program.
     * @return source The path to the current version of the program files
     */
    private static String genProgramSourcePath(int product_name,String progVersion){
        String source = null;
        String srcinfo = null;
        File prop = new File(propfile);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(prop);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(LegacyIntegration.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder sb = new StringBuilder();
        switch(product_name){
            case 0: //Pentaho
                srcinfo = (String)properties.get("artifact.pent.srcPath");
                sb.append(srcinfo).append(progVersion);
                source = sb.toString();
            break;
            case 1: //IDS Reporting
                srcinfo = (String)properties.get("artifact.ud.srcPath");
                sb.append(srcinfo).append(progVersion);
                source = sb.toString();
            break;
            case 2: //UniData
                srcinfo = (String)properties.get("artifact.ud.srcPath");
                sb.append(srcinfo).append(progVersion);
                source = sb.toString();
            break;
        }
        return source;
    }
    
    /**
     * Gets the output path for the legacy programs.
     * 
     * @param product_name The integer representing the product
     * @param progVersion The version of the program to be escrowed
     * @return The full path of the destination
     */
    private static String genProgramDestPath(int product_name,String progVersion){
        String dest = null;
        String destinfo = null;
        File prop = new File(propfile);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(prop);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(LegacyIntegration.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder sb = new StringBuilder();
        switch(product_name){
            case 0: //Pentaho
                destinfo = (String)properties.get("artifact.pent.output");
                sb.append(destinfo).append(progVersion);
                dest = sb.toString();
            break;
            case 1: //IDS Reporting
                destinfo = (String)properties.get("artifact.idsrp.output");
                sb.append(destinfo).append(progVersion);
                dest = sb.toString();                
            break;
            case 2: //UniData
                destinfo = (String)properties.get("artifact.und.output");
                sb.append(destinfo).append(progVersion);
                dest = sb.toString();                
            break;
        }
        return dest;
    }
    
    private static String genArchiveDest(int product_name,String progVersion){
        String dest = null;
        String info = null;
        File prop = new File(propfile);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(prop);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(LegacyIntegration.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder sb = new StringBuilder();
        switch(product_name){
            case 0: //Pentaho
                info = (String)properties.get("artifact.pent.tarOut");
                sb.append(info);
                dest = sb.toString();
            break;
            case 1: //IDS Reporting
                info = (String)properties.get("artifact.idsrp.tarOut");
                sb.append(info);
                dest = sb.toString();
            break;
            case 2: //UniData
                info = (String)properties.get("artifact.und.tarOut");
                sb.append(info);
                dest = sb.toString();
            break;
        }
        return dest;
    }
    
    /**
     * Generates the command that will be used to copy the program files to the
     * destination.
     * @param product_name The integer representing the program
     * @param progVersion The current version of the program to be distributed.
     * @return The copy command.
     */
    private static String genCopyCommand(int product_name,String progVersion){
        String source = LegacyIntegration.genProgramSourcePath(product_name, progVersion);
        String dest = LegacyIntegration.genProgramDestPath(product_name, progVersion);
        String spc = " ";
        
        StringBuilder sb = new StringBuilder();
        sb.append("cmd /c").append(spc);
        sb.append("xcopy /y").append(spc);
        sb.append(source).append(spc);
        sb.append(dest).append("\\*").append(spc);
        sb.append("/O /E /H /K");
        String cpArgs = sb.toString();            
        
        return cpArgs;
    }
    
    public static String retrieveProgSrcPath(int product_name,String progVersion){
        srcPath = LegacyIntegration.genProgramSourcePath(product_name, progVersion);
        return srcPath;
    }
    
    public static String retrieveProgDestPath(int product_name,String progVersion){
        dstPath = LegacyIntegration.genProgramDestPath(product_name, progVersion);
        return dstPath;
    }
    
    public static String getArchiveDest(int product_name,String progVersion){
        output = LegacyIntegration.genArchiveDest(product_name, progVersion);
        return output;
    }
    
    public static String getCopyCommand(int product_name,String progVersion){
        cpCommand = LegacyIntegration.genCopyCommand(product_name, progVersion);
        return cpCommand;
    }
    
    /*public static void main(String[] args) throws FileNotFoundException{
    String progVersion = "8.1";
    int product_name = 0;
    
    String log = CCTarArchive.getLogDir();
    PrintStream ps = new PrintStream(new File(log));
    System.setOut(ps);
    
    srcPath = genProgramSourcePath(product_name,progVersion);
    //System.out.println(srcPath);
    cpCommand = genCopyCommand(product_name, progVersion);
    //System.out.println(cpCommand);
    
    try{
    Process xcopy = Runtime.getRuntime().exec(cpCommand);
    StringBuilder sb = new StringBuilder();
    BufferedReader br = new BufferedReader(new InputStreamReader(xcopy.getInputStream()));
    String line;
    while((line = br.readLine()) != null){
    sb.append("Copying source file - ").append(line+ "\n");
    }
    int exitCode = xcopy.waitFor();
    if(exitCode == 0){
    System.out.println(sb);
    System.out.println("Copy complete!");
    }
    }catch(IOException | InterruptedException ex){
    Logger.getLogger(LegacyIntegration.class.getName()).log(Level.SEVERE, null, ex);
    }
    }*/
}
