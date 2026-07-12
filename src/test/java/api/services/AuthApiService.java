package api.services;

import api.dtos.request.LoginRequestDTO;
import api.dtos.response.LoginResponseDTO;
import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthApiService {

    public LoginResponseDTO executeLoginApi(LoginRequestDTO requestDTO) {
        // --- 1. Gửi request GET để lấy 'form_key' động và Cookie khởi tạo ---
        Response getResponse = RestAssured.get("http://live.techpanda.org/index.php/customer/account/login/");
        String htmlBody = getResponse.asString();
        String initialCookie = getResponse.getCookie("frontend");

        // Dùng Regex tìm chuỗi form_key trong đống HTML trả về
        String formKey = "";
        try {
            org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(htmlBody);
            // Tìm thẻ input có name là form_key nằm lẩn khuất trong trang
            org.jsoup.nodes.Element formKeyElement = doc.select("input[name=form_key]").first();
            if (formKeyElement != null) {
                formKey = formKeyElement.attr("value");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi bốc form_key bằng Jsoup: " + e.getMessage());
        }

        System.out.println("--> [TRUY VẾT] Form Key lấy được bằng Jsoup là: [" + formKey + "]");

        Response postResponse = RestAssured.given()
                .redirects().follow(false)
                .header("User-Agent", "AutomationBrowser")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("frontend", initialCookie)
                .formParam("form_key", formKey)
                .formParam("login[username]", requestDTO.getUsername())
                .formParam("login[password]", requestDTO.getPassword())
                .formParam("send", "")
                .post("http://live.techpanda.org/index.php/customer/account/loginPost/");

        // 🌟 DÒNG KIỂM TRA 2: Xem Server thực chất đang điều hướng anh đi đâu (Dashboard hay trang Login lỗi)
        System.out.println("--> [TRUY VẾT] Đường dẫn Redirect thực tế: " + postResponse.getHeader("Location"));

        return new LoginResponseDTO(
                postResponse.getStatusCode(),
                postResponse.getDetailedCookies()
        );
    }
}