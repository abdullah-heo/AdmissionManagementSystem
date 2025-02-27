package classes.dao;

import java.sql.*;
import models.Payments;
import models.StudentApplicant;

public class StudentApplicantDB {
    private static final String URL = "jdbc:mysql://localhost:3306/admission_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void registerStudent(StudentApplicant student) {
        String sql = "INSERT INTO student (cnic, name, gender, email, phone_number, address, password, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getCnic());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getGender());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getPhoneNumber());
            stmt.setString(6, student.getAddress());
            stmt.setString(7, student.getPassword());
            stmt.setInt(8, student.getAge());

            stmt.executeUpdate();
            System.out.println("Student registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error registering student.");
        }
    }
    
    public void applyForAdmission(StudentApplicant student) {
        String sql = "UPDATE student SET application_status = ? WHERE cnic = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Applied");
            stmt.setString(2, student.getCnic());
            stmt.executeUpdate();
            System.out.println("Application submitted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkAdmissionStatus(StudentApplicant student) {
        String sql = "SELECT application FROM student_applicant WHERE cnic = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getCnic());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Admission status: " + rs.getString("application_status"));
            } else {
                System.out.println("No admission application found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void payFee(StudentApplicant student, Payments payment) {
        String sql = "UPDATE student SET fee_status = ? WHERE cnic = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Paid");
            stmt.setString(2, student.getCnic());
            stmt.executeUpdate();
            System.out.println("Fee paid successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readNotifications(StudentApplicant student) {
        String sql = "SELECT message FROM notifications WHERE student_cnic = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getCnic());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Notification: " + rs.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuery(StudentApplicant student, String query) {
        String sql = "INSERT INTO queries (student_cnic, query) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getCnic());
            stmt.setString(2, query);
            stmt.executeUpdate();
            System.out.println("Query added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StudentApplicant getCnic(String cnic) throws SQLException {
        String query = "SELECT * FROM student WHERE cnic = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cnic);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new StudentApplicant(
                        rs.getString("cnic"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("password"),
                        rs.getInt("age"));
            }
        }
        return null; // Return null if no record is found
    }

}