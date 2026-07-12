package hooks;

import commons.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

// Cho kế thừa BaseTest để xài lại hàm khởi tạo Browser của anh
public class CucumberHooks extends BaseTest {
    
    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    @Before(order = 1)
    public void setUp() {
        if (threadDriver.get() == null) {
            WebDriver driver = getBrowserName("chrome"); 
            threadDriver.set(driver);
        }
    }

    @Before(value = "@use_session", order = 2)
    public void handleAutoLoginSession() {
        org.openqa.selenium.WebDriver driver = threadDriver.get();

        // 1. Xóa sạch bách mọi cookie khách vãng lai của Chrome trước
        driver.manage().deleteAllCookies();

        // 2. Tiến hành gọi API Login lấy session xịn
        System.out.println("--> [TRUY VẾT] Đang tiến hành gọi API Login...");
        api.services.AuthApiService apiService = new api.services.AuthApiService();
        api.dtos.request.LoginRequestDTO requestData =
                new api.dtos.request.LoginRequestDTO("long_tester_pro@gmail.com", "123456");

        api.dtos.response.LoginResponseDTO responseData = apiService.executeLoginApi(requestData);

        System.out.println("--> [TRUY VẾT] Mã trạng thái API trả về: " + responseData.getStatusCode());

        // 3. Bơm trực tiếp cookie từ API vào Selenium (Lọc trùng và ép Domain dấu chấm)
        for (io.restassured.http.Cookie apiCookie : responseData.getCookies()) {

            // 🌟 KHÓA CHÍ MẠNG 1: Chỉ lấy đúng con cookie tên là 'frontend' để đăng nhập
            if (apiCookie.getName().equals("frontend")) {

                // 🌟 KHÓA CHÍ MẠNG 2: Ép domain phải có dấu chấm phía trước (.live.techpanda.org) chuẩn Magento
                org.openqa.selenium.Cookie seleniumCookie = new org.openqa.selenium.Cookie.Builder(apiCookie.getName(), apiCookie.getValue())
                        .domain(".live.techpanda.org") // Thêm dấu chấm ở đây anh nhé
                        .path("/")
                        .isSecure(false)
                        .build();

                driver.manage().addCookie(seleniumCookie);
                System.out.println("--> [TRUY VẾT] Đã tiêm thành công Cookie Đăng Nhập VIP vào máu Chrome!");
            }
        }

        // 4. Ép nhảy thẳng vào trang quản lý tài khoản để hưởng thành quả
        driver.get("http://live.techpanda.org/index.php/customer/account/");
        System.out.println("=== [SUCCESS] TRÌNH DUYỆT ĐÃ ĐƯỢC AUTO LOGIN SẴN SÀNG KHỞI CHẠY TEST CASE ===");
    }


   // @After
    public void tearDown() {
        if (threadDriver.get() != null) {
            threadDriver.get().quit();
            threadDriver.remove();
        }
    }

    public static WebDriver getDriver() {
        return threadDriver.get();
    }
}