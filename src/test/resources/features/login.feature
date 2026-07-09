Feature: TechPanda Account Login

  Scenario: Login successfully with valid credentials
    Given the user is on the TechPanda homepage
    When the user navigates to the Login page
    And the user enters the following login credentials:
      | email                    | password |
      | anh_tester_pro@gmail.com | 123456   |
    And the user clicks the Login button