/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.interpreter.fileops.ConvertToZIP;


/**
 *
 * @author paul20
 */
public class TestCompress {
    
    public static void main(String[] args){
        int product_name = 0;
        
        ConvertToZIP compress = new ConvertToZIP();
        compress.compressFiles(product_name);
    }
}
