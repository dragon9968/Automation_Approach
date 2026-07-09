package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",// Đường dẫn tới thư mục chứa file .feature
    glue = {"stepDefinitions", "hooks"},// Nơi chứa code xử lý step và hooks
    tags = "@login",
    plugin = {
        "pretty",
        "html:target/cucumber-report.html",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",  
    } // Xuất báo cáo html cơ bản
    
)
public class TestRunner extends AbstractTestNGCucumberTests {
}