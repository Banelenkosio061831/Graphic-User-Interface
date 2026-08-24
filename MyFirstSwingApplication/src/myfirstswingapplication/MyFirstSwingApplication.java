package myfirstswingapplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Banele
 */

public class MyFirstSwingApplication extends JFrame
{
    private JTextField tfLearner;       //TextField
    private JSpinner spnAge;            //Spinner
    
    private JRadioButton rbtnMale;      //RadioButton
    private JRadioButton rbtnFemale;    //RadioButton
    private ButtonGroup genderGroup;    //ButtonGroup
    
    private JCheckBox ckbRespiratory;   //CheckBox
    private JCheckBox ckbFoodDrugs;     //CheckBox
    private JCheckBox ckbContact;       //CheckBox
    
    private JButton btnSave;            //Button
    private JButton btnClear;           //Button
    
    private JTextArea taOutput;         //TextArea
 
    public MyFirstSwingApplication()
    {
        // Frame setup
        setTitle("My First Swing Application");         // Title
        setSize(600, 500);                              // Size
        setLocationRelativeTo(null);                    // LocationRelativeTo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // DefaultCloseOperation     
        // Main Layout
        setLayout(new BorderLayout(10, 10));            // Layout
    
        //Step 3: Build the Learner row
        JPanel learnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Panel
        
        JLabel lblLearner = new JLabel("Learner");      // Panel
        tfLearner = new JTextField(15);                 // TextField
            
        learnerPanel.add(lblLearner);
        learnerPanel.add(tfLearner);
        
        //Step 4: Build the Age row
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel lblAge = new JLabel("Age");
        
        spnAge = new JSpinner(new SpinnerNumberModel(
                    1, //initial value
                    1, //minimum value
                    50, //maximum value
                    1   // step size
                )
        );
        
        agePanel.add(lblAge);
        agePanel.add(spnAge);
        
        
        //Step 5: Build the Gender row
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel lblGender = new JLabel("Gender"); // Gender 
        rbtnMale = new JRadioButton("Male");     // Male
        rbtnFemale = new JRadioButton("Female"); // Female
        
        genderGroup = new ButtonGroup();         // ButtonGroup
        genderGroup.add(rbtnMale);
        genderGroup.add(rbtnFemale);
        
        genderPanel.add(lblGender);
        genderPanel.add(rbtnMale);
        genderPanel.add(rbtnFemale);
    
        //Step 6: Build the Allergies row
        JPanel allergyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        //Types Allergies
        JLabel lblAllergies = new JLabel("Allergies?");
        ckbRespiratory = new JCheckBox("Respiratory");  // Respiratory
        ckbFoodDrugs = new JCheckBox("Food/Drugs");     // Food/Drugs
        ckbContact = new JCheckBox("Contact");          // Contact
        
        allergyPanel.add(lblAllergies);
        allergyPanel.add(ckbRespiratory);
        allergyPanel.add(ckbFoodDrugs);
        allergyPanel.add(ckbContact);
        
        
        //Step 7: Build the buttom row
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        btnSave = new JButton("Save Info");
        //btnSave method
        btnSave.addActionListener(new btnSaveListener());
        
        btnClear = new JButton("Clear Info");
        btnClear.addActionListener(new BtnClearListener());
              
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        
        //Step 8: Stack the five input rows
        JPanel inputPanel = new JPanel(new GridLayout(5,1));
        
        inputPanel.add(learnerPanel); //learner
        inputPanel.add(agePanel);     //age
        inputPanel.add(genderPanel);  //gender
        inputPanel.add(allergyPanel); //allergy
        inputPanel.add(buttonPanel);  //button
        
        
        //Step 9: Build the Output Section
        JPanel outputPanel = new JPanel(new BorderLayout()); //JPanel
        
        outputPanel.setBorder(BorderFactory.createMatteBorder(2,0,0,0, Color.YELLOW));
        
        JLabel lblOutput = new JLabel("Output Section"); //JLabel
        
        taOutput = new JTextArea(5,10);
        taOutput.setEditable(false);  //setEditable()
        
        taOutput.setLineWrap(false);  //setLineWrap()
        
        JScrollPane scrollPane = new JScrollPane(taOutput, 22,32); //JScrollPane
        
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // setVerticalScrollBarPolicy
        
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // setHorizontalScrollBarPolicy
        
        
        outputPanel.add(lblOutput,BorderLayout.NORTH); //BorderLayout.NORTH
        outputPanel.add(scrollPane,BorderLayout.CENTER); //BorderLayout.CENTER
        
       
        // Step 10: Assemble and show the frame
        add(inputPanel, BorderLayout.NORTH);   //Add inputPanel to the BorderLayout
        add(outputPanel, BorderLayout.CENTER); //Add outputPanel to the BorderLayout
        
         setVisible(true);
       
    }
    
    //Step 11: Save Info listener
    public class btnSaveListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          String learner = tfLearner.getText().trim();
            
          if(learner.isEmpty())
          {
            JOptionPane.showMessageDialog(MyFirstSwingApplication.this, "Please enter the learner name.");
            tfLearner.requestFocus();
            return;
           }  
          
            String gender = "";
          
            if (rbtnMale.isSelected()) 
            {
                gender = rbtnMale.getText();
            } 
            else if (rbtnFemale.isSelected()) 
            {
                gender = rbtnFemale.getText();
            }
            else 
            {
                JOptionPane.showMessageDialog(MyFirstSwingApplication.this, "Please select a gender");
                return;
            }
            
            int age = (int)spnAge.getValue();
            
            // Allergies String
            String allergies = buildAllergyText();
            
          
            
            String output =
                    "=== Learner Information ===\n" +
                    "Learner: " + learner + "\n" +
                    "Age: " + age + "\n" +
                    "Gender: " + gender + "\n" +
                    "Allergies: " + allergies.substring(0,allergies.length()-2);
            
           taOutput.setText(output);
        }
    }
    
    //Step 12: Builld the allergy text
    private String buildAllergyText()
    {
         String allergies = "";
          if (ckbRespiratory.isSelected()) 
            {
                allergies += "Respiratory, ";
            }
            
            if (ckbFoodDrugs.isSelected()) 
            {
                allergies += "Food/Drugs, ";
            }
            
            if(ckbContact.isSelected())
            {
                allergies += "Contact, ";
            }
            
            if(allergies.isEmpty())
            {
               return "None";
            }
            else
            {
                return allergies.substring(0,allergies.length()-2);
            }
    }
    
    //Step 13: Clear Inf0 Listener
    private class BtnClearListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            tfLearner.setText("");
            spnAge.setValue(1);
            genderGroup.clearSelection();
            
            ckbRespiratory.setSelected(false);
            ckbFoodDrugs.setSelected(false);
            ckbContact.setSelected(false);
            
            taOutput.setText("");
            tfLearner.requestFocus();
        }
    
    }
    
    
    public static void main(String[] args) 
    {
        // TODO code application logic here
        new MyFirstSwingApplication();
    }
    
}
