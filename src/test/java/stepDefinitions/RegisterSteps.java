package stepDefinitions;

import commons.DriverManager;
import commons.PageGeneratorManager;
import dtos.UserRegisterDTO;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjects.UserHomePageObject_Techpanda;
import pageObjects.UserRegisterPageObject_Techpanda;
import java.util.List;
import java.util.Random;

public class RegisterSteps{
     WebDriver driver;
     UserHomePageObject_Techpanda homePage;
     UserRegisterPageObject_Techpanda registerPage;
     
    @Given("the user is on the TechPanda homepage")
    public void theUserIsOnTheTechPandaHomepage() {
    	System.out.println("=== Background: User is on Homepage ===");
    }
    
    @When("User input firstname {string}")
    public void inputFirstname(String value) {
        registerPage.inputToFirstnameTextbox(value);
    }

    @When("User input lastname {string}")
    public void inputLastname(String value) {
        registerPage.inputToLastnameTextbox(value);
    }

    @When("User input email {string}")
    public void inputEmail(String value) {
        registerPage.inputToEmailTextbox(value);
    }

    @When("User input password {string}")
    public void inputPassword(String value) {
        registerPage.inputToPasswordTextbox(value);
    }

    @When("User input confirm password {string}")
    public void inputConfirmPassword(String value) {
        registerPage.inputToConfirmPasswordTextbox(value);
    }

    @When("User input existing email {string}")
    public void inputExistingEmail(String value) {
        registerPage.inputToEmailTextbox(value);
    }

    @When("the user navigates to the Registration page")
    public void theUserNavigatesToTheRegistrationPage() {
        driver = DriverManager.getDriver();
    	homePage = PageGeneratorManager.getHomePageTechPanda(driver); 
        homePage.openRegisterPage();
        registerPage = PageGeneratorManager.getRegisterPageTechPanda(driver);
    }

    @And("the user enters the following registration details:")
    public void theUserEntersTheFollowingRegistrationDetails(List<UserRegisterDTO> dataList) {
    	UserRegisterDTO registerData = dataList.get(0);
        
        String emailAddress = registerData.getEmail();
        if (emailAddress.equals("random_email")) {
            emailAddress = "longnguyen" + new Random().nextInt(99999) + "@gmail.com";
        }
        registerPage.inputToFirstnameTextbox(registerData.getFirstName());
        String middleName = registerData.getMiddleName();
        if (middleName != null && !middleName.trim().isEmpty()) {
            registerPage.inputToMiddlenameTextbox(middleName);
        }
        registerPage.inputToLastnameTextbox(registerData.getLastName());
        registerPage.inputToEmailTextbox(emailAddress);
        registerPage.inputToPasswordTextbox(registerData.getPassword());
        registerPage.inputToConfirmPasswordTextbox(registerData.getConfirmPassword());
    }
    
    @And("the user selects the Sign Up for Newsletter checkbox")
    public void theUserSelectsTheSignUpForNewsletterCheckbox() {
        registerPage.checkToSignUpForNewsletterCheckbox();
    }
    
    @And("the user clicks the Register button")
    public void theUserClicksTheRegisterButton() {
        registerPage.clickToRegisterButton();
        registerPage.sleepInSecond(2);
    }
    
    // ===== VERIFY =====

    @Then("Firstname error message is displayed {string}")
    public void verifyFirstnameError(String msg) {
        Assert.assertEquals(registerPage.getErrorMessageAtFirstnameTextbox(), msg);
    }

    @Then("Lastname error message is displayed {string}")
    public void verifyLastnameError(String msg) {
        Assert.assertEquals(registerPage.getErrorMessageAtLastnameTextbox(), msg);
    }

    @Then("Email error message is displayed {string}")
    public void verifyEmailError(String expectedMsg) {
        // 1. Nếu là case để trống (Hiện lỗi Required của Magento)
        if (expectedMsg.contains("required field")) {
            Assert.assertEquals(registerPage.getErrorMessageAtEmailTextbox(), expectedMsg);
        }
        // 2. Nếu là case nhập sai cú pháp email (Bong bóng HTML5 của Browser)
        else {
            String actualMsg = registerPage.getHTML5EmailValidationMessage();

            // Linh hoạt kiểm tra: Khớp chính xác câu của Chrome HOẶC chứa câu mặc định của Firefox
            boolean isMatched = actualMsg.equals(expectedMsg)
                    || actualMsg.contains("Please enter an email address");

            Assert.assertTrue(isMatched,
                    "\n❌ Thông báo lỗi HTML5 Email không khớp trên trình duyệt này!" +
                            "\nActual: " + actualMsg +
                            "\nExpected: " + expectedMsg);
        }
    }
    
    @Then("Existing email error message is displayed {string}")
    public void verifyExistingEmail(String msg) {
        Assert.assertTrue(registerPage.getErrorExistingEmailMessage().contains(msg));
    }

    @Then("Password error message is displayed {string}")
    public void verifyEmptyPasswordError(String msg) {
        Assert.assertEquals(registerPage.getErrorMessageAtPasswordTextbox(), msg);
    }
    
    @Then("Confirm Password error message is displayed {string}")
    public void verifyEmptyConfirmPasswordError(String msg) {
        Assert.assertEquals(registerPage.getErrorMessageAtConfirmPasswordTextbox(), msg);
    }
    
    @Then("Confirm Password error message not match is displayed {string}")
    public void verifyConfirmPasswordNotMatchError(String msg) {
        Assert.assertEquals(registerPage.getErrorMessageNotMatchAtConfirmPasswordTextbox(), msg);
    }

    @Then("the system displays a password error message: {string}")
    public void theSystemDisplaysAPasswordErrorMessage(String expectedErrorMessage) {
        Assert.assertEquals(
            registerPage.getErrorMessageLessThan6AtPasswordTextbox(), 
            expectedErrorMessage
        );
    }
    
    @Then("the system displays a registration success message: {string}")
    public void theSystemDisplaysARegistrationSuccessMessage(String expectedSuccessMessage) {
        Assert.assertEquals(
            registerPage.getRegisterSuccessMessage(), 
            expectedSuccessMessage
        );
    }
    
    @And("the user logs out of the system")
    public void theUserLogsOutOfTheSystem() {
        registerPage.clickToLogoutLink();
    }

    //Use for Scenario: Register with empty data (more optimized)
    @Then("the error message {string} is displayed at {string}")
    public void verifyFieldErrorMessage(String expectedMsg, String fieldName) {
        switch (fieldName) {
            case "firstName":
                Assert.assertEquals(registerPage.getErrorMessageAtFirstnameTextbox(), expectedMsg);
                break;
            case "lastName":
                Assert.assertEquals(registerPage.getErrorMessageAtLastnameTextbox(), expectedMsg);
                break;
            case "email":
                Assert.assertEquals(registerPage.getErrorMessageAtEmailTextbox(), expectedMsg);
                break;
            case "password":
                Assert.assertEquals(registerPage.getErrorMessageAtPasswordTextbox(), expectedMsg);
                break;
            case "confirmPassword":
                Assert.assertEquals(registerPage.getErrorMessageAtConfirmPasswordTextbox(), expectedMsg);
                break;
            default:
                throw new IllegalArgumentException("Field Name is invalid: " + fieldName);
        }
    }

    @Then("the validation error message {string} should be displayed")
    public void verifyValidationErrorMessage(String expectedError) {
        String actualError = registerPage.getRegisterErrorMessage();
        Assert.assertTrue(actualError.contains(expectedError),
                "Lỗi không khớp! \nExpected chứa: " + expectedError + "\nActual: " + actualError);
    }

}