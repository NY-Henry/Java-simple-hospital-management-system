import javax.swing.*; // Import for Swing GUI components (JFrame, JButton, etc.)
import javax.swing.border.EmptyBorder; // Import for creating border spacing in the UI
import javax.swing.table.DefaultTableModel; // Import for creating data models for tables
import javax.swing.table.TableCellRenderer; // Import for rendering table cells with custom components
import java.awt.*; // Import for AWT components like GridLayout
import java.awt.event.ActionEvent; // Import for handling action events (button clicks)
import java.awt.event.ActionListener; // Import for listening to action events
import java.util.ArrayList; // Import for using ArrayList data structure
import java.util.List; // Import for using List interface

/**
 * Hospital.java
 * 
 * This class is the main application class that creates and manages the
 * hospital system GUI.
 * It extends JFrame to create a window and implements ActionListener to handle
 * button clicks.
 * 
 * Key OOP concepts demonstrated in this class:
 * - Inheritance: Extends JFrame to create a GUI window
 * - Interface Implementation: Implements ActionListener to handle events
 * - Composition: Contains lists of Doctor and Patient objects
 * - Encapsulation: Uses private fields with public methods to access them
 * - Inner Classes: Defines ButtonRenderer and ButtonEditor for table
 * functionality
 */
public class Hospital extends JFrame implements ActionListener {

    // --- Data Storage ---
    // ArrayLists to store Patient and Doctor objects - demonstrates the use of
    // collections
    private List<Patient> patientList = new ArrayList<>(); // List to store all patients in the system
    private List<Doctor> doctorList = new ArrayList<>(); // List to store all doctors in the system

    // Static counters for generating unique IDs - incremented each time a new
    // patient or doctor is added
    // Static means these variables belong to the class, not to instances of the
    // class
    private static int nextPatientId = 1; // Counter for generating sequential patient IDs
    private static int nextDoctorId = 1; // Counter for generating sequential doctor IDs

    // --- Patient Form UI Components ---
    private JTextField patientNameField, patientAgeField, patientIllnessField; // Text fields for patient input
    private JComboBox<String> patientGenderComboBox; // Dropdown for selecting patient gender

    // --- Doctor Form UI Components ---
    private JTextField doctorNameField, doctorAgeField, doctorSpecialtyField; // Text fields for doctor input
    private JComboBox<String> doctorGenderComboBox; // Dropdown for selecting doctor gender

    // --- Action Buttons ---
    private JButton addPatientButton, addDoctorButton; // Buttons to add new patients and doctors

    // --- Table Components ---
    private JTable patientTable, doctorTable; // Tables to display patient and doctor data
    private DefaultTableModel patientTableModel, doctorTableModel; // Data models for the tables

    // --- Logging Component ---
    private JTextArea logArea; // Text area to display system logs and error messages

    /**
     * Constructor for the Hospital class.
     * This initializes the GUI and sets up all the components.
     * The constructor is called when a new Hospital object is created.
     */
    public Hospital() {
        // Set the application to use the system's native look and feel
        // This makes the application look more integrated with the operating system
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace(); // Print any errors that occur during this process
        }

        // Configure the main application window properties
        setTitle("Hospital Management System"); // Set the title of the window
        setSize(1000, 800); // Set the window size (width, height)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the window is closed
        setLocationRelativeTo(null); // Center the window on the screen

        // Create the main container panel with BorderLayout (organizes components in
        // five areas: north, south, east, west, center)
        // The 10, 10 parameters add 10px spacing between border areas
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add 10px padding around all sides

        // --- Input Forms Panel ---
        // Create a panel to hold both input forms using GridLayout (1 row, 2 columns)
        // GridLayout organizes components in a grid of equally sized cells
        JPanel formsPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // 10px spacing between columns and rows

        // Define gender options for dropdown menus
        String[] genderOptions = { "Male", "Female", "Rather Not Say" };

        // --- Patient Input Form ---
        // Create a panel for patient input with GridLayout (5 rows, 2 columns)
        JPanel patientPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5px spacing between components
        patientPanel.setBorder(BorderFactory.createTitledBorder("Add New Patient")); // Add a titled border

