@mobile
Feature: Mobile Product Feature

  Background:
    Given the user is on the TechPanda homepage
    When the user navigates to the Mobile list page cleanly

  Scenario: TC_01_Verify_Products_Are_Sorted_By_Name_A_To_Z
    When the user selects sort by "Name"
    Then the products should be sorted by Name in ascending order

  Scenario: TC_02_Verify_Products_Are_Sorted_By_Price_Low_To_High
    When the user selects sort by "Price"
    Then the products should be sorted by Price from Low to High

  Scenario: TC_03_Verify_View_As_List_Layout
    When the user clicks on the List view icon
    Then the mobile page URL should contain "mode=list"
    And the Mobile page should display in List view layout properly

  Scenario: TC_04_Verify_that_cost_of_product_in_list_page_and_details_page_are_equal
    And the user notes the price of Sony Xperia from the list page
    And the user clicks on the Sony Xperia product details
    Then the price of Sony Xperia on the detail page should be equal to the price on the list page

  Scenario: TC_08_Verify_That_You_Are_Able_To_Compare_Two_Products
    When the user clicks Add to Compare for "Sony Xperia"
    Then the comparison success message "The product Sony Xperia has been added to comparison list." should be displayed
    When the user clicks Add to Compare for "IPhone"
    Then the comparison success message "The product IPhone has been added to comparison list." should be displayed
    When the user clicks on the Compare button to open the comparison popup
    Then the comparison popup should display heading "COMPARE PRODUCTS" with selected products:
      | productName | price   | sku     | image      |
      | Sony Xperia | $100.00 | MOB001  | xperia.jpg |
      | IPhone      | $500.00 | MOB0002 | iphone.png |
    When the user closes the comparison popup window
    Then the comparison popup window should be closed