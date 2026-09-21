package reportConfig;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import commons.DriverManager; // 🌟 1. Import DriverManager

public class ExtentReportListenerV2 implements ITestListener {
	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.endTest();
		if (ExtentManager.getReporter() != null) {
			ExtentManager.getReporter().flush();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String desc = result.getMethod().getDescription();
		if (desc == null) {
			desc = result.getName();
		}
		ExtentManager.startTest(testName, desc);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTest test = ExtentManager.getTest();
		if (test != null) {
			test.log(Status.PASS, "Test Passed");
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// 🌟 2. Lấy WebDriver trực tiếp từ DriverManager gọn gàng
		WebDriver webDriver = DriverManager.getDriver();

		ExtentTest test = ExtentManager.getTest();
		String message = result.getThrowable() != null ? result.getThrowable().toString() : "Test Failed";
		if (test != null) {
			try {
				if (webDriver != null) {
					String base64Screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
					test.fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
				} else {
					test.fail(message);
				}
			} catch (Exception e) {
				test.fail(message + " (screenshot capture failed: " + e.getMessage() + ")");
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTest test = ExtentManager.getTest();
		if (test != null) {
			test.log(Status.SKIP, "Test Skipped");
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}
}