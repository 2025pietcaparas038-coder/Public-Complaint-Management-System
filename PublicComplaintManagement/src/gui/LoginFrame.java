package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class LoginFrame extends JFrame {

    // =========================
    // COLORS
    // =========================

    private static final Color DARK_BLUE = new Color(8, 52, 105);
    private static final Color BLUE = new Color(20, 130, 220);
    private static final Color LIGHT_BLUE = new Color(235, 246, 255);
    private static final Color GREEN = new Color(35, 155, 75);
    private static final Color ORANGE = new Color(245, 145, 30);
    private static final Color TEXT_DARK = new Color(25, 50, 80);
    private static final Color TEXT_GRAY = new Color(100, 115, 135);
    private static final Color BORDER = new Color(205, 218, 232);

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Public Complaint Management System");

        setSize(1250, 760);

        setMinimumSize(new Dimension(1000, 650));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // ============================================
        // MAIN PANEL
        // ============================================

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // LEFT SIDE
        CityPanel cityPanel = new CityPanel();

        // RIGHT SIDE
        JPanel loginPanel = createLoginPanel();

        mainPanel.add(cityPanel);
        mainPanel.add(loginPanel);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // =========================================================
    // RIGHT LOGIN PANEL
    // =========================================================

    private JPanel createLoginPanel() {

        JPanel outerPanel = new JPanel(new GridBagLayout());

        outerPanel.setBackground(new Color(247, 250, 253));

        outerPanel.setBorder(
                new EmptyBorder(35, 45, 35, 45)
        );

        // LOGIN CARD

        JPanel card = new JPanel();

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 232, 240)
                        ),
                        new EmptyBorder(
                                35, 45, 35, 45
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        // ============================================
        // WELCOME
        // ============================================

        JLabel welcome = new JLabel(
                "Welcome Back!"
        );

        welcome.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );

        welcome.setForeground(DARK_BLUE);

        welcome.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(welcome);

        card.add(
                Box.createVerticalStrut(8)
        );

        JLabel subtitle = new JLabel(
                "<html><center>Login to continue to the<br>" +
                        "Public Complaint Management System</center></html>"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        17
                )
        );

        subtitle.setForeground(TEXT_GRAY);

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(subtitle);

        card.add(
                Box.createVerticalStrut(32)
        );

        // ============================================
        // EMAIL LABEL
        // ============================================

        JLabel emailLabel =
                createLabel("Email Address");

        card.add(emailLabel);

        card.add(
                Box.createVerticalStrut(7)
        );

        // EMAIL FIELD

        emailField = new JTextField();

        styleTextField(emailField);

        emailField.setToolTipText(
                "Enter your email address"
        );

        card.add(emailField);

        card.add(
                Box.createVerticalStrut(18)
        );

        // ============================================
        // PASSWORD LABEL
        // ============================================

        JLabel passwordLabel =
                createLabel("Password");

        card.add(passwordLabel);

        card.add(
                Box.createVerticalStrut(7)
        );

        // PASSWORD

        passwordField =
                new JPasswordField();

        styleTextField(passwordField);

        card.add(passwordField);

        card.add(
                Box.createVerticalStrut(13)
        );

        // ============================================
        // REMEMBER + FORGOT
        // ============================================

        JPanel optionsPanel =
                new JPanel(
                        new BorderLayout()
                );

        optionsPanel.setOpaque(false);

        JCheckBox remember =
                new JCheckBox(
                        "Remember me"
                );

        remember.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        remember.setForeground(TEXT_GRAY);

        remember.setOpaque(false);

        JLabel forgot =
                new JLabel(
                        "Forgot Password?"
                );

        forgot.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        forgot.setForeground(BLUE);

        forgot.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        optionsPanel.add(
                remember,
                BorderLayout.WEST
        );

        optionsPanel.add(
                forgot,
                BorderLayout.EAST
        );

        card.add(optionsPanel);

        card.add(
                Box.createVerticalStrut(20)
        );

        // ============================================
        // LOGIN BUTTON
        // ============================================

        JButton loginButton =
                createButton(
                        "LOGIN",
                        BLUE
                );

        loginButton.addActionListener(
                e -> login()
        );

        card.add(loginButton);

        card.add(
                Box.createVerticalStrut(14)
        );

        // ============================================
        // REGISTER BUTTON
        // ============================================

        JButton registerButton =
                createOutlineButton(
                        "CREATE NEW ACCOUNT",
                        GREEN
                );

        registerButton.addActionListener(e -> {

            new RegistrationFrame();

            dispose();
        });

        card.add(registerButton);

        card.add(
                Box.createVerticalStrut(22)
        );

        // ============================================
        // OR
        // ============================================

        JPanel orPanel =
                new JPanel(
                        new BorderLayout(12, 0)
                );

        orPanel.setOpaque(false);

        JSeparator leftLine =
                new JSeparator();

        JSeparator rightLine =
                new JSeparator();

        JLabel or =
                new JLabel("OR");

        or.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        or.setForeground(TEXT_GRAY);

        orPanel.add(
                leftLine,
                BorderLayout.WEST
        );

        orPanel.add(
                or,
                BorderLayout.CENTER
        );

        orPanel.add(
                rightLine,
                BorderLayout.EAST
        );

        card.add(orPanel);

        card.add(
                Box.createVerticalStrut(18)
        );

        // ============================================
        // QUOTE BOX
        // ============================================

        JPanel quotePanel =
                new JPanel(
                        new GridBagLayout()
                );

        quotePanel.setBackground(
                new Color(
                        242,
                        247,
                        252
                )
        );

        quotePanel.setBorder(
                new EmptyBorder(
                        18, 15, 18, 15
                )
        );

        JLabel quote =
                new JLabel(
                        "<html><center>" +
                                "<i>\"Your Voice Matters,<br>" +
                                "Together We Build a Better City.\"</i>" +
                                "</center></html>"
                );

        quote.setFont(
                new Font(
                        "Segoe UI",
                        Font.ITALIC,
                        16
                )
        );

        quote.setForeground(
                new Color(
                        80,
                        100,
                        125
                )
        );

        quotePanel.add(quote);

        card.add(quotePanel);

        // ============================================
        // ADD CARD TO CENTER
        // ============================================

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.fill = GridBagConstraints.BOTH;

        outerPanel.add(card, gbc);

        return outerPanel;
    }

    // =========================================================
    // CREATE LABEL
    // =========================================================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        label.setForeground(TEXT_DARK);

        return label;
    }

    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        field.setForeground(TEXT_DARK);

        field.setBackground(
                new Color(
                        250,
                        252,
                        255
                )
        );

        field.setPreferredSize(
                new Dimension(
                        400,
                        52
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 14, 5, 14
                        )
                )
        );
    }

    // =========================================================
    // BLUE BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        // Text color
        button.setForeground(Color.WHITE);

        // Background color
        button.setBackground(color);

        // Force Swing to display our background color
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setPreferredSize(
                new Dimension(
                        400,
                        52
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // OUTLINE BUTTON
    // =========================================================

    private JButton createOutlineButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        button.setForeground(color);

        button.setBackground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        color,
                        2
                )
        );

        button.setPreferredSize(
                new Dimension(
                        400,
                        52
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // LOGIN
    // =========================================================

    private void login() {

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (
                email.isEmpty() ||
                        password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        /*
         * DATABASE LOGIN
         *
         * Put your MySQL password below.
         */

        String url =
                "jdbc:mysql://localhost:3306/" +
                        "public_complaint_management";

        String username = "root";

        String mysqlPassword =
                "Paras@2007";

        String sql =
                "SELECT user_id, full_name, role " +
                        "FROM users " +
                        "WHERE email = ? AND password = ?";

        try {

            Connection con =
                    DriverManager.getConnection(
                            url,
                            username,
                            mysqlPassword
                    );

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, email);

            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                int userId = rs.getInt("user_id");
                String name = rs.getString("full_name");
                String role = rs.getString("role");

                JOptionPane.showMessageDialog(
                        this,
                        "Welcome, " + name + "!"
                );

                if ("CITIZEN".equals(role)) {
                    new CitizenFrame(userId, name).setVisible(true);

                } else if ("OFFICER".equals(role)) {
                    new OfficerFrame(userId, name).setVisible(true);

                } else if ("ADMIN".equals(role)) {
                    new AdminFrame(userId, name).setVisible(true);
                }

                dispose();
            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database connection failed:\n" +
                            ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LEFT CITY PANEL
    // =========================================================

    private static class CityPanel extends JPanel {

        private BufferedImage logoImage;

        CityPanel() {
            setBackground(new Color(8, 105, 190));

            // Load the new tricolour logo image
            try {
                logoImage = ImageIO.read(new File("logo.png"));
            } catch (IOException e) {
                System.out.println("Logo image could not be loaded: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w = getWidth();
            int h = getHeight();

            /*
             * =====================================================
             * 1. BACKGROUND
             * =====================================================
             */

            GradientPaint gradient = new GradientPaint(
                    0, 0,
                    new Color(8, 115, 205),
                    w, h,
                    new Color(5, 48, 105)
            );

            g2.setPaint(gradient);
            g2.fillRect(0, 0, w, h);

            /*
             * Decorative circles
             */

            g2.setColor(new Color(255, 255, 255, 28));

            g2.fillOval(
                    -170,
                    80,
                    430,
                    430
            );

            g2.fillOval(
                    w - 300,
                    -130,
                    450,
                    450
            );

            g2.fillOval(
                    w - 180,
                    300,
                    330,
                    330
            );

            /*
             * =====================================================
             * 2. CITY FIRST
             *
             * IMPORTANT:
             * City is drawn BEFORE text.
             * Therefore buildings cannot cover text.
             * =====================================================
             */

            drawCity(g2, w, h);

            /*
             * =====================================================
             * 3. LOGO
             * =====================================================
             */

            drawNewLogo(
                    g2,
                    w / 2,
                    125
            );

            /*
             * =====================================================
             * 4. TITLE
             * =====================================================
             */

            g2.setColor(Color.WHITE);

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            Math.max(28, w / 20)
                    )
            );

            drawCenteredString(
                    g2,
                    "PUBLIC COMPLAINT",
                    w / 2,
                    290
            );

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            Math.max(23, w / 27)
                    )
            );

            drawCenteredString(
                    g2,
                    "MANAGEMENT SYSTEM",
                    w / 2,
                    330
            );

            /*
             * =====================================================
             * 5. SEPARATOR
             * =====================================================
             */

            g2.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            150
                    )
            );

            g2.fillRoundRect(
                    w / 2 - 170,
                    355,
                    340,
                    2,
                    2,
                    2
            );

            /*
             * =====================================================
             * 6. TAGLINE
             * =====================================================
             */

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            Math.max(14, w / 42)
                    )
            );

            drawCenteredString(
                    g2,
                    "A CLEANER, SAFER AND BETTER TOMORROW",
                    w / 2,
                    390
            );

            /*
             * =====================================================
             * 7. SPECIAL MESSAGE
             * =====================================================
             */

            g2.setFont(
                    new Font(
                            "Segoe Script",
                            Font.ITALIC,
                            Math.max(19, w / 34)
                    )
            );

            g2.setColor(Color.WHITE);

            drawCenteredString(
                    g2,
                    "Together",
                    w / 2,
                    445
            );

            drawCenteredString(
                    g2,
                    "for a Better City",
                    w / 2,
                    475
            );

            /*
             * =====================================================
             * 8. BOTTOM FEATURE AREA
             * =====================================================
             */

            // =====================================================
