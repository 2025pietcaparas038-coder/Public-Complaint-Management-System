
package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // 1. Get all complaints
    public List<Object[]> getAllComplaints() {
        List<Object[]> complaints = new ArrayList<>();

        String sql =
                "SELECT c.complaint_id, c.user_id, c.title, " +
                        "cat.category_name, c.description, c.priority, " +
                        "c.status, c.assigned_officer_id, c.created_at " +
                        "FROM complaints c " +
                        "LEFT JOIN categories cat " +
                        "ON c.category_id = cat.category_id " +
                        "ORDER BY c.created_at DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                complaints.add(new Object[]{
                        rs.getInt("complaint_id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getObject("assigned_officer_id"),
                        rs.getTimestamp("created_at")
                });
            }

        } catch (SQLException e) {
            System.err.println("Error loading complaints: " + e.getMessage());
        }

        return complaints;
    }

    // 2. Get complaint statistics
    // Indexes: 0-total, 1-pending, 2-in progress,
    //          3-resolved, 4-escalated
    public int[] getComplaintStatistics() {
        int[] counts = new int[5];

        String sql =
                "SELECT COUNT(*) AS total, " +
                        "COALESCE(SUM(status IN " +
                        "('SUBMITTED','ASSIGNED','UNDER_REVIEW')),0) AS pending, " +
                        "COALESCE(SUM(status = 'IN_PROGRESS'),0) AS in_progress, " +
                        "COALESCE(SUM(status = 'RESOLVED'),0) AS resolved, " +
                        "COALESCE(SUM(status = 'ESCALATED'),0) AS escalated " +
                        "FROM complaints";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                counts[0] = rs.getInt("total");
                counts[1] = rs.getInt("pending");
                counts[2] = rs.getInt("in_progress");
                counts[3] = rs.getInt("resolved");
                counts[4] = rs.getInt("escalated");
            }

        } catch (SQLException e) {
            System.err.println("Error loading complaint statistics: "
                    + e.getMessage());
        }

        return counts;
    }

    // 3. Get escalated complaints
    public List<Object[]> getEscalatedComplaints() {
        List<Object[]> complaints = new ArrayList<>();

        String sql =
                "SELECT complaint_id, user_id, title, priority, " +
                        "status, assigned_officer_id, created_at " +
                        "FROM complaints " +
                        "WHERE status = 'ESCALATED' " +
                        "ORDER BY created_at DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                complaints.add(new Object[]{
                        rs.getInt("complaint_id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getObject("assigned_officer_id"),
                        rs.getTimestamp("created_at")
                });
            }

        } catch (SQLException e) {
            System.err.println("Error loading escalated complaints: "
                    + e.getMessage());
        }

        return complaints;
    }

    // 4. Get all users, excluding password
    public List<Object[]> getAllUsers() {
        List<Object[]> users = new ArrayList<>();

        String sql =
                "SELECT user_id, full_name, email, phone, role, created_at " +
                        "FROM users ORDER BY created_at DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role"),
                        rs.getTimestamp("created_at")
                });
            }

        } catch (SQLException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

    // 5. Get counts for each user role
    // Indexes: 0-citizens, 1-officers, 2-admins
    public int[] getUserStatistics() {
        int[] counts = new int[3];

        String sql =
                "SELECT " +
                        "COALESCE(SUM(role = 'CITIZEN'), 0) AS citizens, " +
                        "COALESCE(SUM(role = 'OFFICER'), 0) AS officers, " +
                        "COALESCE(SUM(role = 'ADMIN'), 0) AS admins " +
                        "FROM users";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                counts[0] = rs.getInt("citizens");
                counts[1] = rs.getInt("officers");
                counts[2] = rs.getInt("admins");
            }

        } catch (SQLException e) {
            System.err.println("Error loading user statistics: "
                    + e.getMessage());
        }

        return counts;
    }
}
