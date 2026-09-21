package runners;
import org.testng.annotations.BeforeClass;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.ITestContext;

@CucumberOptions(
    features = "src/test/resources/features",// Đường dẫn tới thư mục chứa file .feature
    glue = {"stepDefinitions", "hooks"},// Nơi chứa code xử lý step và hooks
   //tags = "@register_validation",
    plugin = {
        "pretty",
        "html:target/cucumber-report.html", // Xuất báo cáo html cơ bản
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/cucumber.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",  
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    } 
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // 🌟 KÍCH HOẠT PARALLEL EXECUTION
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    // 🌟 Set trực tiếp số luồng cho IDE (Test Runer) tại đây (Ví dụ: 2 luồng)
    @BeforeClass(alwaysRun = true)
    public void setUpParallel(ITestContext context) {
        context.getSuite().getXmlSuite().setDataProviderThreadCount(2);
    }

    // 🌟 KHỐI TĨNH THẦN CHÚ: Chạy ngay khi nạp class, chấp hết mọi loại vòng đời!
    static {
        System.setProperty("org.freemarker.loggerLibrary", "none");
    }
    
}