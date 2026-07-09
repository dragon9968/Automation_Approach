package pageObjects;

import org.openqa.selenium.WebDriver;
import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.LoginPageUI_Techpanda;

public class UserLoginPageObject_Techpanda extends BasePage {
	private WebDriver driver;

	public UserLoginPageObject_Techpanda(WebDriver driver) {
		this.driver = driver;
	}
	
	
//Có return class object để apply cho Page_Generator 2,3
	public UserHomePageObject_Techpanda clickToLoginButton() {
		waitForElementClickable(driver, LoginPageUI_Techpanda.LOGIN_BUTTON);
		clickToElement(driver, LoginPageUI_Techpanda.LOGIN_BUTTON);
		return PageGeneratorManager.getHomePageTechPanda(driver);
	}

	public void inputToEmailTexbox(String Email) {
		waitForElementVisible(driver, LoginPageUI_Techpanda.EMAIL_TEXTBOX);
		sendkeyToElement(driver, LoginPageUI_Techpanda.EMAIL_TEXTBOX, Email);
	}

	public void inputToPasswordTexbox(String Password) {
		waitForElementVisible(driver, LoginPageUI_Techpanda.PASSWORD_TEXTBOX);
		sendkeyToElement(driver, LoginPageUI_Techpanda.PASSWORD_TEXTBOX, Password);
	}

	public String getUnsuccessfullErrorMessage() {
		waitForElementVisible(driver, LoginPageUI_Techpanda.LOGIN_UNSUCCESSFULL_ERROR_MESSAGE);
		return getElementText(driver, LoginPageUI_Techpanda.LOGIN_UNSUCCESSFULL_ERROR_MESSAGE);
	}

	public String getErrorMessageAtEmailTextbox() {
		waitForElementVisible(driver, LoginPageUI_Techpanda.EMAIL_ERROR_MESSAGE);
		return getElementText(driver, LoginPageUI_Techpanda.EMAIL_ERROR_MESSAGE);
	}

	public UserHomePageObject_Techpanda loginAsUser(String emailAddress , String password) {
		inputToEmailTexbox(emailAddress);
		inputToPasswordTexbox(password);
		clickToLoginButton();
		return PageGeneratorManager.getHomePageTechPanda(driver);
	}
}
