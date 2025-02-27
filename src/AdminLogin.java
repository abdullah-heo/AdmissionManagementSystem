import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class AdminLogin extends JFrame {
    private JFormattedTextField txtCNIC;
    private JPasswordField txtPassword;

    public AdminLogin() {
        setTitle("SAMS - Admin Sign In");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null); // Center the window on screen

        JLabel lblLogo = new JLabel("SAMS");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblLogo.setBounds(50, 20, 200, 40);
        add(lblLogo);

        JLabel lblSubtext = new JLabel("SCHOOL OF ARTS");
        lblSubtext.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubtext.setBounds(50, 60, 200, 20);
        add(lblSubtext);

        JLabel lblHelp = new JLabel("Need help? Email us at admission@sams.com");
        lblHelp.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblHelp.setBounds(50, 85, 350, 20);
        add(lblHelp);

        JLabel lblSignIn = new JLabel("Admin Sign in");
        lblSignIn.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblSignIn.setBounds(50, 120, 200, 30);
        add(lblSignIn);

        JLabel lblCNIC = new JLabel("Enter CNIC *");
        lblCNIC.setBounds(50, 180, 250, 20);
        add(lblCNIC);
        try {
            // Create MaskFormatter for CNIC: 5 digits, hyphen, 7 digits, hyphen, 1 digit
            MaskFormatter cnicFormatter = new MaskFormatter("#####-#######-#");
            cnicFormatter.setPlaceholderCharacter('_');
            txtCNIC = new JFormattedTextField(cnicFormatter);
        } catch (ParseException pe) {
            pe.printStackTrace();
            txtCNIC = new JFormattedTextField();
        }
        txtCNIC.setBounds(50, 200, 300, 30);
        add(txtCNIC);

        JLabel lblPassword = new JLabel("Enter password *");
        lblPassword.setBounds(50, 240, 250, 20);
        add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(50, 260, 300, 30);
        add(txtPassword);

        JButton btnSignIn = new JButton("Sign in");
        btnSignIn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setBackground(new Color(41, 98, 255));
        btnSignIn.setBounds(50, 310, 150, 40);
        add(btnSignIn);

        JLabel lblForgot = new JLabel("<html><a href='#'>Forgot Password?</a></html>");
        lblForgot.setBounds(220, 320, 150, 20);
        lblForgot.setForeground(Color.BLUE);
        lblForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(lblForgot);

        JLabel lblStudentLogin = new JLabel("<html><a href='#'>Login as student</a></html>");
        lblStudentLogin.setBounds(50, 360, 150, 20);
        lblStudentLogin.setForeground(Color.BLUE);
        lblStudentLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(lblStudentLogin);

        // Sign in button action
        btnSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cnic = txtCNIC.getText();
                String password = new String(txtPassword.getPassword());
                
                // Validate CNIC format by checking for placeholder characters
                if(cnic.contains("_")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid CNIC in the format xxxxx-xxxxxxx-x.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (validateAdminLogin(cnic, password)) {  
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    AdminDashboard dashboard = new AdminDashboard(); // Open Admin Dashboard
                    dashboard.setVisible(true);
                    dispose(); // Close login window
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid CNIC or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Student login redirection
        lblStudentLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Login studentLogin = new Login();
                studentLogin.setVisible(true);
                dispose();
            }
        });

        // Forgot password redirection
        lblForgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ForgotPassword forgotPassword = new ForgotPassword();
                forgotPassword.setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    // Method to validate admin login using the student table
    private boolean validateAdminLogin(String cnic, String password) {
        boolean isValid = false;
        String url = "jdbc:mysql://localhost:3306/admission_management_system"; // Update with your database name
        String user = "root"; // Update with your database username
        String pass = ""; // Update with your database password

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM student WHERE cnic = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, cnic);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isValid;
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}
