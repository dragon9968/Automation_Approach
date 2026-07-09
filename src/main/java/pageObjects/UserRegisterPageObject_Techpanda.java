package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import pageUIs.HomePageUI_Techpanda;

import pageUIs.RegisterPageUI_Techpanda;

public class UserRegisterPageObject_Techpanda extends BasePage{
	private WebDriver driver;

	public UserRegisterPageObject_Techpanda(WebDriver driver) {
		//super();
		this.driver = driver;
	}

	@Step("Click to Register button")
	public void clickToRegisterButton() {
		waitForElementClickable(driver, RegisterPageUI_Techpanda.REGISTER_BUTTON);
		clickToElement(driver, RegisterPageUI_Techpanda.REGISTER_BUTTON);
 		
	}

	public String getErrorMessageAtFirstnameTextbox() {
        waitForElementVisible(driver, RegisterPageUI_Techpanda.FIRST_NAME_ERROR_MESSAGE);
		return getElementText(driver, RegisterPageUI_Techpanda.FIRST_NAME_ERROR_MESSAGE);
	}

	public String getErrorMessageAtLastnameTextbox() {
        waitForElementVisible(driver, RegisterPageUI_Techpanda.LAST_NAME_ERROR_MESSAGE);
		return getElementText(driver, RegisterPageUI_Techpanda.LAST_NAME_ERROR_MESSAGE);
	}

	public String getErrorMessageAtEmailTextbox() {
        waitForElementVisible(driver, RegisterPageUI_Techpanda.EMAIL_ERROR_MESSAGE);
		return getElementText(driver, RegisterPageUI_Techpanda.EMAIL_ERROR_MESSAGE);
	}
	
	public String getInvalidMessageAtEmailTextbox() {
        waitForElementVisible(driver, RegisterPageUI_Techpanda.EMAIL_TEXTBOX);
		return getElementValidationMessage(driver, RegisterPageUI_Techpanda.EMAIL_TEXTBOX);
	}
	public String getErrorMessageAtPasswordTextbox() {
        waitForElementVisible(driver, RegisterPageUI_Techpanda.PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, RegisterPageUI_Techpanda.PASSWORD_ERROR_MESSAGE);
	}
	
	public String getErrorMessageLessThan6AtPasswordTextbox() {
        waitForElementVisible(driver, RegisterPageUI_Techpanda.PASSWORD_ERROR_MESSAGE_LESS_THAN_6);
		return getElementText(driver, RegisterPageUI_Techpanda.PASSWORD_ERROR_MESSAGE_LESS_THAN_6);
	}

	public String getErrorMessageAtConfirmPasswordTextbox() {
        waitForElementVisible(driver, RegisterPageUI_Techpanda.CONFIRM_PASSWORD_ERROR_MESSAGE);
		return getElementText(driver, RegisterPageUI_Techpanda.CONFIRM_PASSWORD_ERROR_MESSAGE);
	}

	@Step("Input to First Name textbox with value is {0}")
	public void inputToFirstnameTextbox(String firstName) {
		waitForElementVisible(driver, RegisterPageUI_Techpanda.FIRST_NAME_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI_Techpanda.FIRST_NAME_TEXTBOX, firstName);
	}

	@Step("Input to Middle Name textbox with value is {0}")
	public void inputToMiddlenameTextbox(String lastName) {
		waitForElementVisible(driver, RegisterPageUI_Techpanda.MIDDLE_NAME_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI_Techpanda.MIDDLE_NAME_TEXTBOX, lastName);		
	}
	
	@Step("Input to Last Name textbox with value is {0}")
	public void inputToLastnameTextbox(String lastName) {
		waitForElementVisible(driver, RegisterPageUI_Techpanda.LAST_NAME_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI_Techpanda.LAST_NAME_TEXTBOX, lastName);		
	}

	@Step("Input to Email textbox with value is {0}")
	public void inputToEmailTextbox(String emailAddress) {
		waitForElementVisible(driver, RegisterPageUI_Techpanda.EMAIL_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI_Techpanda.EMAIL_TEXTBOX, emailAddress);			
	}

	@Step("Input to Password textbox with value is {0}")
	public void inputToPasswordTextbox(String Password) {
		waitForElementVisible(driver, RegisterPageUI_Techpanda.PASSWORD_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI_Techpanda.PASSWORD_TEXTBOX, Password);				
	}

	@Step("Input to Confirm Password textbox with value is {0}")
	public void inputToConfirmPasswordTextbox(String ConfirmPassword) {
		waitForElementVisible(driver, RegisterPageUI_Techpanda.CONFIRM_PASSWORD_TEXTBOX);
		sendkeyToElement(driver, RegisterPageUI_Techpanda.CONFIRM_PASSWORD_TEXTBOX, ConfirmPassword);			
	}

	@Step("Check to Sign Up for Newsletter checkbox")
	public void checkToSignUpForNewsletterCheckbox() {
		waitForElementVisible(driver, RegisterPageUI_Techpanda.SUBCRIBED_CHECKBOX);
		clickToElement(driver, RegisterPageUI_Techpanda.SUBCRIBED_CHECKBOX);			
	}

	
	
	@Step("Verify Register success message is displayed")
	public String getRegisterSuccessMessage() {
        waitForElementVisible(driver, RegisterPageUI_Techpanda.REGISTER_SUCCESS_MESSAGE);
		return getElementText(driver, RegisterPageUI_Techpanda.REGISTER_SUCCESS_MESSAGE);
	}

	public UserHomePageObject_Techpanda clickToLogoutLink() {
		waitForElementClickable(driver, HomePageUI_Techpanda.MENU_ACCOUNT_LINK);
		clickToElement(driver, HomePageUI_Techpanda.MENU_ACCOUNT_LINK);
		waitForElementClickable(driver, HomePageUI_Techpanda.LOGOUT_LINK);
		clickToElement(driver, HomePageUI_Techpanda.LOGOUT_LINK);
		return PageGeneratorManager.getHomePageTechPanda(driver);
	}

	public String getErrorExistingEmailMessage() {
        waitForElementVisible(driver, RegisterPageUI_Techpanda.EXISTING_EMAIL_ERROR_MESSAGE);
		return getElementText(driver, RegisterPageUI_Techpanda.EXISTING_EMAIL_ERROR_MESSAGE);
	}

	

}