/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.security.encryption.EncodePermissions;

/**
 *
 * @author paul20
 */
public class querytest extends Thread {
    
    public static void main(String args[]){
        String permissions = "1111";
        EncodePermissions crypto = new EncodePermissions();
        crypto.encodePermissionData(permissions);
        String perms = crypto.returnEncodedPermissions();
        
        System.out.println("Customer Permissions are "+perms);
    }
    

}
