package hooks;

import api.helpers.SessionManager;
import commons.BaseTest;
import commons.DriverManager;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils; // 🌟 1. Import FileUtils
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.io.File; // 🌟 2. Import File
import java.text.SimpleDateFormat;
import java.util.Date;

public class CucumberHooks {

    // Lưu tên trình duyệt riêng cho từng luồng để AllureBrowserListener đọc lúc kết thúc Scenario
    public static final ThreadLocal<String> CURRENT_BROWSER = new ThreadLocal<>();

    @Before(order = 1)
    public void setUp() {
        String browserName = null;

        // Bọc try-catch chống NullPointerException khi chạy trực tiếp từ Feature file
        try {
            if (Reporter.getCurrentTestResult() != null
                    && Reporter.getCurrentTestResult().getTestContext() != null
                    && Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest() != null) {
                browserName = Reporter.getCurrentTestResult()
                        .getTestContext()
                        .getCurrentXmlTest()
                        .getParameter("browser");
            }
        } catch (Exception e) {
            browserName = null;
        }

        // Nếu chạy trực tiếp từ file .feature (không qua testng.xml), mặc định mở Firefox
        if (browserName == null || browserName.isEmpty()) {
            browserName = System.getProperty("browser", "firefox"); // Mặc định là Firefox nếu không có tham số
        }

        // 🌟 Lưu tên trình duyệt hiện tại vào ThreadLocal để Allure tách riêng CHROME và FIREFOX
        final String finalBrowser = browserName.toUpperCase();
        CURRENT_BROWSER.set(finalBrowser);

        if (DriverManager.getDriver() == null) {
            System.out.println("[" + finalBrowser + " - THREAD " + Thread.currentThread().getId() + "] Initializing browser...");

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

    // 🌟 1. HOOK CHỤP ẢNH (order = 2 chạy TRƯỚC khi dọn giỏ và tắt driver)
    @After(order = 2)
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

                    System.out.println("📸 [HOOK] Successfully captured a screenshot of the faulty scenario: " + scenario.getName());
                    System.out.println("📂 [HOOK] Successfully saved the physical screenshot file at: " + filePath);

                } catch (Exception e) {
                    System.err.println("❌ Error occurred while capturing screenshot: " + e.getMessage());
                }
            }
        }
    }

    @After(value = "@cart_coupon", order = 1)
    public void cleanUpCartAfterTest() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                PageGeneratorManager.getCartPageTechPanda(driver).cleanUpCart();
                System.out.println("🧹 [HOOK] Successfully cleaned up the shopping cart after the @cart_coupon scenario");
            } catch (Exception e) {
                System.err.println("⚠️ Error occurred while cleaning up the shopping cart: " + e.getMessage());
            }
        }
    }

    // 🌟 2. HOOK DỌN DẸP DRIVER (order = 0 chạy CUỐI CÙNG)
    @After(order = 0)
    public void tearDownDriver() {
        DriverManager.quitDriver();
    }
}