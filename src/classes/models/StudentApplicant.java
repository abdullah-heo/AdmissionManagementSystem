package classes.models;

import classes.dao.StudentApplicantDB;
import java.util.ArrayList;
import java.util.List;

// StudentApplicant Class
public class StudentApplicant extends User {
    private String name;
    private String gender;
    private String address;
    private String phoneNumber;
    private int age;
    private Application applications;
    private List<Query> queries;
    private Payments payments;
    private List<Notification> notifications;

    public StudentApplicant(String cnic, String email, String password, String name, String gender,
            String address, String phoneNumber, int age) {
        super(cnic, email, password);
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.queries = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    @Override
    public String getCnic() {
        // TODO Auto-generated method stub
        return super.getCnic();
    }

    public void register() {
        StudentApplicantDB db = new StudentApplicantDB();
        db.registerStudent(this);
    }

    public void applyForAdmission(Application application) {
        this.applications = application;
        StudentApplicantDB db = new StudentApplicantDB();
        db.applyForAdmission(this);
    }

    public void checkAdmissionStatus() {
        StudentApplicantDB db = new StudentApplicantDB();
        db.checkAdmissionStatus(this);
    }

    public void payFee(StudentApplicant student, Payments payment) {
        StudentApplicantDB db = new StudentApplicantDB();
        db.payFee(student, payment);
    }

    public ConfirmationLetter viewConfirmationLetter() {
        if (applications != null && applications.isAccepted()) {
            System.out.println("Viewing confirmation letter.");
            return new ConfirmationLetter("Admission Confirmed");
        }
        System.out.println("No confirmation letter available.");
        return null;
    }

    public void readNotification() {
        StudentApplicantDB db = new StudentApplicantDB();
        db.readNotifications(this);
    }

    public void addQuery(int id, String query) {
        queries.add(new Query(id, query));
        StudentApplicantDB db = new StudentApplicantDB();
        db.addQuery(this, query);
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAge() {
        return age;
    }

}

// Database Class
