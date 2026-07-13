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
    Then the user verifies page title is "My Account", URL contains "customer/account" and welcome message contains "Long Dinh Nguyen"
