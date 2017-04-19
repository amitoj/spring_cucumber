@US @ACCEPTANCE
Feature: Legal copies and disclaimers on car desktop site
  Owner: Nataliya Golodiuk

  Background:
    Given activate car's version tests
    And the application is accessible

  Scenario Outline: verify rate-related copies on car results
    Given I'm searching for a car from "<fromLocation>" to "<toLocation>"
    And I want to travel in the near future
    When I request quotes
    Then I see copies appropriate to the search results

  Examples:
    | fromLocation | toLocation |
    | SFO          | SFO        |
    | LGA          | SFO        |

  Scenario Outline: verify rate-related copies on car details, airport and local search
    Given I'm searching for a car in "<location>"
    And I want to travel in the near future
    When I request quotes
    And I see a non-empty list of search results
    And I choose a <carType> car
    Then I see appropriate copies on details

  Examples:
    | carType | location          |
    | opaque  | SFO               |
    | retail  | SFO               |
    | retail  | New York City, NY |
