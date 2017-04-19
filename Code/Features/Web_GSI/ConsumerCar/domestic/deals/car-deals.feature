@NO_PREPROD
Feature: Deals

  Background:
    Given default dataset
    Given the application is accessible


  @US @CLUSTER3 @CLUSTER4
  Scenario: RNT-30: Deals on Car landing page
    Given I want to find car deals from landing page
    Then I will see the car deal from the landing page
