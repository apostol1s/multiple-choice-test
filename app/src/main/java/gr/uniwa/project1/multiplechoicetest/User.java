package gr.uniwa.project1.multiplechoicetest;

public class User {

    private static final User INSTANCE = new User();
    private int id;
    private String lastname;
    private String firstname;
    private String academicID;
    public User() {

    }

    public User(int id, String lastName, String firstName, String academicID) {
        this.id = id;
        this.lastname = lastName;
        this.firstname = firstName;
        this.academicID = academicID;
    }

    public static synchronized User getInstance() {
        return INSTANCE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAcademicID() {
        return academicID;
    }

    public void setAcademicID(String academicID) {
        this.academicID = academicID;
    }
}