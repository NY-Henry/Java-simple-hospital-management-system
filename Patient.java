/**
 * Patient.java
 * This is a subclass that inherits from the Person class.
 * It represents a patient and adds patient-specific information.
 */
public class Patient extends Person {
    // --- Attributes ---
    private String illness;
    private String admitDate;

    // --- Constructor ---
    /**
     * Constructor for a Patient object.
     * 
     * @param id        The patient's ID.
     * @param name      The patient's name.
     * @param age       The patient's age.
     * @param gender    The patient's gender.
     * @param illness   The primary illness of the patient.
     * @param admitDate The date the patient was admitted.
     */
    public Patient(String id, String name, int age, String gender, String illness, String admitDate) {
        // The 'super()' call is used to call the constructor of the parent class
        // (Person).
        // This is necessary to initialize the attributes inherited from Person.
        // It MUST be the first line in the constructor.
        super(id, name, age, gender);
        this.illness = illness;
        this.admitDate = admitDate;
    }

    // --- Getters ---
    public String getIllness() {
        return illness;
    }

    public String getAdmitDate() {
        return admitDate;
    }

    // --- Method Overriding ---
    /**
     * This method overrides the displayInfo method from the Person class
     * to provide more specific information for a Patient.
     */
    @Override
    public void displayInfo() {
        // We can call the parent's method using 'super' to avoid rewriting code.
        super.displayInfo();
        // Then we add the patient-specific details.
        System.out.println("Illness: " + illness + ", Admitted on: " + admitDate);
    }
}
