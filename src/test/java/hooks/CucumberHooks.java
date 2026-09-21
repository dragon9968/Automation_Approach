package hooks;

import commons.BaseTest;
import commons.DriverManager;
import commons.GlobalConstants;
import api.helpers.SessionManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CucumberHooks {

    @Before(order = 1)
    public void setUp() {
        if (DriverManager.getDriver() == null) {
            String browserName = System.getProperty("browser");
            if (browserName == null || browserName.isEmpty()) {
                browserName = "firefox";
            }
            // Gọi BaseTest khởi tạo Driver rồi lưu vào DriverManager
            WebDriver driver = new BaseTest().createDriver(browserName);
            DriverManager.setDriver(driver);
        }
    }

    @Before(value = "@use_session", order = 2)
    public void handleAutoLoginSession() {
        SessionManager.injectLoginSession(
                DriverManager.getDriver(),
                GlobalConstants.TECHPANDA_DEFAULT_USER,
                GlobalConstants.TECHPANDA_DEFAULT_PASSWORD
        );
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && DriverManager.getDriver() != null) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "📸 ẢNH CHỤP MÀN HÌNH LÚC BỊ LỖI");
        }

        // Gọi hàm đóng driver và giải phóng ThreadLocal
        DriverManager.quitDriver();
    }

    // Khi bất kỳ StepDefinition nào cần dùng Driver, chỉ cần gọi: DriverManager.getDriver()
}