package gui;

import javax.swing.*;
import dao.ComplaintDAO;
import model.Complaint;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Citizen Dashboard
 * Public Complaint Management System
 */
public class CitizenFrame extends JFrame {

    // ============================================================
    // COLORS
    // ============================================================

    private static final Color NAVY = new Color(8, 52, 105);
    private static final Color BLUE = new Color(30, 125, 210);
    private static final Color BLUE_DARK = new Color(18, 90, 160);
    private static final Color LIGHT_BLUE = new Color(238, 247, 255);

    private static final Color WHITE = Color.WHITE;
    private static final Color BACKGROUND = new Color(245, 248, 252);
    private static final Color TEXT = new Color(30, 45, 65);
    private static final Color MUTED = new Color(105, 120, 140);
    private static final Color BORDER = new Color(218, 226, 236);

    private static final Color GREEN = new Color(40, 160, 95);
    private static final Color ORANGE = new Color(235, 145, 40);
    private static final Color RED = new Color(210, 70, 75);
    private static final Color PURPLE = new Color(105, 85, 185);

    // ============================================================
    // USER DATA
    // ============================================================

    private final int userId;
    private final String userName;

    private JPanel contentPanel;
    private final ComplaintDAO complaintDAO = new ComplaintDAO();

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public CitizenFrame(int userId, String userName) {

        this.userId = userId;
        this.userName = userName;

        setTitle("Public Complaint Management System - Citizen Portal");
        setSize(1250, 780);
        setMinimumSize(new Dimension(1050, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        // Header
        root.add(createHeader(), BorderLayout.NORTH);

        // Sidebar
        root.add(createSidebar(), BorderLayout.WEST);

        // Main content
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(
                new EmptyBorder(22, 22, 22, 22)
        );

        showDashboard();

        // Main content scroll
        JScrollPane mainScrollPane =
                new JScrollPane(contentPanel);

        mainScrollPane.setBorder(null);

        mainScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        mainScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        mainScrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        root.add(
                mainScrollPane,
                BorderLayout.CENTER
        );

        setContentPane(root);
    }

    // ============================================================
    // HEADER
    // ============================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(NAVY);
        header.setPreferredSize(
                new Dimension(0, 88)
        );

        header.setBorder(
                new EmptyBorder(12, 28, 12, 25)
        );

        // -------------------------
        // LEFT
        // -------------------------

        JPanel titlePanel = new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "PUBLIC COMPLAINT MANAGEMENT SYSTEM"
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        JLabel subtitle =
                new JLabel("Citizen Portal");

        subtitle.setForeground(
                new Color(205, 225, 245)
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        titlePanel.add(title);
        titlePanel.add(
                Box.createVerticalStrut(4)
        );
        titlePanel.add(subtitle);

        // -------------------------
        // RIGHT
        // -------------------------

        JPanel profilePanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                14,
                                8
                        )
                );

        profilePanel.setOpaque(false);

        JButton notificationButton =
                new JButton();

        notificationButton.setPreferredSize(
                new Dimension(42, 42)
        );

        notificationButton.setFocusPainted(false);
        notificationButton.setBorderPainted(false);
        notificationButton.setContentAreaFilled(false);

        notificationButton.setToolTipText(
                "Notifications"
        );

        notificationButton.setIcon(
                new BellIcon(
                        Color.WHITE,
                        22
                )
        );

        notificationButton.addActionListener(
                e -> showNotifications()
        );

        JLabel userIcon =
                new JLabel(
                        new UserIcon(
                                Color.WHITE,
                                27
                        )
                );

        JPanel userText = new JPanel();

        userText.setOpaque(false);

        userText.setLayout(
                new BoxLayout(
                        userText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel name =
                new JLabel(userName);

        name.setForeground(Color.WHITE);

        name.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        JLabel role =
                new JLabel("Citizen");

        role.setForeground(
                new Color(190, 215, 240)
        );

        role.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        userText.add(name);
        userText.add(role);

        profilePanel.add(notificationButton);
        profilePanel.add(userIcon);
        profilePanel.add(userText);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                profilePanel,
                BorderLayout.EAST
        );

        return header;
    }

    // ============================================================
    // SIDEBAR
    // ============================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(new BorderLayout());

        sidebar.setPreferredSize(
                new Dimension(245, 0)
        );

        sidebar.setBackground(WHITE);

        sidebar.setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        0,
                        1,
                        BORDER
                )
        );

        // -------------------------
        // MENU TITLE
        // -------------------------

        JLabel menuTitle =
                new JLabel("CITIZEN MENU");

        menuTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        menuTitle.setForeground(MUTED);

        menuTitle.setBorder(
                new EmptyBorder(
                        22,
                        25,
                        15,
                        15
                )
        );

        sidebar.add(
                menuTitle,
                BorderLayout.NORTH
        );

        // -------------------------
        // MENU
        // -------------------------

        JPanel menu = new JPanel();

        menu.setBackground(WHITE);

        menu.setLayout(
                new BoxLayout(
                        menu,
                        BoxLayout.Y_AXIS
                )
        );

        menu.setBorder(
                new EmptyBorder(
                        0,
                        12,
                        15,
                        12
                )
        );

        JButton dashboardButton =
                createMenuButton(
                        "Dashboard",
                        new DashboardIcon(BLUE),
                        true
                );

        JButton submitButton =
                createMenuButton(
                        "Submit Complaint",
                        new ComplaintIcon(BLUE),
                        false
                );

        JButton complaintsButton =
                createMenuButton(
                        "My Complaints",
                        new DocumentIcon(BLUE),
                        false
                );

        JButton notificationButton =
                createMenuButton(
                        "Notifications",
                        new BellIcon(BLUE, 20),
                        false
                );

        JButton feedbackButton =
                createMenuButton(
                        "Feedback",
                        new StarIcon(BLUE),
                        false
                );

        JButton profileButton =
                createMenuButton(
                        "My Profile",
                        new UserIcon(BLUE, 20),
                        false
                );

        JButton helpButton =
                createMenuButton(
                        "Help & Support",
                        new HelpIcon(BLUE),
                        false
                );

        menu.add(dashboardButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(submitButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(complaintsButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(notificationButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(feedbackButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(profileButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(helpButton);

        // -------------------------
        // ACTIONS
        // -------------------------

        dashboardButton.addActionListener(
                e -> showDashboard()
        );

        submitButton.addActionListener(
                e -> showSubmitComplaint()
        );

        complaintsButton.addActionListener(
                e -> showMyComplaints()
        );

        notificationButton.addActionListener(
                e -> showNotifications()
        );

        feedbackButton.addActionListener(
                e -> showFeedback()
        );

        profileButton.addActionListener(
                e -> showProfile()
        );

        helpButton.addActionListener(
                e -> showHelp()
        );

        // -------------------------
        // SCROLLABLE MENU
        // -------------------------

        JScrollPane menuScroll =
                new JScrollPane(menu);

        menuScroll.setBorder(null);

        menuScroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        menuScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        menuScroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        sidebar.add(
                menuScroll,
                BorderLayout.CENTER
        );

        // -------------------------
        // LOGOUT
        // -------------------------

        JPanel bottom =
                new JPanel(new BorderLayout());

        bottom.setBackground(WHITE);

        bottom.setBorder(
                new EmptyBorder(
                        8,
                        12,
                        18,
                        12
                )
        );

        JSeparator separator =
                new JSeparator();

        separator.setForeground(BORDER);

        JButton logoutButton =
                createMenuButton(
                        "Logout",
                        new LogoutIcon(BLUE),
                        false
                );

        logoutButton.addActionListener(
                e -> logout()
        );

        bottom.add(
                separator,
                BorderLayout.NORTH
        );

        bottom.add(
                logoutButton,
                BorderLayout.SOUTH
        );

        sidebar.add(
                bottom,
                BorderLayout.SOUTH
        );

        return sidebar;
    }

    // ============================================================
    // DASHBOARD
    // ============================================================

    private void showDashboard() {

        contentPanel.removeAll();

        JPanel main = new JPanel();

        main.setBackground(BACKGROUND);

        main.setLayout(
                new BoxLayout(
                        main,
                        BoxLayout.Y_AXIS
                )
        );

        // Welcome
        main.add(
                createWelcomePanel()
        );

        main.add(
                Box.createVerticalStrut(20)
        );

        // Statistics
        JPanel statistics =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        statistics.setOpaque(false);

        int[] stats = complaintDAO.getUserComplaintStatistics(userId);

        statistics.add(
                createStatCard(
                        "TOTAL",
                        String.valueOf(stats[0]),
                        "Complaints",
                        BLUE,
                        new DocumentIcon(BLUE)
                )
        );

        statistics.add(
                createStatCard(
                        "PENDING",
                        String.valueOf(stats[1]),
                        "Active complaints",
                        ORANGE,
                        new ClockIcon(ORANGE)
                )
        );

        statistics.add(
                createStatCard(
                        "RESOLVED",
                        String.valueOf(stats[2]),
                        "Completed",
                        GREEN,
                        new CheckIcon(GREEN)
                )
        );

        statistics.add(
                createStatCard(
                        "ESCALATED",
                        String.valueOf(stats[3]),
                        "Needs attention",
                        RED,
                        new CrossIcon(RED)
                )
        );

        main.add(statistics);

        main.add(
                Box.createVerticalStrut(25)
        );

        // Quick actions title
        JLabel quickTitle =
                new JLabel("Quick Actions");

        quickTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        quickTitle.setForeground(TEXT);

        quickTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        main.add(quickTitle);

        main.add(
                Box.createVerticalStrut(12)
        );

        // Quick actions
        JPanel actions =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        actions.setOpaque(false);

        actions.add(
                createActionCard(
                        "Submit Complaint",
                        "Report a public issue",
                        new ComplaintIcon(BLUE),
                        BLUE,
                        "Submit"
                )
        );

        actions.add(
                createActionCard(
                        "My Complaints",
                        "Track your complaints",
                        new DocumentIcon(GREEN),
                        GREEN,
                        "View"
                )
        );

        actions.add(
                createActionCard(
                        "Notifications",
                        "View latest updates",
                        new BellIcon(PURPLE, 28),
                        PURPLE,
                        "Open"
                )
        );

        actions.add(
                createActionCard(
                        "Feedback",
                        "Rate resolved complaints",
                        new StarIcon(ORANGE),
                        ORANGE,
                        "Give"
                )
        );

        main.add(actions);

        main.add(
                Box.createVerticalStrut(25)
        );

        // Bottom
        JPanel bottom =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );

        bottom.setOpaque(false);

        bottom.add(
                createRecentComplaintsPanel()
        );

        bottom.add(
                createInformationPanel()
        );

        main.add(bottom);

        contentPanel.add(
                main,
                BorderLayout.CENTER
        );

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ============================================================
    // WELCOME
    // ============================================================

    private JPanel createWelcomePanel() {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                22,
                                25,
                                22,
                                25
                        )
                )
        );

        JLabel welcome =
                new JLabel(
                        "Welcome back, " + userName + "!"
                );

        welcome.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        welcome.setForeground(NAVY);

        JLabel message =
                new JLabel(
                        "Thank you for helping us build a cleaner, safer and better city."
                );

        message.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        message.setForeground(MUTED);

        JLabel slogan =
                new JLabel(
                        "Report. Track. Make a Difference."
                );

        slogan.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        slogan.setForeground(BLUE);

        JPanel text = new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        text.add(welcome);
        text.add(
                Box.createVerticalStrut(8)
        );
        text.add(message);
        text.add(
                Box.createVerticalStrut(6)
        );
        text.add(slogan);

        JPanel icons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                25,
                                10
                        )
                );

        icons.setOpaque(false);

        icons.add(
                new JLabel(
                        new DocumentIcon(BLUE)
                )
        );

        icons.add(
                new JLabel(
                        new TreeIcon(GREEN)
                )
        );

        icons.add(
                new JLabel(
                        new BulbIcon(ORANGE)
                )
        );

        panel.add(
                text,
                BorderLayout.CENTER
        );

        panel.add(
                icons,
                BorderLayout.EAST
        );

        return panel;
    }

    // ============================================================
    // STAT CARD
    // ============================================================

    private JPanel createStatCard(
            String title,
            String number,
            String description,
            Color color,
            Icon icon) {

        JPanel card =
                new JPanel(new BorderLayout());

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                16,
                                15,
                                16,
                                15
                        )
                )
        );

        JPanel left =
                new JPanel(new BorderLayout());

        left.setOpaque(false);

        JPanel line = new JPanel();

        line.setBackground(color);

        line.setPreferredSize(
                new Dimension(5, 72)
        );

        left.add(
                line,
                BorderLayout.WEST
        );

        JPanel text = new JPanel();

        text.setOpaque(false);

        text.setBorder(
                new EmptyBorder(
                        0,
                        12,
                        0,
                        0
                )
        );

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        titleLabel.setForeground(MUTED);

        JLabel numberLabel =
                new JLabel(number);

        numberLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        numberLabel.setForeground(color);

        JLabel descLabel =
                new JLabel(description);

        descLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        descLabel.setForeground(TEXT);

        text.add(titleLabel);

        text.add(
                Box.createVerticalStrut(2)
        );

        text.add(numberLabel);

        text.add(descLabel);

        left.add(
                text,
                BorderLayout.CENTER
        );

        JLabel iconLabel =
                new JLabel(icon);

        card.add(
                left,
                BorderLayout.CENTER
        );

        card.add(
                iconLabel,
                BorderLayout.EAST
        );

        return card;
    }

    // ============================================================
    // ACTION CARD
    // ============================================================

    private JPanel createActionCard(
            String title,
            String description,
            Icon icon,
            Color color,
            String buttonText) {

        JPanel card =
                new JPanel(new BorderLayout());

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );

        JPanel top =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        top.setOpaque(false);

        top.add(
                new JLabel(icon)
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        titleLabel.setForeground(TEXT);

        titleLabel.setBorder(
                new EmptyBorder(
                        5,
                        12,
                        0,
                        0
                )
        );

        top.add(titleLabel);

        JPanel center =
                new JPanel();

        center.setOpaque(false);

        center.setLayout(
                new BoxLayout(
                        center,
                        BoxLayout.Y_AXIS
                )
        );

        center.add(top);

        center.add(
                Box.createVerticalStrut(8)
        );

        JLabel desc =
                new JLabel(description);

        desc.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        desc.setForeground(MUTED);

        center.add(desc);

        JButton button =
                new JButton(
                        buttonText + "  >"
                );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(color);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        100,
                        35
                )
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                12
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(button);

        if (title.equals("Submit Complaint")) {

            button.addActionListener(
                    e -> showSubmitComplaint()
            );

        } else if (title.equals("My Complaints")) {

            button.addActionListener(
                    e -> showMyComplaints()
            );

        } else if (title.equals("Notifications")) {

            button.addActionListener(
                    e -> showNotifications()
            );

        } else if (title.equals("Feedback")) {

            button.addActionListener(
                    e -> showFeedback()
            );
        }

        card.add(
                center,
                BorderLayout.CENTER
        );

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        return card;
    }

    // ============================================================
    // RECENT COMPLAINTS
    // ============================================================

    private JPanel createRecentComplaintsPanel() {

        JPanel panel = createWhitePanel();
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Recent Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT);
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "Complaint", "Priority", "Status", "Date"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(LIGHT_BLUE);
        table.getTableHeader().setForeground(TEXT);
        table.setShowGrid(false);

        for (Object[] row : complaintDAO.getRecentUserComplaints(userId, 5)) {
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ============================================================
    // INFORMATION PANEL
    // ============================================================

    private JPanel createInformationPanel() {

        JPanel panel =
                createWhitePanel();

        panel.setLayout(
                new BorderLayout()
        );

        JLabel title =
                new JLabel(
                        "Quick Information"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        title.setForeground(TEXT);

        panel.add(
                title,
                BorderLayout.NORTH
        );

        JPanel infoGrid =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                10
                        )
                );

        infoGrid.setOpaque(false);

        infoGrid.setBorder(
                new EmptyBorder(
                        15,
                        0,
                        0,
                        0
                )
        );

        infoGrid.add(
                createInfo(
                        "Guidelines",
                        "How to submit a complaint",
                        new DocumentIcon(BLUE)
                )
        );

        infoGrid.add(
                createInfo(
                        "SLA",
                        "Expected resolution time",
                        new ClockIcon(ORANGE)
                )
        );

        infoGrid.add(
                createInfo(
                        "Help",
                        "Get assistance",
                        new HelpIcon(GREEN)
                )
        );

        infoGrid.add(
                createInfo(
                        "Updates",
                        "System announcements",
                        new BellIcon(PURPLE, 20)
                )
        );

        panel.add(
                infoGrid,
                BorderLayout.CENTER
        );

        return panel;
    }

    // ============================================================
    // INFORMATION ITEM
    // ============================================================

    private JPanel createInfo(
            String title,
            String description,
            Icon icon) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                LIGHT_BLUE
        );

        panel.setBorder(
                new EmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );

        JLabel iconLabel =
                new JLabel(icon);

        JPanel text =
                new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        titleLabel.setForeground(TEXT);

        JLabel descLabel =
                new JLabel(description);

        descLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );

        descLabel.setForeground(MUTED);

        text.add(titleLabel);

        text.add(
                Box.createVerticalStrut(3)
        );

        text.add(descLabel);

        panel.add(
                iconLabel,
                BorderLayout.WEST
        );

        panel.add(
                text,
                BorderLayout.CENTER
        );

        return panel;
    }

    // ============================================================
    // WHITE PANEL
    // ============================================================

    private JPanel createWhitePanel() {

        JPanel panel =
                new JPanel();

        panel.setBackground(WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );

        return panel;
    }

    // ============================================================
    // MENU BUTTON
    // ============================================================

    private JButton createMenuButton(
            String text,
            Icon icon,
            boolean selected) {

        JButton button =
                new JButton(
                        text,
                        icon
                );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setIconTextGap(14);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );

        button.setPreferredSize(
                new Dimension(
                        215,
                        52
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        if (selected) {

            button.setBackground(BLUE);
            button.setForeground(Color.WHITE);

        } else {

            button.setBackground(WHITE);
            button.setForeground(TEXT);
        }

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        if (!selected) {

                            button.setBackground(
                                    LIGHT_BLUE
                            );

                            button.setForeground(
                                    BLUE_DARK
                            );
                        }
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        if (!selected) {

                            button.setBackground(
                                    WHITE
                            );

                            button.setForeground(
                                    TEXT
                            );
                        }
                    }
                }
        );

        return button;
    }

    // ============================================================
    // SUBMIT COMPLAINT
    // ============================================================

    private void showSubmitComplaint() {

        JTextField titleField = new JTextField(25);
        JTextField locationField = new JTextField(25);
        JComboBox<String> categoryBox = new JComboBox<>();

        java.util.List<Object[]> categories = complaintDAO.getCategories();

        if (categories.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load complaint categories from the database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        for (Object[] category : categories) {
            categoryBox.addItem(category[1] + "  |  " + category[3]);
        }

        JTextArea descriptionArea = new JTextArea(5, 25);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JLabel departmentLabel = new JLabel(categories.get(0)[3].toString());
        departmentLabel.setForeground(BLUE_DARK);

        JLabel priorityLabel = new JLabel(categories.get(0)[4].toString());
        priorityLabel.setForeground(ORANGE);

        categoryBox.addActionListener(e -> {
            int index = categoryBox.getSelectedIndex();
            if (index >= 0 && index < categories.size()) {
                departmentLabel.setText(categories.get(index)[3].toString());
                priorityLabel.setText(categories.get(index)[4].toString());
            }
        });

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Complaint Title:"), gbc);
        gbc.gridx = 1;
        form.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        form.add(categoryBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        form.add(departmentLabel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Priority:"), gbc);
        gbc.gridx = 1;
        form.add(priorityLabel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        form.add(locationField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        form.add(new JScrollPane(descriptionArea), gbc);

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                "Submit New Complaint",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) return;

        String title = titleField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty() || location.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int selectedIndex = categoryBox.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= categories.size()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a valid category.",
                    "Invalid Category",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Object[] selected = categories.get(selectedIndex);

        int categoryId = ((Number) selected[0]).intValue();
        int departmentId = ((Number) selected[2]).intValue();
        String priority = selected[4].toString();

        Complaint complaint = new Complaint(
                0,
                userId,
                categoryId,
                departmentId,
                title,
                description,
                location,
                priority,
                "SUBMITTED"
        );

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int complaintId;
        try {
            complaintId = complaintDAO.submitComplaint(complaint);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }

        if (complaintId > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Complaint submitted successfully!\n\n"
                            + "Complaint ID: #" + complaintId
                            + "\nDepartment: " + selected[3]
                            + "\nPriority: " + priority
                            + "\n\nYour complaint has been recorded and routed automatically.",
                    "Complaint Submitted",
                    JOptionPane.INFORMATION_MESSAGE
            );
            showDashboard();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "The complaint could not be submitted.\n"
                            + "Please check your database connection and try again.",
                    "Submission Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ============================================================
    // MY COMPLAINTS
    // ============================================================

    private void showMyComplaints() {

        contentPanel.removeAll();

        JPanel panel = createWhitePanel();
        panel.setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel title = new JLabel("My Complaints");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(NAVY);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFocusPainted(false);
        refreshButton.setBackground(BLUE);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(e -> showMyComplaints());

        top.add(title, BorderLayout.WEST);
        top.add(refreshButton, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);

        String[] columns = {
                "Complaint ID", "Title", "Category", "Department",
                "Location", "Priority", "Status", "Date"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(LIGHT_BLUE);
        table.getTableHeader().setForeground(TEXT);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (Object[] row : complaintDAO.getUserComplaints(userId)) {
            model.addRow(row);
        }

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() >= 0) {
                    int row = table.getSelectedRow();
                    int complaintId =
                            ((Number) model.getValueAt(row, 0)).intValue();
                    showComplaintDetails(complaintId);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel hint = new JLabel(
                "Double-click a complaint to view full details."
        );
        hint.setForeground(MUTED);
        hint.setBorder(new EmptyBorder(10, 0, 0, 0));
        panel.add(hint, BorderLayout.SOUTH);

        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showComplaintDetails(int complaintId) {

        Object[] details = complaintDAO.getComplaintDetails(
                complaintId,
                userId
        );

        if (details == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Complaint details could not be loaded.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setBackground(Color.WHITE);
        area.setBorder(new EmptyBorder(15, 15, 15, 15));

        area.setText(
                "COMPLAINT DETAILS\n"
                        + "==============================\n\n"
                        + "Complaint ID: #" + details[0] + "\n"
                        + "Title: " + details[1] + "\n\n"
                        + "Category: " + details[3] + "\n"
                        + "Department: " + details[4] + "\n"
                        + "Location: " + details[5] + "\n"
                        + "Priority: " + details[6] + "\n"
                        + "Status: " + details[7] + "\n\n"
                        + "Expected Resolution: " + details[8] + "\n"
                        + "Created At: " + details[9] + "\n"
                        + "Updated At: " + details[10] + "\n\n"
                        + "DESCRIPTION\n"
                        + "------------------------------\n"
                        + details[2]
        );

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(560, 430));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Complaint #" + complaintId,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ============================================================
    // NOTIFICATIONS
    // ============================================================

    private void showNotifications() {

        java.util.List<Object[]> notifications =
                complaintDAO.getUserNotifications(userId);

        if (notifications.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "You currently have no notifications.",
                    "Notifications",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (Object[] notification : notifications) {

            int notificationId =
                    ((Number) notification[0]).intValue();

            String message = String.valueOf(notification[3]);
            boolean isRead = (Boolean) notification[4];

            JPanel item = new JPanel(new BorderLayout(10, 5));
            item.setBackground(isRead ? WHITE : LIGHT_BLUE);
            item.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER),
                            new EmptyBorder(10, 10, 10, 10)
                    )
            );

            JLabel messageLabel = new JLabel(
                    "<html>" + escapeHtml(message) + "</html>"
            );

            JLabel dateLabel = new JLabel(
                    String.valueOf(notification[5])
            );
            dateLabel.setForeground(MUTED);
            dateLabel.setFont(
                    new Font("Segoe UI", Font.PLAIN, 11)
            );

            JPanel textPanel = new JPanel();
            textPanel.setOpaque(false);
            textPanel.setLayout(
                    new BoxLayout(textPanel, BoxLayout.Y_AXIS)
            );
            textPanel.add(messageLabel);
            textPanel.add(Box.createVerticalStrut(5));
            textPanel.add(dateLabel);

            item.add(
                    new JLabel(new BellIcon(
                            isRead ? MUTED : PURPLE, 24
                    )),
                    BorderLayout.WEST
            );
            item.add(textPanel, BorderLayout.CENTER);

            if (!isRead) {

                JButton readButton = new JButton("Mark Read");
                readButton.setFocusPainted(false);
                readButton.setBackground(BLUE);
                readButton.setForeground(Color.WHITE);
                readButton.setBorderPainted(false);

                readButton.addActionListener(e -> {
                    if (complaintDAO.markNotificationRead(notificationId)) {
                        item.setBackground(WHITE);
                        readButton.setVisible(false);
                        panel.revalidate();
                        panel.repaint();
                    }
                });

                item.add(readButton, BorderLayout.EAST);
            }

            panel.add(item);
            panel.add(Box.createVerticalStrut(8));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(650, 450));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Notifications",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String escapeHtml(String value) {
        if (value == null) return "";
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // ============================================================
    // FEEDBACK
    // ============================================================

    private void showFeedback() {

        String[] options = {
                "Excellent",
                "Good",
                "Average",
                "Poor"
        };

        String selected =
                (String) JOptionPane.showInputDialog(
                        this,
                        "How was your complaint resolution experience?",
                        "Feedback",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        if (selected != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Thank you for your feedback: "
                            + selected,
                    "Feedback Submitted",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ============================================================
    // PROFILE
    // ============================================================

    private void showProfile() {

        JOptionPane.showMessageDialog(
                this,
                "Citizen Profile\n\n"
                        + "Name: " + userName + "\n"
                        + "User ID: " + userId + "\n"
                        + "Role: Citizen",
                "My Profile",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ============================================================
    // HELP
    // ============================================================

    private void showHelp() {

        JOptionPane.showMessageDialog(
                this,
                "Help & Support\n\n"
                        + "1. Submit a complaint using Submit Complaint.\n"
                        + "2. Track progress from My Complaints.\n"
                        + "3. Check Notifications for status updates.\n"
                        + "4. Provide feedback after resolution.",
                "Help & Support",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ============================================================
    // LOGOUT
    // ============================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            new LoginFrame().setVisible(true);

            dispose();
        }
    }

    // ============================================================
    // BASE ICON
    // ============================================================

    static abstract class BaseIcon
            implements Icon {

        protected Color color;
        protected int size;

        BaseIcon(
                Color color,
                int size) {

            this.color = color;
            this.size = size;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        protected Graphics2D graphics(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setStroke(
                    new BasicStroke(
                            2f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            return g2;
        }
    }

    // ============================================================
    // DASHBOARD ICON
    // ============================================================

    static class DashboardIcon
            extends BaseIcon {

        DashboardIcon(Color color) {
            super(color, 22);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.drawRoundRect(
                    x + 2,
                    y + 2,
                    7,
                    7,
                    2,
                    2
            );

            g2.drawRoundRect(
                    x + 13,
                    y + 2,
                    7,
                    7,
                    2,
                    2
            );

            g2.drawRoundRect(
                    x + 2,
                    y + 13,
                    7,
                    7,
                    2,
                    2
            );

            g2.drawRoundRect(
                    x + 13,
                    y + 13,
                    7,
                    7,
                    2,
                    2
            );

            g2.dispose();
        }
    }

    // ============================================================
    // COMPLAINT ICON
    // ============================================================

    static class ComplaintIcon
            extends BaseIcon {

        ComplaintIcon(Color color) {
            super(color, 22);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.drawRect(
                    x + 3,
                    y + 2,
                    16,
                    18
            );

            g2.drawLine(
                    x + 6,
                    y + 7,
                    x + 16,
                    y + 7
            );

            g2.drawLine(
                    x + 6,
                    y + 11,
                    x + 16,
                    y + 11
            );

            g2.drawLine(
                    x + 6,
                    y + 15,
                    x + 13,
                    y + 15
            );

            g2.dispose();
        }
    }

    // ============================================================
    // DOCUMENT ICON
    // ============================================================

    static class DocumentIcon
            extends BaseIcon {

        DocumentIcon(Color color) {
            super(color, 22);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.drawRoundRect(
                    x + 4,
                    y + 2,
                    14,
                    18,
                    2,
                    2
            );

            g2.drawLine(
                    x + 7,
                    y + 7,
                    x + 15,
                    y + 7
            );

            g2.drawLine(
                    x + 7,
                    y + 11,
                    x + 15,
                    y + 11
            );

            g2.drawLine(
                    x + 7,
                    y + 15,
                    x + 13,
                    y + 15
            );

            g2.dispose();
        }
    }

    // ============================================================
    // BELL ICON
    // ============================================================

    static class BellIcon
            extends BaseIcon {

        BellIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            int center =
                    size / 2;

            g2.drawArc(
                    x + center - 7,
                    y + 4,
                    14,
                    15,
                    0,
                    180
            );

            g2.drawLine(
                    x + center - 7,
                    y + 11,
                    x + center - 7,
                    y + 16
            );

            g2.drawLine(
                    x + center + 7,
                    y + 11,
                    x + center + 7,
                    y + 16
            );

            g2.drawLine(
                    x + center - 9,
                    y + 16,
                    x + center + 9,
                    y + 16
            );

            g2.drawOval(
                    x + center - 2,
                    y + 17,
                    4,
                    4
            );

            g2.dispose();
        }
    }

    // ============================================================
    // USER ICON
    // ============================================================

    static class UserIcon
            extends BaseIcon {

        UserIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            int center =
                    size / 2;

            g2.drawOval(
                    x + center - 5,
                    y + 2,
                    10,
                    10
            );

            g2.drawArc(
                    x + center - 9,
                    y + 12,
                    18,
                    12,
                    0,
                    180
            );

            g2.dispose();
        }
    }

    // ============================================================
    // STAR ICON
    // ============================================================

    static class StarIcon
            extends BaseIcon {

        StarIcon(Color color) {
            super(color, 22);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            Polygon star =
                    new Polygon();

            for (int i = 0; i < 10; i++) {

                double angle =
                        -Math.PI / 2
                                + i * Math.PI / 5;

                int radius =
                        (i % 2 == 0)
                                ? 9
                                : 4;

                int px =
                        x + 11
                                + (int)
                                (Math.cos(angle)
                                        * radius);

                int py =
                        y + 11
                                + (int)
                                (Math.sin(angle)
                                        * radius);

                star.addPoint(
                        px,
                        py
                );
            }

            g2.drawPolygon(star);

            g2.dispose();
        }
    }

    // ============================================================
    // HELP ICON
    // ============================================================

    static class HelpIcon
            extends BaseIcon {

        HelpIcon(Color color) {
            super(color, 22);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.drawOval(
                    x + 2,
                    y + 2,
                    18,
                    18
            );

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            14
                    )
            );

            g2.drawString(
                    "?",
                    x + 8,
                    y + 16
            );

            g2.dispose();
        }
    }

    // ============================================================
    // CLOCK ICON
    // ============================================================

    static class ClockIcon
            extends BaseIcon {

        ClockIcon(Color color) {
            super(color, 22);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.drawOval(
                    x + 2,
                    y + 2,
                    18,
                    18
            );

            g2.drawLine(
                    x + 11,
                    y + 6,
                    x + 11,
                    y + 11
            );

            g2.drawLine(
                    x + 11,
                    y + 11,
                    x + 15,
                    y + 14
            );

            g2.dispose();
        }
    }

    // ============================================================
    // CHECK ICON
    // ============================================================

    static class CheckIcon
            extends BaseIcon {

        CheckIcon(Color color) {
            super(color, 22);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.drawOval(
                    x + 2,
                    y + 2,
                    18,
                    18
            );

            g2.drawLine(
                    x + 6,
                    y + 11,
                    x + 10,
                    y + 15
            );

            g2.drawLine(
                    x + 10,
                    y + 15,
                    x + 17,
                    y + 7
            );

            g2.dispose();
        }
    }

    // ============================================================
    // CROSS ICON
    // ============================================================

    static class CrossIcon
            extends BaseIcon {

        CrossIcon(Color color) {
            super(color, 22);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.drawOval(
                    x + 2,
                    y + 2,
                    18,
                    18
            );

            g2.drawLine(
                    x + 7,
                    y + 7,
                    x + 15,
                    y + 15
            );

            g2.drawLine(
                    x + 15,
                    y + 7,
                    x + 7,
                    y + 15
            );

            g2.dispose();
        }
    }

    // ============================================================
    // LOGOUT ICON
    // ============================================================

    static class LogoutIcon
            extends BaseIcon {

        LogoutIcon(Color color) {
            super(color, 22);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.drawLine(
                    x + 3,
                    y + 3,
                    x + 3,
                    y + 19
            );

            g2.drawLine(
                    x + 3,
                    y + 3,
                    x + 11,
                    y + 3
            );

            g2.drawLine(
                    x + 3,
                    y + 19,
                    x + 11,
                    y + 19
            );

            g2.drawLine(
                    x + 10,
                    y + 11,
                    x + 20,
                    y + 11
            );

            g2.drawLine(
                    x + 16,
                    y + 7,
                    x + 20,
                    y + 11
            );

            g2.drawLine(
                    x + 16,
                    y + 15,
                    x + 20,
                    y + 11
            );

            g2.dispose();
        }
    }

    // ============================================================
    // TREE ICON
    // ============================================================

    static class TreeIcon
            extends BaseIcon {

        TreeIcon(Color color) {
            super(color, 30);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.fillOval(
                    x + 7,
                    y + 2,
                    16,
                    16
            );

            g2.fillOval(
                    x + 3,
                    y + 8,
                    16,
                    14
            );

            g2.fillOval(
                    x + 13,
                    y + 8,
                    16,
                    14
            );

            g2.fillRect(
                    x + 13,
                    y + 18,
                    5,
                    10
            );

            g2.dispose();
        }
    }

    // ============================================================
    // BULB ICON
    // ============================================================

    static class BulbIcon
            extends BaseIcon {

        BulbIcon(Color color) {
            super(color, 30);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    graphics(g);

            g2.setColor(color);

            g2.drawOval(
                    x + 6,
                    y + 2,
                    18,
                    20
            );

            g2.drawLine(
                    x + 11,
                    y + 22,
                    x + 19,
                    y + 22
            );

            g2.drawLine(
                    x + 11,
                    y + 25,
                    x + 19,
                    y + 25
            );

            g2.drawLine(
                    x + 13,
                    y + 28,
                    x + 17,
                    y + 28
            );

            g2.dispose();
        }
    }
}
