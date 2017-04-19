Feature: Last minute Hotels
  Last minute are available to Last minute Aware visitors.
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  Scenario: Last minute Hotels are available in top destinations.
    Given I'm from "United Kingdom" POS
    When I access Last minute Hotels in "Paris"
    Then the Last minute Hotels are available