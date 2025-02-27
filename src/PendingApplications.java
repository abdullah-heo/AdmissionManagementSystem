import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.DefaultCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PendingApplications extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;

    public PendingApplications() {
        setTitle("Pending Applications");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // "Go Back" button
        RoundedButton btnGoBack = new RoundedButton("Go Back", 15);
        btnGoBack.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnGoBack.setBounds(20, 20, 100, 30);
        btnGoBack.addActionListener(e -> {
            new AdminDashboard(); // Return to AdminDashboard
            dispose();
        });
        contentPane.add(btnGoBack);

        // Title label ("Pending Applications")
        JLabel lblTitle = new JLabel("Pending Applications");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitle.setForeground(new Color(135, 152, 250));
        lblTitle.setBounds(20, 70, 200, 20);
        contentPane.add(lblTitle);

        // Table columns: "Application ID" and "Status"
        String[] columns = {"Application ID", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Let the user edit only the Status column (index = 1)
                return column == 1;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        table.setBackground(new Color(135, 152, 250));
        table.setForeground(Color.WHITE);

        // Add a mouse listener so clicking the "Application ID" column opens the details
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                // If the user clicked the ID column (0), open ApplicationDetails
                if (col == 0 && row != -1) {
                    int appId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    new ApplicationDetails(appId).setVisible(true);
                    // Optionally, dispose() if you want to close this screen
                }
            }
        });

        // Combo box editor for "Status" column
        String[] statuses = {"Pending", "Approved", "Denied"};
        JComboBox<String> comboBox = new JComboBox<>(statuses);
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));

        // Listen for changes in the table model to update DB
        tableModel.addTableModelListener(e -> {
            // If the user changed the Status column
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE && e.getColumn() == 1) {
                int row = e.getFirstRow();
                int appId = (int) tableModel.getValueAt(row, 0);
                String newStatus = (String) tableModel.getValueAt(row, 1);
                updateApplicationStatus(appId, newStatus);
            }
        });

        // ScrollPane for the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 540, 200);
        contentPane.add(scrollPane);

        // Fetch pending applications from DB
        fetchPendingApplications();

        setVisible(true);
    }

    /**
     * Fetch pending applications from DB and populate the table.
     */
    private void fetchPendingApplications() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "")) {
            String query = "SELECT app_id, app_status FROM application WHERE app_status = 'Pending'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int appId = rs.getInt("app_id");
                String status = rs.getString("app_status");
                tableModel.addRow(new Object[]{appId, status});
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Update the application status in the DB when the user changes it in the table.
     */
    private void updateApplicationStatus(int appId, String newStatus) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/admission_management_system", "root", "")) {
            String updateSql = "UPDATE application SET app_status = ? WHERE app_id = ?";
            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setString(1, newStatus);
            ps.setInt(2, appId);
            ps.executeUpdate();
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

    public static void main(String[] args) {
        new PendingApplications();
    }
}
