package commons;

import java.io.File;

public class GlobalConstants {
	public static final String TECHPANDA_PAGE_URL = "http://live.techpanda.org/index.php/";
	public static final String PROJECT_PATH = System.getProperty("user.dir");
	public static final String REPORTING_SCREENSHOT = PROJECT_PATH + File.separator + "reportNGImages"  + File.separator;
	public static final long SHORT_TIMEOUT = 3;
	public static final long LONG_TIMEOUT = 20;

	public static final String TECHPANDA_DOMAIN = ".live.techpanda.org";
	public static final String TECHPANDA_ACCOUNT_URL = TECHPANDA_PAGE_URL + "customer/account/";
	public static final String TECHPANDA_DEFAULT_USER = "long_tester_pro@gmail.com";
	public static final String TECHPANDA_DEFAULT_PASSWORD = "123456";

	public static final String DB_POSTGRES_URL = "jdbc:postgresql://localhost:5432/postgres";
	public static final String DB_POSTGRES_USER = "postgres";
	public static final String DB_POSTGRES_PASS = "123456";
}
