package pageUIs;

public class RegisterPageUI_Techpanda {

	public static final String FIRST_NAME_TEXTBOX = "css=input#firstname";
	public static final String MIDDLE_NAME_TEXTBOX = "css=input#middlename";
	public static final String LAST_NAME_TEXTBOX = "css=input#lastname";
	public static final String EMAIL_TEXTBOX = "xpath=//input[@id='email_address']";
	public static final String PASSWORD_TEXTBOX = "css=input#password";
	public static final String SUBCRIBED_CHECKBOX= "css=input#is_subscribed";
	public static final String CONFIRM_PASSWORD_TEXTBOX = "css=input#confirmation";
	public static final String REGISTER_BUTTON = "xpath=//button[@title='Register']";
	
	public static final String FIRST_NAME_ERROR_MESSAGE = "css=#advice-required-entry-firstname";
	public static final String LAST_NAME_ERROR_MESSAGE = "css=#advice-required-entry-lastname";
	public static final String EMAIL_ERROR_MESSAGE = "css=#advice-required-entry-email_address";
	public static final String PASSWORD_ERROR_MESSAGE = "css=#advice-required-entry-password";
	public static final String PASSWORD_ERROR_MESSAGE_LESS_THAN_6 = "css=#advice-validate-password-password";
	
	public static final String CONFIRM_PASSWORD_ERROR_MESSAGE = "css=#advice-required-entry-confirmation";
	public static final String CONFIRM_PASSWORD_NOT_MATCH_ERROR_MESSAGE = "css=#advice-validate-cpassword-confirmation";

	public static final String REGISTER_SUCCESS_MESSAGE = "css=.success-msg";
	public static final String EXISTING_EMAIL_ERROR_MESSAGE = "css=li.error-msg";

}