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
     api.services.AuthApiService authApiService = new api.services.AuthApiService();
     WebDriver driver= CucumberHooks.getDriver();
     
    @When("the user navigates to the Login page")
    public void theUserNavigatesToTheLoginPage() {
        homePage = PageGeneratorManager.getHomePageTechPanda(driver);
        homePage.openLoginPage();
        loginPage = PageGeneratorManager.getLoginPageTechPanda(driver);
    }

   /* @When("the user enters the following login credentials:")
    public void theUserEntersTheFollowingLoginCredentials(List<UserLoginDTO> dataList) {
        UserLoginDTO loginData = dataList.get(0);
        loginPage.inputToEmailTextbox(loginData.getEmail());
        loginPage.inputToPasswordTextbox(loginData.getPassword());
    }*/
    
    
    @When("the user performs login action via API with the following credentials:")
    public void theUserPerformsLoginActionViaAPIWithTheFollowingCredentials(List<dtos.UserLoginDTO> dataList) {
        dtos.UserLoginDTO loginData = dataList.get(0);
        api.dtos.request.LoginRequestDTO requestData = new api.dtos.request.LoginRequestDTO(loginData.getEmail(), loginData.getPassword());
        api.dtos.response.LoginResponseDTO responseData = authApiService.executeLoginApi(requestData);
     // 🌟 ĐỔI THÀNH KIỂU Cookies của Rest Assured để đồng bộ dữ liệu
        io.restassured.http.Cookies apiCookies = responseData.getCookies();
        // Vòng lặp duyệt qua từng con Cookie chi tiết
        for (io.restassured.http.Cookie apiCookie : apiCookies) {
            org.openqa.selenium.Cookie seleniumCookie = new org.openqa.selenium.Cookie.Builder(apiCookie.getName(), apiCookie.getValue())
                    .domain(apiCookie.getDomain())
                    .path(apiCookie.getPath())
                    .build();
            driver.manage().addCookie(seleniumCookie);
        }
        driver.get("http://live.techpanda.org/index.php/customer/account/");
        System.out.println("--> [INFO] Đã login qua API bằng DataTable và bơm Cookies thành công!");
    }

    @And("the user clicks the Login button")
    public void theUserClicksTheLoginButton() {
        loginPage.clickToLoginButton();
    }
   
}