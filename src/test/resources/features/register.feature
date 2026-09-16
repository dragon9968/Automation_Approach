@register
Feature: TechPanda Account Registration

  Background:
    Given the user is on the TechPanda homepage
    When the user navigates to the Registration page
  #@empty_data
  Scenario: Register with empty data
    When the user clicks the Register button
    Then Firstname error message is displayed "This is a required field."
    And Lastname error message is displayed "This is a required field"
    And Email error message is displayed "This is a required field."
    And Password error message is displayed "This is a required field."
    And Confirm Password error message is displayed "This is a required field."
  #@invalid_email
  Scenario: Register with invalid email
    When the user enters the following registration details:
      | firstName | middleName | lastName | email    | password | confirmPassword |
      | Long      |            | Nguyen   | longmail | 123456   | 123456          |
    And the user clicks the Register button
    Then Email error message is displayed "Please include an '@' in the email address. 'longmail' is missisng an '@'."

  Scenario: Register with existing email
    When the user enters the following registration details:
      | firstName | middleName | lastName | email                     | password | confirmPassword |
      | Long      | Dinh       | Nguyen   | long_tester_pro@gmail.com | 123456   | 123456          |
    And the user clicks the Register button
    Then Existing email error message is displayed "There is already an account"

  Scenario: Register with confirm password not match
    When the user enters the following registration details:
      | firstName | middleName | lastName | email        | password | confirmPassword |
      | Long      | Dinh       | Nguyen   | random_email | 123456   | 123457          |
    And the user clicks the Register button
    Then Confirm Password error message not match is displayed "Please make sure your passwords match."

  Scenario: Register with a password less than 6 characters
    When the user enters the following registration details:
      | firstName | middleName | lastName | email        | password | confirmPassword |
      | long      | dinh       | nguyen   | random_email | 123      | 123             |
    And the user clicks the Register button
    Then the system displays a password error message: "Please enter 6 or more characters without leading or trailing spaces."

  Scenario: Register a new account successfully with valid information
    When the user enters the following registration details:
      | firstName | middleName | lastName | email        | password | confirmPassword |
      | long      | dinh       | nguyen   | random_email | 123456   | 123456          |
    And the user selects the Sign Up for Newsletter checkbox
    And the user clicks the Register button
    Then the system displays a registration success message: "Thank you for registering with Main Website Store."
    And the user logs out of the system
