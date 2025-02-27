import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class BankPaymentVoucher {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BankPaymentVoucher window = new BankPaymentVoucher();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public BankPaymentVoucher() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Bank Payment Voucher");
        frame.setBounds(100, 100, 600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().setLayout(null);

        // "Go Back" Button
        JButton btnGoBack = createRoundedButton("Go Back");
        btnGoBack.setBounds(20, 20, 100, 30);
        frame.getContentPane().add(btnGoBack);
        btnGoBack.addActionListener(e -> {
            frame.dispose();
            new PaymentSelection();
        });

        // Instruction Label
        JLabel lblInstruction = new JLabel("Download and print the voucher, then visit the nearest bank.");
        lblInstruction.setFont(new Font("Arial", Font.BOLD, 14));
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstruction.setBounds(50, 70, 500, 30);
        frame.getContentPane().add(lblInstruction);

        // Voucher Image
        ImageIcon icon = new ImageIcon("voucher.png"); // Ensure correct path
        JLabel lblVoucher = new JLabel();
        lblVoucher.setIcon(icon);
        lblVoucher.setBounds(50, 120, 500, 200);
        frame.getContentPane().add(lblVoucher);

        // Download Button
        JButton btnDownload = createRoundedButton("Download");
        btnDownload.setBounds(200, 350, 200, 40);
        frame.getContentPane().add(btnDownload);
        btnDownload.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Downloading Voucher..."));

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
