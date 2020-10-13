/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;



import com.ids.ops.escrow.security.connections.MSSQLConnector;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *
 * @author paul20
 */
public class QueryGeneratorTest {    
    
    public static void main(String args[]){
        ResultSet resultcache = null;
        String acct = null;
        String name = null;
        String sysnum = null;
        String address = null;
        String esctype = null;
        String deptype = null;
        String value = "I";
        PreparedStatement ps;
        String query = "SELECT * FROM escrw_cust_dta WHERE cst_cmp_nme LIKE ?";
        
        try{
            ps = MSSQLConnector.getConnection().prepareStatement(query);
            ps.setString(1,value+"%");
            resultcache = ps.executeQuery();
            resultcache.beforeFirst();
            while(resultcache.next()){
                acct = resultcache.getString("cst_acct_num");
                name = resultcache.getString("cst_cmp_nme");
                sysnum = resultcache.getString("cst_sys_num");
                address = resultcache.getString("cst_bus_adr");
                esctype = resultcache.getString("cst_esc_typ");
                deptype = resultcache.getString("cst_dep_typ");
            }
        }catch(SQLException | ClassNotFoundException ex){
            java.util.logging.Logger.getLogger(QueryGeneratorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(acct+" "+name+" "+sysnum+" "+address+" "+esctype+" "+deptype);
    }
}