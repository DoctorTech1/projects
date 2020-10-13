/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.security.validation;

import com.ids.ops.escrow.security.connections.MSSQLConnector;
import com.ids.ops.escrow.uix.OperationsMenu;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 *
 * @author paul20
 */
public class InputValidation {
    
    /**
     * Regex string to validate the email address of the user entered.
     * Conforms to RFC822 standards for email validation.
     */
    private static final Pattern emlValidation = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)", Pattern.CASE_INSENSITIVE);
    
    
    private static final Pattern passwdValidation = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    
    /**
     * Validates the username of the person is unique.
     * Queries the database user-table to validate the username is not in use.
     * If the username returns unique, changes the field background to GREEN,
     * else it changes it to RED.
     * @param username  The requested username of the person. should be no 
     * longer than 10 UTF-8 characters.
     * @param usernamefield The embedded field the username is entered into.
     */
    public void validateUserID(String username,JTextField usernamefield){
        if((username != null && !username.isEmpty())){
            if((username.length() <= 10) && (username.length() >0)){
                PreparedStatement ps;
                ResultSet rs;
                String query = "SELECT count(*) FROM escrw_app_ud WHERE u_unm = ?";
                try{
                    ps = MSSQLConnector.getConnection().prepareStatement(query);
                    ps.setString(1, username);
                    rs = ps.executeQuery();
                    if(rs.next()){
                        usernamefield.setBackground(Color.WHITE);
                    }else{
                        JOptionPane.showMessageDialog(null,"Username is NOT unique. Please select a new username.","Username Invalid",JOptionPane.WARNING_MESSAGE);
                        usernamefield.setBackground(Color.RED);
                    }
                }catch(SQLException ex){
                    Logger.getLogger(MSSQLConnector.class.getName()).log(Level.SEVERE, null, ex);
                }catch(ClassNotFoundException ex){
                    Logger.getLogger(OperationsMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Username cannot be more than 10 characters in length.","Username Format Error",JOptionPane.ERROR_MESSAGE);
                usernamefield.setBackground(Color.RED);
            } 
        }else{
            JOptionPane.showMessageDialog(null,"Username cannot be blank. Please enter a valid username.","Username Error",JOptionPane.ERROR_MESSAGE);
            usernamefield.setBackground(Color.RED);
        }  
    }
    
    /**
     * Validates the email address of the user is valid.
     * Takes the email address and applies an RFC822 compliant regular 
     * expression to validate the information entered is in a valid format. If
     * valid, changes the field to GREEN, else changes it to RED.
     * @param email The email address to be validated
     * @param emailfield The field the email address is entered into.
     * 
     */
    public void validateEmailAddress(String email,JTextField emailfield){
        if((email != null && !email.isEmpty())){
            if((email.length() <50) && (email.length() >0)){
                Matcher emlmatch = emlValidation.matcher(email);
                if(emlmatch.matches()){
                    emailfield.setBackground(Color.WHITE);
                }else{
                    JOptionPane.showMessageDialog(null,"Email address format is not valid. Please enter a valid email address.","Email Format Error",JOptionPane.ERROR_MESSAGE);
                    emailfield.setBackground(Color.RED);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Email address cannot be longer than 50 characters in length. Please enter a valid email address.","Email Length Mismatch",JOptionPane.ERROR_MESSAGE);
                emailfield.setBackground(Color.RED);
            }  
        }else{
            JOptionPane.showMessageDialog(null,"Email Address cannot be blank. Please enter a valid email address.","Email Address Error",JOptionPane.ERROR_MESSAGE);
            emailfield.setBackground(Color.RED);
        }
    }
    
    /**
     * Validates password information entered matches in both fields.
     * Takes the contents of the passwd string and compares it to the contents
     * of the passconf field. If the fields match, change their backgrounds to
     * GREEN; else it changes it to RED.
     * @param passwd The password to be compared against.
     * @param passconf The password to be compared.
     * @param passfield The field holding the password.
     * @param pwdconf The field holding the confirming password.
     */
    public void validateUserPassword(String passwd, String passconf,JPasswordField passfield,JPasswordField pwdconf){
        if((passwd != null && !passwd.isEmpty())){
            if((passwd.length() <50) && (passwd.length() >= 8)){
                Matcher pwdmatch = passwdValidation.matcher(passwd);
                if(pwdmatch.matches()){
                    if(passwd.equals(passconf)){
                        passfield.setBackground(Color.WHITE);
                        pwdconf.setBackground(Color.WHITE);
                        JOptionPane.showMessageDialog(null,"Passwords match","Password Validation Complete",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null,"Passwords do not match, please reenter your password and try again.","Password Mismatch 4",JOptionPane.WARNING_MESSAGE);
                        passfield.setBackground(Color.RED);
                        pwdconf.setBackground(Color.RED);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Password must conform to the following: \n\n1 Uppercase Letter;\n1 Lowercase Letter;\n1 Special Character (@,#,$,%,^,&,+, and/or =);\n1 Number\nNo whitespace;\nLonger than 8 characters","Password Error 1",JOptionPane.ERROR_MESSAGE);
                    passfield.setBackground(Color.RED);
                    pwdconf.setBackground(Color.RED);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Password must conform to the following rules: \n\n1 Uppercase Letter;\n1 Lowercase Letter;\n1 Special Character (@,#,$,%,^,&,+, and/or =);\n1 Number\nNo whitespace;\nLonger than 8 characters and less than 50 characters","Password Error 2",JOptionPane.ERROR_MESSAGE);
                    passfield.setBackground(Color.RED);
                    pwdconf.setBackground(Color.RED);            
            }  
        }else{
            JOptionPane.showMessageDialog(null,"Passwords cannot be blank. Please enter a password.","Password Error 3",JOptionPane.ERROR_MESSAGE);
            passfield.setBackground(Color.RED);
            pwdconf.setBackground(Color.RED);
        }
        
    }
    
    /**
     * Validates the super user selections.
     * Takes the contents of the permission selections and checks to make sure
     * all super user parameters are selected, since super user only works when
     * all parameters are selected.
     * If the parameters are not selected properly, show an OptionPane saying
     * so. If they are selected correctly, display a warning about abuse of
     * power.
     * @param usrstat The selected index of the Account Status ComboBox.
     * @param usrctrl The selected index of the Permissions List.
     * @param usrflags The selected index of the Account Flags ComboBox.
     * @param usrinfo The selected index of the User Information ComboBox.
     * @param status The ComboBox holding the account status information.
     * @param ctrl The List containing the control information.
     * @param flags The ComboBox containing the account flags information.
     * @param info The ComboBox containing the extended account information.
     */
    public void validateSuperUserPermissions(int usrstat, int usrctrl, int usrflags, int usrinfo,JButton subinfo,JComboBox status,JList ctrl,JComboBox flags,JComboBox info){
        if((usrstat == 3) && (usrctrl == 9) && (usrflags == 3) && (usrinfo == 3)){
            Object[] options = {"Yes", "No"};
            int valid = JOptionPane.showOptionDialog(null,"Super user permission assignments are valid.\n\nTo keep these permissions, click Yes. Otherwise, click No.","Super User Validation Gatekeeper",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if(valid == 1){
                status.setSelectedIndex(1);
                ctrl.setSelectedIndex(0);
                flags.setSelectedIndex(4);
                info.setSelectedIndex(0);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Super User string is invalid. \nPermissions have been defaulted to read-only.","Invalid Selection",JOptionPane.ERROR_MESSAGE);
            status.setSelectedIndex(0);
            ctrl.setSelectedIndex(0);
            flags.setSelectedIndex(1);
            info.setSelectedIndex(4);
            subinfo.setEnabled(false);
            
        }
    }
    
    /**
     * Validates the input into the Name fields.
     * Takes the contents of the first name and last name field and checks to
     * make sure that the names are valid. If they are not valid, highlight in
     * RED.
     * @param fnm The first name string.
     * @param lnm The last name string.
     * @param firstname The field containing the first name text.
     * @param lastname The field containing the last name text.
     */
    public void validateUserIdentification(String fnm, String lnm,JTextField firstname,JTextField lastname){
        if((fnm != null && !fnm.isEmpty()) && (lnm != null && !lnm.isEmpty())){
            firstname.setBackground(Color.WHITE);
            lastname.setBackground(Color.WHITE);
        }else{
            JOptionPane.showMessageDialog(null,"First and last name are required fields. Please enter valid data and try again.","Invalid Data",JOptionPane.WARNING_MESSAGE);
            firstname.setBackground(Color.RED);
            lastname.setBackground(Color.RED); 
        }
    }
    
    public void validationComplete(){
        JOptionPane.showMessageDialog(null,"Input validation is complete. \nPlease correct any fields highlighted in red.","Validation Complete",JOptionPane.INFORMATION_MESSAGE);
    }
}