package stepDefinitions;

import commons.PageGeneratorManager;
import dtos.UserLoginDTO;
import hooks.CucumberHooks;
import io.cucumber.java.en.*;
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
     WebDriver driver= CucumberHooks.getDriver();
     
    @When("the user navigates to the Login page")
    public void theUserNavigatesToTheLoginPage() {
        homePage = PageGeneratorManager.getHomePageTechPanda(driver);
        homePage.openLoginPage();
        loginPage = PageGeneratorManager.getLoginPageTechPanda(driver);
    }

    @When("the user enters the following login credentials:")
    public void theUserEntersTheFollowingLoginCredentials(List<UserLoginDTO> dataList) {
        UserLoginDTO loginData = dataList.get(0);
        loginPage.inputToEmailTextbox(loginData.getEmail());
        loginPage.inputToPasswordTextbox(loginData.getPassword());
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
}