@US @simulator
Feature: Testing deposit filtering when no one car is available for selected type of deposit.
  Owner: Vyacheslav Zyryanov
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: No results for local user with debit card. RTC-6701
    Given I'm searching for a car in "Muncie, IN - (MIE)"
    And I want to travel between 6 days from now and 7 days from now
    And I request quotes
    Then I see a non-empty list of search results
    When I select DEBIT card option
    Then I see a non-empty list of search results
    When I choose "I_AM_LOCAL" option for debit card
    Then I see a empty list of search results
    When I select CREDIT card option
    Then I see a non-empty list of search results

