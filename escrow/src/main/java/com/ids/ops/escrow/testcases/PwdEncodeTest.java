/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.security.encryption.EncryptPassword;
import com.ids.ops.escrow.security.encryption.GenerateRandom;
import javax.swing.JOptionPane;

/**
 *
 * @author paul20
 */
public class PwdEncodeTest {
    
    public static void main(String[] args){
        
        String bcryptHashString;
        Boolean permCheck;
        int len = 16;
        
        GenerateRandom passgen = new GenerateRandom();
        String password = passgen.getNewPassword(len);
        javax.swing.JOptionPane.showMessageDialog(null,"Generated Password is:\n\n"+password,"Password",JOptionPane.INFORMATION_MESSAGE);
        
        EncryptPassword encrypt = new EncryptPassword();
        
        encrypt.hashPassword(password);
        bcryptHashString = encrypt.getBCryptHashString(); 
        System.out.println("Hashed Password:\n\n"+bcryptHashString);      
        encrypt.verifyPassword(bcryptHashString, password);
        
        permCheck = encrypt.getVerificationStatus();
        
        System.out.println(permCheck);
    }
}
