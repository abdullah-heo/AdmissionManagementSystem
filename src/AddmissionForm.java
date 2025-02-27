import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class AddmissionForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtGuardianName, txtReligion, txtDOB, txtAddress, txtCity, txtZipCode, txtGuardianPhone;
    private JComboBox<String> cbProvince, cbProgram;
    
    // The CNIC of the logged-in student, passed from login or the dashboard
    private String studentCNIC;
    
    // Flag indicating if documents have been uploaded (if you need that logic)
    private boolean docsUploaded = false;

    // Constructor accepting the studentCNIC
    public AddmissionForm(String studentCNIC) {
        this.studentCNIC = studentCNIC;
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // For testing, pass a dummy CNIC
                AddmissionForm frame = new AddmissionForm("12345-1234567-1");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        setTitle("SAMS - School of Arts (Admission Form)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        setLocationRelativeTo(null); // Center the window

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(contentPane);

        // Use GroupLayout for a cleaner layout
        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Back to Dashboard Button
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setBackground(new Color(0,128,192));
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(e -> {
            new StudentDashboard(studentCNIC); // Return to the dashboard
            dispose();
        });

        // Title label
        JLabel lblTitle = new JLabel("SAMS SCHOOL OF ARTS - Apply for Admissions", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));

        // Father/Guardian’s Name
        JLabel lblGuardianName = new JLabel("<html>Father/Guardian’s Name <font color='red'>*</font></html>");
        txtGuardianName = new JTextField();

        // Religion
        JLabel lblReligion = new JLabel("<html>Religion <font color='red'>*</font></html>");
        txtReligion = new JTextField();

        // Date of Birth (User enters dd-MM-yyyy)
        JLabel lblDOB = new JLabel("<html>Date of Birth (dd-MM-yyyy) <font color='red'>*</font></html>");
        txtDOB = new JTextField();

        // Program
        JLabel lblProgram = new JLabel("<html>Program <font color='red'>*</font></html>");
        cbProgram = new JComboBox<>(new String[]{"Fine Arts", "Music", "Dance", "Drama"});

        // Address
        JLabel lblAddress = new JLabel("<html>Address <font color='red'>*</font></html>");
        txtAddress = new JTextField();

        // Province
        JLabel lblProvince = new JLabel("<html>Province <font color='red'>*</font></html>");
        cbProvince = new JComboBox<>(new String[]{"Punjab", "Sindh", "Balochistan", "KPK", "Gilgit"});

        // City
        JLabel lblCity = new JLabel("<html>City <font color='red'>*</font></html>");
        txtCity = new JTextField();

        // Zip Code
        JLabel lblZipCode = new JLabel("<html>Zip Code <font color='red'>*</font></html>");
        txtZipCode = new JTextField();

        // Guardian’s Phone
        JLabel lblGuardianPhone = new JLabel("<html>Guardian’s Phone <font color='red'>*</font></html>");
        txtGuardianPhone = new JTextField();

        // Continue to Pay Fee Button
        JButton btnContinue = new JButton("Continue to Pay Fee");
        btnContinue.setBackground(new Color(0,128,255));
        btnContinue.setForeground(Color.WHITE);
        btnContinue.addActionListener(e -> {
            if (!validateFields()) {
                return; // If validation fails, stop
            }
            // Check if an admission already exists for this student
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "")) {
                String checkSql = "SELECT COUNT(*) AS cnt FROM application WHERE cnic = ?";
                PreparedStatement psCheck = conn.prepareStatement(checkSql);
                psCheck.setString(1, studentCNIC);
                ResultSet rsCheck = psCheck.executeQuery();
                if (rsCheck.next() && rsCheck.getInt("cnt") > 0) {
                    JOptionPane.showMessageDialog(this, "You have already submitted an admission form.", "Admission Exists", JOptionPane.ERROR_MESSAGE);
                    rsCheck.close();
                    psCheck.close();
                    return;
                }
                rsCheck.close();
                psCheck.close();

                // Save form data into the 'application' table with app_status set to "Pending"
                String sql = "INSERT INTO application (cnic, app_status, father_name, religion, DOB, program, address, province, city, zip, guardian_phone) "
                           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, studentCNIC);
                ps.setString(2, "Pending");
                ps.setString(3, txtGuardianName.getText().trim());
                ps.setString(4, txtReligion.getText().trim());
                
                // Parse user input from dd-MM-yyyy to a java.sql.Date
                String dobStr = txtDOB.getText().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date parsedDOB = sdf.parse(dobStr);
                java.sql.Date sqlDOB = new java.sql.Date(parsedDOB.getTime());
                ps.setDate(5, sqlDOB);

                ps.setString(6, cbProgram.getSelectedItem().toString());
                ps.setString(7, txtAddress.getText().trim());
                ps.setString(8, cbProvince.getSelectedItem().toString());
                ps.setString(9, txtCity.getText().trim());
                ps.setString(10, txtZipCode.getText().trim());
                ps.setString(11, txtGuardianPhone.getText().trim());

                int rows = ps.executeUpdate();
                ps.close();
                
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Application saved successfully. Proceeding to Payment...");
                    new PaymentSelection();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save application.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Date format. Please use dd-MM-yyyy.", "Date Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Setup the layout horizontally
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblGuardianName)
                        .addComponent(lblReligion)
                        .addComponent(lblDOB)
                        .addComponent(lblAddress))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(txtGuardianName, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtReligion, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDOB, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                    .addGap(20)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblProvince)
                        .addComponent(lblProgram)
                        .addComponent(lblCity)
                        .addComponent(lblZipCode)
                        .addComponent(lblGuardianPhone))
                    .addGap(10)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(cbProvince, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbProgram, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCity, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtZipCode, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGuardianPhone, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
                .addComponent(btnContinue, GroupLayout.Alignment.CENTER, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        );

        // Setup the layout vertically
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGuardianName)
                    .addComponent(txtGuardianName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProvince)
                    .addComponent(cbProvince, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReligion)
                    .addComponent(txtReligion, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProgram)
                    .addComponent(cbProgram, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDOB)
                    .addComponent(txtDOB, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCity)
                    .addComponent(txtCity, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddress)
                    .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblZipCode)
                    .addComponent(txtZipCode, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGuardianPhone)
                    .addComponent(txtGuardianPhone, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                .addGap(20)
                .addComponent(btnContinue, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        );
    }

    /**
     * Validate required fields: father_name, religion, DOB (dd-MM-yyyy), program, address, province, city, zip, guardian_phone.
     */
    private boolean validateFields() {
        // Father/Guardian’s Name
        if (txtGuardianName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Father/Guardian’s Name.", "Missing Field", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Religion
        if (txtReligion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Religion.", "Missing Field", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Date of Birth (dd-MM-yyyy)
        String dobStr = txtDOB.getText().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dobStr); // If invalid, will throw ParseException
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Date of Birth in the format dd-MM-yyyy.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Program
        if (cbProgram.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a Program.", "Missing Field", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Address
        if (txtAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Address.", "Missing Field", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Province
        if (cbProvince.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a Province.", "Missing Field", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // City
        if (txtCity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter City.", "Missing Field", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Zip Code (5 digits)
        String zip = txtZipCode.getText().trim();
        if (!zip.matches("^\\d{5}$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 5-digit Zip Code.", "Invalid Zip Code", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Guardian’s Phone
        if (txtGuardianPhone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Guardian’s Phone.", "Missing Field", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * If you still need docs logic, you can implement it with setDocsUploaded().
     */
    public void setDocsUploaded(boolean uploaded) {
        this.docsUploaded = uploaded;
    }
}
