package stepDefinitions;

import commons.PageGeneratorManager;
import commons.DriverManager;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pageObjects.MobilePageObject_Techpanda;

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
        System.out.println("--> [INFO] Giá ở trang List: " + priceInList);
    }

    @When("the user clicks on the Sony Xperia product details")
    public void theUserClicksOnTheSonyXperiaProductDetails() {
        mobilePage.clickSonyXperiaDetail();
    }

    @Then("the price of Sony Xperia on the detail page should be equal to the price on the list page")
    public void thePriceOfSonyXperiaOnTheDetailPageShouldBeEqualToThePriceOnTheListPage() {
        String priceInDetail = mobilePage.getSonyXperiaPriceInDetail();
        System.out.println("--> [INFO] Giá ở trang Detail: " + priceInDetail);

        // Tiến hành so sánh chuỗi tương tự như lệnh expect bên Playwright của anh
        Assert.assertEquals(
            priceInDetail, 
            this.priceInList, 
            "LỖI: Giá sản phẩm giữa trang danh sách và trang chi tiết không khớp nhau!"
        );
        
        System.out.println("=== XÁC THỰC THÀNH CÔNG: Giá hai trang khớp nhau 100%! ===");
    }
}