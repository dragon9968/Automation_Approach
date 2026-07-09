package hooks;

import commons.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

// Cho kế thừa BaseTest để xài lại hàm khởi tạo Browser của anh
public class CucumberHooks extends BaseTest {
    
    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    @Before
    public void setUp() {
        if (threadDriver.get() == null) {
            WebDriver driver = getBrowserName("chrome"); 
            threadDriver.set(driver);
        }
    }
    @After
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