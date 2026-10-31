
package gui;

import dao.OfficerDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class OfficerFrame extends JFrame {

    private final int officerId;
    private final String officerName;
    private final OfficerDAO officerDAO = new OfficerDAO();

    private JTable complaintTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusBox;
    private JLabel totalLabel;
    private JLabel pendingLabel;
    private JLabel progressLabel;
    private JLabel resolvedLabel;

    private final Color navy = new Color(24, 43, 77);
    private final Color blue = new Color(43, 105, 190);
    private final Color background = new Color(245, 247, 251);

    public OfficerFrame(int officerId, String officerName) {
        this.officerId = officerId;
        this.officerName = officerName;

        setTitle("Officer Dashboard");
        setSize(1150, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navy);
        header.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JLabel welcome = new JLabel("Officer Dashboard");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 25));

        JLabel officer = new JLabel("Welcome, " + officerName);
        officer.setForeground(new Color(220, 230, 245));
        officer.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel headerText = new JPanel();
        headerText.setOpaque(false);
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        headerText.add(welcome);
        headerText.add(Box.createVerticalStrut(5));
        headerText.add(officer);

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(190, 60, 65));
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        header.add(headerText, BorderLayout.WEST);
        header.add(logoutButton, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new BorderLayout(15, 15));
        content.setBackground(background);
        content.setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

        JLabel sectionTitle = new JLabel("Complaint Overview");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 21));
        sectionTitle.setForeground(navy);

        JPanel cards = new JPanel(new GridLayout(1, 4, 12, 12));
        cards.setOpaque(false);

        totalLabel = new JLabel("0");
        pendingLabel = new JLabel("0");
        progressLabel = new JLabel("0");
        resolvedLabel = new JLabel("0");

        cards.add(createCard("Total Assigned", totalLabel, blue));
        cards.add(createCard("Pending", pendingLabel, new Color(200, 135, 30)));
        cards.add(createCard("In Progress", progressLabel, new Color(105, 85, 190)));
        cards.add(createCard("Resolved", resolvedLabel, new Color(35, 145, 95)));

        JPanel top = new JPanel(new BorderLayout(0, 15));
        top.setOpaque(false);
        top.add(sectionTitle, BorderLayout.NORTH);
        top.add(cards, BorderLayout.CENTER);

        // Search and status controls
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controls.setBackground(Color.WHITE);
        controls.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

        searchField = new JTextField(14);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton searchButton = new JButton("Search ID");
        JButton refreshButton = new JButton("Refresh");
        JButton updateButton = new JButton("Update Status");

        styleButton(searchButton, blue);
        styleButton(refreshButton, new Color(75, 90, 110));
        styleButton(updateButton, new Color(35, 145, 95));

        statusBox = new JComboBox<>(new String[]{
                "Assigned", "Under Review", "In Progress", "Resolved"
        });
        statusBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        controls.add(new JLabel("Complaint ID:"));
        controls.add(searchField);
        controls.add(searchButton);
        controls.add(refreshButton);
        controls.add(new JLabel("New status:"));
        controls.add(statusBox);
        controls.add(updateButton);

        // Complaints table
        tableModel = new DefaultTableModel(
                new String[]{
                        "Complaint ID", "Citizen ID", "Category",
                        "Description", "Status"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        complaintTable = new JTable(tableModel);
        complaintTable.setRowHeight(34);
        complaintTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        complaintTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        complaintTable.setShowVerticalLines(false);
        complaintTable.setGridColor(new Color(230, 234, 240));
        complaintTable.setFillsViewportHeight(true);

        complaintTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        complaintTable.getTableHeader().setBackground(
                new Color(229, 236, 247)
        );
        complaintTable.getTableHeader().setForeground(navy);
        complaintTable.getTableHeader().setPreferredSize(
                new Dimension(0, 38)
        );

        complaintTable.getColumnModel().getColumn(3).setPreferredWidth(400);

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        complaintTable.getColumnModel().getColumn(0)
                .setCellRenderer(centerRenderer);
        complaintTable.getColumnModel().getColumn(1)
                .setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(complaintTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(220, 226, 235)
        ));

        JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
        tablePanel.setBackground(background);
        tablePanel.add(top, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(controls, BorderLayout.SOUTH);

        content.add(tablePanel, BorderLayout.CENTER);
        root.add(content, BorderLayout.CENTER);

        setContentPane(root);

        searchButton.addActionListener(e -> searchComplaint());
        searchField.addActionListener(e -> searchComplaint());
        refreshButton.addActionListener(e -> loadComplaints());
        updateButton.addActionListener(e -> updateComplaintStatus());

        loadComplaints();
    }

    private JPanel createCard(String title, JLabel value, Color accent) {
        JPanel card = new JPanel(new BorderLayout(5, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 238)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel heading = new JLabel(title);
        heading.setForeground(new Color(90, 100, 115));
        heading.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        value.setForeground(accent);
        value.setFont(new Font("Segoe UI", Font.BOLD, 28));

        card.add(heading, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        return card;
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(9, 14, 9, 14));
    }

    private void loadComplaints() {
        tableModel.setRowCount(0);

        try {
            List<Object[]> complaints =
                    officerDAO.getAssignedComplaints(officerId);

            int pending = 0;
            int inProgress = 0;
            int resolved = 0;

            for (Object[] complaint : complaints) {
                tableModel.addRow(complaint);

                String status = String.valueOf(complaint[4]);

                if ("SUBMITTED".equalsIgnoreCase(status)
                        || "ASSIGNED".equalsIgnoreCase(status)
                        || "UNDER_REVIEW".equalsIgnoreCase(status)) {
                    pending++;
                } else if ("IN_PROGRESS".equalsIgnoreCase(status)) {
                    inProgress++;
                } else if ("RESOLVED".equalsIgnoreCase(status)) {
                    resolved++;
                }
            }

            totalLabel.setText(String.valueOf(complaints.size()));
            pendingLabel.setText(String.valueOf(pending));
            progressLabel.setText(String.valueOf(inProgress));
            resolvedLabel.setText(String.valueOf(resolved));

            if (complaints.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No complaints are currently assigned to your officer account.",
                        "Complaint Overview",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load complaints. Check the console for details.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void searchComplaint() {
        String id = searchField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a complaint ID.");
            return;
        }

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (String.valueOf(tableModel.getValueAt(row, 0)).equals(id)) {
                complaintTable.setRowSelectionInterval(row, row);
                complaintTable.scrollRectToVisible(
                        complaintTable.getCellRect(row, 0, true)
                );
                return;
            }
        }

        JOptionPane.showMessageDialog(
                this, "Complaint not found in your assigned complaints."
        );
    }

    private void updateComplaintStatus() {
        int row = complaintTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select a complaint from the table first."
            );
            return;
        }

        int modelRow = complaintTable.convertRowIndexToModel(row);
        int complaintId = Integer.parseInt(
                String.valueOf(tableModel.getValueAt(modelRow, 0))
        );

        String selected = String.valueOf(statusBox.getSelectedItem());

        String newStatus;
        switch (selected) {
            case "Assigned":
                newStatus = "ASSIGNED";
                break;
            case "Under Review":
                newStatus = "UNDER_REVIEW";
                break;
            case "In Progress":
                newStatus = "IN_PROGRESS";
                break;
            case "Resolved":
                newStatus = "RESOLVED";
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid status.");
                return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Update complaint #" + complaintId +
                        " to " + selected + "?",
                "Confirm Status Update",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean updated = officerDAO.updateComplaintStatus(
                complaintId, officerId, newStatus
        );

        if (updated) {
            JOptionPane.showMessageDialog(
                    this, "Complaint status updated successfully."
            );
            loadComplaints();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Status was not changed. Check the selected complaint and database console.",
                    "Update Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
