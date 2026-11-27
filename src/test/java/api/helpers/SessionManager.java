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

        System.out.println("--> [TRACE] Initiating API Login call...");
        AuthApiService apiService = new AuthApiService();
        LoginRequestDTO requestData = new LoginRequestDTO(username, password);
        LoginResponseDTO responseData = apiService.executeLoginApi(requestData);
        System.out.println("--> [TRACE] API response status code: " + responseData.getStatusCode());
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
                System.out.println("--> [TRACE] Successfully injected the VIP Login Cookie into browser!");
            }
        }

        // 🌟 Tận dụng GlobalConstants cho đường dẫn trang Account
        driver.get(GlobalConstants.TECHPANDA_ACCOUNT_URL);
        System.out.println("=== [SUCCESS] BROWSER HAS BEEN AUTO-LOGGED IN AND READY TO RUN TEST CASES ===");
    }
}