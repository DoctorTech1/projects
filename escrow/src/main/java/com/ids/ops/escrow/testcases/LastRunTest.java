/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.testcases;

import com.ids.ops.escrow.interpreter.fileops.LastDBUpdate;
import java.text.SimpleDateFormat;

/**
 *
 * @author paul20
 */
public class LastRunTest {
    
    public static void main(String[] args){
        Boolean isCurrent = LastDBUpdate.compareLastRun();
        if(isCurrent.equals(true)){
            System.out.println("Customer data refresh is not necessary. Last update was on:\n"+LastDBUpdate.parseLastRun());
        }else if(isCurrent.equals(false)){
            System.out.println("Customer data refresh is needed. Last update was on:\n"+LastDBUpdate.parseLastRun());    
            //LastDBUpdate.clearLastRun();
            //LastDBUpdate.writeLastRun();
            System.out.println("Customer data has been imported. Date of import has been changed to:\n"+LastDBUpdate.parseLastRun());
            
        }
    }
}
