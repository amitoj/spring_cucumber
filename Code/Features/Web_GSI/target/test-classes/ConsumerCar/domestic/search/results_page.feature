@US @SingleThreaded
Feature: Car - Results Page
  RTC: 4362
  Owner: Vyacheslav Zyryanov

  Background:
    Given default dataset
    Given activate car's version tests

  @ACCEPTANCE
  Scenario: There should be no search date min reduction for internat RTC-4631
    Given I'm a guest user
    And I am on home page
    And I'm searching for a car in "London, United Kingdom"
    And I want to travel between 1 days from now and 4 days from now
    And I want to pick up with 1 hours shift from now
    And I request quotes
    And I see a non-empty list of search results
    And I am on home page
    And I'm searching for a car in "Montreal, Canada"
    And I want to travel between 1 days from now and 4 days from now
    And I want to pick up with 1 hours shift from now
    And I request quotes
    And I see a non-empty list of search results

  @ACCEPTANCE
  Scenario: RTC-1083 "Search again" is clicked with no changes
    Given the application is accessible
    And I am on home page
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request quotes
    And I verify that car results page didn't change after searching again
