package dao;

import model.Complaint;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    // =========================================================
    // 1. SUBMIT COMPLAINT
    // =========================================================

    public int submitComplaint(Complaint complaint) {

        String complaintSql =
                "INSERT INTO complaints " +
                        "(user_id, category_id, department_id, title, description, " +
                        "location, priority, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String routingSql =
                "INSERT INTO complaint_routing " +
                        "(complaint_id, category_id, department_id, officer_id, " +
                        "routing_method, routing_reason) " +
                        "VALUES (?, ?, ?, ?, 'AUTOMATIC', ?)";

        String assignSql =
                "UPDATE complaints " +
                        "SET assigned_officer_id = ?, status = 'ASSIGNED' " +
                        "WHERE complaint_id = ?";

        String historySql =
                "INSERT INTO complaint_status_history " +
                        "(complaint_id, old_status, new_status, changed_by, remarks) " +
                        "VALUES (?, ?, ?, ?, ?)";

        String auditSql =
                "INSERT INTO audit_logs " +
                        "(user_id, action, entity_type, entity_id, description, ip_address) " +
                        "VALUES (?, 'SUBMIT_COMPLAINT', 'COMPLAINT', ?, ?, 'JAVA-APP')";

        String notificationSql =
                "INSERT INTO notifications " +
                        "(user_id, complaint_id, notification_type, message, is_read) " +
                        "VALUES (?, ?, 'COMPLAINT_SUBMITTED', ?, FALSE)";

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            int complaintId;

            // -------------------------------------------------
            // Insert complaint
            // -------------------------------------------------

            try (PreparedStatement stmt =
                         conn.prepareStatement(
                                 complaintSql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, complaint.getUserId());
                stmt.setInt(2, complaint.getCategoryId());
                stmt.setInt(3, complaint.getDepartmentId());
                stmt.setString(4, complaint.getTitle());
                stmt.setString(5, complaint.getDescription());
                stmt.setString(6, complaint.getLocation());
                stmt.setString(7, complaint.getPriority());
                stmt.setString(8, complaint.getStatus());

                int rows = stmt.executeUpdate();

                if (rows == 0) {
                    conn.rollback();
                    return -1;
                }

                try (ResultSet keys = stmt.getGeneratedKeys()) {

                    if (!keys.next()) {
                        conn.rollback();
                        return -1;
                    }

                    complaintId = keys.getInt(1);
                }
            }

            // -------------------------------------------------
            // Find officer for department
            // -------------------------------------------------

            int officerId =
                    getOfficerForDepartment(
                            conn,
                            complaint.getDepartmentId()
                    );

            String finalStatus = complaint.getStatus();

            // -------------------------------------------------
            // Automatically assign officer
            // -------------------------------------------------

            if (officerId != -1) {

                finalStatus = "ASSIGNED";

                try (PreparedStatement stmt =
                             conn.prepareStatement(assignSql)) {

                    stmt.setInt(1, officerId);
                    stmt.setInt(2, complaintId);

                    stmt.executeUpdate();
                }
            }

            // -------------------------------------------------
            // Status history
            // -------------------------------------------------

            try (PreparedStatement stmt =
                         conn.prepareStatement(historySql)) {

                stmt.setInt(1, complaintId);
                stmt.setString(2, complaint.getStatus());
                stmt.setString(3, finalStatus);
                stmt.setInt(4, complaint.getUserId());

                if (officerId != -1) {

                    stmt.setString(
                            5,
                            "Complaint submitted and automatically assigned to department officer."
                    );

                } else {

                    stmt.setString(
                            5,
                            "Complaint submitted successfully."
                    );
                }

                stmt.executeUpdate();
            }

            // -------------------------------------------------
            // Routing record
            // -------------------------------------------------

            try (PreparedStatement stmt =
                         conn.prepareStatement(routingSql)) {

                stmt.setInt(1, complaintId);
                stmt.setInt(2, complaint.getCategoryId());
                stmt.setInt(3, complaint.getDepartmentId());

                if (officerId == -1) {

                    stmt.setNull(
                            4,
                            Types.INTEGER
                    );

                } else {

                    stmt.setInt(
                            4,
                            officerId
                    );
                }

                stmt.setString(
                        5,
                        "Automatically routed based on complaint category and department."
                );

                stmt.executeUpdate();
            }

            // -------------------------------------------------
            // Audit log
            // -------------------------------------------------

            try (PreparedStatement stmt =
                         conn.prepareStatement(auditSql)) {

                stmt.setInt(
                        1,
                        complaint.getUserId()
                );

                stmt.setInt(
                        2,
                        complaintId
                );

                stmt.setString(
                        3,
                        "Citizen submitted complaint titled: "
                                + complaint.getTitle()
                );

                stmt.executeUpdate();
            }

            // -------------------------------------------------
            // Citizen notification
            // -------------------------------------------------

            String message =
                    "Complaint #" + complaintId +
                            " has been successfully submitted and routed.";

            try (PreparedStatement stmt =
                         conn.prepareStatement(notificationSql)) {

                stmt.setInt(
                        1,
                        complaint.getUserId()
                );

                stmt.setInt(
                        2,
                        complaintId
                );

                stmt.setString(
                        3,
                        message
                );

                stmt.executeUpdate();
            }

            // -------------------------------------------------
            // Commit transaction
            // -------------------------------------------------

            conn.commit();

            return complaintId;

        } catch (SQLException e) {

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {

                    System.out.println(
                            "Rollback Error: "
                                    + rollbackError.getMessage()
                    );
                }
            }

            System.out.println(
                    "Complaint Submission Error: "
                            + e.getMessage()
            );

            return -1;

        } finally {

            if (conn != null) {

                try {

                    conn.setAutoCommit(true);
                    conn.close();

                } catch (SQLException e) {

                    System.out.println(
                            "Connection Close Error: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }


    // =========================================================
    // 2. FIND OFFICER FOR DEPARTMENT
    // =========================================================

    private int getOfficerForDepartment(
            Connection conn,
            int departmentId) throws SQLException {

        String sql =
                "SELECT officer_id " +
                        "FROM officer_departments " +
                        "WHERE department_id = ? " +
                        "ORDER BY officer_id " +
                        "LIMIT 1";

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    departmentId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(
                            "officer_id"
                    );
                }
            }
        }

        return -1;
    }


    // =========================================================
    // 3. GET CITIZEN DASHBOARD STATISTICS
    // =========================================================

    public int[] getUserComplaintStatistics(int userId) {

        int[] statistics = new int[4];

        String sql =
                "SELECT " +
                        "COUNT(*) AS total, " +
                        "SUM(status IN " +
                        "('SUBMITTED', 'UNDER_REVIEW', 'ASSIGNED', 'IN_PROGRESS')) AS active, " +
                        "SUM(status = 'RESOLVED') AS resolved, " +
                        "SUM(status = 'ESCALATED') AS escalated " +
                        "FROM complaints " +
                        "WHERE user_id = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    userId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    statistics[0] =
                            rs.getInt("total");

                    statistics[1] =
                            rs.getInt("active");

                    statistics[2] =
                            rs.getInt("resolved");

                    statistics[3] =
                            rs.getInt("escalated");
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving complaint statistics: "
                            + e.getMessage()
            );
        }

        return statistics;
    }


    // =========================================================
    // 4. GET RECENT COMPLAINTS
    // =========================================================

    public List<Object[]> getRecentUserComplaints(
            int userId,
            int limit) {

        List<Object[]> complaints =
                new ArrayList<>();

        String sql =
                "SELECT complaint_id, title, priority, " +
                        "status, created_at " +
                        "FROM complaints " +
                        "WHERE user_id = ? " +
                        "ORDER BY created_at DESC " +
                        "LIMIT ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    userId
            );

            stmt.setInt(
                    2,
                    limit
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    complaints.add(
                            new Object[]{
                                    rs.getInt("complaint_id"),
                                    rs.getString("title"),
                                    rs.getString("priority"),
                                    rs.getString("status"),
                                    rs.getTimestamp("created_at")
                            }
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving recent complaints: "
                            + e.getMessage()
            );
        }

        return complaints;
    }


    // =========================================================
    // 5. GET ALL CITIZEN COMPLAINTS
    // =========================================================

    public List<Object[]> getUserComplaints(
            int userId) {

        List<Object[]> complaints =
                new ArrayList<>();

        String sql =
                "SELECT c.complaint_id, " +
                        "c.title, " +
                        "cat.category_name, " +
                        "d.department_name, " +
                        "c.location, " +
                        "c.priority, " +
                        "c.status, " +
                        "c.created_at " +
                        "FROM complaints c " +
                        "LEFT JOIN categories cat " +
                        "ON c.category_id = cat.category_id " +
                        "LEFT JOIN departments d " +
                        "ON c.department_id = d.department_id " +
                        "WHERE c.user_id = ? " +
                        "ORDER BY c.created_at DESC";

        try (Connection conn =
                     DatabaseConnection.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    userId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    complaints.add(
                            new Object[]{
                                    rs.getInt("complaint_id"),
                                    rs.getString("title"),
                                    rs.getString("category_name"),
                                    rs.getString("department_name"),
                                    rs.getString("location"),
                                    rs.getString("priority"),
                                    rs.getString("status"),
                                    rs.getTimestamp("created_at")
                            }
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving citizen complaints: "
                            + e.getMessage()
            );
        }

        return complaints;
    }


    // =========================================================
    // 6. GET CATEGORIES
    // =========================================================

    // =========================================================
