@US
Feature: Cars - Search - Search cashing

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE @JANKY
  Scenario Outline: Search cashing for car RTC-1000, RTC-1001, RTC-1002
    When    I'm searching for a car in "<location>"
    And     I want to travel between <startDateShift> and <endDateShift>
    And     I want to pick up at <pickUpTime> and drop off at <dropOffTime>
    And     I request quotes
    And     I see a non-empty list of search results
    And     I note search_id for car results
    When    I am on car landing page
    And     I'm searching for a car in "<location>"
    And     I want to travel between <newStartDateShift> and <newEndDateShift>
    And     I want to pick up at <newPickUpTime> and drop off at <newPropOffTime>
    And     I request quotes
    And     I see a non-empty list of search results
    Then    search_id must be different from the previous result

  Examples: car search options
    | RTC  | location | startDateShift  | endDateShift    | pickUpTime | dropOffTime | newStartDateShift | newEndDateShift  | newPickUpTime | newPropOffTime |
    | 1000 | SFO      | 5 days from now | 7 days from now | 3:00pm     | 2:00pm      | 9 days from now   | 12 days from now | 3:00pm        | 2:00pm         |
    | 1001 | SFO      | 5 days from now | 7 days from now | 3:00pm     | 2:00pm      | 5 days from now   | 7 days from now  | 4:00pm        | 9:00pm         |
    | 1002 | SFO      | 5 days from now | 7 days from now | 3:00pm     | 2:00pm      | 9 days from now   | 12 days from now | 5:00pm        | 7:00pm         |

  @ACCEPTANCE @JANKY
  Scenario: Live Car Search - confirm db shows results not from cache. RTC-885
    Given   I am a new user
    Given   I create an account
    When    I am on home page
    When    I'm searching for a car in "SFO"
    And     I want to travel in the near future
    And     I request quotes
    And     I see a non-empty list of search results
    And     I verify car results not from cache

