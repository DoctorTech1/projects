/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.interpreter.fileops.GetFileNames;
import com.ids.ops.escrow.interpreter.fileops.GetFilePaths;
import com.ids.ops.escrow.interpreter.fileops.LegacyIntegration;

/**
 *
 * @author paul20
 */
public class FileNameTest {

        
    public static void main(String[] args){
        int product_name = 2;
        String progVersion = "ud8.2";
        StringBuilder sb = new StringBuilder();
        
        String filepath = LegacyIntegration.getArchiveDest(product_name, progVersion);
        String filename = GetFileNames.setLegacyArtifactName(product_name, progVersion);
        sb.append(filepath).append(filename);
        String path = sb.toString();
        
        System.out.println(filepath);
        System.out.println(filename);
        System.out.print(path);

    }
}
