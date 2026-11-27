package pageObjects;

import commons.BasePage;
import commons.ConfigManager;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pageUIs.CartPageUI_Techpanda;

public class CartPageObject_Techpanda extends BasePage {
    private WebDriver driver;

    public CartPageObject_Techpanda(WebDriver driver) {
        this.driver = driver;
    }

    public String getSuccessMessage() {
        waitForElementVisible(driver, CartPageUI_Techpanda.SUCCESS_MESSAGE);
        return getElementText(driver, CartPageUI_Techpanda.SUCCESS_MESSAGE);
    }

    public void enterCouponCode(String couponCode) {
        waitForElementVisible(driver, CartPageUI_Techpanda.COUPON_INPUT);
        sendkeyToElement(driver, CartPageUI_Techpanda.COUPON_INPUT, couponCode);
    }

    public void clickApplyCoupon() {
        waitForElementClickable(driver, CartPageUI_Techpanda.APPLY_COUPON_BUTTON);
        clickToElement(driver, CartPageUI_Techpanda.APPLY_COUPON_BUTTON);
    }

    public String getDiscountAmountMessage() {
        waitForElementVisible(driver, CartPageUI_Techpanda.DISCOUNT_TEXT);
        return getElementText(driver, CartPageUI_Techpanda.DISCOUNT_TEXT);
    }

    public String getGrandTotal() {
        waitForElementVisible(driver, CartPageUI_Techpanda.GRAND_TOTAL_TEXT);
        return getElementText(driver, CartPageUI_Techpanda.GRAND_TOTAL_TEXT);
    }

    public void enterQuantity(String quantity) {
        waitForElementVisible(driver, CartPageUI_Techpanda.QTY_INPUT);
        sendkeyToElement(driver, CartPageUI_Techpanda.QTY_INPUT, quantity);
        // Nhấn Enter để submit form cập nhật số lượng tương tự await qtyInput.press('Enter')
        getWebElement(driver, CartPageUI_Techpanda.QTY_INPUT).sendKeys(Keys.ENTER);
    }

    public void clickUpdate() {
        waitForElementClickable(driver, CartPageUI_Techpanda.UPDATE_BUTTON);
        clickToElement(driver, CartPageUI_Techpanda.UPDATE_BUTTON);
    }

    public String getErrorMessage() {
        waitForElementVisible(driver, CartPageUI_Techpanda.ERROR_MESSAGE);
        return getElementText(driver, CartPageUI_Techpanda.ERROR_MESSAGE);
    }

    public String getQuantityErrorMessage() {
        waitForElementVisible(driver, CartPageUI_Techpanda.ERROR_QTY_MESSAGE);
        return getElementText(driver, CartPageUI_Techpanda.ERROR_QTY_MESSAGE);
    }

    public void clickEmptyCart() {
        waitForElementClickable(driver, CartPageUI_Techpanda.EMPTY_CART_BUTTON);
        clickToElement(driver, CartPageUI_Techpanda.EMPTY_CART_BUTTON);
    }

    public String getEmptyCartMessage() {
        waitForElementVisible(driver, CartPageUI_Techpanda.EMPTY_CART_TEXT);
        return getElementText(driver, CartPageUI_Techpanda.EMPTY_CART_TEXT);
    }

    // Dọn sạch giỏ hàng sau mỗi kịch bản (tương đương test.afterEach bên Playwright)
    public void cleanUpCart() {
        String currentUrl = getPageUrl(driver);
        if (!currentUrl.contains("/checkout/cart/")) {
            String baseUrl = ConfigManager.getAppUrl().replaceAll("/$", "");
            openPageUrl(driver, baseUrl + "/index.php/checkout/cart/");
        }
        if (!isElementUndisplayed(driver, CartPageUI_Techpanda.EMPTY_CART_BUTTON)) {
            clickToElement(driver, CartPageUI_Techpanda.EMPTY_CART_BUTTON);
        }
    }
}