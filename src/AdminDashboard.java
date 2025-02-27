import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);  // Center the window
        setLayout(null);

        // Background Panel with Image
        JPanel panel = new JPanel() {
            private Image backgroundImage = new ImageIcon(getClass().getResource("/images/rb_59421.png")).getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Error: Background image not found!");
                }
            }
        };
        panel.setBounds(0, 0, 800, 500);
        panel.setLayout(null);
        panel.setOpaque(false);
        setContentPane(panel);

        // Logo (Top Left)
        JLabel lblLogo = new JLabel("SAMS");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 30));
        lblLogo.setForeground(Color.DARK_GRAY);
        lblLogo.setBounds(30, 10, 120, 40);
        panel.add(lblLogo);

        // Logout Button (Top Right)
        JButton btnLogout = createButton("Log Out", 680, 15, 90, 30, e -> {
            dispose(); // Close Admin Dashboard
            new AdminLogin().setVisible(true); // Return to Admin Login
        });
        panel.add(btnLogout);

        // Admin Dashboard Title (Centered near top)
        JLabel lblTitle = new JLabel("Admin Dashboard");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 28));
        lblTitle.setBounds(300, 60, 300, 40);
        panel.add(lblTitle);

        // Main Buttons
        // Left Column: Manage Deadlines
        JButton btnManageDeadlines = createButton("Manage Deadlines", 100, 200, 200, 60, e -> {
            new AdmissionDeadline().setVisible(true);
            dispose(); // Close current dashboard
        });
        panel.add(btnManageDeadlines);

        // Middle Column: Send Notification
        JButton btnSendNotification = createButton("Send Notification", 320, 200, 200, 60, e -> new NotifyStudents(this));
        panel.add(btnSendNotification);

        // Right Column: Pending Applications
        JButton btnPendingApplications = createButton("Pending Applications", 540, 200, 200, 60, e -> {
            new PendingApplications();
        });
        panel.add(btnPendingApplications);

        setVisible(true);
    }

    // Overloaded method to create buttons with specified bounds and action
    private JButton createButton(String text, int x, int y, int width, int height, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 82, 204));
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, width, height);
        button.addActionListener(action);
        return button;
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
