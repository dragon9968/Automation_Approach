package commons;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties prop = new Properties();

    static {
        try {
            // 🌟 1. Lấy tham số -Denv từ Maven command line (Mặc định là "dev" nếu không truyền)
            String env = System.getProperty("env", "dev").toLowerCase();
            String fileName = "configs/" + env + ".properties";

            InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName);
            if (input != null) {
                prop.load(input);
                System.out.println("🚀 [CONFIG] Đã load cấu hình môi trường: " + env.toUpperCase());
            } else {
                throw new RuntimeException("❌ Không tìm thấy file cấu hình: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAppUrl() {
        return prop.getProperty("app.url");
    }

    public static long getLongTimeout() {
        return Long.parseLong(prop.getProperty("long.timeout", "20"));
    }

    public static long getShortTimeout() {
        return Long.parseLong(prop.getProperty("short.timeout", "3"));
    }

    public static String getDbUrl() {
        return prop.getProperty("db.url");
    }

    public static String getDbUser() {
        return prop.getProperty("db.user");
    }

    // 🌟 2. Ưu tiên lấy Mật khẩu từ Biến môi trường (GitHub Secrets / System Env), nếu không có mới lấy từ file properties local
    public static String getDbPassword() {
        String envPass = System.getenv("DB_PASS");
        return (envPass != null && !envPass.isEmpty()) ? envPass : prop.getProperty("db.pass");
    }
}