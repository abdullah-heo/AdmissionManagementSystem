import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.sql.*;

public class NewPassword extends JFrame {
    private JFormattedTextField txtCNIC;  // CNIC field using a mask
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public NewPassword() {
        setTitle("SAMS - New Password");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Logo Label
        JLabel logoLabel = new JLabel("SAMS");
        logoLabel.setFont(new Font("Arial Black", Font.BOLD, 30));
        logoLabel.setBounds(40, 20, 200, 40);
        add(logoLabel);

        // Title Label
        JLabel resetLabel = new JLabel("Reset Password");
        resetLabel.setFont(new Font("Serif", Font.BOLD, 18));
        resetLabel.setBounds(40, 80, 200, 25);
        add(resetLabel);

        // CNIC Input Label and Field using MaskFormatter
        JLabel lblCNIC = new JLabel("Enter CNIC *");
        lblCNIC.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCNIC.setBounds(40, 120, 200, 20);
        add(lblCNIC);
        
        try {
            MaskFormatter cnicFormatter = new MaskFormatter("#####-#######-#");
            cnicFormatter.setPlaceholderCharacter('_');
            txtCNIC = new JFormattedTextField(cnicFormatter);
        } catch (ParseException pe) {
            pe.printStackTrace();
            txtCNIC = new JFormattedTextField();
        }
        txtCNIC.setBounds(40, 140, 350, 30);
        add(txtCNIC);

        // New Password Label and Field
        JLabel newPasswordLabel = new JLabel("New Password *");
        newPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        newPasswordLabel.setBounds(40, 180, 200, 20);
        add(newPasswordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(40, 200, 350, 30);
        add(passwordField);
        
        // Confirm Password Label and Field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password *");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setBounds(40, 240, 200, 20);
        add(confirmPasswordLabel);
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(40, 260, 350, 30);
        add(confirmPasswordField);
        
        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(40, 320, 100, 40);
        saveButton.setBackground(new Color(63, 81, 181));
        saveButton.setForeground(Color.WHITE);
        add(saveButton);
        
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cnic = txtCNIC.getText().trim();
                String newPassword = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                
                // Validate that both fields are not empty
                if (cnic.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter CNIC, new password, and confirm password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Validate CNIC format
                if (cnic.contains("_")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid CNIC in the format xxxxx-xxxxxxx-x.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Validate that passwords match
                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Update the student's password in the database if the student exists
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "");
                    String queryCheck = "SELECT * FROM student WHERE cnic = ?";
                    PreparedStatement psCheck = conn.prepareStatement(queryCheck);
                    psCheck.setString(1, cnic);
                    ResultSet rs = psCheck.executeQuery();
                    
                    if (rs.next()) {
                        String queryUpdate = "UPDATE student SET password = ? WHERE cnic = ?";
                        PreparedStatement psUpdate = conn.prepareStatement(queryUpdate);
                        psUpdate.setString(1, newPassword);
                        psUpdate.setString(2, cnic);
                        int rowsAffected = psUpdate.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Your password has been successfully reset.");
                            // Instead of redirecting to AdminLogin, show the success screen.
                            new ResetPasswordSuccessful();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        psUpdate.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "No user found with the entered CNIC.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    rs.close();
                    psCheck.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new NewPassword();
    }
}
