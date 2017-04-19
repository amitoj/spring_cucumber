@US @ACCEPTANCE
Feature: Help center on Cars vertical
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: verify that there is help center module on the car billing page
    Given I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    When I request quotes
    And I see a non-empty list of search results
    And I choose a car
    Then customer care module is displayed on billing





