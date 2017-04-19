@ROW @JANKY
Feature: Customers from Singapore site should be notified about additional mandatory fees and taxes.
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  @MAP
  Scenario: Disclaimer module on mapped results
    Given I'm from "Singapore" POS
    And I am notified about additional taxes and fees
    When I'm searching for a hotel in "Paris, France"
    And I request quotes
    Then I am notified about additional taxes and fees
    When I choose a hotel result
    Then I am notified about additional taxes and fees