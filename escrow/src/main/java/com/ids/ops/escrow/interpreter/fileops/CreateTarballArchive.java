/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.fileops;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author paul20
 */
public class CreateTarballArchive {
    private static final String p4Properties = "src/main/resources/p4v/helix.properties";
    private static final String logOutput = "src/main/resources/messages/logging.properties";
    private static final String fileExtension = ".tar.gz";
    private static String filedetails;
    
    /**
     * Sets the file-path where all console output will be logged.
     * Sets the file and path where all output will be logged.
     * @return Log file fully-qualified path and name.
     */
    private static String getLogFile(){
        File propfile = new File(logOutput);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
	}
	String logfile = (String)properties.get("console.zip.logging.output");
	return logfile; 
    }
    
    /**
     * Generates the name of the artifact that will be sent to the beneficiary.
     * Takes the following inputs and returns the full name of the file that 
     * will be delivered to the escrow beneficiary.
     * @param product_name The numeric representation of the product to be
     * escrowed.
     * @param sysNum The specific customer system number associated with the
     * escrow account.
     * @return The fully-qualified name of the artifact to be escrowed.
     */
    private static String getFinalArtifactName(int product_name,String sysNum){
        String fileName = GetFileNames.setFullArtifactName(product_name, sysNum);
        StringBuilder sb = new StringBuilder();
        sb.append(fileName).append(fileExtension);
        return sb.toString().trim();
    }
    
    
    /**
     * Generates the path to the location of the uncompiled product code.
     * Takes the following inputs and generates the full-path where the 
     * uncompiled source code is to be found.
     * @param product_name The numeric representation of the product to be
     * escrowed.
     * @param branch The most recent Generally-Available (GA) product release
     * @return The full path to the local source code repository.
     */
    private static String getSourcePath(int product_name,String branch){
        String sourceDirectory = GetFilePaths.setArtifactSourcePaths(product_name, branch);
        return sourceDirectory;
    }
    
    /**
     * Generates the path to the location where the compressed file will be
     * locally stored.
     * Takes the following inputs and generates the path where the compressed
     * archive file will be located before being delivered to the beneficiary.
     * @param product_name The numeric representation of the product that is
     * going to be escrowed.
     * @param sysNum The system number of the customer to whom the compressed
     * artifact will be delivered.
     * @return The full path to the location where the file is to be temporarily
     * stored.
     */
    private static String getDestinationPath(int product_name){
        String destinationDirectory = GetFilePaths.setArtifactArchivePaths(product_name);
        return destinationDirectory;
    }
    
    
    /**
     * Generates the path to the location where the customer-specific
     * enhancements are located.
     * Takes the following input and generates the path where the enhancements
     * are located.
     * @param branch The code branch that is associated with the enhancements
     * @return The full-path to the enhancement location.
     */
    private static String getEnhancementBundles(String branch){
        String enhancementPath = GetFilePaths.retrieveIL10EnhancementPath(branch);       
        return enhancementPath;
    }
    
    /**
     * Generates the list of system numbers that have custom code associated
     * with them.
     * Takes the list from the property file and splices it at the comma
     * delimiter.
     * @return The string array of system numbered enhancement bundles.
     */
    private static String[] getEnhancementBundleList(){
        File propfile = new File(p4Properties);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
	}
        String list = (String)properties.get("artifact.il10.enhList");
        String[] enhSysNum = list.split(",");
        return enhSysNum;
    }
    
    /**
     * Gets the folder where the enhancement bundles will be stored.
     */
    private static String getEnhancementTempFolder(){
        File propfile = new File(p4Properties);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
	}
	String tempFolder = (String)properties.get("artifact.il10.tempFolder");
	return tempFolder; 
    }
    
    
    /**
     * Copies the enhancement bundle folder to a temporary location.
     * Takes the following parameters and copies the enhancement bundle folder
     * to a temporary location to separate out individual customer files.
     * @param branch The code branch that is associated with the customer-
     * specific enhancements.
     */
    private static void copyEnhancementBundles(String branch){
        String srcPath = CreateTarballArchive.getEnhancementBundles(branch);
        String destPath = CreateTarballArchive.getEnhancementTempFolder();
        File source = new File(srcPath);
        File dest = new File(destPath);
        try{
            FileUtils.copyDirectory(source,dest);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
        }
    }
    
     /**
     * Copies the enhancement bundle from the temp folder to the main folder.
     * Takes the following parameters and copies the enhancement bundle folder
     * from the temporary location to the main folder.
     * @param branch The code branch that is associated with the customer-
     * specific enhancements.
     */
    private static void copyEnhancementBundlesBack(String branch, String sysNum){
        String destPath = CreateTarballArchive.getEnhancementBundles(branch);     
        String sourcePath = CreateTarballArchive.getEnhancementTempFolder();
        StringBuilder sb = new StringBuilder();
        sb.append(sourcePath).append(sysNum);
        String srcPath = sb.toString().trim();
        System.out.println("Source path has been set to: "+srcPath);
        File source = new File(srcPath);
        File dest = new File(destPath);
        try{
            FileUtils.copyDirectory(source,dest);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
        }
    }
    
    private static void deleteEnhancementFolder(String branch){
        String srcPath = CreateTarballArchive.getEnhancementBundles(branch);
        File source = new File(srcPath);
        try{
            FileUtils.deleteDirectory(source);
        }catch(IOException ex){
            System.out.println("The following exception has been caught: "+ex);
        }
    }
    
    /**
     * Compares the current system number against the list of custom
     * enhancements.
     * Takes the current system number and compares it against the list of
     * custom enhancement code. If it matches, return true; else return false.
     * @param sysNum The current system number that is bring processed.
     * @return The system number match status (TRUE/FALSE).
     */
    private static Boolean getEnhancementStatus(String sysNum){
        Boolean hasCustomCode = false;
        String[] enhancementList = CreateTarballArchive.getEnhancementBundleList();
        for(String s: enhancementList){
            System.out.println("Checking custom code for System Number: "+s);
            if(sysNum.equals(s)){
                hasCustomCode = true;
                System.out.println("Custom code has been detected for System Number: "+s);
            }else{
                System.out.println("No custom code has been found for System Number: "+s);
                hasCustomCode = false;
            }
        }
        return hasCustomCode;
    }
    
    /**
     * Gets the file size of the final compressed artifact.
     * Reads the file size of the final compressed artifact for recording in the
     * Exhibit B file that is delivered with the artifact.
     * @param tarFile The path to the compressed artifact.
     * @return The size of the file in bytes (converted to a string).
     */
    private static String getArtifactFileSize(String tarFile){
        File size = new File(tarFile);
        String sizeInfo = String.valueOf(size.length());
        return sizeInfo;
    }
    /**
     * Adds the appropriate custom code bundles back to the main file path.
     * 
     * @param branch The Generally-Available (GA) code release being escrowed.
     * @param sysNum The system number of the enhancement bundle
     */
    private static void addEnhancementsToPath(String branch,String sysNum){
         /*
        Copy the enhancement bundles from the main folder to the temp folder
        */
        CreateTarballArchive.copyEnhancementBundles(branch);
        System.out.println("Copying global enhancement bundles for: "+branch+" to temp directory");       
        /*
        Deletes the enhancement bundle folder from the main directory
        */
        CreateTarballArchive.deleteEnhancementFolder(branch);
        System.out.println("Deleting old enhancement bundle folder");       
        /*
        Gets the custom code match status. If true, copy enhancements, 
        else continue
        */
        Boolean isMatch = CreateTarballArchive.getEnhancementStatus(sysNum);
        if(isMatch.equals(true)){
            System.out.println("Copying enhancement bundle for System Number "+sysNum+" to artifact directory");
            CreateTarballArchive.copyEnhancementBundlesBack(branch,sysNum);
        } //May need to insert an "else" statement here.
    }
    
    /**
     * Adds all files to the archive file being created.
     * 
     * @param filePath The child files located in the source directory
     * @param parent The parent root directory
     * @param tarArchive The tarball archive being created.
     */
    private static void addFilesToArchive(String filePath,String parent,TarArchiveOutputStream tarArchive){
        /*
        Get the path that all console output will be logged to and set System.out to write to it
        */
        String logPath = CreateTarballArchive.getLogFile();
        try{
            PrintStream ps = new PrintStream(new File(logPath));
            System.setOut(ps); 
        }catch(FileNotFoundException ex){
            System.out.println("The following exception has been caught: "+ex);
        }
        /*
        Adds files to the tarball archive.
        */
        try{           
            File file = new File(filePath);
            String entryName = parent + file.getName();
            tarArchive.putArchiveEntry(new TarArchiveEntry(file, entryName));
            if(file.isFile()){
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                IOUtils.copy(bis,tarArchive);                
                tarArchive.closeArchiveEntry();
                bis.close();
                System.out.println("File - "+file+" has been added to archive");
            }else if(file.isDirectory()){
                tarArchive.closeArchiveEntry();
                System.out.println("File - "+file+" has been added to archive");
                for(File arch : file.listFiles()){                    
                    addFilesToArchive(arch.getAbsolutePath(), entryName+File.separator, tarArchive);
                }
            }
        }catch(IOException ex){
            Logger.getLogger(CCTarArchiveOld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates the compressed archive file of the IDS Product.
     * Takes the following parameters and creates a compressed GZip file from
     * the loose, uncompressed and uncompiled, source code files for one of the
     * IDS Products (InofLease (IL9 & IL10),InfoAnalysis,and/or Rapport).
     * @param product_name The numeric representation of the product that is
     * going to be escrowed.
     * @param branch The GA branch of code that is to be used.
     * @param sysNum The System Number of the customer that will receive the 
     * escrow.
     */
    private static void createIDSTar(int product_name,String branch,String sysNum){
        String srcPath = CreateTarballArchive.getSourcePath(product_name, branch);
        String dstPath = CreateTarballArchive.getDestinationPath(product_name);
        Boolean customCode = CreateTarballArchive.getEnhancementStatus(sysNum);
        String tarName = CreateTarballArchive.getFinalArtifactName(product_name,sysNum);
        TarArchiveOutputStream taos = null;
        
        if(customCode.equals(true)){
            System.out.println("Copying enhancement bundle source-code to temporary location");
            CreateTarballArchive.copyEnhancementBundles(branch);
            System.out.println("Copy to temp location complete. Deleting original folder");
            CreateTarballArchive.deleteEnhancementFolder(branch);
            System.out.println("Folder deletion complete. Copying enhancement bundles for system: "+sysNum+"back to path");
            CreateTarballArchive.addEnhancementsToPath(branch, sysNum);
            StringBuilder sb = new StringBuilder();
            sb.append(dstPath).append(tarName);
            String tarPath = sb.toString().trim();
            try{
                File dest = new File(tarPath);
                FileOutputStream fos = new FileOutputStream(dest.getAbsolutePath());
                GZIPOutputStream gzo = new GZIPOutputStream(new BufferedOutputStream(fos));
                taos = new TarArchiveOutputStream(gzo);
                taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
                CreateTarballArchive.addFilesToArchive(srcPath, "", taos);
            }catch(IOException ex){
                System.out.println("An unhandled exception has been caught: "+ex);
            }finally{
                try{
                    if(taos != null){
                        taos.finish();
                        taos.close();
                    }
                }catch(IOException ie){
                    System.out.println("An unhandled exception has been caught: "+ie);
                }
            }
            String bytes = CreateTarballArchive.getArtifactFileSize(tarPath);
            double result = Double.parseDouble(bytes);
            String filesize = String.format("%,.0f", result);
            sb = new StringBuilder();
            sb.append("Archive - ").append(tarName).append(" has been created with filesize: ").append(filesize);
            filedetails = sb.toString().trim();
        }else{
            StringBuilder sb = new StringBuilder();
            sb.append(dstPath).append(tarName);
            String tarPath = sb.toString().trim();
            try{
                File dest = new File(tarPath);
                FileOutputStream fos = new FileOutputStream(dest.getAbsolutePath());
                GZIPOutputStream gzo = new GZIPOutputStream(new BufferedOutputStream(fos));
                taos = new TarArchiveOutputStream(gzo);
                taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
                CreateTarballArchive.addFilesToArchive(srcPath, "", taos);
            }catch(IOException ex){
                System.out.println("An unhandled exception has been caught: "+ex);
            }finally{
                try{
                    if(taos != null){
                        taos.finish();
                        taos.close();
                    }
                }catch(IOException ie){
                    System.out.println("An unhandled exception has been caught: "+ie);
                }
            }
            String bytes = CreateTarballArchive.getArtifactFileSize(tarPath);
            double result = Double.parseDouble(bytes);
            String filesize = String.format("%,.0f", result);
            sb = new StringBuilder();
            sb.append("Archive - ").append(tarName).append(" has been created with filesize: ").append(filesize);
            filedetails = sb.toString().trim();
        }       
    }
    
    private static void createThirdPartyTAR(){
        
    }
}
