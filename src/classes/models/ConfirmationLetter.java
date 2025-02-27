package classes.models;

import java.sql.*;

public class ConfirmationLetter {
    private int letter_id;
    private StudentApplicant student; // Assumes Student has getCnic() if needed

    public ConfirmationLetter(int letter_id, StudentApplicant student) {
        this.letter_id = letter_id;
        this.student = student;
    }

    ConfirmationLetter(String admission_Confirmed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getLetter_id() {
        return letter_id;
    }

    public void setLetter_id(int letter_id) {
        this.letter_id = letter_id;
    }

    public StudentApplicant getStudent() {
        return student;
    }

    public void setStudent(StudentApplicant student) {
        this.student = student;
    }
    
    // Sync this ConfirmationLetter with the DB.
    // NOTE: The SQL table expects an app_id and status.
    // Here we use a placeholder app_id (0) and a default "Confirmed" status.
    public void save(Connection conn) throws SQLException {
        int appId = 0; // In a full implementation, derive this from the student's application.
        String status = "Confirmed";
        
        if (this.letter_id == 0) {
            String sql = "INSERT INTO confirmation_letter (app_id, status) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, appId);
            stmt.setString(2, status);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.letter_id = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } else {
            String sql = "UPDATE confirmation_letter SET app_id = ?, status = ? WHERE letter_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, appId);
            stmt.setString(2, status);
            stmt.setInt(3, this.letter_id);
            stmt.executeUpdate();
            stmt.close();
        }
    }
}
