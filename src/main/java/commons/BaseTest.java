package commons;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	private WebDriver driverBaseTest;
	protected final Log log;
	
	protected BaseTest() {
		log = LogFactory.getLog(getClass());
	}
	
	public WebDriver getBrowserName(String browserName) {
		if (browserName.equalsIgnoreCase("fireFox")) {
            FirefoxOptions options = new FirefoxOptions();        
            options.addPreference("security.warn_submit_secure_to_insecure", false);
            options.addPreference("security.warn_submit_insecure", false);
            options.addPreference("security.insecure_field_warning.contextual.enabled", false);
            options.addPreference("dom.security.https_only_mode", false);
            options.addArguments("--user-agent=AutomationBrowser");	
            //options.addArguments("-headless"); 
            //options.addArguments("--window-size=1920,1080");
			driverBaseTest = new FirefoxDriver();
			
		} else if(browserName.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.addArguments("--allow-running-insecure-content");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--disable-web-security");
            options.addArguments("--unsafely-treat-insecure-origin-as-secure=http://live.techpanda.org");
            options.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "chrome_automation_profile" + System.currentTimeMillis());
            options.addArguments("--user-agent=AutomationBrowser");	
           // options.addArguments("--headless=new");
           // options.addArguments("--window-size=1920,1080");
			driverBaseTest = new ChromeDriver(options);
			
		} else if(browserName.equalsIgnoreCase("edge")) {
			driverBaseTest = new EdgeDriver();

	    } else if(browserName.equalsIgnoreCase("ie")) {

		driverBaseTest = new InternetExplorerDriver();
	    } 
			
		else {
			throw new RuntimeException("Browser name is invalid");
		}
		
		driverBaseTest.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driverBaseTest.get(GlobalConstants.TECHPANDA_PAGE_URL);
		driverBaseTest.manage().window().maximize();

		
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
			// TODO Auto-generated catch block
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
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
				} else {
					cmd = "pkill chromedriver";
				}
			} else if (driverInstanceName.contains("internetexplorer")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
				}
			} else if (driverInstanceName.contains("firefox")) {
				if (osName.contains("windows")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
				} else {
					cmd = "pkill geckodriver";
				}
			} else if (driverInstanceName.contains("edge")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
				} else {
					cmd = "pkill msedgedriver";
				}
			} else if (driverInstanceName.contains("opera")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq operadriver*\"";
				} else {
					cmd = "pkill operadriver";
				}
			} else if (driverInstanceName.contains("safari")) {
				if (osName.contains("mac")) {
					cmd = "pkill safaridriver";
				}
			}

			if (driverBaseTest != null) {
				//IE: lưu lại các phiên đăng nhập trước đó (Khác Thread)
				driverBaseTest.manage().deleteAllCookies();
				driverBaseTest.quit();
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			try {
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
