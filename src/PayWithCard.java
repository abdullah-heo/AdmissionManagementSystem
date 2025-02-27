import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class PayWithCard {

    private JFrame frame;
    private JTextField txtCardNumber;
    private JTextField txtExpiryDate;
    private JPasswordField txtSecurityCode;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PayWithCard window = new PayWithCard();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PayWithCard() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Pay with Card");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().setLayout(null);

        // "Go Back" Button
        JButton btnGoBack = createRoundedButton("Go Back");
        btnGoBack.setBounds(380, 20, 100, 30);
        frame.getContentPane().add(btnGoBack);
        btnGoBack.addActionListener(e -> {
            frame.dispose();
            new PaymentSelection();
        });

        // Logo
        JLabel lblLogo = new JLabel("SAMS");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 28));
        lblLogo.setBounds(50, 20, 100, 30);
        frame.getContentPane().add(lblLogo);

        JLabel lblSchool = new JLabel("SCHOOL OF ARTS");
        lblSchool.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSchool.setBounds(50, 50, 150, 15);
        frame.getContentPane().add(lblSchool);

        JLabel lblHelp = new JLabel("Need help? Email us at techsupport@sams.edu.pk");
        lblHelp.setFont(new Font("Arial", Font.PLAIN, 10));
        lblHelp.setForeground(Color.BLUE);
        lblHelp.setBounds(50, 70, 300, 15);
        frame.getContentPane().add(lblHelp);

        // Title
        JLabel lblTitle = new JLabel("Pay Admission Fee");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(50, 100, 200, 25);
        frame.getContentPane().add(lblTitle);

        // Card Number Field
        JLabel lblCardNumber = new JLabel("Credit/Debit Card Number *");
        lblCardNumber.setFont(new Font("Arial", Font.BOLD, 12));
        lblCardNumber.setBounds(50, 140, 200, 20);
        frame.getContentPane().add(lblCardNumber);

        txtCardNumber = new JTextField("0000 0000 0000 0000");
        txtCardNumber.setBounds(50, 160, 250, 30);
        txtCardNumber.setBorder(new LineBorder(Color.LIGHT_GRAY));
        frame.getContentPane().add(txtCardNumber);

        // Expiry Date Field
        JLabel lblExpiryDate = new JLabel("Expiry Date *");
        lblExpiryDate.setFont(new Font("Arial", Font.BOLD, 12));
        lblExpiryDate.setBounds(50, 200, 100, 20);
        frame.getContentPane().add(lblExpiryDate);

        txtExpiryDate = new JTextField("MM/YY");
        txtExpiryDate.setBounds(50, 220, 100, 30);
        txtExpiryDate.setBorder(new LineBorder(Color.LIGHT_GRAY));
        frame.getContentPane().add(txtExpiryDate);

        // Security Code Field
        JLabel lblSecurityCode = new JLabel("Security Code *");
        lblSecurityCode.setFont(new Font("Arial", Font.BOLD, 12));
        lblSecurityCode.setBounds(180, 200, 100, 20);
        frame.getContentPane().add(lblSecurityCode);

        txtSecurityCode = new JPasswordField();
        txtSecurityCode.setBounds(180, 220, 100, 30);
        txtSecurityCode.setBorder(new LineBorder(Color.LIGHT_GRAY));
        frame.getContentPane().add(txtSecurityCode);

        // Proceed Button
        JButton btnProceed = createRoundedButton("Proceed");
        btnProceed.setBounds(50, 270, 150, 40);
        frame.getContentPane().add(btnProceed);

        // Pay with Phone App Link
        JLabel lblPayPhone = new JLabel("<html><u>Pay with phone app</u></html>");
        lblPayPhone.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPayPhone.setForeground(Color.BLUE);
        lblPayPhone.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblPayPhone.setBounds(210, 280, 200, 20);
        frame.getContentPane().add(lblPayPhone);

        // Button Actions
        btnProceed.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Processing Payment...");
            frame.dispose();
            new PaymentSelection();
        });

        lblPayPhone.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                frame.dispose();
                new PayAdmissionFee();
            }
        });

        frame.setVisible(true);
    }

    /**
     * Method to create a JButton with rounded edges.
     */
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(51, 102, 204));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(51, 102, 204), 2, true));
        return button;
    }

    public void showWindow() {
        frame.setVisible(true);
    }
}
