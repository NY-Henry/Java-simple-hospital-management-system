/**
 * Person.java
 * This is the superclass for all people in the hospital system.
 * It holds common attributes like ID, name, age, and gender.
 * This demonstrates the principle of code reuse.
 */
public class Person {
    // --- Attributes ---
    // These are 'private' to enforce encapsulation. They can only be accessed
    // through the public getter and setter methods.
    private String id;
    private String name;
    private int age;
    private String gender;

    // --- Constructor ---
    /**
     * The constructor is a special method used to initialize a new Person object.
     * 
     * @param id     The person's unique identifier.
     * @param name   The person's full name.
     * @param age    The person's age.
     * @param gender The person's gender.
     */
    public Person(String id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // --- Getters and Setters ---
    // These public methods provide controlled access to the private attributes.

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    // A method to display person's info, can be overridden by subclasses.
    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Gender: " + gender);
    }
}
