
package gui;

import dao.AdminDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {

    private final int adminId;
    private final String adminName;
    private final AdminDAO adminDAO = new AdminDAO();

    private final Color navy = new Color(20, 43, 78);
    private final Color blue = new Color(45, 105, 190);
    private final Color background = new Color(245, 247, 251);

    private JPanel contentPanel;
    private JLabel totalValue, pendingValue, progressValue;
    private JLabel resolvedValue, escalatedValue;

    public AdminFrame(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;

        setTitle("PCMS | Admin Dashboard");
        setSize(1200, 750);
        setMinimumSize(new Dimension(950, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navy);
        header.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel title = new JLabel("PUBLIC COMPLAINT MANAGEMENT SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel welcome = new JLabel("Administrator: " + adminName);
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        header.add(title, BorderLayout.WEST);
        header.add(welcome, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        // Sidebar
        JPanel sidebar = new JPanel(new GridLayout(8, 1, 0, 8));
        sidebar.setBackground(new Color(232, 237, 246));
        sidebar.setBorder(BorderFactory.createEmptyBorder(18, 12, 18, 12));
        sidebar.setPreferredSize(new Dimension(205, 0));

        JButton homeButton = createMenuButton("Dashboard");
        JButton complaintsButton = createMenuButton("All Complaints");
        JButton usersButton = createMenuButton("Registered Users");
        JButton escalatedButton = createMenuButton("Escalated Complaints");
        JButton slaButton = createMenuButton("SLA Status");
        JButton auditButton = createMenuButton("Audit Logs");
        JButton analyticsButton = createMenuButton("Area Analytics");
        JButton logoutButton = createMenuButton("Logout");

        sidebar.add(homeButton);
        sidebar.add(complaintsButton);
        sidebar.add(usersButton);
        sidebar.add(escalatedButton);
        sidebar.add(slaButton);
        sidebar.add(auditButton);
        sidebar.add(analyticsButton);
        sidebar.add(logoutButton);

        root.add(sidebar, BorderLayout.WEST);

        // Main content
        contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(background);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);

        homeButton.addActionListener(e -> showDashboard());
        complaintsButton.addActionListener(e -> showComplaints());
        usersButton.addActionListener(e -> showUsers());
        escalatedButton.addActionListener(e -> showEscalated());
        slaButton.addActionListener(e -> showSlaInfo());
        auditButton.addActionListener(e -> showAuditLogs());
        analyticsButton.addActionListener(e -> showAnalytics());

        logoutButton.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(
                    this, "Are you sure you want to logout?",
                    "Confirm Logout", JOptionPane.YES_NO_OPTION
            );

            if (answer == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        showDashboard();
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(navy);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 8));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createCard(String heading, JLabel value, Color accent) {
        JPanel card = new JPanel(new BorderLayout(5, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 238)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel label = new JLabel(heading);
        label.setForeground(new Color(90, 100, 115));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        value.setFont(new Font("Segoe UI", Font.BOLD, 27));
        value.setForeground(accent);

        card.add(label, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        return card;
    }

    private JLabel newValueLabel() {
        return new JLabel("0");
    }

    private void showDashboard() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout(15, 18));

        JLabel heading = new JLabel("Dashboard Overview");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(navy);

        totalValue = newValueLabel();
        pendingValue = newValueLabel();
        progressValue = newValueLabel();
        resolvedValue = newValueLabel();
        escalatedValue = newValueLabel();

        JPanel cards = new JPanel(new GridLayout(2, 3, 14, 14));
        cards.setOpaque(false);
        cards.add(createCard("Total Complaints", totalValue, blue));
        cards.add(createCard("Pending Complaints", pendingValue,
                new Color(190, 125, 25)));
        cards.add(createCard("In Progress", progressValue,
                new Color(115, 85, 185)));
        cards.add(createCard("Resolved", resolvedValue,
                new Color(35, 145, 95)));
        cards.add(createCard("Escalated", escalatedValue,
                new Color(190, 55, 65)));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(heading, BorderLayout.NORTH);
        top.add(cards, BorderLayout.CENTER);
        cards.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));

        JButton refresh = new JButton("Refresh Statistics");
        refresh.addActionListener(e -> refreshStatistics());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(refresh);

        contentPanel.add(top, BorderLayout.CENTER);
        contentPanel.add(bottom, BorderLayout.SOUTH);

        refreshStatistics();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void refreshStatistics() {
        int[] stats = adminDAO.getComplaintStatistics();

        totalValue.setText(String.valueOf(stats[0]));
        pendingValue.setText(String.valueOf(stats[1]));
        progressValue.setText(String.valueOf(stats[2]));
        resolvedValue.setText(String.valueOf(stats[3]));
        escalatedValue.setText(String.valueOf(stats[4]));
    }

    private void showComplaints() {
        List<Object[]> data = adminDAO.getAllComplaints();

        showTable("All Complaints",
                new String[]{"Complaint ID", "Citizen ID", "Title",
                        "Category", "Description", "Priority", "Status",
                        "Officer ID", "Created At"},
                data);
    }

    private void showUsers() {
        List<Object[]> data = adminDAO.getAllUsers();

        showTable("Registered Users",
                new String[]{"User ID", "Full Name", "Email", "Phone",
                        "Role", "Registered At"},
                data);
    }

    private void showEscalated() {
        List<Object[]> data = adminDAO.getEscalatedComplaints();

        showTable("Escalated Complaints",
                new String[]{"Complaint ID", "Citizen ID", "Title",
                        "Priority", "Status", "Officer ID", "Created At"},
                data);
    }

    private void showTable(String title, String[] columns,
                           List<Object[]> data) {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout(12, 12));

        JLabel heading = new JLabel(title + " (" + data.size() + ")");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(navy);

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Object[] row : data) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(229, 236, 247));

        JScrollPane scrollPane = new JScrollPane(table);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> {
            if (title.equals("All Complaints")) {
                showComplaints();
            } else if (title.equals("Registered Users")) {
                showUsers();
            } else {
                showEscalated();
            }
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(refresh);

        contentPanel.add(heading, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(bottom, BorderLayout.SOUTH);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showSlaInfo() {
        JOptionPane.showMessageDialog(
                this,
                "SLA information is stored in the complaint_sla table.\n" +
                        "The detailed SLA view will be connected after verifying its columns.",
                "SLA Status",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showAuditLogs() {
        JOptionPane.showMessageDialog(
                this,
                "The audit_logs table is available.\n" +
                        "The audit log viewer will be connected after verifying its columns.",
                "Audit Logs",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showAnalytics() {
        JOptionPane.showMessageDialog(
                this,
                "Area analytics will be connected to the area_complaint_analytics table.",
                "Area Analytics",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
