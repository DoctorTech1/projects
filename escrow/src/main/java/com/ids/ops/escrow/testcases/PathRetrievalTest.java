/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.interpreter.fileops.GetFilePaths;

/**
 *
 * @author paul20
 */
public class PathRetrievalTest {
    
    public static void main(String[] args){
        int product_name = 2;
        String branch = "FARap.074";
        String source;
        
        GetFilePaths getpath = new GetFilePaths();
        source = getpath.setArtifactSourcePaths(product_name, branch);
        System.out.println(source);
        
    }
    
}
