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
        java.util.List<org.openqa.selenium.Cookie> savedCookies = commons.CookieManager.loadCookiesFromFile();

        // Nếu file trống trơn (lần đầu chạy), tự động gọi API Login để lấy cookies mới và lưu lại
        if (savedCookies.isEmpty()) {
            System.out.println("--> [INFO] Không tìm thấy file session cũ, tiến hành gọi API khởi tạo...");
            api.services.AuthApiService apiService = new api.services.AuthApiService();
            api.dtos.request.LoginRequestDTO requestData = 
                new api.dtos.request.LoginRequestDTO("long_tester_pro@gmail.com", "123456");
                
            api.dtos.response.LoginResponseDTO responseData = apiService.executeLoginApi(requestData);
            
            // Lưu cookies thu được từ API vào file dữ liệu
            commons.CookieManager.saveCookiesToFile(responseData.getCookies());
            
            // Đọc lại mảng cookies vừa lưu
            savedCookies = commons.CookieManager.loadCookiesFromFile();
        }

        // BƠM COOKIES vào trình duyệt đang mở
        for (org.openqa.selenium.Cookie cookie : savedCookies) {
            driver.manage().addCookie(cookie);
        }

        // Refresh một phát để giao diện ăn session đăng nhập luôn vĩnh viễn!
        driver.navigate().refresh();
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