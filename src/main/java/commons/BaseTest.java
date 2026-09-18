package commons;

import java.io.IOException;
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
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BaseTest {
	private WebDriver driverBaseTest;
	protected final Log log;

	protected BaseTest() {
		log = LogFactory.getLog(getClass());
	}

	public WebDriver getBrowserName(String browserName) {
		// 🌟 TỰ ĐỘNG BẬT HEADLESS KHI CHẠY TRÊN GITHUB ACTIONS HOẶC TRUYỀN -Dheadless=true
		boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"))
				|| System.getenv("GITHUB_ACTIONS") != null;

		if (browserName.equalsIgnoreCase("fireFox")) {
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
			driverBaseTest = new FirefoxDriver(options);

		} else if(browserName.equalsIgnoreCase("chrome")) {
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
			driverBaseTest = new ChromeDriver(options);

		} else if(browserName.equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--user-agent=AutomationBrowser");
			options.addArguments("--remote-allow-origins=*");

			if (isHeadless) {
				options.addArguments("--headless=new");
				options.addArguments("--window-size=1920,1080");
			}
			// 🌟 BỔ SUNG: Bật EdgeOptions hỗ trợ Headless cho Edge
			driverBaseTest = new EdgeDriver(options);

		} else if(browserName.equalsIgnoreCase("ie")) {
			driverBaseTest = new InternetExplorerDriver();
		} else {
			throw new RuntimeException("Browser name is invalid: " + browserName);
		}

		// Tận dụng hằng số LONG_TIMEOUT từ GlobalConstants
		driverBaseTest.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
		driverBaseTest.get(GlobalConstants.TECHPANDA_PAGE_URL);

		if (!isHeadless) {
			driverBaseTest.manage().window().maximize();
		}

		return driverBaseTest;
	}

	public WebDriver getDriverInstance() {
		return this.driverBaseTest;
	}

	protected int generateRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void closeBrowserAndDriver() {
		String cmd = "";
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			log.info("OS name = " + osName);

			String driverInstanceName = driverBaseTest.toString().toLowerCase();
			log.info("Driver instance name = " + driverInstanceName);

			if (driverInstanceName.contains("chrome")) {
				cmd = osName.contains("window") ? "taskkill /F /FI \"IMAGENAME eq chromedriver*\"" : "pkill chromedriver";
			} else if (driverInstanceName.contains("internetexplorer")) {
				if (osName.contains("window")) cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
			} else if (driverInstanceName.contains("firefox")) {
				cmd = osName.contains("window") ? "taskkill /F /FI \"IMAGENAME eq geckodriver*\"" : "pkill geckodriver";
			} else if (driverInstanceName.contains("edge")) {
				cmd = osName.contains("window") ? "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"" : "pkill msedgedriver";
			} else if (driverInstanceName.contains("opera")) {
				cmd = osName.contains("window") ? "taskkill /F /FI \"IMAGENAME eq operadriver*\"" : "pkill operadriver";
			} else if (driverInstanceName.contains("safari")) {
				if (osName.contains("mac")) cmd = "pkill safaridriver";
			}

			if (driverBaseTest != null) {
				driverBaseTest.manage().deleteAllCookies();
				driverBaseTest.quit();
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			try {
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}