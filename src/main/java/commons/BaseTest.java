package commons;

import java.time.Duration;
import java.util.Collections;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseTest {
	protected final Log log;

	public BaseTest() {
		log = LogFactory.getLog(getClass());
	}

	// 🌟 Hàm khởi tạo Driver với tên mới createDriver
	public WebDriver createDriver(String browserName) {
		WebDriver driver;
		boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"))
				|| System.getenv("GITHUB_ACTIONS") != null;

		if (browserName.equalsIgnoreCase("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("security.warn_submit_secure_to_insecure", false);
			options.addPreference("security.warn_submit_insecure", false);
			options.addPreference("security.insecure_field_warning.contextual.enabled", false);
			options.addPreference("dom.security.https_only_mode", false);
			options.addArguments("--user-agent=AutomationBrowser");
			if (isHeadless) {
				options.addArguments("-headless");
				options.addArguments("--window-size=1920,1080");
			}
			driver = new FirefoxDriver(options);

		} else if (browserName.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--disable-web-security");
			options.addArguments("--unsafely-treat-insecure-origin-as-secure=http://live.techpanda.org");
			options.addArguments("--user-agent=AutomationBrowser");
			options.addArguments("--incognito");
			options.addArguments("--remote-allow-origins=*");
			if (isHeadless) {
				options.addArguments("--headless=new");
				options.addArguments("--window-size=1920,1080");
			}
			driver = new ChromeDriver(options);

		} else if (browserName.equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--user-agent=AutomationBrowser");
			options.addArguments("--remote-allow-origins=*");
			if (isHeadless) {
				options.addArguments("--headless=new");
				options.addArguments("--window-size=1920,1080");
			}
			driver = new EdgeDriver(options);
		} else {
			throw new RuntimeException("Browser name is invalid: " + browserName);
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
		driver.get(GlobalConstants.TECHPANDA_PAGE_URL);

		if (!isHeadless) {
			driver.manage().window().maximize();
		}

		return driver;
	}

}