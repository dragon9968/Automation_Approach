package commons;

import java.io.File;

public class GlobalConstants {
	public static final String TECHPANDA_PAGE_URL = "http://live.techpanda.org/index.php/";
	public static final String PORTAL_PAGE_URL = "https://demo.nopcommerce.com/";
	public static final String ADMIN_PAGE_URL = "https://admin-demo.nopcommerce.com/";
	public static final String PROJECT_PATH = System.getProperty("user.dir");
	public static final String UPLOAD_FILE = PROJECT_PATH + File.separator + "uploadFiles";
	public static final String DOWNLOAD_FILE = PROJECT_PATH + File.separator + "downloadFiles";
	public static final String BROWSER_LOG = PROJECT_PATH + File.separator + "browserLogs";
	public static final String DRAG_DROP_HTML5 = PROJECT_PATH + File.separator + "dragDropHTML5";
	public static final String AUTO_IT_SCRIPT = PROJECT_PATH + File.separator + "autoIT";
	public static final String REPORTING_SCREENSHOT = PROJECT_PATH + File.separator + "reportNGImages"  + File.separator;
	public static final String DB_DEV_URL = "32.18.252.185:9806";
	public static final String DB_DEV_USER = "automationfc";
	public static final String DB_DEV_PASS = "P@ssw0rld1!";
	public static final long SHORT_TIMEOUT = 3;
	public static final long LONG_TIMEOUT = 20;
	public static final long RETRY_TEST_FAIL = 3;
	public static final String BROWSER_USERNAME = "longnguyen9";
	public static final String BROWSER_AUTOMATE_KEY = "zSyApKjqPrZqkUFnGgLt";
	public static final String BROWSER_STACK_URL = "https://" + BROWSER_USERNAME +":"+ BROWSER_AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";
	
}
