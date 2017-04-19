@NO_PREPROD
Feature: Deals

  Background:
    Given default dataset
    Given the application is accessible


  @US @CLUSTER2 @CLUSTER3
  Scenario: RNT-29: Hotel deals on home page
    When I select one of the hotel deals
    Then I will see opaque results page



  @US @ROW @CLUSTER2 @CLUSTER3
  Scenario: Hotel deals on the hotel landing page.
    Given I want to go to the Hotels landing page
    When I select one of the hotel deals
    Then I will see opaque results page


