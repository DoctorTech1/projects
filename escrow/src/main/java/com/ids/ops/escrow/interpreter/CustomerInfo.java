/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter;

/**
 *
 * @author paul20
 */
public class CustomerInfo {
    private String acct_num;
    private String bus_name;
    private String sys_num;
    private String hq_address;
    private String escrow_type;
    private String deposit_type;
    
    /*
    Gets the data
    */
    public String getAccountNo(){
        return acct_num;
    }
    public String getBusinessName(){
        return bus_name;
    }
    public String getSystemNo(){
        return sys_num;
    }
    public String getBusinessAddress(){
        return hq_address;
    }
    public String getEscrowProducts(){
        return escrow_type;
    }
    public String getDepositType(){
        return deposit_type;
    }
    
    /*
    Sets the data
    */
    public void setAccountNo(String acct_num){
        this.acct_num = acct_num;
    }
    public void setBusinessName(String bus_name){
        this.bus_name = bus_name;
    }
    public void setSystemNo(String sys_num){
        this.sys_num = sys_num;
    }
    public void setBusinessAddress(String hq_address){
        this.hq_address = hq_address;
    }
    public void setEscrowProducts(String escrow_type){
        this.escrow_type = escrow_type;
    }
    public void setDepositType(String deposit_type){
        this.deposit_type = deposit_type;
    }
    
    /*
    Default Constructor
    */
    public CustomerInfo(){
        super();
    }
    
    /*
    Parameterized Constructor
    */
    public CustomerInfo(String acct_num,String bus_name,String sys_num,String hq_address,String escrow_type,String deposit_type){
        super();
        this.acct_num = acct_num;
        this.bus_name = bus_name;
        this.sys_num = sys_num;
        this.hq_address = hq_address;
        this.escrow_type = escrow_type;
        this.deposit_type = deposit_type;
    }
}
