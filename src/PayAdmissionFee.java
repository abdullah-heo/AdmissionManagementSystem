import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class PayAdmissionFee {

    private JFrame frame;
    private JTextField txtNumber;
    private JPasswordField txtMPIN;
    private JComboBox<String> comboApps;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PayAdmissionFee window = new PayAdmissionFee();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PayAdmissionFee() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Pay with Mobile App");
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

        // Phone Number Field
        JLabel lblNumber = new JLabel("Number *");
        lblNumber.setFont(new Font("Arial", Font.BOLD, 12));
        lblNumber.setBounds(50, 140, 100, 20);
        frame.getContentPane().add(lblNumber);

        txtNumber = new JTextField("03#########");
        txtNumber.setBounds(50, 160, 200, 30);
        txtNumber.setBorder(new LineBorder(Color.LIGHT_GRAY));
        frame.getContentPane().add(txtNumber);

        // Select App Dropdown
        JLabel lblSelectApp = new JLabel("Select App *");
        lblSelectApp.setFont(new Font("Arial", Font.BOLD, 12));
        lblSelectApp.setBounds(50, 200, 100, 20);
        frame.getContentPane().add(lblSelectApp);

        String[] apps = {"Jazzcash", "Easypaisa", "NayaPay"};
        comboApps = new JComboBox<>(apps);
        comboApps.setBounds(50, 220, 100, 30);
        comboApps.setBorder(new LineBorder(Color.LIGHT_GRAY));
        frame.getContentPane().add(comboApps);

        // MPIN Field
        JLabel lblMPIN = new JLabel("MPIN *");
        lblMPIN.setFont(new Font("Arial", Font.BOLD, 12));
        lblMPIN.setBounds(180, 200, 100, 20);
        frame.getContentPane().add(lblMPIN);

        txtMPIN = new JPasswordField();
        txtMPIN.setBounds(180, 220, 100, 30);
        txtMPIN.setBorder(new LineBorder(Color.LIGHT_GRAY));
        frame.getContentPane().add(txtMPIN);

        // Proceed Button
        JButton btnProceed = createRoundedButton("Proceed");
        btnProceed.setBounds(50, 270, 150, 40);
        frame.getContentPane().add(btnProceed);

        // Pay with Card Link
        JLabel lblPayCard = new JLabel("<html><u>Pay with debit/credit card</u></html>");
        lblPayCard.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPayCard.setForeground(Color.BLUE);
        lblPayCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblPayCard.setBounds(210, 280, 200, 20);
        frame.getContentPane().add(lblPayCard);

        // Button Actions
        btnProceed.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Processing Payment...");
            frame.dispose();
            new PaymentSelection();
        });

        lblPayCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                frame.dispose();
                new PayWithCard().showWindow();
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
