@US @ACCEPTANCE
Feature: Cars - Search - Time tests
  RTC: 24
  Owner: Vyacheslav Zyryanov

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: RTC-24  Pick-up time after Drop-off time for same day for car rental
    And I am on car <pageType> page
    And I'm searching for a car in "<location>"
    And I want to travel between <dateShift> and <dateShift>
    And I want to pick up at 3:00pm and drop off at 2:00pm
    And I request quotes
    Then I receive immediate "Drop off time must be after the pick up time for same-day rentals." error message
    And I want to pick up at 3:00pm and drop off at 5:00pm
    And I request quotes
    Then I see a non-empty list of search results

  Examples: car search options
    | #   | location | pageType | dateShift        |
    | 24h | SFO      | index    | 5 days from now  |
    | 24  | SFO      | landing  | 5 days from now  |

  @ACCEPTANCE
    Scenario Outline: RTC-22 RTC-23 Time test - Confirm pull downs are populating
      And I am on car landing page
      And I'm searching for a car in "SFO"
      And I want to travel in the near future
      And I want to verify list of <typeOfTime> times in Farefinder
      Examples:
      |typeOfTime|
      |pick up   |
      |drop off  |