        // Initialize all input components for patient form
        patientNameField = new JTextField(); // Text field for patient name
        patientAgeField = new JTextField(); // Text field for patient age
        patientGenderComboBox = new JComboBox<>(genderOptions); // Dropdown for gender selection
        patientIllnessField = new JTextField(); // Text field for patient illness
        addPatientButton = new JButton("Add Patient"); // Button to add the patient

        // Add components to the panel in label-field pairs (5 rows x 2 columns)
        patientPanel.add(new JLabel("Name:")); // Label in column 1
        patientPanel.add(patientNameField); // Field in column 2
        patientPanel.add(new JLabel("Age:"));
        patientPanel.add(patientAgeField);
        patientPanel.add(new JLabel("Gender:"));
        patientPanel.add(patientGenderComboBox);
        patientPanel.add(new JLabel("Illness:"));
        patientPanel.add(patientIllnessField);
        patientPanel.add(new JLabel("")); // Empty label for spacing
        patientPanel.add(addPatientButton); // Button in last row

        // --- Doctor Input Form ---
        // Create a panel for doctor input using the same GridLayout structure as the
        // patient panel
        JPanel doctorPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5px spacing between components
        doctorPanel.setBorder(BorderFactory.createTitledBorder("Add New Doctor")); // Add a titled border

        // Initialize all input components for doctor form
        doctorNameField = new JTextField(); // Text field for doctor name
        doctorAgeField = new JTextField(); // Text field for doctor age
        doctorGenderComboBox = new JComboBox<>(genderOptions); // Dropdown for gender selection
        doctorSpecialtyField = new JTextField(); // Text field for doctor specialty
        addDoctorButton = new JButton("Add Doctor"); // Button to add the doctor

        // Add components to the panel in label-field pairs (5 rows x 2 columns)
        doctorPanel.add(new JLabel("Name:")); // Label in column 1
        doctorPanel.add(doctorNameField); // Field in column 2
        doctorPanel.add(new JLabel("Age:"));
        doctorPanel.add(doctorAgeField);
        doctorPanel.add(new JLabel("Gender:"));
        doctorPanel.add(doctorGenderComboBox);
        doctorPanel.add(new JLabel("Specialty:"));
        doctorPanel.add(doctorSpecialtyField);
        doctorPanel.add(new JLabel("")); // Empty label for spacing
        doctorPanel.add(addDoctorButton); // Button in last row

        // Add both panels to the forms container panel
        formsPanel.add(patientPanel); // Patient panel on the left
        formsPanel.add(doctorPanel); // Doctor panel on the right

        // --- Table Display Panel with Tabs ---
        // JTabbedPane creates multiple tabs to organize different tables
        // This is a UI improvement that saves space and organizes content better
        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Patient Table Setup ---
        // Define the column headers for the patient table
        String[] patientColumns = { "ID", "Name", "Gender", "Illness", "Action" };

