package api.dtos.response;
import io.restassured.http.Cookies;
import java.util.Map;

public class LoginResponseDTO {
    private int statusCode;
    private Cookies cookies; // Nơi lưu trữ tất cả cookies đăng nhập thành công

    public LoginResponseDTO(int statusCode, Cookies cookies) {
        this.statusCode = statusCode;
        this.cookies = cookies;
    }

    public int getStatusCode() { return statusCode; }
    public Cookies getCookies() { return cookies; }
}