package classes.models;

import java.sql.*;

public class Payments {
    private int payment_id;
    private double amount;
    private String payment_method;
    private Application application; // Assumes Application has getAppId()

    public Payments(int payment_id, double amount, String payment_method, Application application) {
        this.payment_id = payment_id;
        this.amount = amount;
        this.payment_method = payment_method;
        this.application = application;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    
    // Sync this Payments object with the DB.
    public void save(Connection conn) throws SQLException {
        if (this.payment_id == 0) {
            String sql = "INSERT INTO payment (app_id, amount, pay_method, pay_status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, this.application.getAppId());
            stmt.setDouble(2, this.amount);
            stmt.setString(3, this.payment_method);
            stmt.setString(4, "Pending");
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.payment_id = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } else {
            String sql = "UPDATE payment SET app_id = ?, amount = ?, pay_method = ?, pay_status = ? WHERE pay_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.application.getAppId());
            stmt.setDouble(2, this.amount);
            stmt.setString(3, this.payment_method);
            stmt.setString(4, "Pending");
            stmt.setInt(5, this.payment_id);
            stmt.executeUpdate();
            stmt.close();
        }
    }
}
