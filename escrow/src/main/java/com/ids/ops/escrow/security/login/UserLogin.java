/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.login;

import com.ids.ops.escrow.security.connections.DatabaseOperations;
import java.awt.event.ItemEvent;

/**
 *
 * @author paul20
 */
public class UserLogin extends javax.swing.JFrame {

    /**
     * Creates new form UserLogin
     */
    public UserLogin() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        innerpane = new javax.swing.JPanel();
        headercontent = new javax.swing.JPanel();
        headerlogo = new javax.swing.JLabel();
        headertitle = new javax.swing.JLabel();
        contentpane = new javax.swing.JPanel();
        returntitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        usernamelabel = new javax.swing.JLabel();
        usrnamefield = new javax.swing.JTextField();
        passwordlabel = new javax.swing.JLabel();
        pwdfield = new javax.swing.JPasswordField();
        loginbutton = new javax.swing.JButton();
        clearentry = new javax.swing.JButton();
        forgotpwd = new javax.swing.JButton();
        forgotusrname = new javax.swing.JButton();
        appwarntitle = new javax.swing.JLabel();
        appwarn = new javax.swing.JScrollPane();
        appwarn_content = new javax.swing.JTextArea();
        agreement = new javax.swing.JCheckBox();
        footercontent = new javax.swing.JPanel();
        helpmenu = new javax.swing.JButton();
        statusbar = new javax.swing.JProgressBar();
        statlabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IDS Operations: Login");
        setBackground(new java.awt.Color(229, 234, 237));
        setMaximumSize(new java.awt.Dimension(600, 400));
        setMinimumSize(new java.awt.Dimension(600, 400));
        setName("Login"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        innerpane.setBackground(new java.awt.Color(229, 234, 237));
        innerpane.setMaximumSize(new java.awt.Dimension(600, 400));
        innerpane.setMinimumSize(new java.awt.Dimension(600, 400));
        innerpane.setName(""); // NOI18N

        headercontent.setBackground(new java.awt.Color(229, 234, 237));
        headercontent.setMaximumSize(new java.awt.Dimension(600, 65));
        headercontent.setMinimumSize(new java.awt.Dimension(600, 65));
        headercontent.setPreferredSize(new java.awt.Dimension(600, 65));

        headerlogo.setBackground(new java.awt.Color(229, 234, 237));
        headerlogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerlogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/IDSColor.jpg"))); // NOI18N
        headerlogo.setMaximumSize(new java.awt.Dimension(240, 54));
        headerlogo.setMinimumSize(new java.awt.Dimension(240, 54));
        headerlogo.setPreferredSize(new java.awt.Dimension(240, 54));
        headerlogo.setRequestFocusEnabled(false);

        headertitle.setBackground(new java.awt.Color(229, 234, 237));
        headertitle.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        headertitle.setForeground(new java.awt.Color(121, 123, 128));
        headertitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headertitle.setText("IDS Operations: Login");
        headertitle.setMaximumSize(new java.awt.Dimension(350, 65));
        headertitle.setMinimumSize(new java.awt.Dimension(350, 65));
        headertitle.setPreferredSize(new java.awt.Dimension(350, 65));

        javax.swing.GroupLayout headercontentLayout = new javax.swing.GroupLayout(headercontent);
        headercontent.setLayout(headercontentLayout);
        headercontentLayout.setHorizontalGroup(
            headercontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headercontentLayout.createSequentialGroup()
                .addComponent(headertitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(headerlogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headercontentLayout.setVerticalGroup(
            headercontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headercontentLayout.createSequentialGroup()
                .addGroup(headercontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(headerlogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(headertitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contentpane.setBackground(new java.awt.Color(229, 234, 237));
        contentpane.setMaximumSize(new java.awt.Dimension(600, 248));
        contentpane.setMinimumSize(new java.awt.Dimension(600, 248));
        contentpane.setPreferredSize(new java.awt.Dimension(600, 248));

        returntitle.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        returntitle.setForeground(new java.awt.Color(121, 123, 128));
        returntitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        returntitle.setText("Returning User Login");
        returntitle.setMaximumSize(new java.awt.Dimension(267, 38));
        returntitle.setMinimumSize(new java.awt.Dimension(267, 38));
        returntitle.setPreferredSize(new java.awt.Dimension(267, 38));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setMaximumSize(new java.awt.Dimension(15, 226));
        jSeparator1.setMinimumSize(new java.awt.Dimension(15, 226));
        jSeparator1.setPreferredSize(new java.awt.Dimension(15, 226));

        usernamelabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        usernamelabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        usernamelabel.setText("Username:");
        usernamelabel.setMaximumSize(new java.awt.Dimension(100, 30));
        usernamelabel.setMinimumSize(new java.awt.Dimension(100, 30));
        usernamelabel.setPreferredSize(new java.awt.Dimension(100, 30));

        usrnamefield.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        usrnamefield.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        usrnamefield.setMaximumSize(new java.awt.Dimension(149, 30));
        usrnamefield.setMinimumSize(new java.awt.Dimension(149, 30));
        usrnamefield.setPreferredSize(new java.awt.Dimension(149, 30));
        usrnamefield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usrnamefieldFocusGained(evt);
            }
        });

        passwordlabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        passwordlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        passwordlabel.setText("Password:");
        passwordlabel.setMaximumSize(new java.awt.Dimension(100, 30));
        passwordlabel.setMinimumSize(new java.awt.Dimension(100, 30));
        passwordlabel.setPreferredSize(new java.awt.Dimension(100, 30));

        pwdfield.setToolTipText("Enter the password provided to you");
        pwdfield.setMaximumSize(new java.awt.Dimension(149, 30));
        pwdfield.setMinimumSize(new java.awt.Dimension(149, 30));
        pwdfield.setPreferredSize(new java.awt.Dimension(149, 30));

        loginbutton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        loginbutton.setText("Login");
        loginbutton.setEnabled(false);
        loginbutton.setMaximumSize(new java.awt.Dimension(90, 30));
        loginbutton.setMinimumSize(new java.awt.Dimension(90, 30));
        loginbutton.setPreferredSize(new java.awt.Dimension(90, 30));
        loginbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginbuttonMouseClicked(evt);
            }
        });

        clearentry.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        clearentry.setText("Clear Info");
        clearentry.setMaximumSize(new java.awt.Dimension(90, 30));
        clearentry.setMinimumSize(new java.awt.Dimension(90, 30));
        clearentry.setPreferredSize(new java.awt.Dimension(90, 30));

        forgotpwd.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        forgotpwd.setText("Forgot Password");
        forgotpwd.setMaximumSize(new java.awt.Dimension(150, 25));
        forgotpwd.setMinimumSize(new java.awt.Dimension(150, 25));
        forgotpwd.setPreferredSize(new java.awt.Dimension(150, 25));
        forgotpwd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgotpwdMouseClicked(evt);
            }
        });

        forgotusrname.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        forgotusrname.setText("Forgot Username");
        forgotusrname.setMaximumSize(new java.awt.Dimension(150, 25));
        forgotusrname.setMinimumSize(new java.awt.Dimension(150, 25));
        forgotusrname.setPreferredSize(new java.awt.Dimension(150, 25));

        appwarntitle.setBackground(new java.awt.Color(229, 234, 237));
        appwarntitle.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        appwarntitle.setForeground(new java.awt.Color(121, 123, 128));
        appwarntitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        appwarntitle.setText("Terms of Use Agreement");
        appwarntitle.setMaximumSize(new java.awt.Dimension(254, 39));
        appwarntitle.setMinimumSize(new java.awt.Dimension(254, 39));
        appwarntitle.setPreferredSize(new java.awt.Dimension(254, 39));

        appwarn.setBorder(null);
        appwarn.setMaximumSize(new java.awt.Dimension(254, 130));
        appwarn.setMinimumSize(new java.awt.Dimension(254, 130));
        appwarn.setPreferredSize(new java.awt.Dimension(254, 130));

        appwarn_content.setEditable(false);
        appwarn_content.setBackground(new java.awt.Color(229, 234, 237));
        appwarn_content.setColumns(20);
        appwarn_content.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        appwarn_content.setLineWrap(true);
        appwarn_content.setRows(5);
        appwarn_content.setText("USER WARNING:\nThis system and all connected systems are property of IDS. All activity is recorded and monitored for adherence to IDS security policies. Any and all unlawful activity will be recorded and reported to the proper authorities.");
        appwarn_content.setWrapStyleWord(true);
        appwarn_content.setBorder(null);
        appwarn_content.setMaximumSize(new java.awt.Dimension(254, 130));
        appwarn_content.setMinimumSize(new java.awt.Dimension(254, 130));
        appwarn_content.setPreferredSize(new java.awt.Dimension(254, 130));
        appwarn.setViewportView(appwarn_content);

        agreement.setBackground(new java.awt.Color(229, 234, 237));
        agreement.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        agreement.setText("I Agree to the Terms of Use");
        agreement.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                agreementItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout contentpaneLayout = new javax.swing.GroupLayout(contentpane);
        contentpane.setLayout(contentpaneLayout);
        contentpaneLayout.setHorizontalGroup(
            contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentpaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentpaneLayout.createSequentialGroup()
                        .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(returntitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(contentpaneLayout.createSequentialGroup()
                                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordlabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(usrnamefield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pwdfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentpaneLayout.createSequentialGroup()
                        .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentpaneLayout.createSequentialGroup()
                                .addComponent(loginbutton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(clearentry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentpaneLayout.createSequentialGroup()
                                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(forgotusrname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(forgotpwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)))
                        .addGap(55, 55, 55)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentpaneLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(appwarn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(appwarntitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contentpaneLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(agreement)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        contentpaneLayout.setVerticalGroup(
            contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentpaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentpaneLayout.createSequentialGroup()
                        .addComponent(returntitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usrnamefield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pwdfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clearentry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loginbutton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(forgotpwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(forgotusrname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(contentpaneLayout.createSequentialGroup()
                        .addComponent(appwarntitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(appwarn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(agreement)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        footercontent.setBackground(new java.awt.Color(229, 234, 237));
        footercontent.setMaximumSize(new java.awt.Dimension(600, 65));
        footercontent.setMinimumSize(new java.awt.Dimension(600, 65));
        footercontent.setPreferredSize(new java.awt.Dimension(600, 65));

        helpmenu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        helpmenu.setText("Help Menu");
        helpmenu.setMaximumSize(new java.awt.Dimension(103, 25));
        helpmenu.setMinimumSize(new java.awt.Dimension(103, 25));
        helpmenu.setPreferredSize(new java.awt.Dimension(103, 25));
        helpmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpmenuActionPerformed(evt);
            }
        });

        statusbar.setMaximumSize(new java.awt.Dimension(250, 25));
        statusbar.setMinimumSize(new java.awt.Dimension(250, 25));
        statusbar.setPreferredSize(new java.awt.Dimension(250, 25));
        statusbar.setStringPainted(true);

        statlabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        javax.swing.GroupLayout footercontentLayout = new javax.swing.GroupLayout(footercontent);
        footercontent.setLayout(footercontentLayout);
        footercontentLayout.setHorizontalGroup(
            footercontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footercontentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(helpmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(statlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        footercontentLayout.setVerticalGroup(
            footercontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, footercontentLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(footercontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(statusbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statlabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(helpmenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout innerpaneLayout = new javax.swing.GroupLayout(innerpane);
        innerpane.setLayout(innerpaneLayout);
        innerpaneLayout.setHorizontalGroup(
            innerpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(innerpaneLayout.createSequentialGroup()
                .addGroup(innerpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contentpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(headercontent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(footercontent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        innerpaneLayout.setVerticalGroup(
            innerpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(innerpaneLayout.createSequentialGroup()
                .addComponent(headercontent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contentpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(footercontent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(innerpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(innerpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void agreementItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_agreementItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            loginbutton.setEnabled(true);
        }else{
            loginbutton.setEnabled(false);
        }
    }//GEN-LAST:event_agreementItemStateChanged

    private void helpmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpmenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_helpmenuActionPerformed

    private void loginbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginbuttonMouseClicked
        String username = usrnamefield.getText();
        String password = String.valueOf(pwdfield.getPassword());
        Thread lgnthread = new Thread(() -> {
            DatabaseOperations dbops = new DatabaseOperations();
            dbops.mainLogin(username, password, statusbar, statlabel);
            this.setVisible(false);
        });
        lgnthread.start();      
    }//GEN-LAST:event_loginbuttonMouseClicked

    private void usrnamefieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usrnamefieldFocusGained
        usrnamefield.setText("");
    }//GEN-LAST:event_usrnamefieldFocusGained

    private void forgotpwdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotpwdMouseClicked
        new Thread(() -> {
            javax.swing.JFrame pwdrec = new PassRecovery();
            pwdrec.setLocationRelativeTo(this);
            pwdrec.setAlwaysOnTop(false);
            pwdrec.setVisible(true);
        }).start();       
    }//GEN-LAST:event_forgotpwdMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox agreement;
    private javax.swing.JScrollPane appwarn;
    private javax.swing.JTextArea appwarn_content;
    private javax.swing.JLabel appwarntitle;
    private javax.swing.JButton clearentry;
    private javax.swing.JPanel contentpane;
    private javax.swing.JPanel footercontent;
    private javax.swing.JButton forgotpwd;
    private javax.swing.JButton forgotusrname;
    private javax.swing.JPanel headercontent;
    private javax.swing.JLabel headerlogo;
    private javax.swing.JLabel headertitle;
    private javax.swing.JButton helpmenu;
    private javax.swing.JPanel innerpane;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton loginbutton;
    private javax.swing.JLabel passwordlabel;
    private javax.swing.JPasswordField pwdfield;
    private javax.swing.JLabel returntitle;
    private javax.swing.JLabel statlabel;
    private javax.swing.JProgressBar statusbar;
    private javax.swing.JLabel usernamelabel;
    private javax.swing.JTextField usrnamefield;
    // End of variables declaration//GEN-END:variables
}
