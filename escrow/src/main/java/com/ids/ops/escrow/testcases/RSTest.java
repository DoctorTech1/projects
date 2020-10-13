/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.security.connections.DatabaseOperations;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author paul20
 */
public class RSTest {

    public static void main(String args[]){
        DatabaseOperations ops = new DatabaseOperations();
        ArrayList<String> columns = ops.getCustomerColumns();
        String[] headers = new String[columns.size()];
        columns.toArray(headers);
        System.out.println(Arrays.toString(headers));
        
    }
}
