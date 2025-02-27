import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class NotifyStudents {
    private JFrame frame;
    private JTextArea messageArea;
    private JComboBox<String> studentDropdown;
    private JButton sendButton;
    private HashMap<String, String> studentMap = new HashMap<>(); // Store CNICs with Names
    private JFrame parentFrame; // Admin Dashboard reference

    public NotifyStudents(JFrame parentFrame) {
        this.parentFrame = parentFrame; // Store reference for navigation

        frame = new JFrame("Notify Students");
        frame.setBounds(100, 100, 500, 350);
        frame.setLocationRelativeTo(null); // Center the window on screen
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Notify Students");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 20));
        lblTitle.setBounds(180, 20, 200, 30);
        frame.getContentPane().add(lblTitle);
        
        JLabel lblMessage = new JLabel("Write a Message:");
        lblMessage.setBounds(50, 70, 200, 20);
        frame.getContentPane().add(lblMessage);
        
        messageArea = new JTextArea();
        messageArea.setBounds(50, 90, 380, 60);
        frame.getContentPane().add(messageArea);
        
        JLabel lblStudent = new JLabel("Select Student:");
        lblStudent.setBounds(50, 160, 200, 20);
        frame.getContentPane().add(lblStudent);
        
        studentDropdown = new JComboBox<>();
        studentDropdown.setBounds(50, 180, 250, 25);
        frame.getContentPane().add(studentDropdown);
        loadStudents(); // Fetch students from DB

        sendButton = new JButton("Send Notification");
        sendButton.setBounds(150, 230, 180, 30);
        frame.getContentPane().add(sendButton);
        
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendNotification();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                returnToDashboard(); // Navigate back when closing
            }
        });

        frame.setVisible(true);
    }

    private void loadStudents() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT cnic, name FROM student")) {

            studentDropdown.addItem("Select a student"); // Default option

            while (rs.next()) {
                String cnic = rs.getString("cnic");
                String name = rs.getString("name");
                studentDropdown.addItem(name);
                studentMap.put(name, cnic); // Store CNIC against name
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading students: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendNotification() {
        String selectedStudent = (String) studentDropdown.getSelectedItem();
        if (selectedStudent.equals("Select a student") || selectedStudent == null) {
            JOptionPane.showMessageDialog(frame, "Please select a student.", "Alert", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cnic = studentMap.get(selectedStudent); // Get CNIC from map
        String message = messageArea.getText().trim();
        
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Message cannot be empty!", "Alert", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "")) {
            String query = "INSERT INTO notification (cnic, message, noti_status) VALUES (?, ?, 'Unread')";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, cnic);
                pstmt.setString(2, message);
                pstmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(frame, "Notification sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            messageArea.setText(""); // Clear message box

            returnToDashboard(); // Return to dashboard after sending

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error sending notification: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnToDashboard() {
        frame.dispose(); // Close NotifyStudents frame
        if (parentFrame != null) {
            parentFrame.setVisible(true); // Show Admin Dashboard
        } else {
            new AdminDashboard(); // If no reference, create a new instance
        }
    }

    public static void main(String[] args) {
        new NotifyStudents(new AdminDashboard()); // Pass Admin Dashboard reference
    }
}
