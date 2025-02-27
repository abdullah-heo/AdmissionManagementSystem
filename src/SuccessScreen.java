import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SuccessScreen extends JFrame {
    private String studentCNIC;

    public SuccessScreen(String studentCNIC) {
        this.studentCNIC = studentCNIC;

        setTitle("Success");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        // Success Icon (✓ inside a circle)
        JLabel lblIcon = new JLabel("\u2713"); // Unicode for checkmark ✓
        lblIcon.setFont(new Font("Arial", Font.BOLD, 50));
        lblIcon.setForeground(new Color(30, 136, 229)); // Blue color
        gbc.gridy = 0;
        panel.add(lblIcon, gbc);

        // "Successful" Label
        JLabel lblSuccess = new JLabel("Successful");
        lblSuccess.setFont(new Font("Tahoma", Font.BOLD, 22));
        gbc.gridy = 1;
        panel.add(lblSuccess, gbc);

        // Subtext Label
        JLabel lblSubtext = new JLabel("Your query has been submitted");
        lblSubtext.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSubtext.setForeground(Color.GRAY);
        gbc.gridy = 2;
        panel.add(lblSubtext, gbc);

        // Continue Button
        JButton btnContinue = new JButton("CONTINUE");
        btnContinue.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnContinue.setBackground(new Color(30, 136, 229)); // Blue color
        btnContinue.setForeground(Color.WHITE);
        btnContinue.setFocusPainted(false);
        btnContinue.setBorderPainted(false);
        btnContinue.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 3;
        panel.add(btnContinue, gbc);

        // Button Click Event
        btnContinue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the success screen
                new StudentDashboard(studentCNIC); // Go back to Student Dashboard
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SuccessScreen frame = new SuccessScreen("12345"); // Testing
            frame.setVisible(true);
        });
    }
}
