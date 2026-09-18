package db.queries;

import commons.DatabaseHelper;

public class UserQueries {

    public static int deleteUserByEmail(String email) {
        String sql = "DELETE FROM users_test WHERE email = ?";
        return DatabaseHelper.executeUpdate(sql, email);
    }

    public static int insertUser(String email, String pass, String status) {
        String sql = "INSERT INTO users_test (email, pass, status) VALUES (?, ?, ?)";
        return DatabaseHelper.executeUpdate(sql, email, pass, status);
    }

    public static int updateUserStatus(String email, String newStatus) {
        String sql = "UPDATE users_test SET status = ? WHERE email = ?";
        return DatabaseHelper.executeUpdate(sql, newStatus, email);
    }

    public static String getUserPassword(String email) {
        String sql = "SELECT pass FROM users_test WHERE email = ?";
        return DatabaseHelper.getSingleValue(sql, "pass", email);
    }

    public static String getUserStatus(String email) {
        String sql = "SELECT status FROM users_test WHERE email = ?";
        return DatabaseHelper.getSingleValue(sql, "status", email);
    }
}