import java.util.ArrayList; // We need to import ArrayList to use it.
import java.util.List;

/**
 * Doctor.java
 * This is another subclass that inherits from Person.
 * It represents a doctor and manages their appointments.
 */
public class Doctor extends Person {
    // --- Attributes ---
    private String specialty;
    // This is an example of Composition. A Doctor "has a" list of appointments.
    // We use a List (specifically, an ArrayList) to hold the appointments.
    private List<Appointment> appointments;

    // --- Constructor ---
    public Doctor(String id, String name, int age, String gender, String specialty) {
        // Call the parent constructor
        super(id, name, age, gender);
        this.specialty = specialty;
        // It's important to initialize the list in the constructor.
        this.appointments = new ArrayList<>();
    }

    // --- Getters ---
    public String getSpecialty() {
        return specialty;
    }

    // --- Methods to manage appointments ---
    public void addAppointment(Appointment appt) {
        this.appointments.add(appt);
        System.out.println("Successfully booked an appointment for Dr. " + getName());
    }

    public void viewAppointments() {
        System.out.println("\n--- Appointments for Dr. " + getName() + " (" + specialty + ") ---");
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            for (Appointment appt : appointments) {
                appt.displayAppointment();
                System.out.println("---");
            }
        }
    }
}
