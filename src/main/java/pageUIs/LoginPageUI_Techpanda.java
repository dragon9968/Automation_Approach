package pageUIs;

public class LoginPageUI_Techpanda {

	public static final String EMAIL_TEXTBOX = "css=#email";
	public static final String PASSWORD_TEXTBOX = "css=#pass";
	public static final String LOGIN_BUTTON = "xpath=//button[@id='send2']";

	// Locator thông báo lỗi dưới các ô input (Magento Client-side Validation)
	public static final String EMAIL_ERROR_MESSAGE = "xpath=//div[contains(@id,'advice-') and contains(@id,'email')]";
	public static final String PASSWORD_ERROR_MESSAGE = "xpath=//div[contains(@id,'advice-') and contains(@id,'pass')]";

	// Locator thông báo lỗi chung phía trên cùng (Server-side Validation Error)
	public static final String LOGIN_UNSUCCESSFULL_ERROR_MESSAGE = "xpath=//li[@class='error-msg']//span";
}