        // Create a table model with the defined columns and 0 initial rows
        // DefaultTableModel manages the data and structure of the table
        patientTableModel = new DefaultTableModel(patientColumns, 0) {
            // Override this method to control which cells can be edited by the user
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only allow editing the "Action" column (for delete buttons)
                                    // This is column index 4 (0-based indexing)
            }
        };
        // Create the table with the model we defined
        patientTable = new JTable(patientTableModel);

        // Configure the Action column to display and handle buttons
        // Custom renderer makes the cell appear as a button
        patientTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        // Custom editor handles the button click actions (in this case, for deleting
        // patients)
        patientTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), "Patient"));

        // Add the patient table to the first tab, wrapped in a scroll pane to handle
        // overflow
        tabbedPane.addTab("Patients", new JScrollPane(patientTable));

        // --- Doctor Table Setup ---
        // Similar structure as the patient table, but with doctor-specific columns
        String[] doctorColumns = { "ID", "Name", "Gender", "Specialty", "Action" };

        // Create doctor table model with the same editability rules
        doctorTableModel = new DefaultTableModel(doctorColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only allow editing the "Action" column (for delete buttons)
            }
        };
        // Create the doctor table with its model
        doctorTable = new JTable(doctorTableModel);

        // Configure the Action column for the doctor table (same as for patient table)
        doctorTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        doctorTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), "Doctor"));

        // Add the doctor table to the second tab, also wrapped in a scroll pane
        tabbedPane.addTab("Doctors", new JScrollPane(doctorTable));

        // --- Log Area for System Messages ---
        // Create a text area with 5 rows and 20 columns for displaying system messages
        logArea = new JTextArea(5, 20);
        logArea.setEditable(false); // Prevent user from editing the log messages

        // Wrap the log area in a scroll pane so it can scroll when there are many
        // messages
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(BorderFactory.createTitledBorder("Logs")); // Add a titled border

        // --- Assemble the Main Layout ---
        // Add components to the main panel using BorderLayout positions
        mainPanel.add(formsPanel, BorderLayout.NORTH); // Input forms at the top
        mainPanel.add(tabbedPane, BorderLayout.CENTER); // Tables in the center (largest area)
        mainPanel.add(logScrollPane, BorderLayout.SOUTH); // Log area at the bottom

        // Register action listeners for the buttons
        // 'this' refers to this Hospital object, which implements ActionListener
        addPatientButton.addActionListener(this);
        addDoctorButton.addActionListener(this);

        // Add the main panel to the JFrame (the window)
        this.add(mainPanel);
    }

    /**
     * This method is part of the ActionListener interface implementation.
     * It's called automatically when a registered button is clicked.
     * The method determines which button was clicked and calls the appropriate
     * method.
     * 
     * @param e The ActionEvent containing information about the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Determine which button was clicked and call the appropriate handler method
        if (e.getSource() == addPatientButton) {
            addPatient(); // Call the method to add a new patient
        } else if (e.getSource() == addDoctorButton) {
            addDoctor(); // Call the method to add a new doctor
        }
    }

    /**
     * Method to add a new patient to the system.
     * It retrieves input from form fields, validates them, creates a Patient
     * object,
     * and adds it to the patientList.
     */
    private void addPatient() {
        try {
            // Generate unique patient ID with format P001, P002, etc.
            // The %03d ensures 3 digits with leading zeros if needed
            String id = "P" + String.format("%03d", nextPatientId++);

            // Get values from input fields and validate them
            String name = patientNameField.getText();
            // Basic validation: Check if name is not empty
            if (name.isEmpty()) {
                logArea.append("ERROR: Patient Name cannot be empty.\n");
                return; // Exit the method early if validation fails
            }

            // Parse the age text field as an integer
            // This might throw NumberFormatException if the input is not a valid number
            int age = Integer.parseInt(patientAgeField.getText());

            // Get selected gender from dropdown
            String gender = (String) patientGenderComboBox.getSelectedItem();
            String illness = patientIllnessField.getText();

            // Create a new Patient object using the Patient constructor
            // This demonstrates object creation and constructor use
            Patient newPatient = new Patient(id, name, age, gender, illness, "N/A");

            // Add the new patient to our collection (ArrayList)
            patientList.add(newPatient);

            // Log the successful operation
            logArea.append("SUCCESS: Added Patient " + name + " with ID: " + id + "\n");

            // Reset the form fields for next input
            clearPatientFields();

            // Update the tables to display the new patient
            refreshTables();

        } catch (NumberFormatException ex) {
            // Handle the case where age is not a valid number
            logArea.append("ERROR: Age must be a valid number.\n");
        }
    }

    /**
     * Method to add a new doctor to the system.
     * Similar to addPatient, but creates a Doctor object instead.
     * This method demonstrates parallel structure and code organization.
     */
    private void addDoctor() {
        try {
            // Generate unique doctor ID with format D001, D002, etc.
            String id = "D" + String.format("%03d", nextDoctorId++);

            // Get values from input fields and validate them
            String name = doctorNameField.getText();
            // Basic validation: Check if name is not empty
            if (name.isEmpty()) {
                logArea.append("ERROR: Doctor Name cannot be empty.\n");
                return; // Exit the method early if validation fails
            }

            // Parse the age text field as an integer
            int age = Integer.parseInt(doctorAgeField.getText());

            // Get selected gender from dropdown
            String gender = (String) doctorGenderComboBox.getSelectedItem();
            String specialty = doctorSpecialtyField.getText();

            // Create a new Doctor object using the Doctor constructor
            // Note the different parameter list compared to Patient constructor
            Doctor newDoctor = new Doctor(id, name, age, gender, specialty);

            // Add the new doctor to our collection
            doctorList.add(newDoctor);

            // Log the successful operation
            logArea.append("SUCCESS: Added Doctor " + name + " with ID: " + id + "\n");

            // Reset the form fields for next input
            clearDoctorFields();

            // Update the tables to display the new doctor
            refreshTables();

        } catch (NumberFormatException ex) {
            // Handle the case where age is not a valid number
            logArea.append("ERROR: Age must be a valid number.\n");
        }
    }

    /**
     * Refreshes both tables by clearing them and adding all current data.
     * This method is called whenever the data changes (after adding or deleting
     * records).
     * It demonstrates how to update a GUI based on the current state of data.
     */
    private void refreshTables() {
        // --- Update Patient Table ---
        // First clear the existing rows from the table
        patientTableModel.setRowCount(0);

        // For each Patient in our list, add a new row to the table
        for (Patient p : patientList) {
            // Create an array of objects representing one row
            // Get data using the getter methods from the Patient class
            patientTableModel.addRow(new Object[] {
                    p.getId(), // Column 1: ID
                    p.getName(), // Column 2: Name
                    p.getGender(), // Column 3: Gender
                    p.getIllness(), // Column 4: Illness
                    "Delete" // Column 5: Action button text
            });
        }

        // --- Update Doctor Table ---
        // Similar process for doctor table
        doctorTableModel.setRowCount(0);

        for (Doctor d : doctorList) {
            doctorTableModel.addRow(new Object[] {
                    d.getId(), // Column 1: ID
                    d.getName(), // Column 2: Name
                    d.getGender(), // Column 3: Gender
                    d.getSpecialty(), // Column 4: Specialty
                    "Delete" // Column 5: Action button text
            });
        }
    }

    /**
     * Clears all patient input fields to prepare for the next entry.
     * This improves user experience by resetting the form after an action.
     */
    private void clearPatientFields() {
        patientNameField.setText(""); // Clear name field
        patientAgeField.setText(""); // Clear age field
        patientGenderComboBox.setSelectedIndex(0); // Reset gender dropdown to first option
        patientIllnessField.setText(""); // Clear illness field
    }

    /**
     * Clears all doctor input fields to prepare for the next entry.
     * Similar to clearPatientFields, but for doctor form.
     */
    private void clearDoctorFields() {
        doctorNameField.setText(""); // Clear name field
        doctorAgeField.setText(""); // Clear age field
        doctorGenderComboBox.setSelectedIndex(0); // Reset gender dropdown to first option
        doctorSpecialtyField.setText(""); // Clear specialty field
    }

    /**
     * Inner class for rendering buttons in table cells.
     * This demonstrates the use of inner classes for related functionality.
     * This class extends JButton and implements TableCellRenderer interface.
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructor for the ButtonRenderer.
         * Sets the button to be opaque so it will show its background color.
         */
        public ButtonRenderer() {
            setOpaque(true); // Make button background visible
        }

        /**
         * This method is called by the JTable to render the cell.
         * It configures how the button should look in the table.
         * 
         * @param table      The JTable containing the button
         * @param value      The value to display (button text)
         * @param isSelected Whether the cell is selected
         * @param hasFocus   Whether the cell has focus
         * @param row        The row index of the cell
         * @param column     The column index of the cell
         * @return The configured button component
         */
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            // Set the text of the button to the value in the cell ("Delete")
            setText((value == null) ? "" : value.toString());
            return this; // Return the configured button
        }
    }

    /**
     * Inner class for handling button clicks in table cells.
     * This class extends DefaultCellEditor to create an interactive button in the
     * table.
     * It handles the actions when the "Delete" button is clicked in the table.
     */
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button; // The button component displayed in the cell
        private String label; // Text displayed on the button
        private boolean isPushed; // Tracks if the button has been clicked
        private String type; // Specifies if this is for "Patient" or "Doctor" table
        private int selectedRow; // Keeps track of which row was clicked

        /**
         * Constructor for ButtonEditor.
         * 
         * @param checkBox A checkbox component (required by DefaultCellEditor)
         * @param type     The type of data this editor handles ("Patient" or "Doctor")
         */
        public ButtonEditor(JCheckBox checkBox, String type) {
            super(checkBox); // Call parent constructor with the checkbox
            this.type = type; // Store the type ("Patient" or "Doctor")

            // Create and configure the button
            button = new JButton();
            button.setOpaque(true);

            // Add an ActionListener using lambda expression
            // This calls fireEditingStopped() when the button is clicked
            button.addActionListener(e -> fireEditingStopped());
        }

        /**
         * This method is called when the cell is selected for editing.
         * It configures how the editor (button) should appear.
         * 
         * @param table      The JTable containing the button
         * @param value      The value in the cell (button text)
         * @param isSelected Whether the cell is selected
         * @param row        The row index of the cell
         * @param column     The column index of the cell
         * @return The configured button component
         */
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            this.selectedRow = row; // Store which row was clicked for later use

            // Set the button text
            label = (value == null) ? "" : value.toString();
            button.setText(label);

            isPushed = true; // Mark the button as pushed
            return button; // Return the configured button
        }

        /**
         * This method is called after editing is complete (button is clicked).
         * It handles the action that should be performed when the button is clicked.
         * 
         * @return The value to store in the cell after editing
         */
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Show a confirmation dialog before deleting
                // This improves user experience by preventing accidental deletions
                int confirmation = JOptionPane.showConfirmDialog(button,
                        "Are you sure you want to delete this " + type + "?", "Delete Confirmation",
                        JOptionPane.YES_NO_OPTION);

                // Only proceed if the user confirmed the deletion
                if (confirmation == JOptionPane.YES_OPTION) {
                    // Handle differently based on whether it's a Patient or Doctor
                    if (type.equals("Patient")) {
                        // Get the ID from the first column of the selected row
                        String patientId = (String) patientTableModel.getValueAt(selectedRow, 0);

                        // Remove the patient with matching ID from the list
                        // This uses a lambda expression with the removeIf method
                        patientList.removeIf(p -> p.getId().equals(patientId));

                        // Log the successful operation
                        logArea.append("SUCCESS: Removed patient with ID " + patientId + "\n");
                    } else {
                        // Similar process for doctors
                        String doctorId = (String) doctorTableModel.getValueAt(selectedRow, 0);
                        doctorList.removeIf(d -> d.getId().equals(doctorId));
                        logArea.append("SUCCESS: Removed doctor with ID " + doctorId + "\n");
                    }

                    // Update the tables to reflect the changes
                    refreshTables();
                }
            }

            // Reset the button state and return the label
            isPushed = false;
            return label;
        }

        /**
         * This method is called when cell editing is stopped.
         * It resets the button state and calls the parent method.
         * 
         * @return true if editing was stopped successfully
         */
        @Override
        public boolean stopCellEditing() {
            isPushed = false; // Reset button state
            return super.stopCellEditing(); // Call parent method
        }
    }

    /**
     * The main method - entry point of the application.
     * Creates and displays the Hospital GUI.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures that the GUI is created on the
        // Event Dispatch Thread, which is the proper thread for GUI operations
        SwingUtilities.invokeLater(() -> {
            // Create a new Hospital object (which is a JFrame)
            // The lambda expression creates the Hospital and makes it visible
            new Hospital().setVisible(true);
        });
    }
}