// BOTTOM FEATURES
// =====================================================

            int featureY = h - 32;

            drawFeature(
                    (Graphics2D) g,
                    w / 6,
                    featureY,
                    "✓",
                    "CLEAN",
                    "CITIES"
            );

            drawFeature(
                    (Graphics2D) g,
                    w / 2,
                    featureY,
                    "★",
                    "RESPONSIVE",
                    "GOVERNANCE"
            );

            drawFeature(
                    (Graphics2D) g,
                    (w * 5) / 6,
                    featureY,
                    "♟",
                    "STRONGER",
                    "COMMUNITIES"
            );

            g2.dispose();
        }


        /*
         * =========================================================
         * NEW IMAGE LOGO
         * =========================================================
         */

        private void drawNewLogo(
                Graphics2D g,
                int cx,
                int cy
        ) {

            if (logoImage == null) {
                return;
            }

            int logoWidth = 210;
            int logoHeight = 210;

            int x = cx - logoWidth / 2;
            int y = cy - logoHeight / 2;

            g.drawImage(
                    logoImage,
                    x,
                    y,
                    logoWidth,
                    logoHeight,
                    null
            );
        }


        /*
         * =========================================================
         * CITY
         * =========================================================
         */

        private void drawCity(Graphics2D g, int w, int h) {

            /*
             * CITY SCENE
             * Everything is intentionally kept in the lower
             * portion of the blue panel so it does not overlap
             * the title and tagline.
             */

            // City ground starts near the bottom
            int ground = h - 105;

            // =====================================================
            // CLOUDS
            // =====================================================

            drawCloud(
                    g,
                    30,
                    ground - 125
            );

            drawCloud(
                    g,
                    w - 115,
                    ground - 145
            );


            // =====================================================
            // BUILDINGS
            // =====================================================

            // Small buildings only - kept LOW

            drawBuilding(
                    g,
                    15,
                    ground,
                    65,
                    75
            );

            drawBuilding(
                    g,
                    90,
                    ground,
                    55,
                    60
            );

            drawBuilding(
                    g,
                    155,
                    ground,
                    70,
                    95
            );

            drawBuilding(
                    g,
                    235,
                    ground,
                    55,
                    70
            );

            drawBuilding(
                    g,
                    300,
                    ground,
                    70,
                    90
            );

            drawBuilding(
                    g,
                    380,
                    ground,
                    55,
                    65
            );

            drawBuilding(
                    g,
                    445,
                    ground,
                    70,
                    85
            );

            drawBuilding(
                    g,
                    525,
                    ground,
                    60,
                    70
            );


            // =====================================================
            // GROUND
            // =====================================================

            g.setColor(
                    new Color(
                            6,
                            55,
                            105
                    )
            );

            g.fillRect(
                    0,
                    ground,
                    w,
                    105
            );


            // =====================================================
            // ROAD
            // =====================================================

            g.setColor(
                    new Color(
                            35,
                            65,
                            80
                    )
            );

            g.fillRect(
                    0,
                    ground + 45,
                    w,
                    60
            );


            // =====================================================
            // ROAD MARKINGS
            // =====================================================

            g.setColor(
                    new Color(
                            245,
                            205,
                            65
                    )
            );

            for (
                    int x = 10;
                    x < w;
                    x += 95
            ) {

                g.fillRoundRect(
                        x,
                        ground + 72,
                        50,
                        5,
                        5,
                        5
                );
            }


            // =====================================================
            // TREES
            // =====================================================

            int treeY = ground + 5;

            for (
                    int x = 35;
                    x < w;
                    x += 120
            ) {

                drawTree(
                        g,
                        x,
                        treeY
                );
            }


            // =====================================================
            // STREET LIGHTS
            // =====================================================

            for (
                    int x = 80;
                    x < w;
                    x += 220
            ) {

                // Pole

                g.setColor(
                        new Color(
                                25,
                                45,
                                60
                        )
                );

                g.fillRect(
                        x,
                        ground - 5,
                        4,
                        75
                );


                // Light

                g.setColor(
                        new Color(
                                255,
                                215,
                                70
                        )
                );

                g.fillOval(
                        x - 6,
                        ground - 14,
                        16,
                        16
                );
            }
        }
        private void drawBuilding(
                Graphics2D g,
                int x,
                int ground,
                int width,
                int height
        ) {

            g.setColor(
                    new Color(
                            75,
                            160,
                            215
                    )
            );

            g.fillRect(
                    x,
                    ground - height,
                    width,
                    height
            );

            /*
             * Building top
             */

            g.setColor(
                    new Color(
                            105,
                            185,
                            230
                    )
            );

            g.fillRect(
                    x,
                    ground - height,
                    width,
                    8
            );

            /*
             * Windows
             */

            g.setColor(
                    new Color(
                            225,
                            245,
                            255
                    )
            );

            for (
                    int yy =
                    ground - height + 22;
                    yy <
                            ground - 15;
                    yy += 27
            ) {

                for (
                        int xx =
                        x + 13;
                        xx <
                                x + width - 10;
                        xx += 24
                ) {

                    g.fillRoundRect(
                            xx,
                            yy,
                            9,
                            12,
                            2,
                            2
                    );
                }
            }
        }


        /*
         * =========================================================
         * TREE
         * =========================================================
         */

        private void drawTree(
                Graphics2D g,
                int x,
                int y
        ) {

            /*
             * Trunk
             */

            g.setColor(
                    new Color(
                            100,
                            60,
                            30
                    )
            );

            g.fillRect(
                    x - 5,
                    y,
                    10,
                    60
            );

            /*
             * Leaves
             */

            g.setColor(
                    new Color(
                            40,
                            170,
                            70
                    )
            );

            g.fillOval(
                    x - 35,
                    y - 35,
                    65,
                    55
            );

            g.fillOval(
                    x - 45,
                    y - 10,
                    60,
                    50
            );

            g.fillOval(
                    x - 5,
                    y - 15,
                    60,
                    50
            );
        }


        /*
         * =========================================================
         * CLOUD
         * =========================================================
         */

        private void drawCloud(
                Graphics2D g,
                int x,
                int y
        ) {

            g.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            130
                    )
            );

            g.fillOval(
                    x,
                    y,
                    55,
                    30
            );

            g.fillOval(
                    x + 25,
                    y - 15,
                    55,
                    45
            );

            g.fillOval(
                    x + 55,
                    y,
                    55,
                    30
            );

            g.fillRoundRect(
                    x + 20,
                    y + 10,
                    65,
                    20,
                    10,
                    10
            );
        }


        /*
         * =========================================================
         * FEATURE
         * =========================================================
         */

        private void drawFeature(
                Graphics2D g,
                int x,
                int y,
                String icon,
                String line1,
                String line2
        ) {

            /*
             * Circle
             */

            g.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            35
                    )
            );

            g.fillOval(
                    x - 30,
                    y - 38,
                    60,
                    60
            );

            /*
             * Icon
             */

            g.setColor(Color.WHITE);

            g.setFont(new Font("SansSerif", Font.BOLD, 12));

            drawCenteredString(
                    g,
                    icon,
                    x,
                    y - 14
            );

            /*
             * Text
             */

            g.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            12
                    )
            );

            drawCenteredString(
                    g,
                    line1,
                    x,
                    y + 28
            );

            drawCenteredString(
                    g,
                    line2,
                    x,
                    y + 44
            );
        }


        /*
         * =========================================================
         * CENTER TEXT
         * =========================================================
         */

        private void drawCenteredString(
                Graphics2D g,
                String text,
                int x,
                int y
        ) {

            FontMetrics fm =
                    g.getFontMetrics();

            int textWidth =
                    fm.stringWidth(text);

            g.drawString(
                    text,
                    x - textWidth / 2,
                    y
            );
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> new LoginFrame()
        );
    }
}
