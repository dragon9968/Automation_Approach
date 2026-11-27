package pageObjects;

import commons.BasePage;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageUIs.MobilePageUI_Techpanda;

import java.util.ArrayList;
import java.util.List;

public class MobilePageObject_Techpanda extends BasePage {
    private WebDriver driver;
    // Biến lưu ID cửa sổ chính để switch về sau khi đóng Popup Compare
    private String parentWindowId;

    public MobilePageObject_Techpanda(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Click on Mobile menu")
    public void clickMobileMenu() {
        waitForElementClickable(driver, MobilePageUI_Techpanda.MENU_MOBILE);
        clickToElement(driver, MobilePageUI_Techpanda.MENU_MOBILE);
    }

    @Step("Click on TV menu")
    public void clickTVMenu() {
        waitForElementClickable(driver, MobilePageUI_Techpanda.MENU_TV);
        clickToElement(driver, MobilePageUI_Techpanda.MENU_TV);
    }

    @Step("Get Sony Xperia price on Mobile list page")
    public String getSonyXperiaPriceInList() {
        waitForElementVisible(driver, MobilePageUI_Techpanda.LIST_PAGE_SONY_PRICE);
        return getElementText(driver, MobilePageUI_Techpanda.LIST_PAGE_SONY_PRICE);
    }

    @Step("Click on Sony Xperia product name to open detail page")
    public void clickSonyXperiaDetail() {
        waitForElementClickable(driver, MobilePageUI_Techpanda.LIST_PAGE_SONY_NAME_LINK);
        clickToElement(driver, MobilePageUI_Techpanda.LIST_PAGE_SONY_NAME_LINK);
    }

    @Step("Get Sony Xperia price on product detail page")
    public String getSonyXperiaPriceInDetail() {
        waitForElementVisible(driver, MobilePageUI_Techpanda.DETAIL_PAGE_SONY_PRICE);
        return getElementText(driver, MobilePageUI_Techpanda.DETAIL_PAGE_SONY_PRICE);
    }

    // 🌟 BỔ SUNG MỚI CHO TC_01, TC_02, TC_03
    @Step("Select sort option '{0}' from Sort By dropdown")
    public void selectSortBy(String optionText) {
        // dùng cho extend report, Allure report, log4j, logback, slf4j, ... để log ra thông tin step đang thực hiện
        logStep("Select sort option '<b>" + optionText + "</b>' from Sort By dropdown");
        waitForElementVisible(driver, MobilePageUI_Techpanda.SORT_BY_DROPDOWN);
        selectItemInDefaultDropDown(driver, MobilePageUI_Techpanda.SORT_BY_DROPDOWN, optionText);
    }

    @Step("Get list of all displayed product names on Mobile page")
    public List<String> getProductNames() {
        waitForAllElementVisible(driver, MobilePageUI_Techpanda.PRODUCT_NAMES);
        List<WebElement> elements = getListWebElement(driver, MobilePageUI_Techpanda.PRODUCT_NAMES);
        List<String> productNames = new ArrayList<>();
        for (WebElement element : elements) {
            productNames.add(element.getText().trim());
        }
        return productNames;
    }

    @Step("Get list of all displayed product prices on Mobile page")
    public List<Double> getProductPrices() {
        waitForAllElementVisible(driver, MobilePageUI_Techpanda.PRODUCT_PRICES);
        List<WebElement> elements = getListWebElement(driver, MobilePageUI_Techpanda.PRODUCT_PRICES);
        List<Double> productPrices = new ArrayList<>();
        for (WebElement element : elements) {
            // Xóa ký tự '$' và dấu phẩy ',' rồi ép sang kiểu số thực Double (VD: "$130.00" -> 130.0)
            String priceText = element.getText().replace("$", "").replace(",", "").trim();
            productPrices.add(Double.parseDouble(priceText));
        }
        return productPrices;
    }

    @Step("Ensure product layout is reset to Grid view")
    public void ensureGridView() {
        // Nếu nút Grid (thẻ <a>) đang hiển thị nghĩa là trang đang bị ở List view -> click để reset về Grid
        if (!isElementUndisplayed(driver, MobilePageUI_Techpanda.VIEW_AS_GRID)) {
            clickToElement(driver, MobilePageUI_Techpanda.VIEW_AS_GRID);
        }
    }

    @Step("Switch product display layout to List view")
    public void clickListView() {
        ensureGridView();
        waitForElementClickable(driver, MobilePageUI_Techpanda.VIEW_AS_LIST);
        clickToElement(driver, MobilePageUI_Techpanda.VIEW_AS_LIST);
    }

    @Step("Get current Mobile page URL")
    public String getMobilePageUrl() {
        return getPageUrl(driver);
    }

    @Step("Verify List view product container is displayed")
    public boolean isListViewDisplayed() {
        waitForElementVisible(driver, MobilePageUI_Techpanda.LIST_VIEW_PRODUCTS);
        return isElementDisplayed(driver, MobilePageUI_Techpanda.LIST_VIEW_PRODUCTS);
    }

    @Step("Verify List view icon is active on toolbar")
    public boolean isListViewIconActive() {
        waitForElementVisible(driver, MobilePageUI_Techpanda.VIEW_AS_LIST_ACTIVE);
        return isElementDisplayed(driver, MobilePageUI_Techpanda.VIEW_AS_LIST_ACTIVE);
    }

    @Step("Verify product description is visible in List view")
    public boolean isProductDescriptionVisible() {
        waitForElementVisible(driver, MobilePageUI_Techpanda.PRODUCT_DESC);
        return isElementDisplayed(driver, MobilePageUI_Techpanda.PRODUCT_DESC);
    }

    @Step("Click 'Add to Cart' button for Sony Xperia")
    public CartPageObject_Techpanda clickAddToCartSonyXperia() {
        waitForElementClickable(driver, MobilePageUI_Techpanda.ADD_TO_CART_SONY_XPERIA_BUTTON);
        clickToElement(driver, MobilePageUI_Techpanda.ADD_TO_CART_SONY_XPERIA_BUTTON);
        return PageGeneratorManager.getCartPageTechPanda(driver);
    }

    // 🌟 BỔ SUNG CHO TC_08 (COMPARE PRODUCTS)
    @Step("Click 'Add to Compare' for product '{0}'")
    public void clickAddToCompareByProductName(String productName) {
        waitForElementClickable(driver, MobilePageUI_Techpanda.DYNAMIC_ADD_TO_COMPARE_LINK, productName);
        clickToElement(driver, MobilePageUI_Techpanda.DYNAMIC_ADD_TO_COMPARE_LINK, productName);
    }

    @Step("Get comparison success message")
    public String getCompareSuccessMessage() {
        waitForElementVisible(driver, MobilePageUI_Techpanda.COMPARE_SUCCESS_MESSAGE);
        return getElementText(driver, MobilePageUI_Techpanda.COMPARE_SUCCESS_MESSAGE);
    }

    @Step("Click 'COMPARE' button and switch to comparison popup window")
    public void clickCompareButtonAndSwitchToPopup() {
        this.parentWindowId = driver.getWindowHandle();
        waitForElementClickable(driver, MobilePageUI_Techpanda.COMPARE_BUTTON);
        clickToElement(driver, MobilePageUI_Techpanda.COMPARE_BUTTON);

        // Chờ cửa sổ popup mở ra (tổng số window > 1) trước khi switch
        long endTime = System.currentTimeMillis() + 10000;
        while (driver.getWindowHandles().size() == 1 && System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {
            }
        }

        // Gọi trực tiếp hàm switchToWindowByID từ BasePage
        switchToWindowByID(driver, parentWindowId);
    }

    @Step("Get heading text on Compare Products popup")
    public String getComparePopupHeading() {
        waitForElementVisible(driver, MobilePageUI_Techpanda.COMPARE_POPUP_HEADING);
        return getElementText(driver, MobilePageUI_Techpanda.COMPARE_POPUP_HEADING).trim();
    }

    @Step("Get list of product names displayed in Compare popup")
    public List<String> getComparePopupProductNames() {
        waitForAllElementVisible(driver, MobilePageUI_Techpanda.COMPARE_POPUP_PRODUCT_NAMES);
        List<WebElement> elements = getListWebElement(driver, MobilePageUI_Techpanda.COMPARE_POPUP_PRODUCT_NAMES);
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            names.add(el.getText().trim());
        }
        return names;
    }

