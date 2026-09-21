package commons;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        threadDriver.set(driver);
    }

    public static void quitDriver() {
        if (threadDriver.get() != null) {
            threadDriver.get().quit();
            threadDriver.remove(); // Dọn dẹp bộ nhớ ThreadLocal
        }
    }
}