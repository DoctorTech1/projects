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
public class EnhancementBundleTest {
        
    public static void main(String[] args){
        String branch = "il.10.6.2";
        GetFilePaths gfp = new GetFilePaths();
        gfp.retrieveIL10EnhancementPath(branch);
    }
}
