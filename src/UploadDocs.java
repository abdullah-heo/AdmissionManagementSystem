import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.File;

public class UploadDocs {

    private JFrame frame;
    private JTextField txtApplicantPhoto;
    private JTextField txtIdCard;
    private JTextField txtResults;
    private JTextField txtPaymentReceipt;

    // Reference to the AddmissionForm
    private AddmissionForm parentForm;

    public UploadDocs(AddmissionForm parentForm) {
        this.parentForm = parentForm;
        initialize();
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Upload Documents");
        frame.setBounds(100, 100, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null); // Center the window

        // Logo
        JLabel lblLogo = new JLabel("SAMS");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 28));
        lblLogo.setBounds(50, 20, 100, 30);
        frame.getContentPane().add(lblLogo);

        JLabel lblSchool = new JLabel("SCHOOL OF ARTS");
        lblSchool.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSchool.setBounds(50, 50, 150, 15);
        frame.getContentPane().add(lblSchool);

        JLabel lblHelp = new JLabel("Need help? Email us at admissions@sams.edu.pk");
        lblHelp.setFont(new Font("Arial", Font.PLAIN, 10));
        lblHelp.setForeground(Color.BLUE);
        lblHelp.setBounds(50, 70, 300, 15);
        frame.getContentPane().add(lblHelp);

        // Title
        JLabel lblTitle = new JLabel("Admission Form");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(50, 100, 200, 25);
        frame.getContentPane().add(lblTitle);

        // Applicant Photo
        JLabel lblApplicantPhoto = new JLabel("<html>Applicants Photo <font color='red'>*</font></html>");
        lblApplicantPhoto.setFont(new Font("Arial", Font.BOLD, 12));
        lblApplicantPhoto.setBounds(50, 140, 200, 20);
        frame.getContentPane().add(lblApplicantPhoto);

        txtApplicantPhoto = createFileChooserField();
        txtApplicantPhoto.setBounds(50, 160, 250, 30);
        frame.getContentPane().add(txtApplicantPhoto);

        // ID Card/B Form
        JLabel lblIdCard = new JLabel("<html>ID Card/B Form <font color='red'>*</font></html>");
        lblIdCard.setFont(new Font("Arial", Font.BOLD, 12));
        lblIdCard.setBounds(50, 200, 200, 20);
        frame.getContentPane().add(lblIdCard);

        txtIdCard = createFileChooserField();
        txtIdCard.setBounds(50, 220, 250, 30);
        frame.getContentPane().add(txtIdCard);

        // Previous Results
        JLabel lblResults = new JLabel("<html>Previous Results <font color='red'>*</font></html>");
        lblResults.setFont(new Font("Arial", Font.BOLD, 12));
        lblResults.setBounds(50, 260, 200, 20);
        frame.getContentPane().add(lblResults);

        txtResults = createFileChooserField();
        txtResults.setBounds(50, 280, 250, 30);
        frame.getContentPane().add(txtResults);

        // Payment Receipt
        JLabel lblPayment = new JLabel("<html>Payment Receipt <font color='red'>*</font></html>");
        lblPayment.setFont(new Font("Arial", Font.BOLD, 12));
        lblPayment.setBounds(50, 320, 200, 20);
        frame.getContentPane().add(lblPayment);

        txtPaymentReceipt = createFileChooserField();
        txtPaymentReceipt.setBounds(50, 340, 250, 30);
        frame.getContentPane().add(txtPaymentReceipt);

        // Send Button
        JButton btnSend = createRoundedButton("Send");
        btnSend.setBounds(50, 390, 150, 40);
        frame.getContentPane().add(btnSend);

        // Button Action
        btnSend.addActionListener(e -> {
            // Check if all fields are filled with actual file paths (not "Select media")
            if (!validateDocs()) {
                JOptionPane.showMessageDialog(frame, "Please select all required documents before sending.", "Incomplete Documents", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(frame, "Documents Uploaded Successfully!");
            // Mark docs as uploaded in the parent form
            parentForm.setDocsUploaded(true);
            // Return to the Admission Form
            parentForm.setVisible(true);
            frame.dispose();
        });
    }

    /**
     * Validate if the user actually selected files for all required fields.
     */
    private boolean validateDocs() {
        if (txtApplicantPhoto.getText().equals("Select media") ||
            txtIdCard.getText().equals("Select media") ||
            txtResults.getText().equals("Select media") ||
            txtPaymentReceipt.getText().equals("Select media")) {
            return false;
        }
        return true;
    }

    /**
     * Method to create a rounded JButton.
     */
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(51, 102, 204));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(51, 102, 204), 2, true)); // Rounded border
        button.setContentAreaFilled(true);
        return button;
    }

    /**
     * Method to create a JTextField that allows file selection.
     */
    private JTextField createFileChooserField() {
        JTextField textField = new JTextField("Select media");
        textField.setBorder(new LineBorder(Color.LIGHT_GRAY));
        textField.setEditable(false);

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = fileChooser.showOpenDialog(frame);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        return textField;
    }
}
