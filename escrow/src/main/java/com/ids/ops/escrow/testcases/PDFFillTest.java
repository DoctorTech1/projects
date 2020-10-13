/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream; 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
/**
 *
 * @author paul20
 */
public class PDFFillTest {
    //PDF Form field variables
    private static String cmp_nme,acct_num,dep_nme,dep_vsn,file_nme,file_sze,
            file_cnt,folder_cnt,name,date,email,phone,fax,cus_addr,cus_nme,
            enc_yes,enc_no,dep_check,type1,type2,type3,type4;
    private static String pdftemplate,pdfexport;
    
    //Values to be input into the document
    private static String company = "International Decision Systems, Inc";
    private static String account = "31960";
    private static String deposit_artifact = "InfoLease10_6896.zip";
    private static String deposit_version = "InfoLease 10: 10.6.5";
    private static String filename = "InfoLease10";
    private static String filesize = "3158496015";
    private static String filecount = "1";
    private static String foldercount = "1";
    private static String depositor_name = "Paul Flowers";
    private static String depositor_date;
    private static String depositor_email = "pflowers@idsgrp.com";
    private static String depositor_phone = "(612) 851-3363";
    private static String depositor_fax = "N/A";
    private static String benefi_name = "IDS";
    private static String benefi_addr = "220 South 6th St, Suite 700\n"+"Minneapolis, MN 55402";
    
    
    private static void loadPDFProperties(){
        String prop = "src/main/resources/filefolder/fillablepdf.properties";
        File file = new File(prop);
        Properties properties = new Properties();
        
        try{
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }
        catch(IOException e){
            System.out.println("Unable to find or read property file " + e.getMessage());
        }
        cmp_nme=(String)properties.get("beneficiary.name");
        acct_num=(String)properties.get("beneficiary.account");
        dep_nme=(String)properties.get("beneficiary.deposit.name");
        dep_vsn=(String)properties.get("beneficiary.deposit.version");
        cus_nme=(String)properties.get("beneficiary.primary.name");
        cus_addr=(String)properties.get("beneficiary.primary.address");
        file_nme=(String)properties.get("sftp.details.filename");
        file_sze=(String)properties.get("sftp.details.filesize");
        file_cnt=(String)properties.get("sftp.details.filecount");
        folder_cnt=(String)properties.get("sftp.details.foldercount");
        enc_yes=(String)properties.get("general.encryption.yes");
        enc_no=(String)properties.get("general.encryption.no");
        dep_check=(String)properties.get("signatory.self.certification");
        type1=(String)properties.get("mediatypes.cdrom");
        type2=(String)properties.get("mediatypes.usb");
        type3=(String)properties.get("mediatypes.hdd");
        type4=(String)properties.get("mediatypes.sftp");
        name=(String)properties.get("signatory.self.name");
        date=(String)properties.get("signatory.self.date");
        email=(String)properties.get("signatory.self.email");
        phone=(String)properties.get("signatory.self.phone");
        fax=(String)properties.get("signatory.self.fax");
        pdftemplate=(String)properties.get("input.template.test");
        pdfexport=(String)properties.get("output.template.test");              
    }
    
    public static void main(String[] args){
        PDFFillTest.loadPDFProperties();
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd 'at' HH:mm:ss z ('GMT' Z)");
        Date dater = new Date(System.currentTimeMillis());
        depositor_date = formatter.format(dater);
        
        try{
           PDDocument exhbtemplate = PDDocument.load(new File(pdftemplate));
           PDAcroForm exhb = exhbtemplate.getDocumentCatalog().getAcroForm();
           
           //Gets the various bound variables in the PDF document
           PDField comp_name = exhb.getField(cmp_nme);
           PDField comp_acct = exhb.getField(acct_num);
           PDField comp_dep = exhb.getField(dep_nme);
           PDField comp_dep_vsn = exhb.getField(dep_vsn);
           PDField file_name = exhb.getField(file_nme);
           PDField file_size = exhb.getField(file_sze);
           PDField file_count = exhb.getField(file_cnt);
           PDField folder_count = exhb.getField(folder_cnt);
           PDField depos_name = exhb.getField(name);
           PDField depos_date = exhb.getField(date);
           PDField depos_email = exhb.getField(email);
           PDField depos_phone = exhb.getField(phone);
           PDField depos_fax = exhb.getField(fax);
           PDField bene_name = exhb.getField(cus_nme);
           PDField bene_addr = exhb.getField(cus_addr);
           PDField ironmtn = exhb.getField(type4);
           PDField depcheck = exhb.getField(dep_check);
           PDField encryption = exhb.getField(enc_no);
           
           //Sets the various fields in the PDF form
           comp_name.setValue(company);
           comp_acct.setValue(account);
           comp_dep.setValue(deposit_artifact);
           comp_dep_vsn.setValue(deposit_version);
           ((PDCheckBox) ironmtn).check();
           ((PDCheckBox) depcheck).check();
           ((PDCheckBox) encryption).check();
           file_name.setValue(filename);
           file_size.setValue(filesize);
           file_count.setValue(filecount);
           folder_count.setValue(foldercount);
           depos_name.setValue(depositor_name);
           depos_date.setValue(depositor_date);
           depos_email.setValue(depositor_email);
           depos_phone.setValue(depositor_phone);
           depos_fax.setValue(depositor_fax);
           bene_name.setValue(benefi_name);
           bene_addr.setValue(benefi_addr);
           
           //Saves the Document and closes the file-stream
           exhbtemplate.save(pdfexport);
           exhbtemplate.close();
        }catch(InvalidPasswordException ipe){
            ipe.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
    }
    
}