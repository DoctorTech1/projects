/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter.xlsxparse;

import com.ids.ops.escrow.security.encryption.EncodePermissions;
import java.util.regex.Pattern;

/**
 *
 * @author paul20
 */
public class ImportDataFormatter {
    private String company;
    private String sysNum;
    private String address;
    private String escrow;
    private String escType;
    private String acctNum;
    private String il9,il10,rap,ia;
    private static final String regex = "([A-Z])|(\\([A-Z]|\\)|(\\[|\\])|(\\s*(\\s|&|\\[\\]|\\s|-)\\s*))";
    private static final String reg = "(\\s+\\([a-z]{3}\\-[0-9]{4}\\))";
    private static final String sysreg = "([A-Z]{2,3}\\s)|([0-9]{0}\\s)"; //Special case for caterpillar
    private static final String sysreg1 = "(\\D.\\D*)";
    
    public String getCmpName(){
        return company;
    }
    public String getSysNum(){
        return sysNum;
    }
    
    public String getEscrow(){
        return escrow;
    }
    
    public String getEscType(){
        return escType;
    }
    
    public String getIL9(){
        return il9;
    }
    
    public String getIL10(){
        return il10;
    }
    
    public String getRap(){
        return rap;
    }
    
    public String getIA(){
        return ia;
    }
    
    public String getAddress(){
        return address;
    }
    
    public String getAcctNum(){
        return acctNum;
    }
    
    public void setAcctNum(String acctNum){
        this.acctNum = acctNum;
    }
    
    public void setCmpName(String company){
        this.company = company;
    }
    
    public void setSysNum(String sysNum){
        String sysn;
        String snn;
        StringBuilder sb = new StringBuilder();
        int len = sysNum.length();
        switch(len){
            case 0: //Empty Cells
                sb.append("None");               
                snn = sb.toString().trim();
                this.sysNum = snn;
            break;
            case 4: 
                this.sysNum = sysNum;
            break;
            case 15: //NS Leasing
                sb.append(sysNum);
                sysn = sb.toString().trim();
                snn = sysn.replaceAll(reg,"");
                this.sysNum = snn;
            break;
            case 17: //Diversified
                sb.append(sysNum);
                sysn = sb.toString().replaceAll(sysreg1,",").trim();
                snn = sysn.replaceAll(",$","");
                this.sysNum = snn;
            break;
            case 24: //Caterpillar
                sb.append(sysNum);
                sysn = sb.toString().trim();
                snn = sysn.replaceAll(sysreg,",");
                this.sysNum = snn;
            break;
            default: //Everything else
                sysn = sysNum.replaceAll(regex,"");
                String[] multi = sysn.split(",");
                for(String s : multi){
                    sb.append(s).append(",");
                }
                String syn = sb.toString().trim();
                String sny = syn.replaceAll(",$", "");
                this.sysNum = sny;
            break;
        }
    }
    
    public void setEscrow(String escrow){
        StringBuilder sb = new StringBuilder();
        if(escrow.equals("Yes")){
            sb.append("1");
            this.escrow = sb.toString();
        }else if(escrow.equals("No")){
            sb.append("0");
            this.escrow = sb.toString();
        }
    }
    
    public void setIL9(String il9){
        StringBuilder sb = new StringBuilder();
        if(il9.equals("Yes")){
            sb.append("1");
            this.il9 = sb.toString();
        }else if(il9.equals("No")){
            sb.append("0");
            this.il9 = sb.toString();
        }
    }
    
    public void setIL10(String il10){
        StringBuilder sb = new StringBuilder();
        if(il10.equals("Yes")){
            sb.append("1");
            this.il10 = sb.toString();
        }else if(il10.equals("No")){
            sb.append("0");
            this.il10 = sb.toString();
        }
    }
    
    public void setRap(String rap){
        StringBuilder sb = new StringBuilder();
        if(rap.equals("Yes")){
            sb.append("1");
            this.rap = sb.toString();
        }else if(rap.equals("No")){
            sb.append("0");
            this.rap = sb.toString();
        }
    }
    
    public void setIA(String ia){
        StringBuilder sb = new StringBuilder();
        if(ia.equals("Yes")){
            sb.append("1");
            this.ia = sb.toString();
        }else if(ia.equals("No")){
            sb.append("0");
            this.ia = sb.toString();
        }
    }
    
    public void setEscrowType(String il9,String il10,String rap,String ia){
        String p1,p2,p3,p4;
        ImportDataFormatter fmt = new ImportDataFormatter();
        fmt.setIL9(il9);
        fmt.setIL10(il10);
        fmt.setRap(rap);
        fmt.setIA(ia);
        p1 = fmt.getIL9();
        p2 = fmt.getIL10();
        p3 = fmt.getRap();
        p4 = fmt.getIA();
        StringBuilder sb = new StringBuilder();
        sb.append(p1).append(p2).append(p3).append(p4);
        String perms = sb.toString();
        
        EncodePermissions ep = new EncodePermissions();
        ep.encodePermissionData(perms);
        String enc = ep.returnEncodedPermissions();
        this.escType = enc;   
    }
    
    public void setAddress(String address){
        StringBuilder sb = new StringBuilder();
        String ufmt = address;
        switch(ufmt.length()){
            case 0:
                sb.append("Unknown");
                String adr = sb.toString().trim();
                this.address = adr;
            break;
            default:
                String rmbreak = ufmt.replace(" $\n", ";").replace(",", "").replace("$ ", ";");
                String[] multi = rmbreak.split(";");
                for(int z = 0; z < multi.length; z++){
                    if(z == 0){
                        sb.append(multi[z]).append("\n");
                    }else if(z > 0 && z < multi.length){
                        String str = multi[z].trim();
                        sb.append(str).append("\n");
                    }else if(z == multi.length){
                        sb.append(multi[z]).trimToSize();
                    }
                }
                this.address = sb.toString().trim();
            break;
        }                  
    }
}
