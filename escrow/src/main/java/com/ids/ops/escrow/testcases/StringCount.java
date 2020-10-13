/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.interpreter.xlsxparse.ImportDataFormatter;

/**
 *
 * @author paul20
 */
public class StringCount {
    
    public static void main(String[] args){
        String sysNumber = "";
        ImportDataFormatter idf = new ImportDataFormatter();
        idf.setSysNum(sysNumber);
        String system = idf.getSysNum();
        System.out.println(system);
    }
}
