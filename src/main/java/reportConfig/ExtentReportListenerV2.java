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

import commons.BaseTest;

public class ExtentReportListenerV2 implements ITestListener {
	@Override
	public void onStart(ITestContext context) {
		// no-op: reporter is lazily initialized in ExtentManager when a test starts
	}

	@Override
	public void onFinish(ITestContext context) {
		// Ensure any remaining tests are cleaned up and report flushed
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
		// Start a new test for the current thread
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
		// Try to capture screenshot if driver is available
		Object testClass = result.getInstance();
		WebDriver webDriver = null;
		try {
			if (testClass instanceof BaseTest) {
				webDriver = ((BaseTest) testClass).getDriverInstance();
			}
		} catch (Exception e) {
			// ignore - we will log failure without screenshot
		}

		ExtentTest test = ExtentManager.getTest();
		String message = result.getThrowable() != null ? result.getThrowable().toString() : "Test Failed";
		if (test != null) {
			try {
				if (webDriver != null) {
					String base64Screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
					// Attach screenshot using MediaEntityBuilder. Note: pass base64 string directly.
					test.fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
				} else {
					test.fail(message);
				}
			} catch (Exception e) {
				// If attaching media fails, at least log the failure
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
		// no-op
	}

}