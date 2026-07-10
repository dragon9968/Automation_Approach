@mobile @use_session
Feature: Mobile Product Feature

  Scenario: Verify that cost of product in list page and details page are equal
    # --- ĐÂY LÀ CÁC BƯỚC MỚI CỦA TRANG MOBILE ---
    And the user navigates to the Mobile list page cleanly
    And the user notes the price of Sony Xperia from the list page
    And the user clicks on the Sony Xperia product details
    Then the price of Sony Xperia on the detail page should be equal to the price on the list page