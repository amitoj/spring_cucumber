@US @ACCEPTANCE
Feature: Scenarios with registration page checks from Silktest regression suite
  Owner: Boris Shukaylo

  @ACCEPTANCE
  Scenario: RTC-394 Getting to page
    Given I am a new user
    And I am on home page
    And I go to register and set country
    And I verify registration page URL

  @ACCEPTANCE
  Scenario: RTC-398 Successful registration
    Given I am a new user
    And I am on home page
    And I create an account and stay on this page

  @MOBILE @ACCEPTANCE
  Scenario: RTC-398 Successful mWeb registration
  #author v-ngolodiuk
    Given I am a new user
    And I am on home page
    And I create an account
    Then the new user is created

