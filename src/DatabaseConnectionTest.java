
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/admission_management_system"; // Replace with your DB name
        String user = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password (if any)

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Database Connected Successfully!");
                conn.close(); // Close connection after testing
            } else {
                System.out.println("Failed to connect to the database.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
