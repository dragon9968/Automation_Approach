package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.MobilePageUI_Techpanda;

public class MobilePageObject_Techpanda extends BasePage {
    private WebDriver driver;

    public MobilePageObject_Techpanda(WebDriver driver) {
        this.driver = driver;
    }

    public void clickMobileMenu() {
        waitForElementClickable(driver, MobilePageUI_Techpanda.MENU_MOBILE);
        clickToElement(driver, MobilePageUI_Techpanda.MENU_MOBILE);
    }

    public void clickTVMenu() {
        waitForElementClickable(driver, MobilePageUI_Techpanda.MENU_TV);
        clickToElement(driver, MobilePageUI_Techpanda.MENU_TV);
    }

    public String getSonyXperiaPriceInList() {
        waitForElementVisible(driver, MobilePageUI_Techpanda.LIST_PAGE_SONY_PRICE);
        return getElementText(driver, MobilePageUI_Techpanda.LIST_PAGE_SONY_PRICE);
    }

    public void clickSonyXperiaDetail() {
        waitForElementClickable(driver, MobilePageUI_Techpanda.LIST_PAGE_SONY_NAME_LINK);
        clickToElement(driver, MobilePageUI_Techpanda.LIST_PAGE_SONY_NAME_LINK);
    }

    public String getSonyXperiaPriceInDetail() {
        waitForElementVisible(driver, MobilePageUI_Techpanda.DETAIL_PAGE_SONY_PRICE);
        return getElementText(driver, MobilePageUI_Techpanda.DETAIL_PAGE_SONY_PRICE);
    }
}