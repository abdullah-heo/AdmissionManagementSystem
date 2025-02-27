import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ContactAdmin {
    private JFrame frame;
    private JTextField subjectField;
    private JTextArea messageArea;
    private JRadioButton admissionBtn, paymentBtn;
    private JCheckBox agreeCheckBox;
    private JButton submitButton, backButton;
    private String studentCNIC; // Remembered but not shown in the UI

    public ContactAdmin(String studentCNIC) {
        this.studentCNIC = studentCNIC;
        frame = new JFrame("Contact Admin");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null); // Center the window

        JLabel lblTitle = new JLabel("Contact Admin");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 20));
        lblTitle.setBounds(230, 20, 200, 30);
        frame.getContentPane().add(lblTitle);
        
        JLabel lblCategory = new JLabel("Select Category Of Query *");
        lblCategory.setBounds(50, 70, 200, 20);
        frame.getContentPane().add(lblCategory);
        
        admissionBtn = new JRadioButton("Admission");
        admissionBtn.setBounds(250, 70, 100, 20);
        frame.getContentPane().add(admissionBtn);
        
        paymentBtn = new JRadioButton("Payment");
        paymentBtn.setBounds(350, 70, 100, 20);
        frame.getContentPane().add(paymentBtn);
        
        ButtonGroup categoryGroup = new ButtonGroup();
        categoryGroup.add(admissionBtn);
        categoryGroup.add(paymentBtn);
        
        JLabel lblSubject = new JLabel("Enter Subject Of Query *");
        lblSubject.setBounds(50, 110, 200, 20);
        frame.getContentPane().add(lblSubject);
        
        subjectField = new JTextField();
        subjectField.setBounds(50, 130, 500, 25);
        frame.getContentPane().add(subjectField);
        
        JLabel lblMessage = new JLabel("Enter Message *");
        lblMessage.setBounds(50, 170, 200, 20);
        frame.getContentPane().add(lblMessage);
        
        messageArea = new JTextArea();
        messageArea.setBounds(50, 190, 500, 80);
        frame.getContentPane().add(messageArea);
        
        agreeCheckBox = new JCheckBox("I agree my query will be sent to the admin.");
        agreeCheckBox.setBounds(50, 280, 300, 20);
        frame.getContentPane().add(agreeCheckBox);
        
        submitButton = new JButton("Submit");
        submitButton.setBounds(250, 320, 100, 30);
        frame.getContentPane().add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!agreeCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "Please agree to the terms before submitting.", "Alert", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String subject = subjectField.getText().trim();
                String category = (admissionBtn.isSelected() ? "Admission" : (paymentBtn.isSelected() ? "Payment" : ""));
                String message = messageArea.getText().trim();
                
                if(subject.isEmpty() || category.isEmpty() || message.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill out all fields and select a category.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int appId = 0;
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "")) {
                    String queryApp = "SELECT app_id FROM application WHERE cnic = ?";
                    PreparedStatement psApp = conn.prepareStatement(queryApp);
                    psApp.setString(1, studentCNIC);
                    ResultSet rsApp = psApp.executeQuery();
                    if(rsApp.next()){
                        appId = rsApp.getInt("app_id");
                    } else {
                        appId = 1; // If not found, use 1
                    }
                    rsApp.close();
                    psApp.close();
                    
                    String insertQuery = "INSERT INTO query (cnic, app_id, q_category, q_subject, q_status) VALUES (?, ?, ?, ?, 'Pending')";
                    PreparedStatement psInsert = conn.prepareStatement(insertQuery);
                    psInsert.setString(1, studentCNIC);
                    psInsert.setInt(2, appId);
                    psInsert.setString(3, category);
                    psInsert.setString(4, subject);
                    psInsert.executeUpdate();
                    psInsert.close();
                    
                    new SuccessScreen(studentCNIC);
                    frame.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton = new JButton("Back to Dashboard");
        backButton.setBounds(400, 320, 150, 30);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StudentDashboard(studentCNIC);
            }
        });

        frame.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        new ContactAdmin("12345-1234567-1");
    }
}
