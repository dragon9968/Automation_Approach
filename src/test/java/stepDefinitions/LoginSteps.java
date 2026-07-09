package stepDefinitions;

import commons.PageGeneratorManager;
import dtos.UserLoginDTO;
import hooks.CucumberHooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageObjects.UserHomePageObject_Techpanda;
import pageObjects.UserLoginPageObject_Techpanda;
import java.util.List;

public class LoginSteps {
     UserHomePageObject_Techpanda homePage;
     UserLoginPageObject_Techpanda loginPage;
     WebDriver driver;

    @When("the user navigates to the Login page")
    public void theUserNavigatesToTheLoginPage() {
        driver = CucumberHooks.getDriver();
        homePage = PageGeneratorManager.getHomePageTechPanda(driver);
        homePage.openLoginPage();
        loginPage = PageGeneratorManager.getLoginPageTechPanda(driver);
    }

    @And("the user enters the following login credentials:")
    public void theUserEntersTheFollowingLoginCredentials(List<UserLoginDTO> dataList) {
        UserLoginDTO loginData = dataList.get(0);
        loginPage.inputToEmailTextbox(loginData.getEmail());
        loginPage.inputToPasswordTextbox(loginData.getPassword());
    }

    @And("the user clicks the Login button")
    public void theUserClicksTheLoginButton() {
        // Bước 5 từ ảnh của anh
        loginPage.clickToLoginButton();
    }
}