package classes.dao;
import java.sql.*;

public class DocumentsDB {
    private Connection connection;

    public DocumentsDB() {
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

    public void saveDocument(int docId, String docType, int applicationId) {
        String query = "INSERT INTO documents (doc_id, doc_type, application_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, docId);
            pstmt.setString(2, docType);
            pstmt.setInt(3, applicationId);
            pstmt.executeUpdate();
            System.out.println("Document saved.");
        } catch (SQLException e) {
            System.err.println("Error saving document: " + e.getMessage());
        }
    }

    public void getDocuments(int applicationId) {
        String query = "SELECT * FROM documents WHERE application_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, applicationId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Document ID: " + rs.getInt("doc_id") + ", Type: " + rs.getString("doc_type"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving documents: " + e.getMessage());
        }
    }
}
