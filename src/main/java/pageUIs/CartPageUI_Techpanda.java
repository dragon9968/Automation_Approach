package pageUIs;

public class CartPageUI_Techpanda {
    public static final String PROCEED_TO_CHECKOUT = "xpath=//ul[@class='checkout-types top']//button[@title='Proceed to Checkout']";
    public static final String SUCCESS_MESSAGE = "css=li.success-msg span";
    public static final String ERROR_MESSAGE = "css=li.error-msg span";
    public static final String ERROR_QTY_MESSAGE = "css=p.item-msg";
    public static final String COUPON_INPUT = "css=#coupon_code";
    public static final String APPLY_COUPON_BUTTON = "css=button[value='Apply']";

    // Chuyển từ tr:has-text(...) của Playwright sang XPath chuẩn cho Selenium
    public static final String DISCOUNT_TEXT = "xpath=//tr[contains(.,'Discount (GURU50)')]/td[last()]//span";
    public static final String GRAND_TOTAL_TEXT = "xpath=//tr[contains(.,'Grand Total')]/td[last()]//span";

    public static final String QTY_INPUT = "css=input[title='Qty']";
    public static final String UPDATE_BUTTON = "css=button[title='Update']";
    public static final String EMPTY_CART_BUTTON = "css=button[title='Empty Cart']";
    public static final String EMPTY_CART_TEXT = "css=.cart-empty p:first-of-type";
}