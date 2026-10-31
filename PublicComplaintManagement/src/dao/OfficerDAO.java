
package dao;

import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfficerDAO {

    // Get complaints assigned to this officer
    public List<Object[]> getAssignedComplaints(int officerId) {
        List<Object[]> complaints = new ArrayList<>();

        String sql =
                "SELECT c.complaint_id, c.user_id, " +
                        "cat.category_name, c.description, c.status " +
                        "FROM complaints c " +
                        "LEFT JOIN categories cat " +
                        "ON c.category_id = cat.category_id " +
                        "WHERE c.assigned_officer_id = ? " +
                        "ORDER BY c.created_at DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, officerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    complaints.add(new Object[]{
                            rs.getInt("complaint_id"),
                            rs.getInt("user_id"),
                            rs.getString("category_name"),
                            rs.getString("description"),
                            rs.getString("status")
                    });
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error loading officer complaints: " + e.getMessage()
            );
        }

        return complaints;
    }

    // Update status and record status history in one transaction
    public boolean updateComplaintStatus(
            int complaintId, int officerId, String newStatus) {

        String checkSql =
                "SELECT status FROM complaints " +
                        "WHERE complaint_id = ? AND assigned_officer_id = ?";

        String updateSql =
                "UPDATE complaints SET status = ? " +
                        "WHERE complaint_id = ? AND assigned_officer_id = ?";

        String historySql =
                "INSERT INTO complaint_status_history " +
                        "(complaint_id, old_status, new_status, changed_by, remarks) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                String oldStatus;

                try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                    ps.setInt(1, complaintId);
                    ps.setInt(2, officerId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }
                        oldStatus = rs.getString("status");
                    }
                }

                if (newStatus.equalsIgnoreCase(oldStatus)) {
                    con.rollback();
                    return false;
                }

                try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                    ps.setString(1, newStatus);
                    ps.setInt(2, complaintId);
                    ps.setInt(3, officerId);

                    if (ps.executeUpdate() == 0) {
                        con.rollback();
                        return false;
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(historySql)) {
                    ps.setInt(1, complaintId);
                    ps.setString(2, oldStatus);
                    ps.setString(3, newStatus);
                    ps.setInt(4, officerId);
                    ps.setString(5, "Status updated by assigned officer.");

                    ps.executeUpdate();
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error updating complaint status: " + e.getMessage()
            );
            return false;
        }
    }
}
