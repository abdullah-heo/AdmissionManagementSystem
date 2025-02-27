package classes.dao;

import java.sql.*;
import models.Application;
import models.StudentApplicant;

public class ApplicationDB {
    private static final String URL = "jdbc:mysql://localhost:3306/admission_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public void insertApplication(Application application) {
        String sql = "INSERT INTO application (app_id, cnic, app_status) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, application.getAppId());
            stmt.setString(2, application.getStudent().getCnic());
            stmt.setString(3, application.getStatus());
            stmt.executeUpdate();
            System.out.println("Application inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error inserting application: " + e.getMessage());
        }
    }

    public Application getApplicationById(int appId) {
        String sql = "SELECT * FROM application WHERE app_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                StudentApplicantDB studentDB = new StudentApplicantDB();
                StudentApplicant student = studentDB.getCnic(rs.getString("cnic"));
                return new Application(
                        rs.getInt("app_id"),
                        rs.getString("app_status"),
                        student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error retrieving application: " + e.getMessage());
        }
        return null;
    }

    public void updateApplicationStatus(int appId, String newStatus) {
        String sql = "UPDATE application SET app_status = ? WHERE app_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, appId);
            stmt.executeUpdate();
            System.out.println("Application status updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating application status: " + e.getMessage());
        }
    }
}
