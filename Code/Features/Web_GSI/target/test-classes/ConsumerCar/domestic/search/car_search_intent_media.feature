@US @ACCEPTANCE
Feature: Cars search when intent media code is active
  Owner: Jorge Lopez

  Background:
    Given default dataset
    Given set version test "vt.IMC15" to value "4"
    Given the application is accessible

  Scenario Outline: Intent media Elements instead of D2C module
    Given I'm searching for a car from "<pickUp>" to "<dropOff>"
    When I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    Then I verify Intent Media elements exist


  Examples:
    | pickUp                | dropOff               |
    | Las Vegas, NV - (LAS) | Milwaukee, WI - (MKE) |