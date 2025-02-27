import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;

public class PaymentSelection {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PaymentSelection window = new PaymentSelection();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PaymentSelection() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Payment Selection");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().setLayout(null);

        // Title Label
        JLabel lblTitle = new JLabel("Select a Payment Method");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(100, 80, 300, 30);
        frame.getContentPane().add(lblTitle);

        // Payment Buttons
        JButton btnBank = createRoundedButton("Pay through Bank");
        JButton btnCard = createRoundedButton("Pay with Card");
        JButton btnApps = createRoundedButton("Pay with Mobile Apps");

        btnBank.setBounds(140, 120, 220, 40);
        btnCard.setBounds(140, 180, 220, 40);
        btnApps.setBounds(140, 240, 220, 40);

        frame.getContentPane().add(btnBank);
        frame.getContentPane().add(btnCard);
        frame.getContentPane().add(btnApps);

        // Button Actions - Open new windows
        btnBank.addActionListener(e -> {
            frame.dispose();
            new BankPaymentVoucher().showWindow();
        });

        btnCard.addActionListener(e -> {
            frame.dispose();
            new PayWithCard().showWindow();
        });

        btnApps.addActionListener(e -> {
            frame.dispose();
            new PayAdmissionFee().showWindow();
        });

        frame.setVisible(true);
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(51, 102, 204));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(51, 102, 204), 2, true));
        return button;
    }
}
