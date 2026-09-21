package reportConfig;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import commons.DriverManager; // 🌟 IMPORT DRIVERMANAGER
import io.qameta.allure.Attachment;

public class AllureTestListener implements ITestListener {

	@Attachment(value = "Screenshot of {0}", type = "image/png")
	public static byte[] saveScreenshotPNG(String testName, WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		// 🌟 Lấy driver trực tiếp từ DriverManager
		WebDriver driver = DriverManager.getDriver();
		if (driver != null) {
			saveScreenshotPNG(iTestResult.getName(), driver);
		}
	}

	@Override
	public void onStart(ITestContext iTestContext) {
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
	}

	@Override
	public void onFinish(ITestContext arg0) {
	}

	@Override
	public void onTestStart(ITestResult arg0) {
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
	}
}