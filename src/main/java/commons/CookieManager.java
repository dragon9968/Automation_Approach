package commons;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CookieManager {
    private static final String COOKIE_FILE_PATH = "target/techpanda_cookies.txt";

    // 1. Hàm lưu Cookies từ API Rest Assured ra file văn bản
    public static void saveCookiesToFile(Cookies apiCookies) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COOKIE_FILE_PATH))) {
            for (Cookie cookie : apiCookies) {
                
                // 🌟 KHÓA CHÍ MẠNG: Nếu domain hoặc path bị null, ép về giá trị chuẩn của website luôn
                String domain = (cookie.getDomain() != null) ? cookie.getDomain() : "live.techpanda.org";
                String path = (cookie.getPath() != null) ? cookie.getPath() : "/";
                
                writer.write(cookie.getName() + "|" + cookie.getValue() + "|" + domain + "|" + path);
                writer.newLine();
            }
            System.out.println("--> [INFO] Đã lưu session login vào file: " + COOKIE_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2. Hàm đọc Cookies từ file lên để chuẩn bị bơm vào Selenium
    public static List<org.openqa.selenium.Cookie> loadCookiesFromFile() {
        List<org.openqa.selenium.Cookie> seleniumCookies = new ArrayList<>();
        File file = new File(COOKIE_FILE_PATH);
        
        if (!file.exists()) return seleniumCookies;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                if (tokens.length >= 4) {
                    String domain = tokens[2].equals("null") ? "live.techpanda.org" : tokens[2];
                    String path = tokens[3].equals("null") ? "/" : tokens[3];

                    org.openqa.selenium.Cookie selCookie = new org.openqa.selenium.Cookie.Builder(tokens[0], tokens[1])
                            .domain(domain)
                            .path(path)
                            .isSecure(false) // 🌟 BỔ SUNG DÒNG NÀY: Ép false để chạy trên HTTP thường của TechPanda
                            .build();
                    seleniumCookies.add(selCookie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return seleniumCookies;
    }
}