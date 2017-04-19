@US
Feature: Checking vendor grid on car details page
  Owner: Vyacheslav Zyryanov
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: Verify vendor grid is sorted by total price and offer with lowest price is checked by default. RTC-6837
    Given I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a retail car
    And Result with lowest total price should be checked by default
    Then Results in the vendor grid are sorted by total price
    Then I switch between retail solutions and car's details has updated according to the vendor grid

