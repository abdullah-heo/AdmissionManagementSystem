import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ApplicationStatus extends JFrame {

    // For demonstration, let's say the student has application ID 123
    // In practice, you might pass this appId from the StudentDashboard or fetch from DB
    private int appId = 123;

    public ApplicationStatus() {
        setTitle("Application Status");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window
        getContentPane().setLayout(null); // Absolute positioning

        // "Go Back" Button
        JButton btnGoBack = new JButton("Go Back");
        btnGoBack.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnGoBack.setForeground(Color.WHITE);
        btnGoBack.setBackground(new Color(57, 87, 155)); // Blue shade
        btnGoBack.setBounds(20, 15, 90, 30);
        btnGoBack.setFocusPainted(false);
        btnGoBack.setBorderPainted(false);
        btnGoBack.addActionListener(e -> {
            dispose(); // Close current window
            // Possibly go back to the StudentDashboard or any other screen
            // new StudentDashboard(cnic);
        });
        getContentPane().add(btnGoBack);

        // Title Label
        JLabel lblTitle = new JLabel("Your Application Status:");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setForeground(new Color(57, 87, 155)); // Blue shade
        lblTitle.setBounds(20, 60, 300, 30);
        getContentPane().add(lblTitle);

        // Panel for Details
        JPanel panel = new JPanel();
        panel.setBounds(20, 100, 540, 200);
        panel.setBackground(new Color(90, 120, 190)); // Blue background
        panel.setLayout(new GridLayout(7, 2, 5, 5)); // 7 rows, 2 columns
        getContentPane().add(panel);

        // Add each row: label on the left, value on the right
        addLabel(panel, "Application ID", createClickableAppIdLabel());
        addLabel(panel, "Student Name", "student_name");
        addLabel(panel, "Father’s Name", "father_name");
        addLabel(panel, "Program Name", "program_name");
        addLabel(panel, "Session", "Fall 2024");
        addLabel(panel, "Submission Date", "xx/xx/xxxx");
        addLabel(panel, "Approval Status", "Pending/Approved/Rejected");
    }

    /**
     * Helper method to add a row to the GridLayout panel.
     */
    private void addLabel(JPanel panel, String labelText, String valueText) {
        JLabel lblKey = new JLabel(labelText);
        lblKey.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblKey.setForeground(Color.WHITE);
        panel.add(lblKey);

        JLabel lblValue = new JLabel(valueText);
        lblValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblValue.setForeground(Color.WHITE);
        panel.add(lblValue);
    }

    /**
     * Overloaded helper method to add a row if the value is a component (like a clickable label).
     */
    private void addLabel(JPanel panel, String labelText, JComponent valueComponent) {
        JLabel lblKey = new JLabel(labelText);
        lblKey.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblKey.setForeground(Color.WHITE);
        panel.add(lblKey);

        panel.add(valueComponent);
    }

    /**
     * Creates a clickable label that shows the application ID and opens ApplicationDetails on click.
     */
    private JLabel createClickableAppIdLabel() {
        JLabel lblAppId = new JLabel(String.valueOf(appId));
        lblAppId.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAppId.setForeground(Color.WHITE);
        lblAppId.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener to open ApplicationDetails(appId)
        lblAppId.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // On click, open ApplicationDetails for this appId
                new ApplicationDetails(appId).setVisible(true);
                // If you want to close this window: dispose();
            }
        });
        return lblAppId;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ApplicationStatus frame = new ApplicationStatus();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
