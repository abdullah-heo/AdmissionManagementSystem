import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ForgotPassword extends JFrame {
    public ForgotPassword() {
        setTitle("SAMS - Forgot Password");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);  // Center the window on screen

        // Logo and Heading
        JLabel lblLogo = new JLabel("SAMS", SwingConstants.LEFT);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblLogo.setBounds(50, 20, 200, 40);
        add(lblLogo);
        
        JLabel lblSubHeading = new JLabel("SCHOOL OF ARTS");
        lblSubHeading.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubHeading.setBounds(50, 60, 200, 20);
        add(lblSubHeading);
        
        JLabel lblHelp = new JLabel("Need Help? Email us at techsupport@sams.edu.pk");
        lblHelp.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblHelp.setForeground(Color.BLUE);
        lblHelp.setBounds(50, 85, 400, 20);
        add(lblHelp);

        // Forgot Password Title
        JLabel lblTitle = new JLabel("Forgot Password");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setBounds(50, 130, 250, 30);
        add(lblTitle);

        // Email/Phone/CNIC Input
        JLabel lblInput = new JLabel("Enter email/phone no/ CNIC *");
        lblInput.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblInput.setBounds(50, 180, 250, 20);
        add(lblInput);

        JTextField txtInput = new JTextField();
        txtInput.setBounds(50, 200, 400, 35);
        add(txtInput);
        
        // Send Pin Button
        JButton btnSendPin = new JButton("Send Pin");
        btnSendPin.setBounds(50, 260, 120, 40);
        btnSendPin.setBackground(new Color(60, 90, 255));
        btnSendPin.setForeground(Color.WHITE);
        btnSendPin.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(btnSendPin);

        // Back to Login Button
        JButton btnBack = new JButton("Back to Login");
        btnBack.setBounds(200, 260, 150, 40);
        btnBack.setBackground(Color.GRAY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(btnBack);

        // Event Listeners
        btnSendPin.addActionListener(e -> {
            String userInput = txtInput.getText().trim();
            if (userInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your email, phone, or CNIC!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "")) {
                // Query the student table using CNIC, email, or phone number
                String query = "SELECT * FROM student WHERE cnic = ? OR email = ? OR phone_no = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, userInput);
                ps.setString(2, userInput);
                ps.setString(3, userInput);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "A reset PIN has been sent to your registered contact.");
                    new ResetPassword(); // Open Reset Password window
                    dispose(); // Close Forgot Password window
                } else {
                    JOptionPane.showMessageDialog(null, "No account found with the provided information. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBack.addActionListener(e -> {
            new AdminLogin(); // Navigate back to login
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ForgotPassword();
    }
}
