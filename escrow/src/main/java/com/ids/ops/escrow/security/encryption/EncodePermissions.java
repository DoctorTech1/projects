/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.encryption;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author paul20
 */
public class EncodePermissions {
    
    /**
     * Initialize null strings to hold our data
     */
    String hexString = null;
    String decString = null;
    Boolean permCheck;
    
    /**
     * Method to encode the permission string
     * @param permissions The string of plain-text permission data.
     */
    public void encodePermissionData(String permissions){
        try{
            hexString = Hex.encodeHexString(permissions.getBytes("UTF-8"));
        }catch(UnsupportedEncodingException ex){
            Logger.getLogger(EncodePermissions.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    /**
     * API to access the encoded permission string
     * @return hexString The encoded permission string
     */
    public String returnEncodedPermissions(){       
        return hexString;
    }
    
    /**
     * Method to decode the permission string
     * @param hexString The encoded permission string to decrypt.
     * Takes the encoded permission byte-string and converts it to a human-
     * readable byte-string. The resulting byte-string is then converted from
     * to a UTF-8 encoded plan-text string.
     * Converts the CharArray to a UTF-8 
     */
    public void decodePermissionData(String hexString){
        byte[] encBytes = null;
        try {
            encBytes = Hex.decodeHex(hexString.toCharArray());
            decString = new String(encBytes,StandardCharsets.UTF_8);
        } catch (DecoderException ex) {
            Logger.getLogger(EncodePermissions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * API to access the decoded permission string
     * @return decString The decoded permission string.
     */
    public String returnDecodedPermissions(){
        return decString;
    }
    
   /**
    * Method to compare the decoded string and the original string
    * @param permissions The original, un-encoded, permission string for
    * comparison
    * @param decString The decrypted permission string that is being compared
    * The method throws a JOptionPane error if permissions do not match and
    * an information message if they do. The JOptionPane displays both the
    * original string and the decoded string.
    */
    public void comparePermissionData(String permissions, String decString){
        if(!decString.equals(permissions)){
            permCheck = false;
        }else{
            permCheck = true;
        }
    }
    public Boolean getComparisonData(){
        return permCheck;
    }
}
