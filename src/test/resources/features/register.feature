Feature: TechPanda Account Registration

  Scenario: Register with a password less than 6 characters
    Given the user is on the TechPanda homepage
    When the user navigates to the Registration page
    And the user enters the following registration details:
      | firstName | middleName | lastName | email        | password | confirmPassword |
      | long      | dinh       | nguyen   | random_email | 123   | 123         |
    And the user clicks the Register button
    Then the system displays a password error message: "Please enter 6 or more characters without leading or trailing spaces."

  Scenario: Register a new account successfully with valid information
    Given the user is on the TechPanda homepage
    When the user navigates to the Registration page
    And the user enters the following registration details:
      | firstName | middleName | lastName | email        | password | confirmPassword |
      | long      | dinh       | nguyen   | random_email | 123456   | 123456          |
    And the user selects the Sign Up for Newsletter checkbox
    And the user clicks the Register button
    Then the system displays a registration success message: "Thank you for registering with Main Website Store."
    And the user logs out of the system