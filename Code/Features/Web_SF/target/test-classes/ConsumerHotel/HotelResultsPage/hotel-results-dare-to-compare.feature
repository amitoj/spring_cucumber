@US
Feature: Hotel Results D2C Module
    Intent media dare to compare hotel results module.

  Background:
    Given default dataset
    Given the application is accessible

  Scenario Outline: Verify dare to compare module
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose <resultstype> hotels tab on results
    Then The dare to compare module will exist

  Examples:
    | destinationLocation | startDateShift | endDateShift | resultstype |
    | San Francisco, CA   | 6              | 8            | opaque      |
    | San Francisco, CA   | 6              | 8            | retail      |

  @ACCEPTANCE @STBL
  Scenario Outline: Verify that D2C module leads to the partner sites opened with correct search parameters.RTC-4831, 4833
    Given I'm searching for a hotel in "San Francisco"
    And I want to travel in the near future
    And I request quotes
    When I choose opaque hotels tab on results
    Then The dare to compare module will exist
    When I compare hotel results with next partners "<partnersList>" for <numberOfD2CModule> D2C module
    Then I verify next hotel partners "<partnersList>"
    Then I check partners "<partnersList>" in click tracking table in DB

  Examples:
    | partnersList    |  numberOfD2CModule|
    | Trivago, Hotels |   first           |
    | Trivago, Hotels |   second          |
