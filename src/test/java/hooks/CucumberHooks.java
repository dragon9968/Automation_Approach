package hooks;

import commons.BaseTest;
import commons.DriverManager;
import commons.GlobalConstants;
import api.helpers.SessionManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils; // 🌟 1. Import FileUtils
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File; // 🌟 2. Import File
import java.text.SimpleDateFormat;
import java.util.Date;

public class CucumberHooks {

    @Before(order = 1)
    public void setUp() {
        if (DriverManager.getDriver() == null) {
            String browserName = System.getProperty("browser");
            if (browserName == null || browserName.isEmpty()) {
                browserName = "firefox";
            }
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

    // 🌟 1. HOOK CHỤP ẢNH (order = 1 chạy TRƯỚC khi tắt driver)
    @After(order = 1)
    public void captureScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverManager.getDriver();

            if (driver != null) {
                try {
                    // Lấy byte[] để đính kèm trực tiếp vào HTML/Extent/Allure Report
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                    // Tạo tên ảnh chuẩn không chứa ký tự đặc biệt (để tránh lỗi lưu file)
                    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String cleanScenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
                    String screenshotName = "FAILED_" + cleanScenarioName + "_" + timestamp;

                    // Đính kèm ảnh vào Báo cáo của Cucumber
                    scenario.attach(screenshot, "image/png", "📸 " + screenshotName);

                    // 🌟 3. ĐOẠN CODE LƯU FILE ẢNH VẬT LÝ RA THƯ MỤC target/screenshots/
                    File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    String filePath = "target/screenshots/" + screenshotName + ".png";
                    FileUtils.copyFile(srcFile, new File(filePath));

                    System.out.println("📸 [HOOK] Đã chụp ảnh màn hình thành công cho kịch bản bị lỗi: " + scenario.getName());
                    System.out.println("📂 [HOOK] Đã lưu file ảnh vật lý tại: " + filePath);

                } catch (Exception e) {
                    System.err.println("❌ Lỗi trong quá trình chụp ảnh màn hình: " + e.getMessage());
                }
            }
        }
    }

    // 🌟 2. HOOK DỌN DẸP DRIVER (order = 0 chạy CUỐI CÙNG)
    @After(order = 0)
    public void tearDownDriver() {
        DriverManager.quitDriver();
    }
}