/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;
import com.ids.ops.escrow.security.gatekeeper.MailerSubsystem;
/**
 *
 * @author paul20
 */
public class sendmailtest {
    
    public static void main(String[] args){
        String firstname = "Paul";
        String lastname = "Flowers";
        String email = "pflowers@idsgrp.com";
        String username = "pflowers";
    
        MailerSubsystem sendmail = new MailerSubsystem();
        sendmail.sendUsernameRecoveryEmail(firstname, lastname, email, username);
    }
}
