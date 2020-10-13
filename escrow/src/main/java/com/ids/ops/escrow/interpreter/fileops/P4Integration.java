/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.fileops;

import java.io.File;
import java.io.FileInputStream; 
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul20
 */
public class P4Integration {
    private static String p4user;
    private static String p4pwd;
    private static String p4workspace;
    private static String p4command;
    private static String il10Branch;
    private static String iaBranch;    
    private static String rapbranch;
    private static String p4cmdargs;
    
    /**
     * Gets the username of the Escrow P4V user.
     * Reads the property file and assigns the value contained to a string
     * field.
     */
    private static void getP4User(){
	String p4prop = "src/main/resources/p4v/helix.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(P4Integration.class.getName()).log(Level.SEVERE, null, ex);
	}
	p4user = (String)properties.get("p4v.defaults.user");
    }

    /**
     * Gets the password for the Escrow P4V user.
     * Reads the property file and assigns the value contained to a string
     * field.
     */
    private static void getP4Password(){
	String p4prop = "src/main/resources/p4v/helix.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(P4Integration.class.getName()).log(Level.SEVERE, null, ex);
	}
	p4pwd = (String)properties.get("p4v.defaults.pwd");
    }

    /**
     * Gets the workspace path(s) for the Escrow P4V user.
     * Reads the property file and assigned the value contained to a string
     * field.
     */
    private static void getP4Workspace(){
	String p4prop = "src/main/resources/p4v/helix.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(P4Integration.class.getName()).log(Level.SEVERE, null, ex);
	}
	p4workspace = (String)properties.get("p4v.defaults.client");
    }

    /**
     * Gets the current InfoLease 10 branch information.
     * Reads the property file and assigns the value contained to a string
     * field.
     * The output is formatted as follows:
     * //depot/infolease/<shortcode>/...
     */
    private static void getILBranch(){
	String p4prop = "src/main/resources/p4v/helix.properties";
	File propfile = new File(p4prop);
	Properties properties = new Properties();
	try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
	}catch(IOException ex){
		Logger.getLogger(P4Integration.class.getName()).log(Level.SEVERE, null, ex);
	}
	il10Branch = (String)properties.get("p4v.il10.branchPath");
    }
    
    /**
     * Gets the current Rapport branch information.
     * Reads the property file and assigns the value contained to a string
     * field.
     * The output is formatted as follows:
     * //depot/rapport/<shortcode>/...
     */
    private static void getRapBranch(){
        String p4prop = "src/main/resources/p4v/helix.properties";
        File propfile = new File(p4prop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(P4Integration.class.getName()).log(Level.SEVERE, null, ex);
        }
        rapbranch = (String)properties.get("p4v.rap.branchPath");
    }
    
    /**
     * Gets the current InfoAnalysis branch information.
     * Reads the property file and assigns the value contained to a string
     * field.
     * The output is formatted as follows:
     * //depot/InfoAnalysis/<shortcode>/...
     */
    private static void getIABranch(){
        String p4prop = "src/main/resources/p4v/helix.properties";
        File propfile = new File(p4prop);
        Properties properties = new Properties();
        try{
            FileInputStream fis = new FileInputStream(propfile);
            properties.load(fis);
        }catch(IOException ex){
            Logger.getLogger(P4Integration.class.getName()).log(Level.SEVERE, null, ex);
        }
        iaBranch = (String)properties.get("p4v.ia.branchPath");
   } 
    
    /**
     * Generates the P4 "p4 sync" command string that will be used to sync the
     * P4 workspace before each escrow run.
     * Takes the below inputs and generates the specific P4 command string
     * that will be used to perform a workspace sync before each escrow run.
     * @param branch The current, generally available (GA), branch of the 
     * product to be escrowed.
     * @param label The P4 label the GA build was built off of.
     * @param product The product that will be escrowed (represented by an
     * integer ranging from 0 to 2).
     */
    private static void getP4CMDString(String branch, String label, int product_name){
	String user = P4Integration.retrieveP4User();
	String pwd = P4Integration.retrieveP4Pass();
	String wrkspc = P4Integration.retrieveP4Workspace();
	String ilbranch = P4Integration.retrieveIL10Branch();
        String rapmain = P4Integration.retrieveRapMain();
        String iamain = P4Integration.retrieveIAMain();
	
	StringBuilder sb = new StringBuilder();
	switch (product_name){
            case 0: //InfoLease 10 Main
                sb.append("cmd /c").append(" ");
                sb.append("set").append(" ");
                sb.append("P4CLIENT=").append(wrkspc);
                sb.append("&set ");
                sb.append("P4PASSWD=").append(pwd);
                sb.append("&set ");
                sb.append("P4USER=").append(user);
                sb.append("&p4 sync ").append("--parallel threads=32,batch=560 ").append("-f").append(" ");
                sb.append(ilbranch).append(branch).append("/...");
                sb.append("@0,@").append(label);
                p4cmdargs = sb.toString();			
            break;
            case 1: //Rapport Main
                sb.append("cmd /c").append(" ");
                sb.append("set").append(" ");
                sb.append("P4CLIENT=").append(wrkspc);
                sb.append("&set ");
                sb.append("P4PASSWD=").append(pwd);
                sb.append("&set ");
                sb.append("P4USER=").append(user);
                sb.append("&p4 sync ").append("--parallel threads=32,batch=1200 ").append("-f").append(" ");
                sb.append(rapmain).append(branch).append("/...");
                sb.append("@0,@").append(label).append(" ");
                p4cmdargs = sb.toString();
            break;
            case 2: //InfoAnalysis Main
                sb.append("cmd /c").append(" ");
                sb.append("set").append(" ");
                sb.append("P4CLIENT=").append(wrkspc);
                sb.append("&set ");
                sb.append("P4PASSWD=").append(pwd);
                sb.append("&set ");
                sb.append("P4USER=").append(user);
                sb.append("&p4 sync ").append("--parallel threads=32,batch=680 ").append("-f").append(" ");
                sb.append(iamain).append(branch).append("/...");
                sb.append("@0,@").append(label).append(" ");
                p4cmdargs = sb.toString();
            break;
	}
    }
      
    /**
     * Retrieves the P4V Username.
     * Using the method getP4User(), returns the value of the username to a
     * string.
     * @return The P4 username.
     */
    private static String retrieveP4User(){
	P4Integration.getP4User();
	return p4user;
    }
    
    /**
     * Retrieves the P4V user password.
     * Using the method getP4Password(), returns the value of the user-password
     * to a string.
     * @return The P4 user-password.
     */
    private static String retrieveP4Pass(){
	P4Integration.getP4Password();
	return p4pwd;
    }
    
    /**
     * Retrieves the P4 user workspace.
     * Using the method - getP4Workspace(), returns the value of the user
     * workspace to a string.
     * @return The P4 user workspace.
     */
    private static String retrieveP4Workspace(){
	P4Integration.getP4Workspace();
	return p4workspace;
    }
    
    /**
     * Retrieves the InfoLease 10 Branch Prefix.
     * Using the method - getILBranch(), returns the value of the branch prefix
     * to a string.
     * @return The InfoLease 10 Branch Prefix.
     */
    private static String retrieveIL10Branch(){
	P4Integration.getILBranch();
	return il10Branch;
    }
    
    /**
     * Retrieves the Rapport Branch Prefix.
     * Using the method - getRapBranch(), returns the value of the Rapport
     * branch prefix to a string.
     * @return The Rapport branch prefix.
     */
    private static String retrieveRapMain(){
        P4Integration.getRapBranch();
        return rapbranch;
    }
    
    /**
     * Retrieves the InfoAnalysis Branch Prefix.
     * Using the method - getIABranch(), returns the value of the InfoAnalysis
     * branch prefix to a string.
     * @return The InfoAnalysis branch prefix.
     */
    private static String retrieveIAMain(){
        P4Integration.getIABranch();
        return iaBranch;
    }
    
    /**
     * Retrieves the full P4V command string for "p4 sync" (for external use).
     * Using the method - getP4CMDString(branch,label,product), returns the
     * generated value to a string.
     * @param branch The current, Generally Available (GA), branch prefix of
     * the product to be escrowed.
     * @param label The build label used in the creation of the GA Release
     * Candidate binaries.
     * @param product The product that will be escrowed (represented by an
     * integer ranging from 0 to 2).
     * @return The full command string, including the prefix for invoking a
     * command-line run.
     */
    public static String retrieveP4CMDString(String branch, String label, int product){
        P4Integration.getP4CMDString(branch, label, product);
        return p4cmdargs;
    } 
}
