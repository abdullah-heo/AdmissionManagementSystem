package classes.models;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private int adminID;
    private List<Notification> notifications;
    private List<Application> applications;

    // Constructor
    public Admin(int adminID) {
        this.adminID = adminID;
        this.notifications = new ArrayList<>();
        this.applications = new ArrayList<>();
    }

    // Getter and Setter
    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    // Method to send notifications to students
    public void sendNotification(int id, String notif, List<StudentApplicant> students) {
        Notification notification = new Notification(id, notif, students);
        notifications.add(notification);
        for (StudentApplicant student : students) {
            System.out.println("Notification sent to: " + student.getName());
        }
    }

    // Method to manage an application
    public void manageApplication(Application application, StudentApplicant std) {
        applications.add(application);
        System.out.println("Application for student " + std.getName() + " has been added.");
    }

    // View student details
    public void viewStdDetails(StudentApplicant student) {
        System.out.println("Student Details:");
        System.out.println("ID: " + student.getCnic());
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
    }

    // Track queries
    public String trackQuery() {
        return "Query tracking feature is under development.";
    }

    // Update application status
    public void updateApplicationStatus(int applicationID, String status) {
        for (Application app : applications) {
            if (app.getAppId() == applicationID) {
                app.setStatus(status);
                System.out.println("Application ID " + applicationID + " status updated to: " + status);
                return;
            }
        }
        System.out.println("Application ID not found.");
    }

    // Generate admission letter
    public void generateLetter() {
        System.out.println("Generating admission letter...");
    }
}
