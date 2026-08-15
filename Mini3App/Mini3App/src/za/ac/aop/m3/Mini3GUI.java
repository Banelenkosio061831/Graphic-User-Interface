package za.ac.aop.m3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import za.ac.aop.processor.CallDetailProcessor; 
import za.ac.aop.processor.DetailRecord; 

/**
 *
 * @author LANGARM
 */
public class Mini3GUI extends JFrame
{
    private JPanel tab1;
    
    // TAB 1 Thingz
    private JLabel lblFileStatus;
    private JTextArea taContent;
    private JButton btnCalculateDurations;
    private JComboBox cmbProviders;
    private JCheckBox ckbFilter;
    private JButton btnFormatCountryCode;
    
    private CallDetailProcessor cdp; //add jar to resolve

    public Mini3GUI()
   {
        setTitle("Mini Assignment 3 - AOP/AOR216D");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open File");
        openItem.addActionListener(new MenuItemOpenClickListener());
        JMenuItem saveItem = new JMenuItem("Save File");
        saveItem.addActionListener(new MenuItemSaveClickListener());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new MenuItemExitClickListener());

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        Question1Tab(); //initializing tab2 in here

        // Add tabs
        tabbedPane.addTab("Q1", tab1);

        add(tabbedPane);

        setVisible(true);
    }
    
    //TAB 1 => Complete the following Event Listeners
    private void Question1Tab() {
        tab1 = new JPanel(new BorderLayout());
        taContent = new JTextArea(10, 30);
        taContent.setFont(new Font("Arial", Font.BOLD, 12));
        taContent.setEditable(false);
        taContent.setTabSize(4);
        JScrollPane scrollPane = new JScrollPane(taContent);
        
        lblFileStatus = new JLabel("File not read yet");
        lblFileStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblFileStatus.setForeground(Color.red);
        
        JPanel topPnl = new JPanel();
        topPnl.setBorder(BorderFactory.createTitledBorder("File Operation"));
        topPnl.add(lblFileStatus);
        
        JPanel bottomPnl = new JPanel(new GridLayout(1, 3, 5, 5));
        bottomPnl.setBorder(BorderFactory.createTitledBorder("Functionality"));
        
        btnCalculateDurations = new JButton("Calculate sum of durations");
        btnCalculateDurations.setBackground(Color.decode("#ccafca"));
        btnCalculateDurations.addActionListener(new BtnCalculateDurationsClickListener());
        
        String[] providers = new String[]{"Choose Provider", "Vodacom", "MTN", "CellC", "Telkom", "FNB", "ECN"};
        cmbProviders = new JComboBox(providers);
        cmbProviders.setBackground(Color.decode("#afb6cc"));
        cmbProviders.setSelectedIndex(0);
        
        ckbFilter = new JCheckBox("Apply Filter");
        ckbFilter.setBackground(Color.decode("#c0ccaf"));
        ckbFilter.addActionListener(new CkbFilterChangeListener());
        
        btnFormatCountryCode = new JButton("Format Numbers (+27)");
        btnFormatCountryCode.setBackground(Color.decode("#cccbaf"));
        btnFormatCountryCode.addActionListener(new BtnFormatNumbersClickListener());
        
        bottomPnl.add(btnCalculateDurations); bottomPnl.add(cmbProviders);
        bottomPnl.add(ckbFilter); bottomPnl.add(btnFormatCountryCode);
        
        tab1.add(topPnl, BorderLayout.NORTH);
        tab1.add(scrollPane, BorderLayout.CENTER);
        tab1.add(bottomPnl, BorderLayout.SOUTH);
        
        cdp = new CallDetailProcessor();
    }
    
    private class MenuItemOpenClickListener implements ActionListener
    {
        //Write code here
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // Display JFileChooser open dialog
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(Mini3GUI.this);
            
            
            if (result == fileChooser.APPROVE_OPTION) 
            {
                File selectedFile = fileChooser.getSelectedFile();
                
                try 
                {
                    // Clear previous content to avoid duplicates
                    taContent.setText("");
                    cdp.clearRecords();
                    
                    // Read the file using BufferedReader
                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                    String line;
                    int recordCount = 0;
                    StringBuilder displayText = new StringBuilder();
                    
                    // Add header
                    displayText.append("Date\t\tStart Time\tEnd Time\tDuration\tNumber\t\tProvider\n");
                    displayText.append("=".repeat(100)).append("\n");
                    
          
                    while ((line = reader.readLine()) != null) 
                    {
                        line = line.trim();
                        
                        // Skip empty lines
                        if (line.isEmpty()) 
                        {
                            continue;
                        }
                        
                        try
                        {
                            // convert fields into DetailRecord
                            DetailRecord record = new DetailRecord(line);
                            
                            // Add CallDetailProcessor.addDR() to add the record to the collection
                            cdp.addDR(record);
                            recordCount++;
                            
                            // Display the record in text area with tab characters and new lines
                            displayText.append(record.getDate()).append("\t")
                                      .append(record.getStartTime()).append("\t")
                                      .append(record.getEndTime()).append("\t")
                                      .append(record.getDuration()).append("\t")
                                      .append(record.getNumber()).append("\t")
                                      .append(record.getProvider()).append("\n");
                            
                        } 
                        catch (NumberFormatException|ArrayIndexOutOfBoundsException ex) 
                        {
                            // Handle malformed records and invalid numeric fields safely
                            System.err.println("Malformed record skipped: " + line);
                        }
                    }
                    
                    reader.close();
                    
                    // Display the records in the text area
                    taContent.setText(displayText.toString());
                    
                    // Update file 
                    String fileName = selectedFile.getName();
                    lblFileStatus.setText("File loaded successfully: " + recordCount + " rows");
                    lblFileStatus.setForeground(Color.blue);                  
                } 
                catch (IOException ioException)
                {
                    lblFileStatus.setText("Error reading file: " + ioException.getMessage());
                    lblFileStatus.setForeground(Color.red);
                    ioException.printStackTrace();
                }
            }
            
        }
    }
    
    private class BtnCalculateDurationsClickListener implements ActionListener
    {
        //Write code here
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            ArrayList<DetailRecord> records = cdp.getRecords();
            
            // Handle the case where no file has been loaded or no records exist
            if (records == null || records.isEmpty()) 
            {
                JOptionPane.showMessageDialog(Mini3GUI.this, 
                    "No records loaded. Please open a file first.", 
                    "No Data", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Calculate the sum of all call durations
            long totalDuration = 0;

            for (int i = 0; i < records.size(); i++) 
            {
                DetailRecord record = records.get(i);
                totalDuration += record.getDuration();
            }
            
            // Display the total using JOptionPane
            JOptionPane.showMessageDialog(Mini3GUI.this, 
                "Total call duration: " + totalDuration, 
                "Call Duration Summary", 
                JOptionPane.INFORMATION_MESSAGE);
        
        }
    }
    
    private class CkbFilterChangeListener implements ActionListener
    {
        //Write code here
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // Get all collection
            ArrayList<DetailRecord> allRecords = cdp.getRecords();
            
            // Validate  exist
            if (allRecords == null || allRecords.isEmpty())
            {
                JOptionPane.showMessageDialog(Mini3GUI.this, 
                    "No records loaded. Please open a file first.", 
                    "No Data", 
                    JOptionPane.WARNING_MESSAGE);
                ckbFilter.setSelected(false);
                return;
            }
            
            StringBuilder displayText = new StringBuilder();
            
            // Check  filter 
            if (ckbFilter.isSelected()) 
            {
                // Read the combo box
                String selectedProvider = (String) cmbProviders.getSelectedItem();
                
                // Validate selection
                if (selectedProvider.equals("Choose Provider"))
            {
                    JOptionPane.showMessageDialog(Mini3GUI.this, 
                        "Please select a provider to filter.", 
                        "Select Provider", 
                        JOptionPane.WARNING_MESSAGE);
                    ckbFilter.setSelected(false);
                    return;
                }
                
                // Add header with same formatting as complete dataset
                displayText.append("Date\t\tStart Time\tEnd Time\tDuration\tNumber\t\tProvider\n");
                displayText.append("=".repeat(100)).append("\n");
                
                // Filter and display only records matching the selected provider
                int matchCount = 0;
                for (int i = 0; i < allRecords.size(); i++) 
                {
                    DetailRecord record = allRecords.get(i);

                    if (record.getProvider().equalsIgnoreCase(selectedProvider))
                    {
                        displayText.append(record.getDate()).append("\t")
                                  .append(record.getStartTime()).append("\t")
                                  .append(record.getEndTime()).append("\t")
                                  .append(record.getDuration()).append("\t")
                                  .append(record.getNumber()).append("\t")
                                  .append(record.getProvider()).append("\n");
                        matchCount++;
                    }
                }
                
                // If no records match, show a clear message
                if (matchCount == 0)
                {
                    displayText = new StringBuilder();
                    displayText.append("No records found for provider: ").append(selectedProvider);
                }
            }
            else 
            {
                // When filter is cleared, display all records again
                displayText.append("Date\t\tStart Time\tEnd Time\tDuration\tNumber\t\tProvider\n");
                displayText.append("=".repeat(100)).append("\n");
                
                for (int i = 0; i < allRecords.size(); i++) {
                    DetailRecord record = allRecords.get(i);
                    displayText.append(record.getDate()).append("\t")
                              .append(record.getStartTime()).append("\t")
                              .append(record.getEndTime()).append("\t")
                              .append(record.getDuration()).append("\t")
                              .append(record.getNumber()).append("\t")
                              .append(record.getProvider()).append("\n");
                }
            }
            
            // Update the text area display
            taContent.setText(displayText.toString());
        }
        
    }
    
    private class BtnFormatNumbersClickListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<DetailRecord> records = cdp.getRecords();
            
            // Handle the case where no file has been loaded or no records exist
            if (records == null || records.isEmpty()) 
            {
                JOptionPane.showMessageDialog(Mini3GUI.this, 
                    "No records loaded. Please open a file first.", 
                    "No Data", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Update all phone numbers to include +27 prefix
            for (int i = 0; i < records.size(); i++) 
            {
                DetailRecord record = records.get(i);
                String phoneNumber = record.getPhoneNumber();
                
                // Check if the number starts with 0 (South African local format)
                if (phoneNumber.startsWith("0")) 
                {
                    // Remove leading zero and prefix +27
                    String formattedNumber = "+27" + phoneNumber.substring(1);
                    record.setPhoneNumber(formattedNumber);
                }
            }
            
            StringBuilder displayText = new StringBuilder();
            displayText.append("Date\t\tStart Time\tEnd Time\tDuration\tNumber\t\tProvider\n");
            displayText.append("=".repeat(100)).append("\n");
            
            for (int i = 0; i < records.size(); i++) 
            {
                DetailRecord record = records.get(i);
                displayText.append(record.getDate()).append("\t")
                          .append(record.getStartTime()).append("\t")
                          .append(record.getEndTime()).append("\t")
                          .append(record.getDuration()).append("\t")
                          .append(record.getNumber()).append("\t")
                          .append(record.getProvider()).append("\n");
            }
            
            // Update the text area with formatted numbers
            taContent.setText(displayText.toString());
            
            // Show confirmation message
            JOptionPane.showMessageDialog(Mini3GUI.this, 
                "All phone numbers have been formatted with +27 prefix.", 
                "Formatting Complete", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    private class MenuItemSaveClickListener implements ActionListener
    {
         //Write code here
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // Question 5 - Save processed records
            ArrayList<DetailRecord> records = cdp.getRecords();
            
            // Check if records exist
            if (records == null || records.isEmpty()) 
            {
                JOptionPane.showMessageDialog(Mini3GUI.this, 
                    "No records to save. Please load a file first.", 
                    "No Data", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Open JFileChooser save dialog
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(Mini3GUI.this);
            
            // Continue only when user approves save destination
            if (result == JFileChooser.APPROVE_OPTION) 
            {
                File selectedFile = fileChooser.getSelectedFile();
                
                try {
                    // Write processed records to selected file using PrintWriter and FileWriter
                    PrintWriter writer = new PrintWriter(new FileWriter(selectedFile));
                    
                    // Write records in required output format
                    for (int i = 0; i < records.size(); i++) {
                        DetailRecord record = records.get(i);
                        writer.println(record.getDate() + "," + 
                                      record.getStartTime() + "," + 
                                      record.getEndTime() + "," + 
                                      record.getDuration() + "," + 
                                      record.getPhoneNumber() + "," + 
                                      record.getProvider());
                    }
                    
                    writer.close();
                    
                    // Display  message after successful saving
                    JOptionPane.showMessageDialog(Mini3GUI.this, 
                        "Records saved successfully to: " + selectedFile.getName(), 
                        "Save Complete", 
                        JOptionPane.INFORMATION_MESSAGE);
                } 
                catch (IOException ex) 
                {
                    JOptionPane.showMessageDialog(Mini3GUI.this, 
                        "Error saving file: " + ex.getMessage(), 
                        "Save Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
    }
    
    private class MenuItemExitClickListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // Question 6 - Exit through menu
            // Ask user for confirmation before exiting
            int confirmation = JOptionPane.showConfirmDialog(Mini3GUI.this, 
                "Are you sure you want to exit?", 
                "Confirm Exit", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
            
            // Close application only when user approves
            if (confirmation == JOptionPane.YES_OPTION) 
            {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Mini3GUI::new);
    }
}
