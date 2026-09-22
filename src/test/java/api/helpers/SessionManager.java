package api.helpers;

import api.dtos.request.LoginRequestDTO;
import api.dtos.response.LoginResponseDTO;
import api.services.AuthApiService;
import commons.ConfigManager;
import commons.GlobalConstants; // Import GlobalConstants
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.net.URI;

public class SessionManager {
    public static void injectLoginSession(WebDriver driver, String username, String password) {
        driver.manage().deleteAllCookies();

        System.out.println("--> [TRUY VẾT] Đang tiến hành gọi API Login...");
        AuthApiService apiService = new AuthApiService();
        LoginRequestDTO requestData = new LoginRequestDTO(username, password);
        LoginResponseDTO responseData = apiService.executeLoginApi(requestData);
        System.out.println("--> [TRUY VẾT] Mã trạng thái API trả về: " + responseData.getStatusCode());
        //dùng properties file
        String domain = URI.create(ConfigManager.getAppUrl()).getHost();

        for (io.restassured.http.Cookie apiCookie : responseData.getCookies()) {
            if (apiCookie.getName().equals("frontend")) {
                Cookie seleniumCookie = new Cookie.Builder(apiCookie.getName(), apiCookie.getValue())
                      //  .domain(GlobalConstants.TECHPANDA_DOMAIN) // 🌟 Tận dụng GlobalConstants
                        .domain("." + domain)
                        .path("/")
                        .isSecure(false)
                        .build();

                driver.manage().addCookie(seleniumCookie);
                System.out.println("--> [TRUY VẾT] Đã tiêm thành công Cookie Đăng Nhập VIP vào máu Chrome!");
            }
        }

        // 🌟 Tận dụng GlobalConstants cho đường dẫn trang Account
        driver.get(GlobalConstants.TECHPANDA_ACCOUNT_URL);
        System.out.println("=== [SUCCESS] TRÌNH DUYỆT ĐÃ ĐƯỢC AUTO LOGIN SẴN SÀNG KHỞI CHẠY TEST CASE ===");
    }
}