@US @STBL
Feature: My account
  This feature contains some my account checks from Silk CI suite
  Owner: Boris Shukaylo

  @ACCEPTANCE
  Scenario: RTC-1584 Update your traveler information
    Given I have valid random credentials
    And I am on home page
    When I authenticate myself
    And I access my account information
    And I manage Traveler Names
    And I update first traveler's info

  @ACCEPTANCE
  Scenario: RTC-1585 Verify that the signed in email address is correct on Account overview page
    Given I have valid random credentials
    And I am on home page
    When I authenticate myself
    And I access my account information
    And I verify that email on Account Overview is correct
