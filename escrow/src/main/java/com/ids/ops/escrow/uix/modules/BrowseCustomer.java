/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.uix.modules;



import com.ids.ops.escrow.interpreter.ArrayConverter;
import com.ids.ops.escrow.security.connections.DatabaseExport;
import com.ids.ops.escrow.security.connections.DatabaseOperations;
import com.ids.ops.escrow.security.connections.MSSQLConnector;
import com.ids.ops.escrow.uix.OperationsMenu;
import com.ids.ops.escrow.uix.modules.search.CustomerSearch;
import java.awt.Color;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author paul20
 */
public class BrowseCustomer extends javax.swing.JFrame {
    
    private static ArrayList<ArrayList<String>> mdarray = new ArrayList<ArrayList<String>>();
    private static ArrayList<String> results = new ArrayList<>();
    private static int col = 0;
    private static int row = 0;
    private static String armed;
    private static int numrow;
    private static int count;
    
    /**
     * Creates new form BrowseCustomer
     */
    public BrowseCustomer() {
        initComponents();
    }
    
    /**
     * Method to silently get the total number of customer records for a given search.
     * This method takes the values from the search window and generates a
     * SQL query to count the total number of records that will be returned by
     * the search query.
     * 
     * @param search The selected index of the search-by JComboBox
     * @param comp The selected index of the comparator JComboBox
     * @param value The value the user enters into the search parameter(s) box
     * @param resbar The progress bar that shows the progress of a given query
     * @param reslabel The label that displays information to the user
     */       
    private static void countSilent(int search, int comp, String value) throws InterruptedException{
        final String query;
        /**
         * Check if search terms are null or not
         */
        if(value != null){
            /**
            * Retrieve customer search query & associated count query
            */
            CustomerSearch genstring = new CustomerSearch();
            query = genstring.getSQLRowCount(search,comp);
            /**
             * Query DB and return total result count
             */           
            int row_count = comp;
            int count;
            PreparedStatement ps;
            ResultSet rs;
            switch (row_count){ 
                case 0: //BEGINS WITH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value+"%");
                        rs = ps.executeQuery();                                
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
                case 1: //CONTAINS
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value+"%");
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }                            
                break;
                case 2: //ENDS WITH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value);
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }                                
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
                case 3: //EXACT MATCH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value);
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }                               
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
            }
         }else{
            javax.swing.JOptionPane.showMessageDialog(null,"Please enter valid search terms.","Search Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger(DatabaseOperations.class.getName());
        }
    }
    
    /**
     * Method to get the total number of customer records for a given search.
     * This method takes the values from the search window and generates a
     * SQL query to count the total number of records that will be returned by
     * the search query.
     * 
     * @param search The selected index of the search-by JComboBox
     * @param comp The selected index of the comparator JComboBox
     * @param value The value the user enters into the search parameter(s) box
     * @param resbar The progress bar that shows the progress of a given query
     * @param reslabel The label that displays information to the user
     */       
    private static void countRecords(int search, int comp, String value, javax.swing.JProgressBar resbar, javax.swing.JLabel reslabel){
        resbar.setForeground(Color.BLUE);
	resbar.setStringPainted(true);
	new Thread(() -> {
            for(int i = resbar.getValue(); i <= resbar.getMaximum(); i++){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
                resbar.setValue(i);
                resbar.setString("Record Count Progress ("+i+"%)");
                switch (resbar.getValue()) {
                    case 0:
                        reslabel.setText("Parsing Inputs");
                        break;                    
                    case 20:
                        reslabel.setText("Counting Records");
                        break;
                    case 100:
                        reslabel.setText("Count Complete");
                        break;
                    default:
                        break;
                }
            }
	}).start();	
        final String query;
        /**
         * Check if search terms are null or not
         */
        if(value != null){
            /**
            * Retrieve customer search query & associated count query
            */
            CustomerSearch genstring = new CustomerSearch();
            query = genstring.getSQLCount(search,comp);
            /**
             * Query DB and return total result count
             */
            int row_count = comp;
            int count;
            PreparedStatement ps;
            ResultSet rs;
            switch (row_count){                       
                case 0: //BEGINS WITH                          
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value+"%");
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }
                        //javax.swing.JOptionPane.showMessageDialog(null,"Search returned "+count+" database records.","Test Complete",javax.swing.JOptionPane.INFORMATION_MESSAGE);                                
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }                            
                break;
                case 1: //CONTAINS
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value+"%");
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }
                        //javax.swing.JOptionPane.showMessageDialog(null,"Search returned "+count+" database records.","Test Complete",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }                            
                break;
                case 2: //ENDS WITH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,"%"+value);
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }
                        //javax.swing.JOptionPane.showMessageDialog(null,"Search returned "+count+" database records.","Test Complete",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
                case 3: //EXACT MATCH
                    try{
                        ps = MSSQLConnector.getConnection().prepareStatement(query);
                        ps.setString(1,value);
                        rs = ps.executeQuery();
                        if(rs.next()){
                            numrow = rs.getInt(1);
                        }else{
                            count = 0;
                        }
                        //javax.swing.JOptionPane.showMessageDialog(null,"Search returned "+count+" database records.","Test Complete",javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    }catch(SQLException | ClassNotFoundException ex){
                        javax.swing.JOptionPane.showMessageDialog(null,"An error has occured.\nPlease check the logs for more information.","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
                        java.util.logging.Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
                }                    
        }else{
            javax.swing.JOptionPane.showMessageDialog(null,"Please enter valid search terms.","Search Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger(DatabaseOperations.class.getName());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentpane = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        idslogo = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        body = new javax.swing.JPanel();
        disptitle = new javax.swing.JLabel();
        searchparamtitle = new javax.swing.JLabel();
        searchbylbl = new javax.swing.JLabel();
        srchbx = new javax.swing.JComboBox<>();
        complbl = new javax.swing.JLabel();
        compbox = new javax.swing.JComboBox<>();
        valuelbl = new javax.swing.JLabel();
        srchvalue = new javax.swing.JTextField();
        searchbtn = new javax.swing.JButton();
        gohome = new javax.swing.JButton();
        dryrun = new javax.swing.JButton();
        statusbar = new javax.swing.JProgressBar();
        statlabel = new javax.swing.JLabel();
        resbar = new javax.swing.JProgressBar();
        reslabel = new javax.swing.JLabel();
        helpbtn = new javax.swing.JButton();
        resultpanel = new javax.swing.JPanel();
        cusacct = new javax.swing.JTextField();
        cusname = new javax.swing.JTextField();
        cusnum = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        cusaddr = new javax.swing.JTextArea();
        cusescrow = new javax.swing.JTextField();
        cusdep = new javax.swing.JTextField();
        exportsearch = new javax.swing.JButton();
        resultsclr = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(640, 500));
        setResizable(false);

        contentpane.setBackground(new java.awt.Color(229, 234, 237));
        contentpane.setMaximumSize(new java.awt.Dimension(640, 520));
        contentpane.setMinimumSize(new java.awt.Dimension(640, 480));
        contentpane.setPreferredSize(new java.awt.Dimension(640, 520));

        header.setBackground(new java.awt.Color(229, 234, 237));
        header.setMaximumSize(new java.awt.Dimension(640, 65));
        header.setMinimumSize(new java.awt.Dimension(640, 65));
        header.setPreferredSize(new java.awt.Dimension(640, 65));

        idslogo.setBackground(new java.awt.Color(229, 234, 237));
        idslogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/IDSColor.jpg"))); // NOI18N
        idslogo.setMaximumSize(new java.awt.Dimension(240, 54));
        idslogo.setMinimumSize(new java.awt.Dimension(240, 54));
        idslogo.setPreferredSize(new java.awt.Dimension(240, 54));

        title.setBackground(new java.awt.Color(229, 234, 237));
        title.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        title.setForeground(new java.awt.Color(121, 123, 128));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Browse Customer Record(s)");
        title.setMaximumSize(new java.awt.Dimension(394, 65));
        title.setMinimumSize(new java.awt.Dimension(394, 65));
        title.setPreferredSize(new java.awt.Dimension(394, 65));

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(idslogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(idslogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        body.setBackground(new java.awt.Color(229, 234, 237));
        body.setMaximumSize(new java.awt.Dimension(640, 449));
        body.setMinimumSize(new java.awt.Dimension(640, 449));
        body.setPreferredSize(new java.awt.Dimension(640, 449));

        disptitle.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        disptitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        disptitle.setText("Search Results");

        searchparamtitle.setFont(new java.awt.Font("Arial", 2, 16)); // NOI18N
        searchparamtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        searchparamtitle.setText("Database Search Parameters");

        searchbylbl.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        searchbylbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        searchbylbl.setText("Search By:");

        srchbx.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        srchbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Company Name", "Escrow Account Number", "System Number" }));
        srchbx.setMaximumSize(new java.awt.Dimension(174, 30));
        srchbx.setMinimumSize(new java.awt.Dimension(174, 30));
        srchbx.setPreferredSize(new java.awt.Dimension(174, 30));

        complbl.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        complbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        complbl.setText("Comparator:");

        compbox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        compbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Begins with", "Contains", "Ends with", "Is Equal to" }));
        compbox.setMaximumSize(new java.awt.Dimension(174, 30));
        compbox.setMinimumSize(new java.awt.Dimension(174, 30));
        compbox.setPreferredSize(new java.awt.Dimension(174, 30));

        valuelbl.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        valuelbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        valuelbl.setText("Value:");

        srchvalue.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        srchvalue.setMaximumSize(new java.awt.Dimension(174, 30));
        srchvalue.setMinimumSize(new java.awt.Dimension(174, 30));
        srchvalue.setPreferredSize(new java.awt.Dimension(174, 30));
        srchvalue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                srchvalueFocusGained(evt);
            }
        });

        searchbtn.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        searchbtn.setText("Search");
        searchbtn.setMaximumSize(new java.awt.Dimension(130, 30));
        searchbtn.setMinimumSize(new java.awt.Dimension(130, 30));
        searchbtn.setPreferredSize(new java.awt.Dimension(130, 30));
        searchbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchbtnMouseClicked(evt);
            }
        });
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });

        gohome.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gohome.setText("Return Home");
        gohome.setMaximumSize(new java.awt.Dimension(130, 30));
        gohome.setMinimumSize(new java.awt.Dimension(130, 30));
        gohome.setPreferredSize(new java.awt.Dimension(130, 30));
        gohome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gohomeMouseClicked(evt);
            }
        });

        dryrun.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        dryrun.setText("Test Search");
        dryrun.setMaximumSize(new java.awt.Dimension(130, 30));
        dryrun.setMinimumSize(new java.awt.Dimension(130, 30));
        dryrun.setPreferredSize(new java.awt.Dimension(130, 30));
        dryrun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dryrunMouseClicked(evt);
            }
        });

        statusbar.setMaximumSize(new java.awt.Dimension(300, 25));
        statusbar.setMinimumSize(new java.awt.Dimension(300, 25));
        statusbar.setPreferredSize(new java.awt.Dimension(300, 25));
        statusbar.setString("Waiting for Input...");
        statusbar.setStringPainted(true);

        statlabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        statlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        resbar.setMaximumSize(new java.awt.Dimension(300, 25));
        resbar.setMinimumSize(new java.awt.Dimension(300, 25));
        resbar.setPreferredSize(new java.awt.Dimension(300, 25));
        resbar.setString("Waiting for Input...");
        resbar.setStringPainted(true);

        reslabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        reslabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        helpbtn.setText("Help");

        resultpanel.setBackground(new java.awt.Color(229, 234, 237));
        resultpanel.setMaximumSize(new java.awt.Dimension(320, 300));
        resultpanel.setMinimumSize(new java.awt.Dimension(320, 300));
        resultpanel.setPreferredSize(new java.awt.Dimension(320, 300));

        cusacct.setEditable(false);
        cusacct.setBackground(new java.awt.Color(255, 255, 255));
        cusacct.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        cusacct.setForeground(new java.awt.Color(51, 51, 51));
        cusacct.setToolTipText("The escrow account number of the customer. Takes the format xxxxx-xxxx");
        cusacct.setMaximumSize(new java.awt.Dimension(300, 30));
        cusacct.setMinimumSize(new java.awt.Dimension(300, 30));
        cusacct.setPreferredSize(new java.awt.Dimension(300, 30));

        cusname.setEditable(false);
        cusname.setBackground(new java.awt.Color(255, 255, 255));
        cusname.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        cusname.setForeground(new java.awt.Color(51, 51, 51));
        cusname.setMaximumSize(new java.awt.Dimension(300, 30));
        cusname.setMinimumSize(new java.awt.Dimension(300, 30));
        cusname.setPreferredSize(new java.awt.Dimension(300, 30));

        cusnum.setEditable(false);
        cusnum.setBackground(new java.awt.Color(255, 255, 255));
        cusnum.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        cusnum.setForeground(new java.awt.Color(51, 51, 51));
        cusnum.setToolTipText("The customers' four digit system number identifier.");
        cusnum.setMaximumSize(new java.awt.Dimension(300, 30));
        cusnum.setMinimumSize(new java.awt.Dimension(300, 30));
        cusnum.setPreferredSize(new java.awt.Dimension(300, 30));

        jScrollPane1.setToolTipText("The customers' business (either mailing or headquarters) address.");
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(300, 89));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(300, 89));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 89));

        cusaddr.setEditable(false);
        cusaddr.setColumns(20);
        cusaddr.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        cusaddr.setForeground(new java.awt.Color(51, 51, 51));
        cusaddr.setLineWrap(true);
        cusaddr.setRows(5);
        cusaddr.setWrapStyleWord(true);
        cusaddr.setMaximumSize(new java.awt.Dimension(300, 89));
        cusaddr.setMinimumSize(new java.awt.Dimension(300, 89));
        cusaddr.setPreferredSize(new java.awt.Dimension(300, 89));
        jScrollPane1.setViewportView(cusaddr);

        cusescrow.setEditable(false);
        cusescrow.setBackground(new java.awt.Color(255, 255, 255));
        cusescrow.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        cusescrow.setForeground(new java.awt.Color(51, 51, 51));
        cusescrow.setToolTipText("The products the customer is contracted to have escrowed.");
        cusescrow.setMaximumSize(new java.awt.Dimension(300, 30));
        cusescrow.setMinimumSize(new java.awt.Dimension(300, 30));
        cusescrow.setPreferredSize(new java.awt.Dimension(300, 30));

        cusdep.setEditable(false);
        cusdep.setBackground(new java.awt.Color(255, 255, 255));
        cusdep.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        cusdep.setForeground(new java.awt.Color(51, 51, 51));
        cusdep.setToolTipText("Where and how the customer receives their escrowed product.");
        cusdep.setMaximumSize(new java.awt.Dimension(300, 30));
        cusdep.setMinimumSize(new java.awt.Dimension(300, 30));
        cusdep.setPreferredSize(new java.awt.Dimension(300, 30));

        javax.swing.GroupLayout resultpanelLayout = new javax.swing.GroupLayout(resultpanel);
        resultpanel.setLayout(resultpanelLayout);
        resultpanelLayout.setHorizontalGroup(
            resultpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(resultpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cusacct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cusname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cusnum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cusescrow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cusdep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        resultpanelLayout.setVerticalGroup(
            resultpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cusacct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cusname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cusnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cusescrow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cusdep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        exportsearch.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        exportsearch.setText("Export Records");
        exportsearch.setMaximumSize(new java.awt.Dimension(130, 30));
        exportsearch.setMinimumSize(new java.awt.Dimension(130, 30));
        exportsearch.setPreferredSize(new java.awt.Dimension(130, 30));
        exportsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportsearchMouseClicked(evt);
            }
        });

        resultsclr.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        resultsclr.setText("Clear Results");
        resultsclr.setMaximumSize(new java.awt.Dimension(130, 30));
        resultsclr.setMinimumSize(new java.awt.Dimension(130, 30));
        resultsclr.setPreferredSize(new java.awt.Dimension(130, 30));
        resultsclr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultsclrMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(searchparamtitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(bodyLayout.createSequentialGroup()
                                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(complbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(valuelbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(searchbylbl, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(compbox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(srchvalue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(srchbx, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(reslabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(statlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dryrun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gohome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(bodyLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(helpbtn))
                            .addComponent(exportsearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resultsclr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(disptitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchparamtitle, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(disptitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resultpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bodyLayout.createSequentialGroup()
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(searchbylbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(srchbx, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(complbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(compbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(valuelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(srchvalue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dryrun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exportsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultsclr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gohome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(helpbtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(statusbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reslabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout contentpaneLayout = new javax.swing.GroupLayout(contentpane);
        contentpane.setLayout(contentpaneLayout);
        contentpaneLayout.setHorizontalGroup(
            contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentpaneLayout.createSequentialGroup()
                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        contentpaneLayout.setVerticalGroup(
            contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentpaneLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gohomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gohomeMouseClicked
        javax.swing.JFrame mainmenu;
        mainmenu = new OperationsMenu();
        mainmenu.setLocationRelativeTo(null);
        mainmenu.setVisible(true);
        mainmenu.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        mainmenu.pack();
        this.dispose();
    }//GEN-LAST:event_gohomeMouseClicked

    private void dryrunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dryrunMouseClicked
        int search = srchbx.getSelectedIndex();
        int comp = compbox.getSelectedIndex();
        String value = srchvalue.getText();
        Thread testrun = new Thread(new Runnable() {
            @Override
            public void run(){
                BrowseCustomer.countRecords(search, comp, value, resbar, reslabel);
            }
        });
        testrun.start();
        try {
            testrun.join();
            javax.swing.JOptionPane.showMessageDialog(null,"Search returned "+numrow+" database records.","Test Complete",javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (InterruptedException ex) {
            Logger.getLogger(BrowseCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_dryrunMouseClicked

    private void searchbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchbtnMouseClicked
        int search = srchbx.getSelectedIndex();
        int comp = compbox.getSelectedIndex();      
        String value = srchvalue.getText();
        DatabaseOperations searchproc = new DatabaseOperations();
        ArrayConverter convert = new ArrayConverter();
        Thread countrow = new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    BrowseCustomer.countSilent(search, comp, value);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BrowseCustomer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        countrow.start();
        try {
            countrow.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(BrowseCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
       if(row == 0){
            row++;            
            new Thread(new Runnable(){
                @Override
                public void run(){
                    searchproc.getResults(search, comp, value, statusbar, statlabel, srchvalue);
                    mdarray = searchproc.getMDResults();
                    convert.displayCustomerInformation(cusacct,cusname,cusnum,cusaddr,cusescrow,cusdep,mdarray,row);
                    if(numrow == 1){
                        searchbtn.setEnabled(false);
                        javax.swing.JOptionPane.showMessageDialog(null,"Last search record has been reached.","Search Complete",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        searchbtn.setText("Next Record");
                    }
                }                
            }).start();
        }        
       /* }else{
       row++;
       new Thread(new Runnable(){
       @Override
       public void run(){
       searchproc.getResults(search, comp, value, statusbar, statlabel, srchvalue);
       mdarray = searchproc.getMDResults();
       convert.displayCustomerInformation(cusacct,cusname,cusnum,cusaddr,cusescrow,cusdep,mdarray,row);
       searchbtn.setText("Next Record");
       }
       }).start();
       }        */
    }//GEN-LAST:event_searchbtnMouseClicked

    private void srchvalueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_srchvalueFocusGained
        if(srchvalue.getBackground() == java.awt.Color.RED){
            srchvalue.setBackground(java.awt.Color.WHITE);
            srchvalue.setText("");           
        }
    }//GEN-LAST:event_srchvalueFocusGained

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        armed = searchbtn.getText();
        ArrayConverter convert = new ArrayConverter();
        if(armed.equals("Next Record")){
            row++;
            if(row < numrow){
                cusacct.setText("");
                cusname.setText("");
                cusnum.setText("");
                cusaddr.setText("");
                cusescrow.setText("");
                cusdep.setText("");
                convert.displayCustomerInformation(cusacct,cusname,cusnum,cusaddr,cusescrow,cusdep,mdarray,row);
            }else if(row == numrow){
                cusacct.setText("");
                cusname.setText("");
                cusnum.setText("");
                cusaddr.setText("");
                cusescrow.setText("");
                cusdep.setText("");                             
                convert.displayCustomerInformation(cusacct,cusname,cusnum,cusaddr,cusescrow,cusdep,mdarray,row);
                javax.swing.JOptionPane.showMessageDialog(null,"Last search record has been reached.","Search Complete",JOptionPane.INFORMATION_MESSAGE);
                searchbtn.setEnabled(false);
                mdarray.clear();
            }  
        }                        
    }//GEN-LAST:event_searchbtnActionPerformed

    private void exportsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportsearchMouseClicked
        int search = srchbx.getSelectedIndex();
        int comp = compbox.getSelectedIndex();      
        String value = srchvalue.getText();
        new Thread(new Runnable(){
            @Override
            public void run(){
                DatabaseExport exporter = new DatabaseExport();
                try {
                    exporter.writeResultSetArrayToCsv(search,comp,value);
                } catch (IOException ex) {
                    Logger.getLogger(BrowseCustomer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BrowseCustomer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();      
    }//GEN-LAST:event_exportsearchMouseClicked

    private void resultsclrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsclrMouseClicked
        searchbtn.setEnabled(true);
        searchbtn.setText("Search");
        cusacct.setText("");
        cusname.setText("");
        cusnum.setText("");
        cusaddr.setText("");
        cusescrow.setText("");
        cusdep.setText("");
        statusbar.setString("Waiting for Input...");
        statusbar.setValue(0);
        statlabel.setText("");
        resbar.setString("Waiting for Input...");
        resbar.setValue(0);
        reslabel.setText("");
        row = 0;
    }//GEN-LAST:event_resultsclrMouseClicked

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
            java.util.logging.Logger.getLogger(BrowseCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BrowseCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BrowseCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BrowseCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BrowseCustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JComboBox<String> compbox;
    private javax.swing.JLabel complbl;
    private javax.swing.JPanel contentpane;
    private javax.swing.JTextField cusacct;
    private javax.swing.JTextArea cusaddr;
    private javax.swing.JTextField cusdep;
    private javax.swing.JTextField cusescrow;
    private javax.swing.JTextField cusname;
    private javax.swing.JTextField cusnum;
    private javax.swing.JLabel disptitle;
    private javax.swing.JButton dryrun;
    private javax.swing.JButton exportsearch;
    private javax.swing.JButton gohome;
    private javax.swing.JPanel header;
    private javax.swing.JButton helpbtn;
    private javax.swing.JLabel idslogo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JProgressBar resbar;
    private javax.swing.JLabel reslabel;
    private javax.swing.JPanel resultpanel;
    private javax.swing.JButton resultsclr;
    private javax.swing.JButton searchbtn;
    private javax.swing.JLabel searchbylbl;
    private javax.swing.JLabel searchparamtitle;
    private javax.swing.JComboBox<String> srchbx;
    private javax.swing.JTextField srchvalue;
    private javax.swing.JLabel statlabel;
    private javax.swing.JProgressBar statusbar;
    private javax.swing.JLabel title;
    private javax.swing.JLabel valuelbl;
    // End of variables declaration//GEN-END:variables
}
