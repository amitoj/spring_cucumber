@US @ACCEPTANCE
Feature: Cars - Search - Date tests
  RTC: 29, 30, 31, 32
  Owner: Vyacheslav Zyryanov

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: 29..32 | Verify search field's validation
    And I am on car <pageType> page
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I request quotes
    Then I receive immediate "<error>" error message
    When I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results

  Examples: car search options
    | #   | location | pageType  | startDateShift     | endDateShift        | error                |
    | 29h | SFO      | index     | 340 days from now  | 5 days from now     | Please enter new dates. Cars can only be reserved within the next 330 days.    |
    | 29  | SFO      | landing   | 340 days from now  | 5 days from now     | Please enter new dates. Cars can only be reserved within the next 330 days.    |
    | 30h | FLO      | index     | 5 days from now    | 345 days from now   | Please enter new dates. Cars can only be reserved within the next 330 days.    |
    | 30  | FLO      | landing   | 5 days from now    | 345 days from now   | Please enter new dates. Cars can only be reserved within the next 330 days.    |
    | 31h | LAX      | index     | 12 days from now   | 5 days from now     | Drop off date is not valid. The drop off date must be after the pick up date.  |
    | 31  | LAX      | landing   | 12 days from now   | 5 days from now     | Drop off date is not valid. The drop off date must be after the pick up date.  |
    | 32h | SAN      | index     | 7 days from now    | 70 days from now    | Drop off date is not valid. The drop off date must be within 60 days of your pick up date.  |
    | 32  | SAN      | landing   | 7 days from now    | 70 days from now    | Drop off date is not valid. The drop off date must be within 60 days of your pick up date.  |

#Next scenarios were added by B. Shukaylo
  @ACCEPTANCE
  Scenario Outline: RTC-27 RTC-28 Default Drop-off/Pick-up date for page display
    When I am on car <pageType> page
    Then I see default dates in FareFinder

  Examples:
    | #       | pageType |
    | 27+28   | index    |
    | 27h+28h | landing  |
