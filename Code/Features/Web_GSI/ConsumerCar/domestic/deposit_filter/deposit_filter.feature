@US
Feature: Checking deposit filter on car results page
  Owner: Vyacheslav Zyryanov
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: Deposit filter is working for one-way airport trip. RTC-6857
    Given I'm searching for a car from "San Francisco, CA - (SFO)" to "New York City, NY - (LGA)"
    And I want to travel between 15 days from now and 27 days from now
    When I request quotes
    Then I see a non-empty list of search results
    Then I see Deposit filtering block

  Scenario: Deposit filter is working for airport round trip. RTC-6857
    Given I'm searching for a car in "Los Angeles, CA - (LAX)"
    And I want to travel between 7 days from now and 14 days from now
    When I request quotes
    Then I see a non-empty list of search results
    Then I see Deposit filtering block

  Scenario: Check that travel type for debit card stay the same after filtering. RTC-6857
    Given I'm searching for a car in "Los Angeles, CA - (LAX)"
    And I want to travel between 7 days from now and 14 days from now
    When I request quotes
    Then I see a non-empty list of search results
    When I select DEBIT card option
    Then Deposit type "I_AM_TRAVELING" is selected
    Then I choose "I_AM_LOCAL" option for debit card
    Then I see a non-empty list of search results
    Then The cheapest result is first
    Then Deposit type "I_AM_LOCAL" is selected
