package classes.dao;

import java.sql.*;
public class ConfirmationLetterDB {
    private Connection connection;

    public ConfirmationLetterDB() {
        connectDatabase();
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "password");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    public void saveConfirmationLetter(int letterId, int studentId) {
        String query = "INSERT INTO confirmation_letters (letter_id, student_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, letterId);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
            System.out.println("Confirmation letter saved.");
        } catch (SQLException e) {
            System.err.println("Error saving confirmation letter: " + e.getMessage());
        }
    }

    public void getConfirmationLetter(int studentId) {
        String query = "SELECT * FROM confirmation_letters WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Letter ID: " + rs.getInt("letter_id") + " for Student ID: " + rs.getInt("student_id"));
            } else {
                System.out.println("No confirmation letter found for student.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving confirmation letter: " + e.getMessage());
        }
    }
}
