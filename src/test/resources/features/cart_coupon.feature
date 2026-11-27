@cart_coupon
Feature: Cart & Coupon Code Feature

  Background:
    Given the user is on the TechPanda homepage
    When the user navigates to the Mobile list page cleanly

  Scenario: TC_05_Verify_Discount_Coupon_Works_Correctly
    When the user adds Sony Xperia to the shopping cart
    Then the cart success message should contain "Sony Xperia was added to your shopping cart."
    When the user updates the cart item quantity to "1"
    And the user enters coupon code "GURU50" and clicks Apply
    Then the discount amount should be "-$5.00"
    And the grand total should be "$100.00"

  Scenario: TC_06_Verify_Error_Message_For_Invalid_Coupon_Code
    When the user adds Sony Xperia to the shopping cart
    And the user enters coupon code "INVALID" and clicks Apply
    Then the cart error message should contain "Coupon code \"INVALID\" is not valid."

  Scenario: TC_07_Verify_Error_Message_For_Invalid_Quantity
    When the user adds Sony Xperia to the shopping cart
    And the user updates the cart item quantity to "501"
    Then the item quantity error message should contain "The maximum quantity allowed for purchase is 500."
    And the cart error message should contain "Some of the products cannot be ordered in requested quantity."
    When the user clicks on the Empty Cart button
    Then the empty cart message should contain "You have no items in your shopping cart."

