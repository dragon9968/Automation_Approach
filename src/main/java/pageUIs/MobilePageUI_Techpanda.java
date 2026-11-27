package pageUIs;

public class MobilePageUI_Techpanda {
    public static final String MENU_MOBILE = "xpath=//a[text()='Mobile']";
    public static final String MENU_TV = "xpath=//a[text()='TV']";
    public static final String LIST_PAGE_SONY_NAME_LINK = "css=.product-name a[title='Sony Xperia']";
    public static final String LIST_PAGE_SONY_PRICE = "xpath=//li[contains(@class,'item') and .//a[@title='Sony Xperia']]//span[@class='price']";
    public static final String DETAIL_PAGE_SONY_PRICE = "css=span.price";

    // 🌟 BỔ SUNG MỚI TỪ PLAYWRIGHT (Sort & View Mode)
    public static final String SORT_BY_DROPDOWN = "css=.toolbar select[title='Sort By']";
    public static final String VIEW_AS_GRID = "css=.toolbar a[title='Grid']";
    public static final String VIEW_AS_LIST = "css=.toolbar a[title='List']";
    public static final String VIEW_AS_LIST_ACTIVE = "css=.toolbar strong[title='List']";

    public static final String PRODUCT_NAMES = "css=h2.product-name a";
    public static final String PRODUCT_PRICES = "css=.price-box .regular-price .price";
    public static final String LIST_VIEW_PRODUCTS = "css=ol.products-list";
    public static final String PRODUCT_DESC = "css=.products-list .desc.std";

    public static final String ADD_TO_CART_SONY_XPERIA_BUTTON = "xpath=//li[contains(@class,'item') and .//a[@title='Sony Xperia']]//button[@title='Add to Cart']";

    // 🌟 BỔ SUNG CHO TC_08 (COMPARE PRODUCTS)
    public static final String DYNAMIC_ADD_TO_COMPARE_LINK = "xpath=//h2[@class='product-name']/a[@title='%s']/ancestor::div[@class='product-info']//a[@class='link-compare']";
    public static final String COMPARE_SUCCESS_MESSAGE = "css=li.success-msg span";
    public static final String COMPARE_BUTTON = "xpath=//div[contains(@class,'block-compare')]//button[@title='Compare']";

    // Các locator bên trong cửa sổ Popup Compare Products
    public static final String COMPARE_POPUP_HEADING = "css=div.page-title h1";
    public static final String COMPARE_POPUP_PRODUCT_NAMES = "css=table#product_comparison h2.product-name a";
    public static final String COMPARE_POPUP_PRODUCT_PRICES = "css=table#product_comparison tr.product-shop-row span.price";
    public static final String COMPARE_POPUP_PRODUCT_IMAGES = "css=table#product_comparison tr.product-shop-row a.product-image img";
    public static final String COMPARE_POPUP_PRODUCT_SKUS = "xpath=//table[@id='product_comparison']//th[normalize-space()='SKU']/following-sibling::td";
    public static final String COMPARE_POPUP_CLOSE_BUTTON = "css=button[title='Close Window']";
}
