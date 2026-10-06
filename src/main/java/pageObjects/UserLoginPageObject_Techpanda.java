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

	public DashBoardPageObject_Techpanda clickToLoginButton() {
		waitForElementClickable(driver, LoginPageUI_Techpanda.LOGIN_BUTTON);
		clickToElement(driver, LoginPageUI_Techpanda.LOGIN_BUTTON);
		return PageGeneratorManager.getDashBoardPageObject_Techpanda(driver);
	}

	public void inputToEmailTextbox(String email) {
		waitForElementVisible(driver, LoginPageUI_Techpanda.EMAIL_TEXTBOX);
		sendkeyToElement(driver, LoginPageUI_Techpanda.EMAIL_TEXTBOX, email);
	}

	public void inputToPasswordTextbox(String password) {
		waitForElementVisible(driver, LoginPageUI_Techpanda.PASSWORD_TEXTBOX);
		sendkeyToElement(driver, LoginPageUI_Techpanda.PASSWORD_TEXTBOX, password);
	}

	public String getUnsuccessfullErrorMessage() {
		waitForElementVisible(driver, LoginPageUI_Techpanda.LOGIN_UNSUCCESSFULL_ERROR_MESSAGE);
		return getElementText(driver, LoginPageUI_Techpanda.LOGIN_UNSUCCESSFULL_ERROR_MESSAGE);
	}

	public String getErrorMessageAtEmailTextbox() {
		waitForElementVisible(driver, LoginPageUI_Techpanda.EMAIL_ERROR_MESSAGE);
		return getElementText(driver, LoginPageUI_Techpanda.EMAIL_ERROR_MESSAGE);
	}

	public String getErrorMessageAtPasswordTextbox() {
		waitForElementVisible(driver, LoginPageUI_Techpanda.PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, LoginPageUI_Techpanda.PASSWORD_ERROR_MESSAGE);
	}

	public String getHTML5EmailValidationMessage() {
		waitForElementVisible(driver, LoginPageUI_Techpanda.EMAIL_TEXTBOX);
		return getElementValidationMessage(driver, LoginPageUI_Techpanda.EMAIL_TEXTBOX);
	}

	public UserHomePageObject_Techpanda loginAsUser(String emailAddress, String password) {
		inputToEmailTextbox(emailAddress);
		inputToPasswordTextbox(password);
		clickToLoginButton();
		return PageGeneratorManager.getHomePageTechPanda(driver);
	}
}