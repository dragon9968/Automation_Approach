@login
Feature: TechPanda Account Login

  Background:
    Given the user is on the TechPanda homepage
    When the user navigates to the Login page


  Scenario: TC_01_Login_Successfully_With_Valid_Credentials
    When the user enters the following login credentials:
      | email                     | password |
      | long_tester_pro@gmail.com | 123456   |
    And the user clicks the Login button
    Then the user verifies page title is "My Account", URL contains "customer/account" and welcome message contains "Long Dinh Nguyen"


  Scenario: TC_02_Login_With_Empty_Email_And_Password
    When the user enters the following login credentials:
      | email | password |
      |       |          |
    And the user clicks the Login button
    Then field validation error "This is a required field." should appear at email textbox
    And field validation error "This is a required field." should appear at password textbox


  Scenario: TC_03_Login_With_Invalid_Email_Format
    When the user enters the following login credentials:
      | email        | password |
      | invalidemail | 123456   |
    And the user clicks the Login button
    Then Email error message is displayed at Login page "Please include an '@' in the email address. 'invalidemail' is missing an '@'."


  Scenario: TC_04_Login_With_Invalid_Email_Format
    When the user enters the following login credentials:
      | email        | password |
      | invalidemail@evizi | 123456   |
    And the user clicks the Login button
    Then field validation error "Please enter a valid email address. For example johndoe@domain.com." should appear at email textbox

  Scenario: TC_05_Login_With_Password_Less_Than_6_Characters
    When the user enters the following login credentials:
      | email                     | password |
      | long_tester_pro@gmail.com | 12345    |
    And the user clicks the Login button
    Then field validation error "Please enter 6 or more characters without leading or trailing spaces." should appear at password textbox

  Scenario: TC_06_Login_With_Incorrect_Credentials
    When the user enters the following login credentials:
      | email                     | password      |
      | long_tester_pro@gmail.com | wrongpassword |
    And the user clicks the Login button
    Then the global error message "Invalid login or password." should appear