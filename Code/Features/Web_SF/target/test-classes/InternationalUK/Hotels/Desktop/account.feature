@ROW
Feature: My account
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Register new Englishman user on UK site and access my account. RTC-4617
    Given I'm from "United Kingdom" POS
    And   I am a new user
    When  I create an account
    And   I access my account information
    Then  my account information is available
    And   the user information was stored correctly

  @ACCEPTANCE
  Scenario: Register new USA user on UK site and access my account. RTC-4627
    Given I am a new user
    When  I create an account
    And   I access my account information
    Then  my account information is available
    And   the user information was stored correctly


  @ACCEPTANCE
  Scenario: Error on attempt to register with empty fields. RTC-4616
    When  I attempt register new user with blank fields
    Then  I receive immediate "Enter the first name." error message
    Then  I receive immediate "Enter the last name." error message
    Then  I receive immediate "Enter the country." error message
    Then  I receive immediate "Enter a valid email address." error message
    Then  I receive immediate "Password must contain 6-30 characters and no spaces. It is case-sensitive." error message
    Then  I receive immediate "Confirm your password." error message

  @ACCEPTANCE
  Scenario: Error on attempt to sign in with empty fields. RTC-4628
    When  I attempt sign in with blank fields
    Then  I receive immediate "Enter a valid email address." error message
    Then  I receive immediate "Password must contain 6-30 characters and no spaces. It is case-sensitive." error message

  @ACCEPTANCE
  Scenario: Verify redirecting to UHP after sign in. RTC-4629
    Given I have valid credentials
    And   I authenticate myself
    Then  I am return to the UHP on International site from login page

  @ACCEPTANCE
  Scenario: Confirm that the password assistance page is displayed with the email pre-filled. RTC-4630
    Given I am a new user
    And   I create an account
    And   I logout to cookied mode
    When  I try to login with invalid password three times in a row
    Then  I see pre-filled email on password assistance page
