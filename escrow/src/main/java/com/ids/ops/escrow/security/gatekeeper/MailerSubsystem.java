/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.gatekeeper;

import java.io.File;
import java.io.FileInputStream; 
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author paul20
 */
public class MailerSubsystem {
   
    public void sendAcctDetailsOnCreation(String fnm, String lnm, String address, String unm, String password){
        /**
         * Load send-mail property file to get the email connection info
         */
        
        String prop = "src/main/resources/sendmail/sendmail.properties";
        File file = new File(prop);
        Properties properties = new Properties();
        
        try{
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }catch(IOException e){
            System.out.println("Unable to find or read the property file: "+e.getMessage());
        }
        String hostname = (String) properties.get("sendmail.hostName");
        String sender = (String) properties.get("sendmail.sentFrom");
        String sendName = (String) properties.get("sendmail.sentFromName");
        int hostport = 25;
        
        /**
         * Create the email message to be sent to the user
         */
        try{
            HtmlEmail email = new HtmlEmail();
            email.setHostName(hostname);
            email.setSmtpPort(hostport);
            email.addTo(address, lnm+", "+fnm);
            email.setFrom(sender, sendName);
            email.setSubject("IDS Operations Account Details For: "+lnm+", "+fnm);
            
            /**
             * Format the HTML Email body for emailing
             */
            email.setHtmlMsg("<html><head></head><body><p style=\"font-family:sans-serif;font-size:14px\"> Dear "+fnm+",</p><p style=\"font-family:sans-serif;font-size:14px\">\n" +
"            A manager and/or superuser has created an account for you in IDS Operations. Your account details have been included in this email for your convenience. <br /><br /><b>Do not share your account with others.</b></p><br /><p style=\"font-family:sans-serif;font-size:18px\">Account Details</p><p><ul style=\"list-style-type:none;font-family:sans-serif;font-size:14px\"><li>Username: "+unm+"</li><li>Password: "+password+"</li><li>E-mail Address: "+address+"</li></ul></p><p style=\"font-family:sans-serif;font-size:14px\">\n" +
"            If any of the above information is incorrect, please contact your manager to have the information corrected.\n" +
"        </p><p style=\"font-family:sans-serif;font-size:14px\">\n" +
"            Regards,<br />\n" +
"            The IDS Operations Team\n" +
"        </p></body></html>");
            email.send();
        }catch(EmailException ex){
            System.out.println("Email send failed."+ex.getMessage());
        }
    }
    
    public void sendPasswordResetEmail(String lastname,String fnm,String password,String email){
        /**
         * Load send-mail property file to get the email connection info
         */
        
        String prop = "src/main/resources/sendmail/sendmail.properties";
        File file = new File(prop);
        Properties properties = new Properties();
        
        try{
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }catch(IOException e){
            System.out.println("Unable to find or read the property file: "+e.getMessage());
        }
        String hostname = (String) properties.get("sendmail.hostName");
        int hostport = 25;
        String sender = (String) properties.get("sendmail.sentFrom");
        String sendName = (String) properties.get("sendmail.sentFromName");
        /**
         * Create the email message to be sent to the user
         */
        try{
            HtmlEmail senddetails = new HtmlEmail();
            senddetails.setHostName(hostname);
            senddetails.setSmtpPort(hostport);
            senddetails.addTo(email, lastname+", "+fnm);
            senddetails.setFrom(sender, sendName);
            senddetails.setSubject("Password Reset Information for: "+lastname+", "+fnm);
            
            /**
             * Format the HTML Email body for emailing
             */
            senddetails.setHtmlMsg("<html><head></head><body><p style=\"font-family:sans-serif;font-size:14px\">"+fnm+",</p><p style=\"font-family:sans-serif;font-size:14px\">You or a manager have requested a password reset for your account in the IDS Operations Application. The new password is included below for your convenience. You will be required to change your password on login.<br /><br /><b>To maintain the security and integrity of this application, never share your account with others.<br />The IDS Operations team will NEVER ask you for your password.</b></p><br /><p style=\"font-family:sans-serif;font-size:18px;text-decoration:underline\">Application Account Details</p><p><ul style=\"list-style-type:none;font-family:sans-serif;font-size:14px\"><li><b>Application Name:</b> IDS Escrow</li><li><b>Application Location:</b> TBD </li><li><b>New Password:</b> "+password+"</li></ul><p style=\"font-family:sans-serif;font-size:14px\">If you or a manager <i>DID NOT</i> request a password reset, please go to the application and request a password reset.</p><p style=\"font-family:sans-serif;font-size:14px\">Regards,<br />The IDS Operations Team</p></body></html>");
            senddetails.send();
        }catch(EmailException ex){
            System.out.println("Email send failed."+ex.getMessage());
        }
    }
    
    public void sendUsernameRecoveryEmail(String firstname,String lastname,String email,String username){
        /**
         * Load send-mail property file to get the email connection info
         */
        
        String prop = "src/main/resources/sendmail/sendmail.properties";
        File file = new File(prop);
        Properties properties = new Properties();
        
        try{
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }catch(IOException e){
            System.out.println("Unable to find or read the property file: "+e.getMessage());
        }
        String hostname = (String)properties.get("sendmail.hostName");
        int hostport = 25;
        String sender = (String)properties.get("sendmail.sentFrom");
        String sendName = (String)properties.get("sendmail.sentFromName");
        
         /**
         * Create the email message to be sent to the user
         */
         try{
            HtmlEmail senddetails = new HtmlEmail();
            senddetails.setHostName(hostname);
            senddetails.setSmtpPort(hostport);
            senddetails.addTo(email, lastname+", "+firstname);
            senddetails.setFrom(sender, sendName);
            senddetails.setSubject("User Account Information for: "+lastname+", "+firstname);
            
            /**
             * Format the HTML Email body for emailing
             */
            senddetails.setHtmlMsg("<html><head></head><body><p style=\"font-family:sans-serif;font-size:14px\">"+firstname+",</p><p style=\"font-family:sans-serif;font-size:14px\">You or a manager requested your username for the IDS Operations Application be sent to you. Below you will find the details of your request.<br /><br /><b>To maintain the security and integrity of this application, never share your account with others.</b></p><br /><p style=\"font-family:sans-serif;font-size:18px;text-decoration:underline\">Application Account Details</p><p><ul style=\"list-style-type:none;font-family:sans-serif;font-size:14px\"><li><b>Application Name:</b> IDS Escrow</li><li><b>Application Location:</b> TBD </li><li><b>Username:</b> "+username+"</li></ul></p><p style=\"font-family:sans-serif;font-size:14px\">If you are unable to remember your password, please submit a password reset request and a new password will be sent to you.</p><p style=\"font-family:sans-serif;font-size:14px\">Regards,<br />The IDS Operations Team</p></body></html>");
            senddetails.send();
         }catch(EmailException ex){
            System.out.println("Email send failed."+ex.getMessage());
        }
    }
}
