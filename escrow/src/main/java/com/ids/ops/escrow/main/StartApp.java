/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.main;

import com.ids.ops.escrow.security.login.UserLogin;

/**
 *
 * @author paul20
 */
public class StartApp {
    public static void main(String args[]){
             try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        new Thread(() -> {
            javax.swing.JFrame start;
            start = new UserLogin();
            start.setLocationRelativeTo(null);
            start.setVisible(true);
            start.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
            start.pack();
        }).start();
    }
}
