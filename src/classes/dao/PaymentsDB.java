package classes.dao;
import java.sql.*;

public class PaymentsDB {
    private Connection connection;

    public PaymentsDB() {
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

    public void savePayment(int paymentId, double amount, String paymentMethod, int applicationId) {
        String query = "INSERT INTO payments (payment_id, amount, payment_method, application_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, paymentId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, paymentMethod);
            pstmt.setInt(4, applicationId);
            pstmt.executeUpdate();
            System.out.println("Payment recorded successfully.");
        } catch (SQLException e) {
            System.err.println("Error recording payment: " + e.getMessage());
        }
    }

    public void getPayments(int applicationId) {
        String query = "SELECT * FROM payments WHERE application_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, applicationId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Payment ID: " + rs.getInt("payment_id") + ", Amount: " + rs.getDouble("amount") + ", Method: " + rs.getString("payment_method"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving payments: " + e.getMessage());
        }
    }
}
