package stepDefinitions;

import commons.DriverManager;
import commons.PageGeneratorManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObjects.CartPageObject_Techpanda;
import pageObjects.MobilePageObject_Techpanda;
import io.cucumber.datatable.DataTable;
import io.qameta.allure.Allure;
import java.util.List;
import java.util.Map;

public class CartCouponSteps {
    private MobilePageObject_Techpanda mobilePage;
    private CartPageObject_Techpanda cartPage;

    @When("the user adds Sony Xperia to the shopping cart")
    public void theUserAddsSonyXperiaToTheShoppingCart() {
        mobilePage = PageGeneratorManager.getMobilePageTechPanda(DriverManager.getDriver());
        cartPage = mobilePage.clickAddToCartSonyXperia();
    }

    @Then("the cart success message should contain {string}")
    public void theCartSuccessMessageShouldContain(String expectedMessage) {
        String actualMessage = cartPage.getSuccessMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "🚨 Error: Add-to-cart success message does not match! Actual: " + actualMessage);
    }

    @When("the user updates the cart item quantity to {string}")
    public void theUserUpdatesTheCartItemQuantityTo(String quantity) {
        cartPage.enterQuantity(quantity);
    }

    @And("the user enters coupon code {string} and clicks Apply")
    public void theUserEntersCouponCodeAndClicksApply(String couponCode) {
        cartPage.enterCouponCode(couponCode);
        cartPage.clickApplyCoupon();
    }

    @Then("the discount amount should be {string}")
    public void theDiscountAmountShouldBe(String expectedDiscount) {
        String actualDiscount = cartPage.getDiscountAmountMessage();
        System.out.println("--> [INFO] Discount Amount: " + actualDiscount);
        Assert.assertEquals(actualDiscount, expectedDiscount, "🚨 Error: Discount amount does not match!");
    }

    @And("the grand total should be {string}")
    public void theGrandTotalShouldBe(String expectedGrandTotal) {
        String actualGrandTotal = cartPage.getGrandTotal();
        System.out.println("--> [INFO] Grand Total: " + actualGrandTotal);
        Assert.assertEquals(actualGrandTotal, expectedGrandTotal, "🚨 Error: Grand total does not match!");
    }

    @Then("the cart error message should contain {string}")
    public void theCartErrorMessageShouldContain(String expectedError) {
        String actualError = cartPage.getErrorMessage();
        Assert.assertTrue(actualError.contains(expectedError),
                "🚨 Error: Cart error message does not match! Actual: " + actualError);
    }

    @Then("the item quantity error message should contain {string}")
    public void theItemQuantityErrorMessageShouldContain(String expectedQtyError) {
        String actualQtyError = cartPage.getQuantityErrorMessage();
        Assert.assertTrue(actualQtyError.contains(expectedQtyError),
                "🚨 Error: Quantity error message does not match! Actual: " + actualQtyError);
    }

    @When("the user clicks on the Empty Cart button")
    public void theUserClicksOnTheEmptyCartButton() {
        cartPage.clickEmptyCart();
    }

    @Then("the empty cart message should contain {string}")
    public void theEmptyCartMessageShouldContain(String expectedEmptyMsg) {
        String actualEmptyMsg = cartPage.getEmptyCartMessage();
        Assert.assertTrue(actualEmptyMsg.contains(expectedEmptyMsg),
                "🚨 Error: Empty cart message does not match! Actual: " + actualEmptyMsg);
    }

}