import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A simplified Hospital Management System with a clean GUI.
 * This version focuses on clarity and simplicity over advanced features.
 */
public class Hospital extends JFrame implements ActionListener {

    // --- Data Storage ---
    private final List<Patient> patientList = new ArrayList<>();
    private final List<Doctor> doctorList = new ArrayList<>();
    private static int nextPatientId = 1;
    private static int nextDoctorId = 1;

    // --- GUI Components ---
    private JTextField patientNameField, patientAgeField, patientIllnessField;
    private JComboBox<String> patientGenderComboBox;
    private JTextField doctorNameField, doctorAgeField, doctorSpecialtyField;
    private JComboBox<String> doctorGenderComboBox;
    private JTextField removeIdField;
    private JButton addPatientButton, addDoctorButton, removeButton, displayButton;
    private JTextArea displayArea;

    public Hospital() {
        // --- 1. Setup the main window ---
        setTitle("Simple Hospital Management System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // --- 2. Create the main panel with layout and padding ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- 3. Create the input forms panel ---
        JPanel formsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        String[] genderOptions = { "Male", "Female", "Rather Not Say" };

        // Patient Form
        JPanel patientPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        patientPanel.setBorder(BorderFactory.createTitledBorder("Add Patient"));
        patientNameField = new JTextField();
        patientAgeField = new JTextField();
        patientGenderComboBox = new JComboBox<>(genderOptions);
        patientIllnessField = new JTextField();
        addPatientButton = new JButton("Add Patient");
        patientPanel.add(new JLabel("Name:"));
        patientPanel.add(patientNameField);
        patientPanel.add(new JLabel("Age:"));
        patientPanel.add(patientAgeField);
        patientPanel.add(new JLabel("Gender:"));
        patientPanel.add(patientGenderComboBox);
        patientPanel.add(new JLabel("Illness:"));
        patientPanel.add(patientIllnessField);
        patientPanel.add(new JLabel("")); // Spacer
        patientPanel.add(addPatientButton);

        // Doctor Form
        JPanel doctorPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        doctorPanel.setBorder(BorderFactory.createTitledBorder("Add Doctor"));
        doctorNameField = new JTextField();
        doctorAgeField = new JTextField();
        doctorGenderComboBox = new JComboBox<>(genderOptions);
        doctorSpecialtyField = new JTextField();
        addDoctorButton = new JButton("Add Doctor");
        doctorPanel.add(new JLabel("Name:"));
        doctorPanel.add(doctorNameField);
        doctorPanel.add(new JLabel("Age:"));
        doctorPanel.add(doctorAgeField);
        doctorPanel.add(new JLabel("Gender:"));
        doctorPanel.add(doctorGenderComboBox);
        doctorPanel.add(new JLabel("Specialty:"));
        doctorPanel.add(doctorSpecialtyField);
        doctorPanel.add(new JLabel("")); // Spacer
        doctorPanel.add(addDoctorButton);

        formsPanel.add(patientPanel);
        formsPanel.add(doctorPanel);

        // --- 4. Create the display area ---
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea); // Add scrollbars

        // --- 5. Create the bottom controls panel ---
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        removeIdField = new JTextField(10);
        removeButton = new JButton("Remove by ID");
        displayButton = new JButton("Refresh Display");
        controlsPanel.add(new JLabel("ID to Remove:"));
        controlsPanel.add(removeIdField);
        controlsPanel.add(removeButton);
        controlsPanel.add(displayButton);

        // --- 6. Add all components to the main panel ---
        mainPanel.add(formsPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(controlsPanel, BorderLayout.SOUTH);

        // --- 7. Register listeners for all buttons ---
        addPatientButton.addActionListener(this);
        addDoctorButton.addActionListener(this);
        removeButton.addActionListener(this);
        displayButton.addActionListener(this);

        // Final step: add the main panel to the frame
        this.add(mainPanel);
    }

    /**
     * Central handler for all button clicks.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatientButton) {
            addPatient();
        } else if (e.getSource() == addDoctorButton) {
            addDoctor();
        } else if (e.getSource() == removeButton) {
            removeRecord();
        } else if (e.getSource() == displayButton) {
            updateDisplay();
        }
    }

    private void addPatient() {
        // Automatically generate ID
        String id = "P" + String.format("%03d", nextPatientId++);
        // Create patient object and add to list
        Patient p = new Patient(id, patientNameField.getText(), 0, (String) patientGenderComboBox.getSelectedItem(),
                patientIllnessField.getText(), "N/A");
        patientList.add(p);
        displayArea.append("SUCCESS: Added Patient " + p.getName() + " with ID " + id + "\n");
        clearPatientFields();
        updateDisplay();
    }

    private void addDoctor() {
        // Automatically generate ID
        String id = "D" + String.format("%03d", nextDoctorId++);
        // Create doctor object and add to list
        Doctor d = new Doctor(id, doctorNameField.getText(), 0, (String) doctorGenderComboBox.getSelectedItem(),
                doctorSpecialtyField.getText());
        doctorList.add(d);
        displayArea.append("SUCCESS: Added Doctor " + d.getName() + " with ID " + id + "\n");
        clearDoctorFields();
        updateDisplay();
    }

    private void removeRecord() {
        String idToRemove = removeIdField.getText().trim();
        if (idToRemove.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show confirmation dialog before deleting
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete record with ID: " + idToRemove + "?", "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return; // User clicked "No"
        }

        // Try to remove from patient list, then doctor list
        boolean removed = patientList.removeIf(p -> p.getId().equalsIgnoreCase(idToRemove));
        if (!removed) {
            removed = doctorList.removeIf(d -> d.getId().equalsIgnoreCase(idToRemove));
        }

        if (removed) {
            displayArea.append("SUCCESS: Record with ID " + idToRemove + " removed.\n");
        } else {
            displayArea.append("INFO: Record with ID " + idToRemove + " not found.\n");
        }
        removeIdField.setText("");
        updateDisplay();
    }

    private void updateDisplay() {
        displayArea.setText(""); // Clear previous content
        displayArea.append("--- PATIENTS ---\n");
        if (patientList.isEmpty()) {
            displayArea.append("No patients registered.\n");
        } else {
            for (Patient p : patientList) {
                displayArea.append(String.format("ID: %s, Name: %s, Gender: %s, Illness: %s\n", p.getId(), p.getName(),
                        p.getGender(), p.getIllness()));
            }
        }

        displayArea.append("\n--- DOCTORS ---\n");
        if (doctorList.isEmpty()) {
            displayArea.append("No doctors registered.\n");
        } else {
            for (Doctor d : doctorList) {
                displayArea.append(String.format("ID: %s, Name: %s, Gender: %s, Specialty: %s\n", d.getId(),
                        d.getName(), d.getGender(), d.getSpecialty()));
            }
        }
    }

    // Helper methods to clear input fields after submission
    private void clearPatientFields() {
        patientNameField.setText("");
        patientAgeField.setText("");
        patientGenderComboBox.setSelectedIndex(0);
        patientIllnessField.setText("");
    }

    private void clearDoctorFields() {
        doctorNameField.setText("");
        doctorAgeField.setText("");
        doctorGenderComboBox.setSelectedIndex(0);
        doctorSpecialtyField.setText("");
    }

    public static void main(String[] args) {
        // Ensures the GUI is created on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> new Hospital().setVisible(true));
    }
}