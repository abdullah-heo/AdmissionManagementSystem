import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class StudentDashboard {
    private JFrame frame;
    private String studentCNIC; // Logged-in student's CNIC

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                StudentDashboard window = new StudentDashboard("12345-1234567-1");
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Constructor that accepts studentCNIC
    public StudentDashboard(String studentCNIC) {
        this.studentCNIC = studentCNIC;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Student Dashboard");
        frame.setSize(746, 469);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            private Image backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("images/rb_59421.png")).getImage();
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
        panel.setLayout(null);
        frame.setContentPane(panel);

        JLabel lblStudentDashboard = new JLabel("Student Dashboard");
        lblStudentDashboard.setForeground(new Color(0, 128, 192));
        lblStudentDashboard.setFont(new Font("Serif", Font.BOLD, 28));
        lblStudentDashboard.setBounds(251, 50, 249, 36);
        panel.add(lblStudentDashboard);

        JLabel lblSignedIn = new JLabel("Signed in as: " + studentCNIC);
        lblSignedIn.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSignedIn.setForeground(Color.BLACK);
        lblSignedIn.setBounds(10, 10, 250, 20);
        panel.add(lblSignedIn);

        JButton btnApply = createButton("Apply For Admission", 148, 175, 220, 56);
        panel.add(btnApply);
        btnApply.addActionListener(e -> {
            frame.dispose(); 
            new AddmissionForm(studentCNIC).setVisible(true);
        });

        JButton btnCheckStatus = createButton("Check Application Status", 396, 175, 250, 56);
        panel.add(btnCheckStatus);
        btnCheckStatus.addActionListener(e -> {
            frame.dispose(); 
            new ApplicationStatus().setVisible(true);
        });

        JButton btnContactAdmin = createButton("Contact Admin", 272, 270, 220, 56);
        panel.add(btnContactAdmin);
        btnContactAdmin.addActionListener(e -> {
            frame.dispose(); 
            new ContactAdmin(studentCNIC).setVisible(true);
        });

        JButton btnLogout = createButton("Log Out", 600, 20, 120, 40);
        panel.add(btnLogout);
        btnLogout.addActionListener(e -> {
            frame.dispose(); 
            new Login().setVisible(true);
        });

        frame.setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 128, 255));
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, width, height);
        return button;
    }
}
