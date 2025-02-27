package classes.models;

import java.sql.*;

public class Documents {
    private int doc_id;
    private String doc_type;
    private Application application; // Assumes Application has getAppId()

    public Documents(int doc_id, String doc_type, Application application) {
        this.doc_id = doc_id;
        this.doc_type = doc_type;
        this.application = application;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    
    // Sync this Documents object with the DB.
    public void save(Connection conn) throws SQLException {
        if (this.doc_id == 0) {
            String sql = "INSERT INTO document (app_id, type) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, this.application.getAppId());
            stmt.setString(2, this.doc_type);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.doc_id = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } else {
            String sql = "UPDATE document SET app_id = ?, type = ? WHERE doc_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.application.getAppId());
            stmt.setString(2, this.doc_type);
            stmt.setInt(3, this.doc_id);
            stmt.executeUpdate();
            stmt.close();
        }
    }
}
