package stepDefinitions;

import commons.BaseTest;
import commons.PageGeneratorManager;
import dtos.UserRegisterDTO;
import hooks.CucumberHooks;
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
        driver = CucumberHooks.getDriver();
        this.homePage = PageGeneratorManager.getHomePageTechPanda(driver);   }

    @When("the user navigates to the Registration page")
    public void theUserNavigatesToTheRegistrationPage() {
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
        registerPage.inputToMiddlenameTextbox(registerData.getMiddleName());
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

}