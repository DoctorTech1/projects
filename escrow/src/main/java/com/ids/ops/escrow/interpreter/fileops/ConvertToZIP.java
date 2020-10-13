/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.fileops;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 *
 * @author paul20
 */
public class ConvertToZIP {
    
    private static String historical_path;
    private static String historical_name;
    private static final String ext = ".zip";
    
    private static String getHistoricalPath(int product_name){
        historical_path = GetFilePaths.setExhBArchivePath(product_name);
        return historical_path;
    }
    
    private static String getHistoricalFileName(){
        historical_name = GetFileNames.setExhBHistName();
        return historical_name;
    }
    
    
    public static void compressFiles(int product_name){
        String zipFile;
        String srcDir = GetFilePaths.setExhBOutputPaths(product_name);
        StringBuilder fqfile = new StringBuilder();
        fqfile.append(ConvertToZIP.getHistoricalPath(product_name)).append(ConvertToZIP.getHistoricalFileName()).append(ext);
        zipFile = fqfile.toString();
        try{
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File dir = new File(srcDir);
            File[] filelist = dir.listFiles();
            for(int i = 0; i < filelist.length; i++){
                FileInputStream fis = new FileInputStream(filelist[i]);
                zos.putNextEntry(new ZipEntry(filelist[i].getName()));
                int length;
                while((length = fis.read(buffer)) > 0){
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();   
        }catch(IOException ex){
            Logger.getLogger(ConvertToZIP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
