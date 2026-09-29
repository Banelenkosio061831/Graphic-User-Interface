package equipmentviewerframegui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * @author Banele
 */
public class EquipmentViewerFrameGUI extends JFrame
{
    // Step 1: Declare UI components and database constants
    private JTable tblEquipment;
    private DefaultTableModel tableModel;
    private JButton btnViewEquipmet;
    private JButton btnClearTable;
    private JLabel lblStatus;

    //Ensure to Add the Liabraric of Java DB Driver on Apache Netbeans
   
    //Ensure to Right-Clik and Execute Command using the database provide in Table under APP
    
    // Database connection details
    private static final String url = "jdbc:derby://localhost:1527/LabDB";
    private static final String user = "app";
    private static final String password = "app";

    public EquipmentViewerFrameGUI()
    {
        // Step 2: Configure the Frame layout
        setTitle("Equipment Database Viewer");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Step 3: Set up DefaultTableModel and JTable
        String[] colums = {
            "ID", "Name", "Department", "Purchase Date", "Condition", "In_Use", "Price"
        };

        // Initialize model with zero rows and custom non-editable cells
        tableModel = new DefaultTableModel(colums, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent manual editing in cells
            }
        };

        tblEquipment = new JTable(tableModel);

        // Step 4: Wrap JTable inside JScrollPane for headers and scrolling support
        JScrollPane tableScrollPane = new JScrollPane(tblEquipment);
        add(tableScrollPane, BorderLayout.CENTER);

        // Step 5: Construct the Top Control Panel (Buttons & Status)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnViewEquipmet = new JButton("View Equipment");
        btnClearTable = new JButton("Clear Table");
        lblStatus = new JLabel("Status: Not connected");

        controlPanel.add(btnViewEquipmet);
        controlPanel.add(btnClearTable);
        controlPanel.add(lblStatus);

        add(controlPanel, BorderLayout.NORTH);

        // Step 6: Register Action Listeners
        btnViewEquipmet.addActionListener(new BtnViewEquipmentClickListener());
        btnClearTable.addActionListener(new BtnClearTableClickListener());
    }

    // Step 7: Listener for executing JDBC query and displaying data
    private class BtnViewEquipmentClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Clear existing table model rows to prevent duplicates on multiple clicks
            tableModel.setRowCount(0);

            String sql = "SELECT EQUIPMENT_ID, NAME, DEPARTMENT, PURCHASE_DATE, CONDITION, IN_USE, PRICE "
                       + "FROM APP.LAB_EQUIPMENT "
                       + "ORDER BY EQUIPMENT_ID";

            // Use try-with-resources for safe database resource cleanup
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 Statement statement = connection.createStatement();
                 ResultSet results = statement.executeQuery(sql)) {

                // Loop through ResultSet and append rows to the table model
                while (results.next()) {
                    // ID - Fixed: removed the trailing dot from "EQUIPMENT_ID."
                    int id = results.getInt("EQUIPMENT_ID");

                    String name = results.getString("NAME");
                    String department = results.getString("DEPARTMENT");
                    Date purchaseDate = results.getDate("PURCHASE_DATE");
                    String condition = results.getString("CONDITION");
                    boolean inUSe = results.getBoolean("IN_USE");
                    double price = results.getDouble("PRICE");

                    Object[] row = {id, name, department, purchaseDate, condition, inUSe, price};
                    tableModel.addRow(row);
                }

                lblStatus.setText("Status: Data loaded successfully.");
            } catch (SQLException ex) {
                lblStatus.setText("Status: Connection or query failed.");

                // Fixed: Changed showInternalMessageDialog to showMessageDialog
                JOptionPane.showMessageDialog(EquipmentViewerFrameGUI.this,
                        "Database Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Step 8: Listener for clearing visible Swing rows (Does NOT delete DB records)
    private class BtnClearTableClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tableModel.setRowCount(0);
            lblStatus.setText("Status: Visible table cleared.");
        }
    }

    // Step 9: Application entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EquipmentViewerFrameGUI().setVisible(true);
        });
    }
}