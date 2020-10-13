/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.fileops;


import com.ids.ops.escrow.testcases.TarballTest;
import java.io.*;
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
public class CCTarArchiveOld {
  
    private static final String p4prop = "src/main/resources/p4v/helix.properties";
    private static final String logprop = "src/main/resources/fileinfo/directories.properties";
    private static String details;
    
    public static String getLogDir(){
        File propfile = new File(logprop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(CCTarArchiveOld.class.getName()).log(Level.SEVERE, null, ex);
	}
	String logfile = (String)properties.get("logger.outFile");
	return logfile;
    }
    
    private static String getSourcePath(int product_name, String branch){
        String sourceDir = GetFilePaths.setArtifactSourcePaths(product_name,branch);        
        return sourceDir;
    }
    
    private static String getDestPath(int product_name){
        String destDir = GetFilePaths.setArtifactArchivePaths(product_name);
        return destDir;
    }
    
    private static String getArtifactFileName(int product_name, String sysnum){
        String fileName = GetFileNames.setFullArtifactName(product_name, sysnum);
        String ext = ".tar.gz";
        StringBuilder sb = new StringBuilder();        
        sb.append(fileName).append(ext);
        
        return sb.toString();
    }
    
    private static String getEnhancementBundles(String branch, String sysnum){
        GetFilePaths gfp = new GetFilePaths();
        return null;
    }
        
    private static String getFileSize(String tarfile){
        File size = new File(tarfile);
        String info = String.valueOf(size.length());
        return info;
    }
    
    private static void createTarGZ(int product_name, String branch, String sysnum){	
	String srcPath = getSourcePath(product_name,branch);
	String destPath = getDestPath(product_name);
	String tarName = getArtifactFileName(product_name, sysnum);
	TarArchiveOutputStream taos = null;
	
	StringBuilder sb = new StringBuilder();
	sb.append(destPath).append(tarName);
	String tarPath = sb.toString();
	
	try{
            File dest = new File(tarPath);
            FileOutputStream fos = new FileOutputStream(dest.getAbsolutePath());
            GZIPOutputStream gzo = new GZIPOutputStream(new BufferedOutputStream(fos));
            taos = new TarArchiveOutputStream(gzo);
            taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            addFilesToArchive(srcPath, "", taos);
	}catch(IOException ex){
            Logger.getLogger(CCTarArchiveOld.class.getName()).log(Level.SEVERE, null, ex);
	}finally{
            try{
                if(taos != null){
                    taos.finish();
                    taos.close();
                }               
            }catch(IOException e){
                Logger.getLogger(TarballTest.class.getName()).log(Level.SEVERE, null, e);
            }
	}
        String bytes = CCTarArchiveOld.getFileSize(tarPath);
        double result = Double.parseDouble(bytes);
        String filesize = String.format("%,.0f",result);
        StringBuilder sb1 = new StringBuilder();
        sb1.append("Archive - ").append(tarName).append(" has been created with filesize: ").append(filesize);
        details = sb1.toString();
    }
    
    private static void createLegacyTarGZ(int product_name,String progVersion){
        String srcPath = LegacyIntegration.retrieveProgDestPath(product_name, progVersion);
        String dstPath = LegacyIntegration.getArchiveDest(product_name,progVersion);
        String tarName = GetFileNames.setLegacyArtifactName(product_name, progVersion);
        String tarPath;
        TarArchiveOutputStream taos = null;
        StringBuilder sb = new StringBuilder();
        sb.append(dstPath).append(tarName);
        tarPath = sb.toString();
        System.out.println("Creating TAR Archive - "+tarPath);
        
        try{
            File dest = new File(tarPath);
            FileOutputStream fos = new FileOutputStream(dest.getAbsolutePath());
            GZIPOutputStream gzo = new GZIPOutputStream(new BufferedOutputStream(fos));
            taos = new TarArchiveOutputStream(gzo);
            taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            addFilesToArchive(srcPath, "", taos);
        }catch(IOException ex){
            Logger.getLogger(CCTarArchiveOld.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                if(taos != null){
                    taos.finish();
                    taos.close();
                }               
            }catch(IOException e){
                Logger.getLogger(TarballTest.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        String bytes = CCTarArchiveOld.getFileSize(tarPath);
        double result = Double.parseDouble(bytes);
        String filesize = String.format("%,.0f",result);
        StringBuilder sb1 = new StringBuilder();
        sb1.append("Archive - ").append(tarName).append(" has been created with filesize: ").append(filesize);
        details = sb1.toString();
    }

    private static void addFilesToArchive(String filePath, String parent, TarArchiveOutputStream tarArchive){      
        String log = CCTarArchiveOld.getLogDir();
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
           
    /*public static void main(String[] args) throws FileNotFoundException{
    int product_name = 0;
    String progVersion = "8.1";
    
    String log = CCTarArchive.getLogDir();
    PrintStream ps = new PrintStream(new File(log));
    System.setOut(ps);
    
    CCTarArchive.createLegacyTarGZ(product_name,progVersion);
    System.out.println(details+"\n\n");
    }*/
}
