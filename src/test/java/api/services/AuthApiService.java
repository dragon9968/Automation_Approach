package api.services;

import api.dtos.request.LoginRequestDTO;
import api.dtos.response.LoginResponseDTO;
import commons.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AuthApiService {
    String loginUrl = ConfigManager.getAppUrl() + "customer/account/login/";
    String loginPostUrl = ConfigManager.getAppUrl() + "customer/account/loginPost/";

    public LoginResponseDTO executeLoginApi(LoginRequestDTO requestDTO) {
        // --- 1. Gửi request GET để lấy 'form_key' động và Cookie khởi tạo ---
        Response getResponse = RestAssured.get(loginUrl);
        String htmlBody = getResponse.asString();
        String initialCookie = getResponse.getCookie("frontend");

        // Dùng Regex tìm chuỗi form_key trong đống HTML trả về
        String formKey = "";
        try {
            Document doc = org.jsoup.Jsoup.parse(htmlBody);
            // Tìm thẻ input có name là form_key nằm lẩn khuất trong trang
            Element formKeyElement = doc.select("input[name=form_key]").first();
            if (formKeyElement != null) {
                formKey = formKeyElement.attr("value");
            }
        } catch (Exception e) {
            System.out.println("Error while extracting form_key with Jsoup: " + e.getMessage());
        }

        System.out.println("--> [TRACE] Form Key retrieved with Jsoup: [" + formKey + "]");

        Response postResponse = RestAssured.given()
                .redirects().follow(false)
                .header("User-Agent", "AutomationBrowser")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("frontend", initialCookie)
                .formParam("form_key", formKey)
                .formParam("login[username]", requestDTO.getUsername())
                .formParam("login[password]", requestDTO.getPassword())
                .formParam("send", "")
                .post(loginPostUrl);

        // 🌟 DÒNG KIỂM TRA 2: Xem Server thực chất đang điều hướng anh đi đâu (Dashboard hay trang Login lỗi)
        System.out.println("--> [TRACE] Actual redirect URL: " + postResponse.getHeader("Location"));

        return new LoginResponseDTO(
                postResponse.getStatusCode(),
                postResponse.getDetailedCookies()
        );
    }
}