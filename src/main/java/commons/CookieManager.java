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
                writer.write(cookie.getName() + "|" + cookie.getValue() + "|" + 
                             cookie.getDomain() + "|" + cookie.getPath());
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
        
        if (!file.exists()) return seleniumCookies; // Nếu file chưa tồn tại thì trả về mảng rỗng

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                if (tokens.length >= 4) {
                    org.openqa.selenium.Cookie selCookie = new org.openqa.selenium.Cookie.Builder(tokens[0], tokens[1])
                            .domain(tokens[2])
                            .path(tokens[3])
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