    @Step("Get list of product prices displayed in Compare popup")
    public List<String> getComparePopupProductPrices() {
        waitForAllElementVisible(driver, MobilePageUI_Techpanda.COMPARE_POPUP_PRODUCT_PRICES);
        List<WebElement> elements = getListWebElement(driver, MobilePageUI_Techpanda.COMPARE_POPUP_PRODUCT_PRICES);
        List<String> prices = new ArrayList<>();
        for (WebElement el : elements) {
            prices.add(el.getText().trim());
        }
        return prices;
    }

    @Step("Get list of product SKUs displayed in Compare popup")
    public List<String> getComparePopupProductSkus() {
        waitForAllElementVisible(driver, MobilePageUI_Techpanda.COMPARE_POPUP_PRODUCT_SKUS);
        List<WebElement> elements = getListWebElement(driver, MobilePageUI_Techpanda.COMPARE_POPUP_PRODUCT_SKUS);
        List<String> skus = new ArrayList<>();
        for (WebElement el : elements) {
            skus.add(el.getText().trim());
        }
        return skus;
    }

    @Step("Get list of product image sources displayed in Compare popup")
    public List<String> getComparePopupProductImageSrcs() {
        waitForAllElementVisible(driver, MobilePageUI_Techpanda.COMPARE_POPUP_PRODUCT_IMAGES);
        List<WebElement> elements = getListWebElement(driver, MobilePageUI_Techpanda.COMPARE_POPUP_PRODUCT_IMAGES);
        List<String> imageSrcs = new ArrayList<>();
        for (WebElement el : elements) {
            imageSrcs.add(el.getAttribute("src"));
        }
        return imageSrcs;
    }

    @Step("Click 'CLOSE WINDOW' button on comparison popup and switch back to main window")
    public void closeComparePopupAndSwitchToMain() {
        waitForElementClickable(driver, MobilePageUI_Techpanda.COMPARE_POPUP_CLOSE_BUTTON);
        clickToElement(driver, MobilePageUI_Techpanda.COMPARE_POPUP_CLOSE_BUTTON);

        // Chờ cửa sổ popup đóng hoàn toàn rồi trả driver về cửa sổ chính
        long endTime = System.currentTimeMillis() + 5000;
        while (driver.getWindowHandles().size() > 1 && System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }
        }

        if (parentWindowId != null) {
            driver.switchTo().window(parentWindowId);
        }
    }

    @Step("Get total number of open browser windows")
    public int getOpenWindowCount() {
        return driver.getWindowHandles().size();
    }
}