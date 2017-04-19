@US
Feature: Local search vendor grid. Checking vendor grid behavior for local search results.
  Owner: Oleksandr Zelenov
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: Checking Shuttle to Vendor car feature for local search
    Given I'm searching for a car in "CITY"
    And I want to travel in the near future
    When I request quotes
    Then I see a non-empty list of search results
    And I don't see On Airport - Shuttle to Vendor in car features list

 #
 Scenario: Checking map behaviour for local search
    Given I'm searching for a car in "CITY"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a retail car
    And I see map in vendor grid

  @simulator
  Scenario Outline: Checking airport result in vendor grid availability
    And I'm searching for a car in "CITY"
    And I want to travel between <startDays> and <endDays>
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a <result> car
    And I see airport result in vendor grid

  Examples:
    | result | startDays       | endDays          |
    | opaque | 7 days from now | 10 days from now |
    | retail | 7 days from now | 10 days from now |

