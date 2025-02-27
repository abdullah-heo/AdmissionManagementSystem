package classes.models;

import java.sql.*;

public class Query {
    private int query_id;
    private String query_type;

    public Query(int query_id, String query_type) {
        this.query_id = query_id;
        this.query_type = query_type;
    }

    public int getQuery_id() {
        return query_id;
    }

    public void setQuery_id(int query_id) {
        this.query_id = query_id;
    }

    public String getType() {
        return query_type;
    }

    public void setType(String query_type) {
        this.query_type = query_type;
    }
    
    // Sync this Query object with the DB.
    public void save(Connection conn) throws SQLException {
        if (this.query_id == 0) {
            String sql = "INSERT INTO query (q_category) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.query_type);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.query_id = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } else {
            String sql = "UPDATE query SET q_category = ? WHERE q_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.query_type);
            stmt.setInt(2, this.query_id);
            stmt.executeUpdate();
            stmt.close();
        }
    }
}
