import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.sql.*;

public class Register extends JFrame {

    private JFormattedTextField txtCNIC;
    private JTextField txtFullName;
    private JComboBox<String> cbGender;
    private JTextField txtEmail;
    private JFormattedTextField txtPhone;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;

    public Register() {
        setTitle("SAMS - Register");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null); // Center the window

        // ─────────────────────────────────────────────────────────
        //  HEADER SECTION (Logo, Subheading, Links)
        // ─────────────────────────────────────────────────────────
        JLabel lblLogo = new JLabel("SAMS", SwingConstants.LEFT);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblLogo.setBounds(50, 20, 200, 40);
        add(lblLogo);

        JLabel lblSubHeading = new JLabel("SCHOOL OF ARTS");
        lblSubHeading.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubHeading.setBounds(50, 60, 200, 20);
        add(lblSubHeading);

        JLabel lblLogin = new JLabel("<html>Already have an account? <a href='#'>Login Here.</a></html>");
        lblLogin.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblLogin.setBounds(50, 85, 300, 20);
        lblLogin.setForeground(Color.BLUE);
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(lblLogin);

        JLabel lblHelp = new JLabel("Need Help? Email us at admissions@sams.edu.pk");
        lblHelp.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblHelp.setBounds(50, 105, 300, 20);
        add(lblHelp);

        // ─────────────────────────────────────────────────────────
        //  LEFT COLUMN (CNIC, Full Name, Email, Phone)
        // ─────────────────────────────────────────────────────────

        // Row 1: CNIC
        JLabel lblCNIC = new JLabel("<html>CNIC <font color='red'>*</font></html>");
        lblCNIC.setBounds(50, 140, 150, 20);
        add(lblCNIC);
        try {
            MaskFormatter cnicFormatter = new MaskFormatter("#####-#######-#");
            cnicFormatter.setPlaceholderCharacter('_');
            txtCNIC = new JFormattedTextField(cnicFormatter);
        } catch (ParseException pe) {
            pe.printStackTrace();
            txtCNIC = new JFormattedTextField();
        }
        txtCNIC.setBounds(50, 160, 250, 30);
        add(txtCNIC);

        // Row 2: Full Name
        JLabel lblFullName = new JLabel("<html>Full Name <font color='red'>*</font></html>");
        lblFullName.setBounds(50, 200, 150, 20);
        add(lblFullName);
        txtFullName = new JTextField();
        txtFullName.setBounds(50, 220, 250, 30);
        add(txtFullName);

        // Row 3: Email
        JLabel lblEmail = new JLabel("<html>Email <font color='red'>*</font></html>");
        lblEmail.setBounds(50, 260, 150, 20);
        add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(50, 280, 250, 30);
        add(txtEmail);

        // Row 4: Phone
        JLabel lblPhone = new JLabel("<html>Phone <font color='red'>*</font></html>");
        lblPhone.setBounds(50, 320, 150, 20);
        add(lblPhone);
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("####-#######");
            phoneFormatter.setPlaceholderCharacter('_');
            txtPhone = new JFormattedTextField(phoneFormatter);
        } catch (ParseException pe) {
            pe.printStackTrace();
            txtPhone = new JFormattedTextField();
        }
        txtPhone.setBounds(50, 340, 250, 30);
        add(txtPhone);

        // ─────────────────────────────────────────────────────────
        //  RIGHT COLUMN (Gender, Password, Confirm Password, Register)
        // ─────────────────────────────────────────────────────────

        // Row 1: Gender
        JLabel lblGender = new JLabel("<html>Gender <font color='red'>*</font></html>");
        lblGender.setBounds(400, 140, 150, 20);
        add(lblGender);
        String[] genders = {"Male", "Female", "Other"};
        cbGender = new JComboBox<>(genders);
        cbGender.setBounds(400, 160, 250, 30);
        add(cbGender);

        // Row 2: Create Password
        JLabel lblPassword = new JLabel("<html>Create Password <font color='red'>*</font></html>");
        lblPassword.setBounds(400, 200, 150, 20);
        add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(400, 220, 250, 30);
        add(txtPassword);

        // Row 3: Confirm Password
        JLabel lblConfirmPassword = new JLabel("<html>Confirm Password <font color='red'>*</font></html>");
        lblConfirmPassword.setBounds(400, 260, 150, 20);
        add(lblConfirmPassword);
        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(400, 280, 250, 30);
        add(txtConfirmPassword);

        // Row 4: Register Button
        JButton btnSignUp = new JButton("Register");
        btnSignUp.setBounds(400, 340, 250, 30);
        btnSignUp.setBackground(new Color(60, 90, 255));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(btnSignUp);

        // ─────────────────────────────────────────────────────────
        //  CLICKABLE LOGIN LINK
        // ─────────────────────────────────────────────────────────
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Login();
            }
        });

        // ─────────────────────────────────────────────────────────
        //  REGISTER BUTTON ACTION
        // ─────────────────────────────────────────────────────────
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cnic = txtCNIC.getText().trim();
                String fullName = txtFullName.getText().trim();
                String email = txtEmail.getText().trim();
                String phone = txtPhone.getText().trim();
                String gender = cbGender.getSelectedItem().toString();
                String password = new String(txtPassword.getPassword());
                String confirmPassword = new String(txtConfirmPassword.getPassword());

                // Validate non-empty fields
                if(cnic.isEmpty() || fullName.isEmpty() || email.isEmpty() ||
                   phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate CNIC format
                if(cnic.contains("_")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid CNIC in the format xxxxx-xxxxxxx-x.", "Invalid CNIC", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate email format (basic regex)
                if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate phone number format
                if(phone.contains("_")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid phone number in the format xxxx-xxxxxxx.", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate that passwords match
                if(!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If all validations pass, insert the data into the student table
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "")) {
                    String sql = "INSERT INTO student (cnic, name, gender, email, phone_no, password) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, cnic);
                    ps.setString(2, fullName);
                    ps.setString(3, gender);
                    ps.setString(4, email);
                    ps.setString(5, phone);
                    ps.setString(6, password);

                    int rowsInserted = ps.executeUpdate();
                    if(rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Registration successful!");
                        dispose();
                        new Login();
                    } else {
                        JOptionPane.showMessageDialog(null, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Register();
    }
}
