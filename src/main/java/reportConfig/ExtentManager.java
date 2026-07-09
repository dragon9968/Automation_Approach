package reportConfig;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	// Use Long for thread ids to avoid lossy casting from long->int
	private static Map<Long, ExtentTest> extentTestMap = new HashMap<Long, ExtentTest>();
	private static ExtentReports extent = null; // lazily initialized
	
	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/ExtentReportV2/ExtentReport.html";
			ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
			reporter.config().setReportName("Automation Test Report");
			reporter.config().setDocumentTitle("Test Results");
			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Tester", "Long Nguyen");
			extent.setSystemInfo("OS", System.getProperty("os.name"));
		}
		return extent;
	}

	public static synchronized ExtentTest getTest() {
		Long id = Long.valueOf(Thread.currentThread().getId());
		return extentTestMap.get(id);
	}

	public static synchronized void endTest() {
		// Remove test from map and flush report. AventStack ExtentReports doesn't require explicit endTest.
		Long id = Long.valueOf(Thread.currentThread().getId());
		extentTestMap.remove(id);
		if (extent != null) {
			extent.flush();
		}
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		// createTest is the ExtentReports v4 API for creating/starting tests
		ExtentReports rep = getReporter();
		ExtentTest test = rep.createTest(testName, desc);
		Long id = Long.valueOf(Thread.currentThread().getId());
		extentTestMap.put(id, test);
		return test;
	}
}