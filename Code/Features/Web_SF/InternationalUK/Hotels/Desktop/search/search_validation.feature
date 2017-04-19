@ROW @SEARCH
Feature: Hotel search and purchase tracking
  Let analyst track user activity during hotel search.
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  Scenario: Unknown destination tracking.
    Given I'm searching for a hotel in "Kopaskerrrr, Iceland"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    Then I see unknown destination validation error on location

  Scenario: get validation error when destination is not specified
    Given I'm from "United Kingdom" POS
    When I start hotel search without specifying the destination
    Then I see empty destination validation error on location