import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResetPasswordSuccessful {
    private JFrame frame;
    
    public ResetPasswordSuccessful() {
        frame = new JFrame("Success");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window
        frame.getContentPane().setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        // Icon (if available)
        JLabel iconLabel = new JLabel(new ImageIcon("check_icon.png")); // Replace with actual icon path
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Success message labels
        JLabel lblSuccess = new JLabel("Successful");
        lblSuccess.setFont(new Font("Serif", Font.BOLD, 24));
        lblSuccess.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSuccess.setForeground(new Color(50, 50, 150));
        
        JLabel lblMessage = new JLabel("Your password has been reset successfully");
        lblMessage.setFont(new Font("Serif", Font.PLAIN, 16));
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMessage.setForeground(new Color(100, 100, 100));
        
        // Continue button to go back to Login page
        JButton btnContinue = new JButton("CONTINUE");
        btnContinue.setPreferredSize(new Dimension(150, 40));
        btnContinue.setBackground(new Color(10, 60, 120));
        btnContinue.setForeground(Color.WHITE);
        btnContinue.setFocusPainted(false);
        btnContinue.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnContinue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the success screen
                new Login().setVisible(true); // Open the login page
            }
        });
        
        // Assemble components in the panel
        panel.add(Box.createVerticalStrut(50));
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblSuccess);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblMessage);
        panel.add(Box.createVerticalStrut(30));
        panel.add(btnContinue);
        
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new ResetPasswordSuccessful();
    }
}
