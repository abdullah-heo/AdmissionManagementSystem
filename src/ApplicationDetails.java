import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationDetails extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private int appId;  // The application ID passed from PendingApplications

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // For testing, pass a dummy application ID
                ApplicationDetails frame = new ApplicationDetails(1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructor that accepts the appId of the application.
     */
    public ApplicationDetails(int appId) {
        this.appId = appId;
        initialize();
        fetchApplicationDetails(); // Query DB to get data for this application
    }

    public ApplicationDetails() {
        this(1);  // Default to appId=1 for demonstration
    }

    private void initialize() {
        setTitle("Application Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // "Go Back" button now simply closes the details screen
        RoundedButton btnGoBack = new RoundedButton("Go Back", 15);
        btnGoBack.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnGoBack.setBounds(20, 20, 100, 30);
        btnGoBack.addActionListener(e -> {
            dispose();
        });
        contentPane.add(btnGoBack);

        // Title label ("Application Details")
        JLabel lblTitle = new JLabel("Application Details");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitle.setForeground(new Color(135, 152, 250));
        lblTitle.setBounds(20, 70, 200, 20);
        contentPane.add(lblTitle);

        // Table Setup
        String[] columnNames = {"", ""};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;  // Read-only table
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setShowGrid(true);
        table.setFillsViewportHeight(true);
        table.setTableHeader(null);
        table.setBackground(new Color(135, 152, 250));
        table.setForeground(Color.WHITE);

        // Custom renderer for left column: 10px left padding and bold text
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                label.setBorder(new EmptyBorder(0, 10, 0, 0));
                label.setFont(new Font("Tahoma", Font.BOLD, 14));
                return label;
            }
        };

        // Custom renderer for right column: 10px left padding, plain text
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                label.setBorder(new EmptyBorder(0, 10, 0, 0));
                label.setFont(new Font("Tahoma", Font.PLAIN, 14));
                return label;
            }
        };

        table.setDefaultRenderer(Object.class, leftRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 550, 200);
        contentPane.add(scrollPane);
    }

    /**
     * Query the DB for this appId and populate the table.
     */
    private void fetchApplicationDetails() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "")) {
            String sql = "SELECT * FROM application WHERE app_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int dbAppId = rs.getInt("app_id");
                String cnic = rs.getString("cnic");
                String status = rs.getString("app_status");
                String fatherName = rs.getString("father_name");
                String religion = rs.getString("religion");
                Date dob = rs.getDate("DOB");
                String program = rs.getString("program");
                String address = rs.getString("address");
                String province = rs.getString("province");
                String city = rs.getString("city");
                String zip = rs.getString("zip");
                String guardianPhone = rs.getString("guardian_phone");

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String dobStr = (dob != null) ? sdf.format(dob) : "N/A";

                String[][] data = {
                    {"Application ID", String.valueOf(dbAppId)},
                    {"CNIC", cnic},
                    {"Status", status},
                    {"Father's Name", fatherName},
                    {"Religion", religion},
                    {"DOB", dobStr},
                    {"Program", program},
                    {"Address", address},
                    {"Province", province},
                    {"City", city},
                    {"Zip", zip},
                    {"Guardian Phone", guardianPhone}
                };

                model.setRowCount(0);
                for (String[] row : data) {
                    model.addRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No application found for ID " + appId, "Not Found", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * A custom JButton that draws itself as a fully rounded rectangle (no square corners)
     * and briefly changes color when pressed.
     */
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
