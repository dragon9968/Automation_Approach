package stepDefinitions;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import commons.DriverManager;
import commons.PageGeneratorManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.testng.Assert;
import pageObjects.MobilePageObject_Techpanda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MobileSteps {
    private MobilePageObject_Techpanda mobilePage;
    private String priceInList; // Biến trung gian lưu giá giữa các step

    public MobileSteps() {
    }

    @When("the user navigates to the Mobile list page cleanly")
    public void theUserNavigatesToTheMobileListPageCleanly() {
        this.mobilePage = PageGeneratorManager.getMobilePageTechPanda(DriverManager.getDriver());
        mobilePage.clickMobileMenu();
        mobilePage.clickTVMenu();
        mobilePage.clickMobileMenu();
    }

    @When("the user notes the price of Sony Xperia from the list page")
    public void theUserNotesThePriceOfSonyXperiaFromTheListPage() {
        this.priceInList = mobilePage.getSonyXperiaPriceInList();
        System.out.println("--> [INFO] Price on the List page: " + priceInList);
    }

    @When("the user clicks on the Sony Xperia product details")
    public void theUserClicksOnTheSonyXperiaProductDetails() {
        mobilePage.clickSonyXperiaDetail();
    }

    @Then("the price of Sony Xperia on the detail page should be equal to the price on the list page")
    public void thePriceOfSonyXperiaOnTheDetailPageShouldBeEqualToThePriceOnTheListPage() {
        String priceInDetail = mobilePage.getSonyXperiaPriceInDetail();
        System.out.println("--> [INFO] Price on the Detail page: " + priceInDetail);

        // Đính kèm giá 2 trang vào Allure Report trước khi Assert
        Allure.addAttachment("Sony Xperia Price Comparison",
                "Price on List Page: " + this.priceInList + "\nPrice on Detail Page: " + priceInDetail);

        // Tiến hành so sánh chuỗi tương tự như lệnh expect bên Playwright của anh
        Assert.assertEquals(
                priceInDetail,
                this.priceInList,
                "Error: Product prices on the list and detail pages do not match!"
        );

        System.out.println("=== VERIFICATION SUCCESSFUL: Prices on both pages match! ===");
    }

    // 🌟 BỔ SUNG MỚI CHO TC_01, TC_02, TC_03

    @When("the user selects sort by {string}")
    public void theUserSelectsSortBy(String sortOption) {
        mobilePage.selectSortBy(sortOption);
    }

    @Then("the products should be sorted by Name in ascending order")
    public void theProductsShouldBeSortedByNameInAscendingOrder() {
        // 1. Lấy danh sách tên thực tế từ UI
        List<String> actualNames = mobilePage.getProductNames();
        System.out.println("--> [INFO] Actual Names: " + actualNames);

        // 2. Tạo bản sao và tự sắp xếp A -> Z
        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);

        // 3. Đính kèm dữ liệu Actual & Expected vào Allure Report (đặt TRƯỚC Assert)
        Allure.addAttachment("Actual vs Expected Names", "Actual: " + actualNames + "\nExpected: " + expectedNames);

        // 4. So sánh
        Assert.assertEquals(actualNames, expectedNames, "🚨 ERROR: Products are not sorted by name from A to Z!");
    }

    @Then("the products should be sorted by Price from Low to High")
    public void theProductsShouldBeSortedByPriceFromLowToHigh() {
        // 1. Lấy danh sách giá thực tế (dạng số Double) từ UI
        List<Double> actualPrices = mobilePage.getProductPrices();
        System.out.println("--> [INFO] Actual Prices: " + actualPrices);

        // 2. Tạo bản sao và tự sắp xếp từ thấp đến cao
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        // Tương tự cho phần kiểm tra giá
        Allure.addAttachment("Actual vs Expected Prices", "Actual: " + actualPrices + "\nExpected: " + expectedPrices);
        // Dùng cho extend report, Allure report, log4j, logback, slf4j, ... để log ra thông tin step đang thực hiện
        ExtentCucumberAdapter.addTestStepLog("<b>Actual Prices:</b> " + actualPrices + "<br><b>Expected Prices:</b> " + expectedPrices);
        // 3. So sánh
        Assert.assertEquals(actualPrices, expectedPrices, "🚨 ERROR: Product prices are not sorted from low to high!");
    }

    @When("the user clicks on the List view icon")
    public void theUserClicksOnTheListViewIcon() {
        mobilePage.clickListView();
    }

    @Then("the mobile page URL should contain {string}")
    public void theMobilePageUrlShouldContain(String expectedUrlPart) {
        String actualUrl = mobilePage.getMobilePageUrl();
        Allure.addAttachment("Mobile Page URL Verification",
                "Actual URL: " + actualUrl + "\nExpected URL Part: " + expectedUrlPart);

        Assert.assertTrue(actualUrl.contains(expectedUrlPart),
                "🚨 ERROR: Current URL (" + actualUrl + ") does not contain '" + expectedUrlPart + "'!");
    }

    @And("the Mobile page should display in List view layout properly")
    public void theMobilePageShouldDisplayInListViewLayoutProperly() {
        Assert.assertTrue(mobilePage.isListViewDisplayed(), "🚨 ERROR: The list layout (ol.products-list) is not displayed!");
        Assert.assertTrue(mobilePage.isListViewIconActive(), "🚨 ERROR: The List icon on the toolbar is not active!");
        Assert.assertTrue(mobilePage.isProductDescriptionVisible(), "🚨 ERROR: The product description is not displayed in List view!");
    }

    // 🌟 BỔ SUNG CHO TC_08 (COMPARE PRODUCTS)
    @When("the user clicks Add to Compare for {string}")
    public void theUserClicksAddToCompareFor(String productName) {
        mobilePage.clickAddToCompareByProductName(productName);
    }

    @Then("the comparison success message {string} should be displayed")
    public void theComparisonSuccessMessageShouldBeDisplayed(String expectedMessage) {
        String actualMessage = mobilePage.getCompareSuccessMessage();
        Allure.addAttachment("Compare Success Message",
                "Expected: " + expectedMessage + "\nActual: " + actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage,
                "🚨 ERROR: Comparison success message does not match!");
    }

    @When("the user clicks on the Compare button to open the comparison popup")
    public void theUserClicksOnTheCompareButtonToOpenTheComparisonPopup() {
        mobilePage.clickCompareButtonAndSwitchToPopup();
    }

    @Then("the comparison popup should display heading {string} with selected products:")
    public void theComparisonPopupShouldDisplayHeadingWithSelectedProducts(String expectedHeading, DataTable dataTable) {
        // 1. Kiểm tra tiêu đề Popup ("COMPARE PRODUCTS")
        String actualHeading = mobilePage.getComparePopupHeading();
        Assert.assertTrue(actualHeading.equalsIgnoreCase(expectedHeading),
                "🚨 ERROR: Popup heading mismatch! Expected: " + expectedHeading + " | Actual: " + actualHeading);

        // 2. Lấy toàn bộ thông tin thực tế của các sản phẩm trên bảng so sánh
        List<String> actualNames = mobilePage.getComparePopupProductNames();
        List<String> actualPrices = mobilePage.getComparePopupProductPrices();
        List<String> actualSkus = mobilePage.getComparePopupProductSkus();
        List<String> actualImageSrcs = mobilePage.getComparePopupProductImageSrcs();

        Allure.addAttachment("Compare Popup Details",
                "Heading: " + actualHeading
                        + "\nNames: " + actualNames
                        + "\nPrices: " + actualPrices
                        + "\nSKUs: " + actualSkus
                        + "\nImages: " + actualImageSrcs);

        // 3. Đối chiếu từng sản phẩm trong bảng dữ liệu Gherkin
        var expectedProducts = dataTable.asMaps();
        Assert.assertEquals(actualNames.size(), expectedProducts.size(),
                "🚨 ERROR: Number of compared products does not match!");

        for (int i = 0; i < expectedProducts.size(); i++) {
            Map<String, String> expected = expectedProducts.get(i);
            String expectedName = expected.get("productName");
            String expectedPrice = expected.get("price");
            String expectedSku = expected.get("sku");
            String expectedImage = expected.get("image");

            Assert.assertTrue(actualNames.get(i).equalsIgnoreCase(expectedName),
                    "🚨 ERROR: Product name mismatch at index " + i + "! Expected: " + expectedName + " | Actual: " + actualNames.get(i));
            Assert.assertEquals(actualPrices.get(i), expectedPrice,
                    "🚨 ERROR: Product price mismatch for " + expectedName);
            Assert.assertEquals(actualSkus.get(i), expectedSku,
                    "🚨 ERROR: Product SKU mismatch for " + expectedName);
            Assert.assertTrue(actualImageSrcs.get(i).contains(expectedImage),
                    "🚨 ERROR: Product image src (" + actualImageSrcs.get(i) + ") does not contain '" + expectedImage + "'");
        }
    }

    @When("the user closes the comparison popup window")
    public void theUserClosesTheComparisonPopupWindow() {
        mobilePage.closeComparePopupAndSwitchToMain();
    }

    @Then("the comparison popup window should be closed")
    public void theComparisonPopupWindowShouldBeClosed() {
        int windowCount = mobilePage.getOpenWindowCount();
        Assert.assertEquals(windowCount, 1,
                "🚨 ERROR: Comparison popup window is still open! Active windows: " + windowCount);
    }
}