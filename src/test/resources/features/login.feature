@login
Feature: TechPanda Account Login

  Background:
    Given the user is on the TechPanda homepage
    When the user navigates to the Login page
  
 
  Scenario: Login successfully with valid credentials
    When  the user enters the following login credentials:
      | email                     | password |
      | long_tester_pro@gmail.com | 123456   |
    And the user clicks the Login button

    
    Scenario: Login successfully via API inside browser
    When the user performs login action via API with the following credentials:
      | email                     | password |
      | long_tester_pro@gmail.com | 123456   |
    Then the user should be redirected to the Dashboard page