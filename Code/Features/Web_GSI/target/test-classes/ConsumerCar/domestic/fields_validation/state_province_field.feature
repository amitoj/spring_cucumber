@US
Feature: Checking State/Province field
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: RTC-5220
    Given I'm a guest user
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I'm from "<countryOfResidence>" and living in "<state>"
    And I request quotes
    Then I see a non-empty list of search results
    And I request insurance
    And I choose a retail car
    And I fill "<postalCode>" in postal code field
    And I purchase as guest a quote
    Then I receive immediate confirmation

  Examples:
    | countryOfResidence | state | postalCode |
    | Canada             | AB    | A3B6C5     |
    | United States      | AK    | 12345      |
    | Australia          | NSW   | 12345      |

  Scenario: RTC-5220 - state as an input field, possible to type invalid values
    Given I'm a guest user
    And I'm searching for a car in "AIRPORT"
    And I want to travel between 10 days from now and 16 days from now
    And I'm from "U.K." and living in "XX"
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    And I purchase as guest a quote
    Then I receive immediate confirmation
