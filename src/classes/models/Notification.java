package classes.models;

import java.sql.*;
import java.util.List;

public class Notification {
    private int notificationId;
    private String msg;
    private List<StudentApplicant> recipients; // Assumes Student has getCnic()

    public Notification(int notificationId, String msg, List<StudentApplicant> recipients) {
        this.notificationId = notificationId;
        this.msg = msg;
        this.recipients = recipients;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public List<StudentApplicant> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<StudentApplicant> recipients) {
        this.recipients = recipients;
    }
    
    // Sync this Notification with the DB.
    // For simplicity, we only process the first recipient.
    public void save(Connection conn) throws SQLException {
        if (recipients == null || recipients.isEmpty()) {
            throw new SQLException("No recipients available for the notification.");
        }
        StudentApplicant recipient = recipients.get(0);
        
        if (this.notificationId == 0) {
            String sql = "INSERT INTO notification (cnic, message, noti_status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, recipient.getCnic());
            stmt.setString(2, this.msg); // Using the msg field
            stmt.setString(3, "Unread");
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.notificationId = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } else {
            String sql = "UPDATE notification SET message = ?, noti_status = ? WHERE noti_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.msg); // Using the msg field
            stmt.setString(2, "Unread");
            stmt.setInt(3, this.notificationId);
            stmt.executeUpdate();
            stmt.close();
        }
    }
}
