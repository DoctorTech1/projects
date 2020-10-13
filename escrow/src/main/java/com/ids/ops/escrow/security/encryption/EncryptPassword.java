/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.encryption;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author paul20
 */
public class EncryptPassword {
    
    private String bcryptHashString = null;
    private Boolean permCheck;
    
    public void hashPassword(String password){
        bcryptHashString = BCrypt.withDefaults().hashToString(14, password.toCharArray());
    }
    public String getBCryptHashString(){
        return bcryptHashString;
    }
    
    public void verifyPassword(String bcryptHashString, String password){
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
        if(result.verified == true){
            permCheck = true;
        }else{
            permCheck = false;
        }
    }
    public Boolean getVerificationStatus(){
        return permCheck;
    }
}
