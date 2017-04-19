@US @ACCEPTANCE @JANKY
Feature: Meso Banners on Car Results page.
  Owner: Jorge Lopez

  Background:
    Given default dataset
    Given set version test "vt.CRM15" to value "2"
    Given the application is accessible

  Scenario Outline: Intent media Elements instead of D2C module
    Given I'm searching for a car from "<pickUp>" to "<dropOff>"
    When I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    Then I verify MeSo Banners exist on car results page


  Examples:
    | pickUp                | dropOff               |
    | Las Vegas, NV - (LAS) | Milwaukee, WI - (MKE) |