package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/public_complaint_management";

    private static final String USER = "root";

    // Put your MySQL root password here
    private static final String PASSWORD = "Paras@2007";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    public static void main(String[] args) {

        try (Connection connection = getConnection()) {

            if (connection != null) {

                System.out.println(
                        "======================================"
                );

                System.out.println(
                        "DATABASE CONNECTION SUCCESSFUL!"
                );

                System.out.println(
                        "Connected to: public_complaint_management"
                );

                System.out.println(
                        "======================================"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "DATABASE CONNECTION FAILED!"
            );

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }
}