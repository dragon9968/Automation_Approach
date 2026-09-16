package runners;
import org.testng.annotations.BeforeClass;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
@CucumberOptions(
    features = "src/test/resources/features",// Đường dẫn tới thư mục chứa file .feature
    glue = {"stepDefinitions", "hooks"},// Nơi chứa code xử lý step và hooks
    tags = "@register",
    plugin = {
        "pretty",
        "html:target/cucumber-report.html", // Xuất báo cáo html cơ bản
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",  
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    } 
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // 🌟 KÍCH HOẠT PARALLEL EXECUTION
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    // 🌟 KHỐI TĨNH THẦN CHÚ: Chạy ngay khi nạp class, chấp hết mọi loại vòng đời!
    static {
        System.setProperty("org.freemarker.loggerLibrary", "none");
    }
    
}