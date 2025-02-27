import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class ResetPassword extends JFrame {
    private JFormattedTextField txtCode;  // Instance variable

    public ResetPassword() {
        setTitle("SAMS - Reset Password");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null); // Center the window

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
        lblHelp.setBounds(50, 85, 400, 20);
        add(lblHelp);

        // Reset Password Title
        JLabel lblTitle = new JLabel("Reset Password");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setBounds(50, 130, 250, 30);
        add(lblTitle);

        // Enter Code Label
        JLabel lblCode = new JLabel("Enter Code (6 digits) *");
        lblCode.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblCode.setBounds(50, 180, 200, 20);
        add(lblCode);
        
        try {
            MaskFormatter codeFormatter = new MaskFormatter("######");
            codeFormatter.setPlaceholderCharacter('_');
            txtCode = new JFormattedTextField(codeFormatter);
        } catch (ParseException pe) {
            pe.printStackTrace();
            txtCode = new JFormattedTextField();
        }
        txtCode.setBounds(50, 210, 300, 30);
        add(txtCode);
        
        // Confirm Button
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setBounds(50, 260, 120, 40);
        btnConfirm.setBackground(new Color(60, 90, 255));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(btnConfirm);
        
        // Confirm Button Action
        btnConfirm.addActionListener(e -> {
            String code = txtCode.getText();
            // Check if the code is complete (i.e. no placeholders remain)
            if (code.contains("_")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid 6-digit code!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                new NewPassword(); // Open New Password window
                dispose(); // Close Reset Password window
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ResetPassword();
    }
}
