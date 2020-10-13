/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.interpreter.fileops.GetFilePaths;
import com.ids.ops.escrow.interpreter.fileops.P4Integration;

/**
 *
 * @author paul20
 */
public class P4GenTest {
    
    public static void main(String[] args){
        String branch = "FARap.074";
        String label = "Rap-7.4.0.0-16";
        String p4command;
        String source;
        int product_name = 2;

        P4Integration test = new P4Integration();

        p4command = test.retrieveP4CMDString(branch, label, product_name);
        System.out.println(p4command);
        GetFilePaths getpaths = new GetFilePaths();
        source = getpaths.setArtifactSourcePaths(product_name, branch);
        System.out.println(source);
    }
    
    
    
    
    
}
