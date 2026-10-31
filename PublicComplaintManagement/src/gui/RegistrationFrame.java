package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegistrationFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;

    private static final Color BLUE = new Color(20, 130, 220);
    private static final Color DARK_BLUE = new Color(8, 52, 105);
    private static final Color GREEN = new Color(35, 155, 75);

    public RegistrationFrame() {

        setTitle("Create New Account");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(242, 248, 253));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        35, 50, 35, 50
                )
        );

        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel title = new JLabel(
                "Create New Account"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );

        title.setForeground(DARK_BLUE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(title);

        mainPanel.add(
                Box.createVerticalStrut(8)
        );

        JLabel subtitle = new JLabel(
                "Register as a citizen"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(subtitle);

        mainPanel.add(
                Box.createVerticalStrut(30)
        );

        // =========================
        // NAME
        // =========================

        mainPanel.add(
                createLabel("Full Name")
        );

        nameField = createField(
                "Enter your full name"
        );

        mainPanel.add(nameField);

        mainPanel.add(
                Box.createVerticalStrut(15)
        );

        // =========================
        // EMAIL
        // =========================

        mainPanel.add(
                createLabel("Email Address")
        );

        emailField = createField(
                "Enter your email"
        );

        mainPanel.add(emailField);

        mainPanel.add(
                Box.createVerticalStrut(15)
        );

        // =========================
        // PHONE
        // =========================

        mainPanel.add(
                createLabel("Phone Number")
        );

        phoneField = createField(
                "Enter your phone number"
        );

        mainPanel.add(phoneField);

        mainPanel.add(
                Box.createVerticalStrut(15)
        );

        // =========================
        // PASSWORD
        // =========================

        mainPanel.add(
                createLabel("Password")
        );

        passwordField =
                new JPasswordField();

        passwordField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        passwordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        50
                )
        );

        passwordField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        200,
                                        215,
                                        230
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 12, 5, 12
                        )
                )
        );

        mainPanel.add(passwordField);

        mainPanel.add(
                Box.createVerticalStrut(25)
        );

        // =========================
        // REGISTER BUTTON
        // =========================

        JButton registerButton =
                new JButton(
                        "CREATE ACCOUNT"
                );

        registerButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(GREEN);

        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);

        registerButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        55
                )
        );

        registerButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        registerButton.addActionListener(
                e -> registerUser()
        );

        mainPanel.add(registerButton);

        mainPanel.add(
                Box.createVerticalStrut(15)
        );

        // =========================
        // BACK BUTTON
        // =========================

        JButton backButton =
                new JButton(
                        "← Back to Login"
                );

        backButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        backButton.setForeground(BLUE);
        backButton.setBackground(
                new Color(
                        242,
                        248,
                        253
                )
        );

        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);

        backButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.addActionListener(e -> {

            new LoginFrame();

            dispose();
        });

        mainPanel.add(backButton);

        add(mainPanel);
    }

    // =========================================================
    // LABEL
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

        label.setForeground(
                new Color(
                        25,
                        50,
                        80
                )
        );

        return label;
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createField(
            String placeholder
    ) {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        field.setToolTipText(
                placeholder
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        50
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        200,
                                        215,
                                        230
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 12, 5, 12
                        )
                )
        );

        return field;
    }

    // =========================================================
    // REGISTER USER
    // =========================================================

    private void registerUser() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (
                name.isEmpty() ||
                        email.isEmpty() ||
                        phone.isEmpty() ||
                        password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Registration",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            String url =
                    "jdbc:mysql://localhost:3306/public_complaint_management";

            String username = "root";

            String mysqlPassword =
                    "YOUR_MYSQL_PASSWORD";

            Connection con =
                    DriverManager.getConnection(
                            url,
                            username,
                            mysqlPassword
                    );

            String sql =
                    "INSERT INTO users "
                            + "(full_name, email, phone, password, role) "
                            + "VALUES (?, ?, ?, ?, 'CITIZEN')";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            ps.close();
            con.close();

            new LoginFrame();

            dispose();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed:\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new RegistrationFrame()
                    .setVisible(true);
        });
    }
}