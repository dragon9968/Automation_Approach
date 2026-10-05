package stepDefinitions;

import commons.PageGeneratorManager;
import dtos.UserLoginDTO;
import commons.DriverManager;
import io.cucumber.java.en.*;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.openqa.selenium.WebDriver;
import pageObjects.UserHomePageObject_Techpanda;
import pageObjects.UserLoginPageObject_Techpanda;
import pageObjects.DashBoardPageObject_Techpanda;
import org.testng.Assert;

import java.util.List;

public class LoginSteps {
    UserHomePageObject_Techpanda homePage;
    UserLoginPageObject_Techpanda loginPage;
    DashBoardPageObject_Techpanda dashboardPage;
    WebDriver driver;

    @When("the user navigates to the Login page")
    public void theUserNavigatesToTheLoginPage() {
        driver = DriverManager.getDriver();
        homePage = PageGeneratorManager.getHomePageTechPanda(driver);
        homePage.openLoginPage();
        loginPage = PageGeneratorManager.getLoginPageTechPanda(driver);
    }

    @When("the user enters the following login credentials:")
    public void theUserEntersTheFollowingLoginCredentials(List<UserLoginDTO> dataList) {
        UserLoginDTO loginData = dataList.get(0);
        // Xử lý chống NullPointerException nếu ô trong DataTable bị bỏ trống
        String email = loginData.getEmail() != null ? loginData.getEmail() : "";
        String password = loginData.getPassword() != null ? loginData.getPassword() : "";

        loginPage.inputToEmailTextbox(email);
        loginPage.inputToPasswordTextbox(password);
    }

    @And("the user clicks the Login button")
    public void theUserClicksTheLoginButton() {
        loginPage.clickToLoginButton();
    }

    @Then("the user verifies page title is {string}, URL contains {string} and welcome message contains {string}")
    public void verifyDashboardDetails(String expectedTitle, String expectedUrlPart, String expectedWelcomeText) {
        dashboardPage = PageGeneratorManager.getDashBoardPageObject_Techpanda(driver);
        Assert.assertEquals(dashboardPage.getDashboardPageTitle(), expectedTitle, "🚨 Lỗi: Page Title không khớp!");
        Assert.assertTrue(dashboardPage.getDashboardPageUrl().contains(expectedUrlPart), "🚨 Lỗi: URL không khớp!");
        String actualWelcomeText = dashboardPage.getWelcomeMessageText().toLowerCase();
        Assert.assertTrue(actualWelcomeText.contains(expectedWelcomeText.toLowerCase()), "🚨 Lỗi: Không tìm thấy tên chào mừng!");

        System.out.println("=== [PASSED] Xác minh trang Dashboard ĐỘNG thành công 100%! ===");
    }

    // 🌟 BỔ SUNG 1: Verify lỗi validation tại ô Email
    @Then("field validation error {string} should appear at email textbox")
    public void fieldValidationErrorShouldAppearAtEmailTextbox(String expectedErrorMessage) {
        Assert.assertEquals(loginPage.getErrorMessageAtEmailTextbox(), expectedErrorMessage, "🚨 Lỗi: Message validation ô Email không khớp!");
    }

    @Then("Email error message is displayed at Login page {string}")
    public void verifyEmailErrorAtLoginPage(String expectedMsg) {
        // 1. Nếu là case để trống (Hiện lỗi Required của Magento)
        if (expectedMsg.contains("required field")) {
            Assert.assertEquals(loginPage.getErrorMessageAtEmailTextbox(), expectedMsg);
        }
        // 2. Nếu là case nhập sai cú pháp email (Bong bóng HTML5 của Browser)
        else {
            String actualMsg = loginPage.getHTML5EmailValidationMessage();

            // Kiểm tra khớp chính xác HOẶC chứa các câu mặc định của Chrome/Firefox/Tiếng Việt
            boolean isMatched = actualMsg.equals(expectedMsg)
                    || actualMsg.contains("Please enter an email address")
                    || actualMsg.contains("Vui lòng bao gồm");

            Assert.assertTrue(isMatched,
                    "\n❌ Thông báo lỗi HTML5 Email không khớp trên trình duyệt này!" +
                            "\nActual: " + actualMsg +
                            "\nExpected: " + expectedMsg);
        }
    }
    // 🌟 BỔ SUNG 2: Verify lỗi validation tại ô Password
    @Then("field validation error {string} should appear at password textbox")
    public void fieldValidationErrorShouldAppearAtPasswordTextbox(String expectedErrorMessage) {
        Assert.assertEquals(loginPage.getErrorMessageAtPasswordTextbox(), expectedErrorMessage, "🚨 Lỗi: Message validation ô Password không khớp!");
    }

    // 🌟 BỔ SUNG 3: Verify thông báo lỗi chung trên cùng (Server response error)
    @Then("the global error message {string} should appear")
    public void theGlobalErrorMessageShouldAppear(String expectedErrorMessage) {
        Assert.assertEquals(loginPage.getUnsuccessfullErrorMessage(), expectedErrorMessage, "🚨 Lỗi: Thông báo lỗi chung từ Server không khớp!");
    }
}