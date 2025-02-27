package classes.dao;

import java.sql.*;

public class NotificationDB {
    private Connection connection;

    public NotificationDB() {
        connectDatabase();
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    public void sendNotification(int notificationId, int studentId, String message) {
        String query = "INSERT INTO notifications (notification_id, student_id, message) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, notificationId);
            pstmt.setInt(2, studentId);
            pstmt.setString(3, message);
            pstmt.executeUpdate();
            System.out.println("Notification sent.");
        } catch (SQLException e) {
            System.err.println("Error sending notification: " + e.getMessage());
        }
    }

    public void getNotifications(int studentId) {
        String query = "SELECT * FROM notifications WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Notification ID: " + rs.getInt("notification_id") + " - Message: " + rs.getString("message"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving notifications: " + e.getMessage());
        }
    }
}
