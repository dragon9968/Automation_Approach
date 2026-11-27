package commons;

import org.openqa.selenium.WebDriver;


import pageObjects.*;


public class PageGeneratorManager {

	public static UserHomePageObject_Techpanda getHomePageTechPanda(WebDriver driver) {
		return new UserHomePageObject_Techpanda(driver);
	}
	
	public static UserRegisterPageObject_Techpanda getRegisterPageTechPanda(WebDriver driver) {
		return new UserRegisterPageObject_Techpanda(driver);
	}
	
	public static UserLoginPageObject_Techpanda getLoginPageTechPanda(WebDriver driver) {
		return new UserLoginPageObject_Techpanda(driver);
	}
	
	public static MobilePageObject_Techpanda getMobilePageTechPanda(WebDriver driver) {
		return new MobilePageObject_Techpanda(driver);
	}
	
	public static DashBoardPageObject_Techpanda getDashBoardPageObject_Techpanda(WebDriver driver) {
        return new DashBoardPageObject_Techpanda(driver);
    }

	public static CartPageObject_Techpanda getCartPageTechPanda(WebDriver driver) {
		return new CartPageObject_Techpanda(driver);
	}
}
