package classes.dao;
import java.sql.*;
import models.*;
class AdminDB {
    private int adminID;
    private Connection connection;

    // Constructor
    public AdminDB(int adminID) {
        this.adminID = adminID;
        connectDatabase();
    }

    // Database Connection
    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "password");
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    // Close connection
    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    // Send notification (store in DB)
    public void sendNotification(String notif, StudentApplicant std) {
        String query = "INSERT INTO notifications (student_id, message) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, std.getCnic());
            pstmt.setString(2, notif);
            pstmt.executeUpdate();
            System.out.println("Notification sent to: " + std.getName());
        } catch (SQLException e) {
            System.err.println("Error sending notification: " + e.getMessage());
        }
    }

    // Manage application (Insert/Update in DB)
    public void manageApplication(Application application, StudentApplicant std) {
        String query = "INSERT INTO application (app_id, student_id, app_status) VALUES (?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE app_status = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, application.getAppId());
            pstmt.setString(2, std.getCnic());
            pstmt.setString(3, application.getStatus());
            pstmt.setString(4, application.getStatus());
            pstmt.executeUpdate();
            System.out.println("Application for student " + std.getName() + " managed successfully.");
        } catch (SQLException e) {
            System.err.println("Error managing application: " + e.getMessage());
        }
    }

    // View student details
    public void viewStdDetails(int studentID) {
        String query = "SELECT * FROM student WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student details: " + e.getMessage());
        }
    }

    // Track student queries
    public String trackQuery(int studentID) {
        String query = "SELECT query_text FROM queries WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return "Student Query: " + rs.getString("query_text");
            } else {
                return "No query found for this student.";
            }
        } catch (SQLException e) {
            return "Error fetching query: " + e.getMessage();
        }
    }

    // Update application status in DB
    public void updateApplicationStatus(int appID, String newStatus) {
        String query = "UPDATE application SET app_status = ? WHERE app_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, appID);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Application ID " + appID + " status updated to: " + newStatus);
            } else {
                System.out.println("Application ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating application status: " + e.getMessage());
        }
    }

    // Generate admission letter
    public void generateLetter(int studentID) {
        String query = "SELECT * FROM student WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Generating admission letter for " + rs.getString("name"));
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error generating letter: " + e.getMessage());
        }
    }

   // Set Admission Deadline
public void setAdmissionDeadline(String deadlineDate) {
    String query = "INSERT INTO admission_deadlines (deadline_date) VALUES (?) " +
                   "ON DUPLICATE KEY UPDATE deadline_date = VALUES(deadline_date)";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setString(1, deadlineDate);
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Admission deadline set to: " + deadlineDate);
        } else {
            System.out.println("Failed to update admission deadline.");
        }
    } catch (SQLException e) {
        System.err.println("Error setting admission deadline: " + e.getMessage());
    }
}

// Get Admission Deadline
public String getAdmissionDeadline() {
    String query = "SELECT deadline_date FROM admission_deadlines ORDER BY id DESC LIMIT 1";
    try (PreparedStatement pstmt = connection.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
            return "Current Admission Deadline: " + rs.getString("deadline_date");
        } else {
            return "No admission deadline set.";
        }
    } catch (SQLException e) {
        return "Error retrieving admission deadline: " + e.getMessage();
    }
}

    // Close connection
    public void close() {
        closeConnection();
    }
}

