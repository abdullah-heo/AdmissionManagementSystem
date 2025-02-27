import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class AdmissionDeadline extends JFrame {

    private JPanel contentPane;
    private JTextField txtStartDate;
    private JTextField txtEndDate;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AdmissionDeadline frame = new AdmissionDeadline();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AdmissionDeadline() {
        setTitle("SAMS - School of Arts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        setLocationRelativeTo(null); // Center the window

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Create Back to Dashboard Button (top left) as a RoundedButton
        RoundedButton btnBack = new RoundedButton("Back to Dashboard", 20);
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnBack.addActionListener(e -> {
            new AdminDashboard();
            dispose();
        });

        // Top left labels
        JLabel lblSAMS = new JLabel("SAMS");
        lblSAMS.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblSAMS.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel lblSchoolOfArts = new JLabel("SCHOOL OF ARTS");
        lblSchoolOfArts.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSchoolOfArts.setHorizontalAlignment(SwingConstants.LEFT);

        // Heading (centered text for emphasis)
        JLabel lblSetDeadlines = new JLabel("Set Admission Deadlines");
        lblSetDeadlines.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSetDeadlines.setHorizontalAlignment(SwingConstants.CENTER);

        // Labels for Start & End Date with red asterisks using HTML formatting.
        JLabel lblStartDate = new JLabel("<html>Start Date For Admission <font color='red'>*</font></html>");
        JLabel lblEndDate = new JLabel("<html>End Date For Admission <font color='red'>*</font></html>");

        // Text fields for dates (slightly bigger than before)
        txtStartDate = new JTextField();
        txtStartDate.setColumns(10);
        txtEndDate = new JTextField();
        txtEndDate.setColumns(10);
        
        // Save button using our custom RoundedButton
        RoundedButton btnSave = new RoundedButton("Save", 20);
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validate both date fields using dd/MM/yyyy format
                String startDateStr = txtStartDate.getText().trim();
                String endDateStr = txtEndDate.getText().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                Date startDate = null, endDate = null;
                try {
                    startDate = sdf.parse(startDateStr);
                    endDate = sdf.parse(endDateStr);
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid dates in the format dd/mm/yyyy.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Convert java.util.Date to java.sql.Date
                java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
                java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
                
                // Save deadlines in the database
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "")) {
                    String sql = "INSERT INTO deadlines (start_date, end_date) VALUES (?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setDate(1, sqlStartDate);
                    ps.setDate(2, sqlEndDate);
                    int rows = ps.executeUpdate();
                    ps.close();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(null, "Deadlines saved successfully.");
                        new AdminDashboard();
                        dispose();  // Go back to the Admin Dashboard
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to save deadlines.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(20)
                    .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblSAMS)
                        .addComponent(lblSchoolOfArts)
                        .addComponent(lblStartDate)
                        .addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                    .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblEndDate)
                        .addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
                    .addGap(40))
                .addGroup(GroupLayout.Alignment.CENTER, gl_contentPane.createSequentialGroup()
                    .addComponent(lblSetDeadlines, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(263, Short.MAX_VALUE))
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(244)
                    .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(244, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(lblSAMS)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lblSchoolOfArts)
                    .addGap(20)
                    .addComponent(lblSetDeadlines, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblStartDate)
                        .addComponent(lblEndDate))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtStartDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEndDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                    .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addGap(40))
        );

        contentPane.setLayout(gl_contentPane);
    }

    // Custom RoundedButton class for consistent button styling.
    private static class RoundedButton extends JButton {
        private final int radius;
        private final Color normalColor = new Color(0, 102, 204);
        private final Color pressedColor = new Color(0, 92, 184);

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setBackground(normalColor);
            setBorder(new EmptyBorder(0, 0, 0, 0));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isArmed()) {
                g2.setColor(pressedColor);
            } else {
                g2.setColor(getBackground());
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
    }
}
