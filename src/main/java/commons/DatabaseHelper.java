package commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHelper {
    // Hàm lấy connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    GlobalConstants.DB_POSTGRES_URL,
                    GlobalConstants.DB_POSTGRES_USER,
                    GlobalConstants.DB_POSTGRES_PASS
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Không thể kết nối tới PostgreSQL!");
        }
    }

    // 🌟 HÀM 1: Chạy các lệnh INSERT, UPDATE, DELETE (Hỗ trợ truyền tham số động)
    public static int executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Tự động gán các tham số vào dấu hỏi chấm (?)
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate(); // Trả về số dòng bị tác động
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 🌟 HÀM 2: Chạy lệnh SELECT để lấy ra 1 giá trị duy nhất (Hỗ trợ truyền tham số)
    public static String getSingleValue(String sql, String columnName, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(columnName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy dữ liệu
    }
}