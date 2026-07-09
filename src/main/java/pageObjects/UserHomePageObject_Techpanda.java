package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import pageUIs.HomePageUI_Techpanda;

public class UserHomePageObject_Techpanda extends BasePage {

	private WebDriver driver;

	public UserHomePageObject_Techpanda(WebDriver driver) {
		super();
		this.driver = driver;
	}

	@Step("Navigate to Register Page")
	public UserRegisterPageObject_Techpanda openRegisterPage() {
		waitForElementClickable(driver, HomePageUI_Techpanda.MENU_ACCOUNT_LINK);
		clickToElement(driver, HomePageUI_Techpanda.MENU_ACCOUNT_LINK);
		waitForElementClickable(driver, HomePageUI_Techpanda.REGISTER_LINK);
		clickToElement(driver, HomePageUI_Techpanda.REGISTER_LINK);
		//return new UserRegisterPageObject_Techpanda(driver);
		return PageGeneratorManager.getRegisterPageTechPanda(driver);
	}
	
	@Step("Navigate to Login Page")
	public UserLoginPageObject_Techpanda openLoginPage() {
		waitForElementClickable(driver, HomePageUI_Techpanda.MENU_ACCOUNT_LINK);
		clickToElement(driver, HomePageUI_Techpanda.MENU_ACCOUNT_LINK);
		waitForElementClickable(driver, HomePageUI_Techpanda.LOGIN_LINK);
		clickToElement(driver, HomePageUI_Techpanda.LOGIN_LINK);
		return new UserLoginPageObject_Techpanda(driver);

		//return PageGeneratorManager.getLoginPage(driver);
	}

}