// GET CATEGORIES
// =========================================================

    public List<Object[]> getCategories() {

        List<Object[]> categories = new ArrayList<>();

        String sql =
                "SELECT c.category_id, " +
                        "c.category_name, " +
                        "c.department_id, " +
                        "d.department_name, " +
                        "c.default_priority " +
                        "FROM categories c " +
                        "LEFT JOIN departments d " +
                        "ON c.department_id = d.department_id " +
                        "ORDER BY c.category_name";

        try (Connection conn =
                     DatabaseConnection.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(sql);

             ResultSet rs =
                     stmt.executeQuery()) {

            while (rs.next()) {

                categories.add(
                        new Object[]{
                                rs.getInt("category_id"),
                                rs.getString("category_name"),
                                rs.getInt("department_id"),
                                rs.getString("department_name"),
                                rs.getString("default_priority")
                        }
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving categories: "
                            + e.getMessage()
            );
        }

        return categories;
    }


    // =========================================================
    // 7. GET CITIZEN NOTIFICATIONS
    // =========================================================

    public List<Object[]> getUserNotifications(
            int userId) {

        List<Object[]> notifications =
                new ArrayList<>();

        String sql =
                "SELECT notification_id, " +
                        "complaint_id, " +
                        "notification_type, " +
                        "message, " +
                        "is_read, " +
                        "created_at " +
                        "FROM notifications " +
                        "WHERE user_id = ? " +
                        "ORDER BY created_at DESC";

        try (Connection conn =
                     DatabaseConnection.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    userId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    notifications.add(
                            new Object[]{
                                    rs.getInt("notification_id"),
                                    rs.getInt("complaint_id"),
                                    rs.getString("notification_type"),
                                    rs.getString("message"),
                                    rs.getBoolean("is_read"),
                                    rs.getTimestamp("created_at")
                            }
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving notifications: "
                            + e.getMessage()
            );
        }

        return notifications;
    }


    // =========================================================
    // 8. MARK NOTIFICATION AS READ
    // =========================================================

    public boolean markNotificationRead(
            int notificationId) {

        String sql =
                "UPDATE notifications " +
                        "SET is_read = TRUE " +
                        "WHERE notification_id = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    notificationId
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error marking notification as read: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================================================
    // 9. GET COMPLAINT DETAILS
    // =========================================================

    public Object[] getComplaintDetails(
            int complaintId,
            int userId) {

        String sql =
                "SELECT c.complaint_id, " +
                        "c.title, " +
                        "c.description, " +
                        "cat.category_name, " +
                        "d.department_name, " +
                        "c.location, " +
                        "c.priority, " +
                        "c.status, " +
                        "c.expected_resolution_date, " +
                        "c.created_at, " +
                        "c.updated_at " +
                        "FROM complaints c " +
                        "LEFT JOIN categories cat " +
                        "ON c.category_id = cat.category_id " +
                        "LEFT JOIN departments d " +
                        "ON c.department_id = d.department_id " +
                        "WHERE c.complaint_id = ? " +
                        "AND c.user_id = ?";

        try (Connection conn =
                     DatabaseConnection.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    complaintId
            );

            stmt.setInt(
                    2,
                    userId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return new Object[]{
                            rs.getInt("complaint_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("category_name"),
                            rs.getString("department_name"),
                            rs.getString("location"),
                            rs.getString("priority"),
                            rs.getString("status"),
                            rs.getDate("expected_resolution_date"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    };
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving complaint details: "
                            + e.getMessage()
            );
        }

        return null;
    }


    // =========================================================
    // 10. EXISTING CONSOLE METHOD
    // =========================================================

    public void viewUserComplaints(
            int userId) {

        String sql =
                "SELECT complaint_id, title, location, " +
                        "priority, status, created_at " +
                        "FROM complaints " +
                        "WHERE user_id = ? " +
                        "ORDER BY created_at DESC";

        try (Connection conn =
                     DatabaseConnection.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    userId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                System.out.println(
                        "\n========== MY COMPLAINTS =========="
                );

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    System.out.println(
                            "-----------------------------------"
                    );

                    System.out.println(
                            "Complaint ID : "
                                    + rs.getInt("complaint_id")
                    );

                    System.out.println(
                            "Title        : "
                                    + rs.getString("title")
                    );

                    System.out.println(
                            "Location     : "
                                    + rs.getString("location")
                    );

                    System.out.println(
                            "Priority     : "
                                    + rs.getString("priority")
                    );

                    System.out.println(
                            "Status       : "
                                    + rs.getString("status")
                    );

                    System.out.println(
                            "Created At   : "
                                    + rs.getTimestamp("created_at")
                    );
                }

                if (!found) {

                    System.out.println(
                            "No complaints found."
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving complaints: "
                            + e.getMessage()
            );
        }
    }
}