@US
Feature: Package (A+C)

  Background:
    Given default dataset
    Given the application is accessible

  @CLUSTER3
  Scenario Outline: Search for a package with Air+Car as a guest user.
    Given I'm a guest user
    Given I see Air Plus Car package
    And I'm searching for "<package>" vacation from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose retail flight with rental car as package with out complete purchase

  Examples: quotable fares parameters
    | package | fromLocation | toLocation | startDateShift | endDateShift |
    | AC      | LAS          | JFK        | 22             | 29           |