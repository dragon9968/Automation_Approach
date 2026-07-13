package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import commons.BasePage;
import pageUIs.DashBoardUI_Techpanda;

public class DashBoardPageObject_Techpanda extends BasePage {
    private WebDriver driver;

    // Constructor để hứng driver từ Hooks truyền sang
    public DashBoardPageObject_Techpanda(WebDriver driver) {
        this.driver = driver;
    }

    public String getDashboardPageTitle() {
        return getPageTitle(driver);
    }

    public String getDashboardPageUrl() {
        return getPageUrl(driver);
    }

    public String getWelcomeMessageText() {
	    waitForElementVisible(driver,DashBoardUI_Techpanda.WELCOME_TEXT);
		return getElementText(driver, DashBoardUI_Techpanda.WELCOME_TEXT);
	}

}