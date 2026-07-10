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
        
        // Trình duyệt hiện tại đang đứng ở trang chủ nhờ BaseTest chạy trước đó...

        // =========================================================================
        // 🌟 BƯỚC THẦN THÁNH BỊ THIẾU: Xóa sạch con cookie "Khách" đang có sẵn trong Chrome
        // =========================================================================
        driver.manage().deleteAllCookies(); 

        // Sau khi Chrome trống trơn không còn cookie khách nữa, mình mới tiến hành đọc file và bơm cookie Vip vào
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
            
            // Đọc lại mảng cookies vừa lưu vào list
            savedCookies = commons.CookieManager.loadCookiesFromFile();
        }

        // BƠM COOKIES VIP VÀO TRÌNH DUYỆT (Lúc này Chrome sẽ nhận 100% vì không còn cookie trùng tên cũ)
        for (org.openqa.selenium.Cookie cookie : savedCookies) {
            driver.manage().addCookie(cookie);
        }

        // REFRESH LÀM MỚI TRANG: Để trình duyệt gửi con Cookie Vip này lên bắt Server trả về giao diện Đã đăng nhập
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