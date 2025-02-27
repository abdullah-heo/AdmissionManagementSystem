import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class Login extends JFrame {
    private JFormattedTextField txtCNIC;
    private JPasswordField txtPassword;

    public Login() {
        setTitle("SAMS - Sign In");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);

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

        JLabel lblSignIn = new JLabel("Student Sign in");
        lblSignIn.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblSignIn.setBounds(50, 120, 200, 30);
        add(lblSignIn);

        JLabel lblCNIC = new JLabel("Enter admission form no./CNIC *");
        lblCNIC.setBounds(50, 180, 250, 20);
        add(lblCNIC);

        try {
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

        btnSignIn.addActionListener(e -> {
            String cnic = txtCNIC.getText();
            String password = new String(txtPassword.getPassword());
            if(cnic.contains("_")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid CNIC in the format xxxxx-xxxxxxx-x.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (validateLogin(cnic, password)) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                dispose();
                new StudentDashboard(cnic);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid CNIC or Password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        JLabel lblForgot = new JLabel("<html><a href='#'>Forgot Password?</a></html>");
        lblForgot.setBounds(220, 320, 150, 20);
        lblForgot.setForeground(Color.BLUE);
        lblForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblForgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ForgotPassword().setVisible(true);
                dispose();
            }
        });
        add(lblForgot);

        JLabel lblAdminLogin = new JLabel("<html><a href='#'>Login as Admin</a></html>");
        lblAdminLogin.setBounds(50, 360, 150, 20);
        lblAdminLogin.setForeground(Color.BLUE);
        lblAdminLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblAdminLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AdminLogin().setVisible(true);
                dispose();
            }
        });
        add(lblAdminLogin);

        JLabel lblRegister = new JLabel("<html>New student? <a href='#'>Register here</a></html>");
        lblRegister.setBounds(220, 360, 200, 20);
        lblRegister.setForeground(Color.BLUE);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Register();
                dispose();
            }
        });
        add(lblRegister);

        setVisible(true);
    }

    private boolean validateLogin(String cnic, String password) {
        String query = "SELECT * FROM student WHERE cnic = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cnic);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        new Login();
    }
}
