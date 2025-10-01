/**
 * Appointment.java
 * A simple class to hold information about a single appointment.
 * This class doesn't inherit from Person as it's not a person.
 */
public class Appointment {
    // --- Attributes ---
    private String patientId;
    private String doctorId;
    private String appointmentDate;
    private String appointmentTime;

    // --- Constructor ---
    public Appointment(String patientId, String doctorId, String date, String time) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = date;
        this.appointmentTime = time;
    }

    // --- Method to display appointment details ---
    public void displayAppointment() {
        System.out.println("Appointment Details:");
        System.out.println("  Patient ID: " + patientId);
        System.out.println("  Doctor ID: " + doctorId);
        System.out.println("  Date: " + appointmentDate);
        System.out.println("  Time: " + appointmentTime);
    }
}