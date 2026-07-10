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
        Pattern pattern = Pattern.compile("name=\"form_key\" value=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(htmlBody);
        if (matcher.find()) {
            formKey = matcher.group(1);
        }

        // --- 2. Gửi request POST dạng Form để Đăng nhập hệ thống ---
        Response postResponse = RestAssured.given()
            // Tắt tự động chuyển hướng để giữ lại Cookies chuẩn mã 302
            .redirects().follow(false)
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .cookie("frontend", initialCookie) 
            .formParam("form_key", formKey)
            .formParam("login[username]", requestDTO.getUsername())
            .formParam("login[password]", requestDTO.getPassword())
            .formParam("send", "")
            .post("http://live.techpanda.org/index.php/customer/account/loginPost/");

        // --- 3. Đóng gói kết quả trả về vào hộp Response DTO ---
        return new LoginResponseDTO(
                postResponse.getStatusCode(),
                postResponse.getDetailedCookies() // 🌟 SỬA THÀNH HÀM NÀY: Để lấy cookie chi tiết (gồm cả Domain, Path)
            );
    }
}