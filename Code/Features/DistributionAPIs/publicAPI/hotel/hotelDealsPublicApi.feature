@API @DistributedOpaque
Feature: Public API Hotel Deals Verification.

  Background:
    Given I am Public API user

  @LIMITED @JANKY
  Scenario: Hotel Deals - RTC-3932
    #Janky because we do not have deals on dev env
    Given I request deals for 3.5 star hotels
    Then I get hotel deals

