package classes.models;

import classes.dao.StudentApplicantDB;
import java.sql.*;

// User Class (Base Class)
class User {
    private String cnic;
    private String email;
    private String password;

    public User(String cnic, String email, String password) {
        this.cnic = cnic;
        this.email = email;
        this.password = password;
    }

    public void login(String email, String password) {
        String sql = "SELECT * FROM student_applicant WHERE email = ? AND password = ?";
        try (Connection conn = StudentApplicantDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("User logged in successfully.");
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        System.out.println("User logged out successfully.");
    }

    public void resetPassword(String newPassword) {
        String sql = "UPDATE student_applicant SET password = ? WHERE cnic = ?";
        try (Connection conn = StudentApplicantDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, this.cnic);
            stmt.executeUpdate();
            System.out.println("Password reset successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCnic() {
        return cnic;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}