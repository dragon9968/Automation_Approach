package hooks;

import commons.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.cucumber.java.Scenario;
import api.helpers.SessionManager;
import commons.GlobalConstants;

// Cho kế thừa BaseTest để xài lại hàm khởi tạo Browser của anh
public class CucumberHooks extends BaseTest {
    
    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    /*@Before(order = 1)
    public void setUp() {
        if (threadDriver.get() == null) {
            // 🌟 Lấy giá trị 'browser' truyền từ lệnh terminal, nếu không truyền thì mặc định lấy 'chrome'
            String browserName = System.getProperty("browser");
            if (browserName == null || browserName.isEmpty()) {
                browserName = "chrome";
            }

            WebDriver driver = getBrowserName(browserName);
            threadDriver.set(driver);
        }
    }

    @Before(value = "@use_session", order = 2)
    public void handleAutoLoginSession() {
        WebDriver driver = threadDriver.get();
        // 🌟 Sử dụng account mặc định khai báo tập trung
        SessionManager.injectLoginSession(
                driver,
                GlobalConstants.TECHPANDA_DEFAULT_USER,
                GlobalConstants.TECHPANDA_DEFAULT_PASSWORD
        );
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) threadDriver.get())
                                .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "📸 ẢNH CHỤP MÀN HÌNH LÚC BỊ LỖI");
        }

        if (threadDriver.get() != null) {
            threadDriver.get().quit();
            threadDriver.remove();
        }
    }*/

    public static WebDriver getDriver() {
        return threadDriver.get();
    }
}