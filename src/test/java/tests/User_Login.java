package tests;
import org.testng.annotations.Test;


import commons.BaseTest;
import commons.PageGeneratorManager;

import pageObjects.UserHomePageObject_Techpanda;

import pageObjects.UserRegisterPageObject_Techpanda;
import pageObjects.UserLoginPageObject_Techpanda;
import pageUIs.RegisterPageUI_Techpanda;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class User_Login extends BaseTest{
	private WebDriver driver_Test_Case;
	public static String emailAddress, password;
	//private String projectPath = System.getProperty("user.dir");
	private UserHomePageObject_Techpanda homePage;
	private UserLoginPageObject_Techpanda loginPage;
	  JavascriptExecutor jsExecutor;

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserame) {
		System.out.println("Run on " + browserame);
		driver_Test_Case = createDriver(browserame);
		jsExecutor = (JavascriptExecutor) driver_Test_Case;
		// Home Page
		// homePage = new UserHomePageObject_Techpanda(driver_Test_Case);
		homePage = PageGeneratorManager.getHomePageTechPanda(driver_Test_Case);
		emailAddress = "long" + generateRandomNumber() + "@qa.team";

	}
	@Test
	public void Login_06_Valid_Login() {
		homePage.openLoginPage();
		loginPage = PageGeneratorManager.getLoginPageTechPanda(driver_Test_Case);
        loginPage.inputToEmailTextbox("long_tester_pro@gmail.com");
        loginPage.inputToPasswordTextbox("123456");
        loginPage.clickToLoginButton();

	}
}
