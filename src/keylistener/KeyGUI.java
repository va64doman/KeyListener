/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keylistener;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * KeyGUI --- the main class that display GUI form to the user.
 * @author Van Do
 * @version 1.0
 */
public class KeyGUI extends JFrame
{    
    // Use for the current date that was being searched
    Date date;
    // Initialize new frame with this title
    JFrame frame = new JFrame();
    // Access ReadFile class methods
    ReadFile file = new ReadFile();
    // Contains list of lines in user input's file
    List<String> list = new ArrayList<>();
    // FileHistory is a double linked list class
    // Contains the starting of the linked list and the end of the linked list
    // The variable, temp, is the objects between the start and end of the linked list, inclusively
    // It will use when the user will search through the linked list, history, and display from the front end
    FileHistory start = null;
    FileHistory end = null;
    FileHistory temp = null;
    // Access Search class methods
    Search search = new Search();
    // Set default list model when containing the list of found search results
    DefaultListModel model = new DefaultListModel();
    /**
     * Creates new form KeyGUI.
     */
    public KeyGUI() 
    {
        initComponents();
        // Set text for the those labels to initialise until being searched
        lblHistoryFile.setText("No file has been searched yet.");
        lblHistorySearchKeyword.setText("No keywords has been searched yet.");
        lblDate.setText("No results have been recorded.");
        // Initially, set search text box to disable
        txtSearch.setEnabled(false);
        // Key listener will be use when file text box or search bar have been pressed enter
        // Also when click on history text box, and then pressed left or right
        FileAction fileAction = new FileAction();
        SearchAction searchAction = new SearchAction();
        HistoryAction historyAction = new HistoryAction();
        // Add key listeners to the text boxes where the class will be use as an interface
        // Those classes should behave as key listeners
        txtFileName.addKeyListener(fileAction);
        txtSearch.addKeyListener(searchAction);
        txtArrows.addKeyListener(historyAction);
    }
    // Nested class to keep it only accessible to the main class
    // Using FileAction to find an existing text file to search for the keyword in file
    /**
     * FileAction --- this nested class used for key listener to allows to read file without clicking button.
     */
    private class FileAction implements KeyListener
    {
        /**
         * Event happened when user typed on key.
         * @param key - the key code that the user typed.
         */
        @Override
        public void keyTyped(KeyEvent key) 
        {
            // When user keeps typing on file text box, display this message in console
            System.out.println("Number of characters in file text box: " + txtFileName.getText().length());
        }
        /**
         * Event happened when user pressed on key. This allows to check the file existence.
         * @param key - the key code that the user pressed.
         */
        @Override
        public void keyPressed(KeyEvent key) 
        {
            // When key is pressed, get the key code from the user input
            int keyCode = key.getKeyCode();
            // If user has pressed enter key from file text box
            if(keyCode == KeyEvent.VK_ENTER)
            {
                // If the file was existed, then collect all lines from file
                System.out.println( "File text box has been entered." );
                if(file.isFile(txtFileName.getText()))
                {
                    // Allow search text box to enable and focus on this control
                    txtSearch.setEnabled(true);
                    txtSearch.requestFocus();
                    // Make file text box unable to be editable for store file's name in linked list object
                    txtFileName.setEditable(false);
                    // Store lines from this file into list
                    list = file.readFile(txtFileName.getText());
                }
                // Else if file does not existed, display message to user
                else
                {
                    String message = txtFileName.getText() + " does not existed.";
                    JOptionPane.showMessageDialog(null, message, "Unknown file", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        /**
         * Event happened when the user released key.
         * @param key - the key code that the user released.
         */
        @Override
        public void keyReleased(KeyEvent key) 
        {
            // When user released key, display the overall text from file text box
            System.out.println("Current text in file text box: " + txtFileName.getText());
        }
    }
    // Using SearchAction to find the keyword and compare that the lines contain this keyword
    /**
     * SearchAction --- this nested class used key listener to search for the lines that contain keywords.
     */
    private class SearchAction implements KeyListener
    {
        /**
         * Event happened when user typed on key.
         * @param key - the key code that the user typed. 
         */
        @Override
        public void keyTyped(KeyEvent key) 
        {
            // When user keeps typing on search text box, display this message in console
            System.out.println("Number of characters in search text box: " + txtSearch.getText().length());
        }
        /**
         * Event happened when user pressed on key. This allows to search through the file and compare keyword from lines.
         * @param key - the key code that the user pressed.
         */
        @Override
        public void keyPressed(KeyEvent key) 
        {
            // When key is pressed, get the key code from the user input
            int keyCode = key.getKeyCode();
            // If user has pressed enter key from search text box
            if(keyCode == KeyEvent.VK_ENTER)
            {
                // Remove all elements from models to avoid additional results when searching for another keywords
                model.removeAllElements();
                // Set current date
                date = new Date();
                System.out.println( "Search text box has been entered." );
                // Convert list into array
                String[] array = list.toArray(new String[0]);
                // Search and compare between lines and keywords
                List<String> searchList = search.searchList(txtSearch.getText(), array);
                // Convert search list into array
                String[] searchArray = searchList.toArray(new String[0]);
                // Disable search text box
                txtSearch.setEnabled(false);
                // Set file's name from file text box's text
                // Set search's text from search text box's text
                String fileName = txtFileName.getText();
                String searchText = txtSearch.getText();
                // Create history object and store the file's name, keywords, list of results and current date
                FileHistory history = new FileHistory(fileName, searchText, searchArray, date.toString());
                // If start is null, set start as history
                if(start == null)
                {
                    start = history;
                }
                // If start does contain the first history object
                else
                {
                    // Set entry as start
                    FileHistory entry = start;
                    // Iterate loop while entry's next node is not null
                    while(entry.getNext() != null)
                    {
                        // If next node contains history data, check next node
                        entry = entry.getNext();
                    }
                    // Set the next empty node as history
                    entry.setNext(history);
                    // Set history's previous node as entry
                    history.setPrevious(entry);
                }
                // Set temp as history to display added results
                temp = history;
                // Set end as history to identify the starting node and ending node
                end = history;
                // Set file text box to editable
                txtFileName.setEditable(true);
                // Disable search text box and clear text in this box
                txtSearch.setEnabled(false);
                txtSearch.setText("");
                // Set focus to the text box to allows user to press left or right
                txtArrows.requestFocus();
                // Display end node's data to front end
                displayHistory();                
            }
        }
        /**
         * Event happened when the user released key.
         * @param key - the key code that the user released.
         */
        @Override
        public void keyReleased(KeyEvent key) 
        {
            // When user released key, display the overall text from search text box
            System.out.println("Current text in search text box: " + txtSearch.getText());
        }        
    }
    /**
     * HistoryAction --- this nested class used key listener to search through the history by pressing arrow keys.
     */
    private class HistoryAction implements KeyListener
    {
        /**
         * Event happened when user typed on key.
         * @param key - the key code that the user typed. 
         */
        @Override
        public void keyTyped(KeyEvent key) 
        {
            // When user keeps typing on history text box, display this message in console
            System.out.println("Key has been touched.");
        }
        /**
         * Event happened when user pressed on key. This allows to check through search history from linked list.
         * @param key - the key code that the user pressed.
         */
        @Override
        public void keyPressed(KeyEvent key) 
        {
            // When user pressed key, get key code from user input
            int keyCode = key.getKeyCode();
            // Use switch to check the condition from user input
            switch(keyCode)
            {
                // If user has pressed left button
                case KeyEvent.VK_LEFT:
                    System.out.println("Left is pressed.");
                    // Check if temp is not null, else display no history message
                    if(temp != null)
                    {
                        // Check if temp's previous node is not null, else display no previous history message
                        if(temp.getPrevious() != null)
                        {
                            // Set temp as temp's previous node
                            temp = temp.getPrevious();
                            // Clear all model elements to avoid additional elements from previous model
                            model.removeAllElements();
                            // Display temp's history data
                            displayHistory();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "There are no more previous search history.", "No previous history", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "There are no history added.", "No history", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                // If user has pressed right button
                case KeyEvent.VK_RIGHT:
                    System.out.println("Right is pressed.");
                    // Same to press left except checking temp's next node
                    if(temp != null)
                    {
                        // Check if temp's next node is not null
                        if(temp.getNext() != null)
                        {
                            temp = temp.getNext();
                            model.removeAllElements();
                            displayHistory();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "There are no next search history yet.", "No next history", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "There are no history added.", "No history", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
            }
        }
        /**
         * Event happened when the user released key.
         * @param key - the key code that the user released.
         */
        @Override
        public void keyReleased(KeyEvent key) 
        {
            // When user released key, display this message to console
            System.out.println("Key has been released.");
        }
        
    }
    /**
     * Display the search item's component from the front end.
     */
    private void displayHistory()
    {
        // Set temp's file name on history file's label
        lblHistoryFile.setText("The name of file: " + temp.getFileName());
        // Set temp's keyword on history keyword's label
        lblHistorySearchKeyword.setText("The keyword to search for: " + temp.getInputSearch());
        // Set temp's date on history date's label to check when did this keyword was being searched
        lblDate.setText("The date was searched on " + temp.getDate());
        // Set model for the listbox
        listResult.setModel(model);
        // Add history's results to model
        for(String result : temp.getResults())
        {
            model.addElement(result);
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

        txtFileName = new javax.swing.JTextField();
        lblFileName = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        lblSearchInfo = new javax.swing.JLabel();
        lblHistoryFile = new javax.swing.JLabel();
        lblHistorySearchKeyword = new javax.swing.JLabel();
        txtArrows = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        listResult = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("File Search With Key Listeners");

        lblFileName.setText("Enter file name");

        lblSearch.setText("Search: ");

        lblSearchInfo.setText("Check Through Search History");

        lblHistoryFile.setText("File Name");

        lblHistorySearchKeyword.setText("Keywords");

        txtArrows.setEditable(false);
        txtArrows.setText("Click here. Then press left or right to check search history");

        lblDate.setText("Date");

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPane.setViewportView(listResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(lblSearchInfo)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtArrows)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDate)
                        .addGap(146, 146, 146)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHistoryFile)
                    .addComponent(lblHistorySearchKeyword)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(lblSearch)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSearch))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(lblFileName)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFileName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSearchInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHistoryFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHistorySearchKeyword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtArrows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) 
    {
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
            java.util.logging.Logger.getLogger(KeyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KeyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KeyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KeyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new KeyGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    /**
     * The label that display the node's search history date.
     */
    private javax.swing.JLabel lblDate;
    /**
     * The label that user should enter a file name.
     */
    private javax.swing.JLabel lblFileName;
    /**
     * The label that display the history file's name that user wants to check 
     * based on the node of the linked list.
     */
    private javax.swing.JLabel lblHistoryFile;
    /**
     * The label that display the history keyword that user wants to check 
     * based on the node of the linked list.
     */
    private javax.swing.JLabel lblHistorySearchKeyword;
    /**
     * The label that user should enter the keyword.
     */
    private javax.swing.JLabel lblSearch;
    /**
     * The label that shows the separation between the search history and user input to search currently.
     */
    private javax.swing.JLabel lblSearchInfo;
    /**
     * The list of found result by line from the file and keyword.
     */
    private javax.swing.JList<String> listResult;
    /**
     * The scroll pane to scroll list box vertically and hortizonally.
     */
    private javax.swing.JScrollPane scrollPane;
    /**
     * The text box that contains key listener to check through the search history.
     */
    private javax.swing.JTextField txtArrows;
    /**
     * The text box that contains key listener to check if file exists.
     */
    private javax.swing.JTextField txtFileName;
    /**
     * The text box that contains key listener to find this keyword line by line from the file.
     */
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
