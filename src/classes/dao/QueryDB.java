package classes.dao;
import java.sql.*;

public class QueryDB {
    private Connection connection;

    public QueryDB() {
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

    public void saveQuery(int queryId, String queryType) {
        String query;
        if (queryId == 0) {
            query = "INSERT INTO queries (query_type) VALUES (?)";
        } else {
            query = "UPDATE queries SET query_type = ? WHERE query_id = ?";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, queryType);
            if (queryId != 0) {
                pstmt.setInt(2, queryId);
            }
            pstmt.executeUpdate();
            if (queryId == 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("Query saved with ID: " + rs.getInt(1));
                }
            } else {
                System.out.println("Query updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error saving query: " + e.getMessage());
        }
    }

    public void getQueries() {
        String query = "SELECT * FROM queries";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Query ID: " + rs.getInt("query_id") + ", Type: " + rs.getString("query_type"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving queries: " + e.getMessage());
        }
    }
}
