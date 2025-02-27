package classes.models;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private int app_id;
    private String status;
    private StudentApplicant student;
    private Payments payment;
    private ConfirmationLetter confirmationLetter;
    private List<Documents> documents;
    private List<Query> queries;

    // Constructor
    public Application(int app_id, String status, StudentApplicant student) {
        this.app_id = app_id;
        this.status = status;
        this.student = student;
        this.payment = null;
        this.confirmationLetter = null;
        this.documents = new ArrayList<>();
        this.queries = new ArrayList<>();
    }

    // Getters
    public int getAppId() {
        return app_id;
    }

    public String getStatus() {
        return status;
    }

    public StudentApplicant getStudent() {
        return student;
    }

    public Payments getPayment() {
        return payment;
    }

    public ConfirmationLetter getConfirmationLetter() {
        return confirmationLetter;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public List<Query> getQueries() {
        return queries;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setPayment(Payments payment) {
        this.payment = payment;
    }

    public void setConfirmationLetter(ConfirmationLetter confirmationLetter) {
        this.confirmationLetter = confirmationLetter;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    // Upload Document
    public void uploadDocs(Documents doc) {
        if (doc != null) {
            documents.add(doc);
            System.out.println("Document uploaded successfully.");
        } else {
            System.out.println("Invalid document.");
        }
    }

    public boolean isAccepted() {
        return "Accepted".equalsIgnoreCase(this.status);
    }


    // Make Payment
    public void makePayment(Payments payment) {
        if (this.payment == null && payment != null) {
            this.payment = payment;
            System.out.println("Payment successful.");
        } else {
            System.out.println("Payment already exists or invalid.");
        }
    }

    // Add Query
    public void addQuery(Query query) {
        if (query != null) {
            queries.add(query);
            System.out.println("Query added successfully.");
        } else {
            System.out.println("Invalid query.");
        }
    }

    // Display Application Details
    public void displayApplicationDetails() {
        System.out.println("Application ID: " + app_id);
        System.out.println("Status: " + status);
        System.out.println("Student: " + (student != null ? student.getName() : "Not Assigned"));
        System.out.println("Payment: " + (payment != null ? "Paid" : "Not Paid"));
        System.out.println("Confirmation Letter: " + (confirmationLetter != null ? "Received" : "Not Received"));
        System.out.println("Documents Uploaded: " + documents.size());
        System.out.println("Queries Raised: " + queries.size());
    }
}
