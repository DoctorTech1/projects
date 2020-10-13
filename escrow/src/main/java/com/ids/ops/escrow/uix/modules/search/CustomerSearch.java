/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.uix.modules.search;

/**
 *
 * @author paul20
 */
public class CustomerSearch{
    
    /**
     * This method generates the search SQL query
     * 
     * @param search The index value from the "Search By" JComboBox
     * @param comp The index value from the "Comparator" JComboBox
     * @return query The MS SQL Query that will be executed.
     */
    private static String genBasicString(int search, int comp){
        String qprep = null;
        
        if(((search == 0) && (comp == 0)) || ((search == 0) && (comp == 1)) || ((search == 0) && (comp == 2))){
            qprep = "SELECT * FROM escrw_cust_dta WHERE cst_cmp_nme LIKE ?";
        }else if((search == 0) && (comp == 3)){
            qprep = "SELECT * FROM escrw_cust_dta WHERE cst_cmp_nme = ?";
        }else if(((search == 1) && (comp == 0)) || ((search == 1) && (comp == 1)) || ((search == 1) && (comp == 2))){
            qprep = "SELECT * FROM escrw_cust_dta WHERE cst_acct_num LIKE ?";
        }else if((search == 1) && (comp == 3)){
            qprep = "SELECT * FROM escrw_cust_dta WHERE cst_acct_num = ?";
        }else if(((search == 2) && (comp == 0)) || ((search == 2) && (comp == 1)) || ((search == 2) && (comp == 2))){
            qprep = "SELECT * FROM escrw_cust_dta WHERE cst_sys_num LIKE ?";
        }else if((search == 2) && (comp == 3)){
            qprep = "SELECT * FROM escrw_cust_dta WHERE cst_sys_num = ?";
        }
        return qprep; 
    }
    
    private static String genSQLCount(int search, int comp){
        String count = null;
        
        if(((search == 0) && (comp == 0)) || ((search == 0) && (comp == 1)) || ((search == 0) && (comp == 2))){
            count = "SELECT COUNT(ALL cst_cmp_nme) FROM escrw_cust_dta WHERE cst_cmp_nme LIKE ?";
        }else if((search == 0) && (comp == 3)){
            count = "SELECT COUNT(DISTINCT cst_cmp_nme) FROM escrw_cust_dta WHERE cst_cmp_nme = ?";
        }else if(((search == 1) && (comp == 0)) || ((search == 1) && (comp == 1)) || ((search == 1) && (comp == 2))){
            count = "SELECT COUNT(ALL cst_acct_num) FROM escrw_cust_dta WHERE cst_acct_num LIKE ?";
        }else if((search == 1) && (comp == 3)){
            count = "SELECT COUNT(DISTINCT cst_acct_num) FROM escrw_cust_dta WHERE cst_acct_num = ?";
        }else if(((search == 2) && (comp == 0)) || ((search == 2) && (comp == 1)) || ((search == 2) && (comp == 2))){
            count = "SELECT COUNT(ALL cst_sys_num) FROM escrw_cust_dta WHERE cst_sys_num LIKE ?";
        }else if((search == 2) && (comp == 3)){
            count="SELECT COUNT(DISTINCT cst_sys_num) FROM escrw_cust_dta WHERE cst_sys_num = ?";
        }       
        return count; 
    }
    

    private static String genSQLRowCount(int search, int comp){
        String count = null;
        
        if(((search == 0) && (comp == 0)) || ((search == 0) && (comp == 1)) || ((search == 0) && (comp == 2))){
            count = "SELECT COUNT(*) FROM escrw_cust_dta WHERE cst_cmp_nme LIKE ?";
        }else if((search == 0) && (comp == 3)){
            count = "SELECT COUNT(*) FROM escrw_cust_dta WHERE cst_cmp_nme = ?";
        }else if(((search == 1) && (comp == 0)) || ((search == 1) && (comp == 1)) || ((search == 1) && (comp == 2))){
            count = "SELECT COUNT(*) FROM escrw_cust_dta WHERE cst_acct_num LIKE ?";
        }else if((search == 1) && (comp == 3)){
            count = "SELECT COUNT(*) FROM escrw_cust_dta WHERE cst_acct_num = ?";
        }else if(((search == 2) && (comp == 0)) || ((search == 2) && (comp == 1)) || ((search == 2) && (comp == 2))){
            count = "SELECT COUNT(*) FROM escrw_cust_dta WHERE cst_sys_num LIKE ?";
        }else if((search == 2) && (comp == 3)){
            count="SELECT COUNT(*) FROM escrw_cust_dta WHERE cst_sys_num = ?";
        }       
        return count; 
    }
    /**
     * Publicly accessible method to get the basic SQL Query.
     * @param search 
     * @param comp
     * @return 
     */
    public static String getBasicSQLString(int search, int comp){
        String prep = CustomerSearch.genBasicString(search,comp);       
        return prep;
    }
    
    public static String getSQLCountQuery(int search, int comp){
        String rowcount = CustomerSearch.genSQLCount(search, comp);
        return rowcount;
    }
    
    public static String getSQLCount(int search, int comp){
        String rowcount = CustomerSearch.genSQLCount(search, comp);
        return rowcount;
    }
    
    public static String getSQLRowCount(int search, int comp){
        String rowcount = CustomerSearch.genSQLRowCount(search,comp);
        return rowcount;
    } 
}
