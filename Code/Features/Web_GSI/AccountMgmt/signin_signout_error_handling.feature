@US
Feature: Testing sign in and sign out  error handling functionality
  Regression Testing / Site - GSI / Account management / Sign In/Sign out
  Owner: Cristian Gonzalez Robles

  Background: 
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario Outline: Sign In module - Error Handling
    Given my name is <user> and my password is <pass>
    And I navigate to sign in page
    And I try to sign in
    Then Confirm the sign in error handling: "<errorMessage>"

    Examples: 
      | user                | pass      | errorMessage                                                               |
      |                     |           | Enter a valid email address.                                               |
      | account@hotwire.com | badpass   | The email address or password is incorrect.                                |
      | &nvalidEmail@format | wrongpass | Enter a valid email address.                                               |
      | account@hotwire.com | one       | Password must contain 6-30 characters and no spaces. It is case-sensitive. |
      | account@hotwire.com |           | Password must contain 6-30 characters and no spaces. It is case-sensitive. |
      |                     | mypass    | Enter a valid email address.                                               |
