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
        dashboardPage = loginPage.clickToLoginButton();
    }

    @Then("the user verifies page title is {string}, URL contains {string} and welcome message contains {string}")
    public void verifyDashboardDetails(String expectedTitle, String expectedUrlPart, String expectedWelcomeText) {
        dashboardPage.waitForPageTitle(driver, expectedTitle);
        dashboardPage.waitForUrlContains(driver, expectedUrlPart);
        Assert.assertEquals(dashboardPage.getDashboardPageTitle(), expectedTitle, "🚨 Error: Page title does not match!");
        Assert.assertTrue(dashboardPage.getDashboardPageUrl().contains(expectedUrlPart), "🚨 Error: URL does not match!");
        String actualWelcomeText = dashboardPage.getWelcomeMessageText().toLowerCase();
        Assert.assertTrue(actualWelcomeText.contains(expectedWelcomeText.toLowerCase()), "🚨 Error: Welcome name was not found!");

        System.out.println("=== [PASSED] Dashboard page verified successfully! ===");
    }

    // 🌟 BỔ SUNG 1: Verify lỗi validation tại ô Email
    @Then("field validation error {string} should appear at email textbox")
    public void fieldValidationErrorShouldAppearAtEmailTextbox(String expectedErrorMessage) {
        Assert.assertEquals(loginPage.getErrorMessageAtEmailTextbox(), expectedErrorMessage, "🚨 Error: Email field validation message does not match!");
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
                    "\n❌ HTML5 email validation message does not match in this browser!" +
                            "\nActual: " + actualMsg +
                            "\nExpected: " + expectedMsg);
        }
    }
    // 🌟 BỔ SUNG 2: Verify lỗi validation tại ô Password
    @Then("field validation error {string} should appear at password textbox")
    public void fieldValidationErrorShouldAppearAtPasswordTextbox(String expectedErrorMessage) {
        Assert.assertEquals(loginPage.getErrorMessageAtPasswordTextbox(), expectedErrorMessage, "🚨 Error: Password field validation message does not match!");
    }

    // 🌟 BỔ SUNG 3: Verify thông báo lỗi chung trên cùng (Server response error)
    @Then("the global error message {string} should appear")
    public void theGlobalErrorMessageShouldAppear(String expectedErrorMessage) {
        Assert.assertEquals(loginPage.getUnsuccessfullErrorMessage(), expectedErrorMessage, "🚨 Error: Global server error message does not match!");
    }

    @Then("the login validation error message {string} should be displayed")
    public void theLoginValidationErrorMessageShouldBeDisplayed(String expectedError) {
        // 1. Lỗi HTML5 của trình duyệt (thiếu ký tự '@')
        if (expectedError.contains("missing an '@'")) {
            String actualMsg = loginPage.getHTML5EmailValidationMessage();
            boolean isMatched = actualMsg.equals(expectedError)
                    || actualMsg.contains("Please enter an email address")
                    || actualMsg.contains("Vui lòng bao gồm");

            Assert.assertTrue(isMatched,
                    "\n❌ HTML5 email validation message does not match in this browser!" +
                            "\nActual: " + actualMsg +
                            "\nExpected: " + expectedError);
        }
        // 2. Lỗi bỏ trống cả Email và Password
        else if (expectedError.equals("This is a required field.")) {
            Assert.assertEquals(loginPage.getErrorMessageAtEmailTextbox(), expectedError, "🚨 Error: Email field validation message does not match!");
            Assert.assertEquals(loginPage.getErrorMessageAtPasswordTextbox(), expectedError, "🚨 Error: Password field validation message does not match!");
        }
        // 3. Lỗi sai định dạng Email (Magento validation)
        else if (expectedError.contains("valid email address")) {
            Assert.assertEquals(loginPage.getErrorMessageAtEmailTextbox(), expectedError, "🚨 Error: Email field validation message does not match!");
        }
        // 4. Lỗi mật khẩu ngắn hơn 6 ký tự (Magento validation)
        else if (expectedError.contains("6 or more characters")) {
            Assert.assertEquals(loginPage.getErrorMessageAtPasswordTextbox(), expectedError, "🚨 Error: Password field validation message does not match!");
        }
        // 5. Lỗi đăng nhập sai tài khoản/mật khẩu từ Server (Global error)
        else {
            Assert.assertEquals(loginPage.getUnsuccessfullErrorMessage(), expectedError, "🚨 Error: Global server error message does not match!");
        }
    }
}