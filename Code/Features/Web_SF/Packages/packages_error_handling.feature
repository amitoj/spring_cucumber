@US
Feature: Vacations error handling
  Owner: Cristian Gonzalez Robles

  Background: 
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario Outline: RTC-726:Error Handling (AHC & AH)
    Given I'm a guest user
    And I'm searching for "<package>" vacation from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I will travel between "<startAnytime>" session and "<endAnytime>" session in vacation
    And I want <numberOfHotelRooms> room(s) in vacation
    And I will be traveling with <numberOfAdults> adults in vacation
    And I will be traveling with <numberOfSeniors> seniors in vacation
    And I will be traveling with "<numberOfChildren>" children with "<childAge>" years in vacation
    And I request quotes
    Then Confirm the following error is received: "<errorMessage>"

    Examples: quotable fares parameters
      | package | fromLocation | toLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime | errorMessage                                                                  |
      | AHC     | SFO          | Memphis    | 22             | 29           | 1                  | 3              | 1                | 6               | 4        | Morning      | Evening    | The maximum number of travelers allowed is 6.                                 |
      | AHC     | SFO          | SFO        | 22             | 29           | 1                  | 2              | 1                | 2               | 4        | Morning      | Evening    | The origin and destination cities cannot be the same.                         |
      | AHC     | SFO          | Memphis    | 22             | 22           | 1                  | 2              | 1                | 2               | 4        | Morning      | Evening    | Packages require a 1-night minimum stay.                                      |
      | AHC     | SFO          | Memphis    | 331            | 339          | 1                  | 2              | 1                | 2               | 4        | Morning      | Evening    | Please enter new dates. Packages can only be booked within the next 330 days. |
      | AH      | SFO          | Memphis    | 22             | 29           | 1                  | 3              | 1                | 6               | 4        | Morning      | Evening    | The maximum number of travelers allowed is 6.                                 |
      | AH      | SFO          | SFO        | 22             | 29           | 1                  | 2              | 1                | 2               | 4        | Morning      | Evening    | The origin and destination cities cannot be the same.                         |
      | AH      | SFO          | Memphis    | 22             | 22           | 1                  | 2              | 1                | 2               | 4        | Morning      | Evening    | Packages require a 1-night minimum stay.                                      |
      | AH      | SFO          | Memphis    | 331            | 339          | 1                  | 2              | 1                | 2               | 4        | Morning      | Evening    | Please enter new dates. Packages can only be booked within the next 330 days. |

  @ACCEPTANCE
  Scenario Outline: RTC-726:Error Handling (HC)
    Given I'm a guest user
    And I'm searching for a <package> package to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I will travel between "<startAnytime>" session and "<endAnytime>" session in vacation
    And I want <numberOfHotelRooms> room(s) in vacation
    And I will be traveling with <numberOfAdults> adults in vacation
    And I will be traveling with <numberOfSeniors> seniors in vacation
    And I will be traveling with "<numberOfChildren>" children with "<childAge>" years in vacation
    And I request quotes
    Then Confirm the following error is received: "<errorMessage>"

    Examples: quotable fares parameters
      | package | toLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime | errorMessage                                                                  |
      | HC      | Memphis    | 22             | 29           | 1                  | 3              | 1                | 6               | 4        | 12:00 PM     | 2:00 PM    | The maximum number of travelers allowed is 6.                                 |
      | HC      | Memphis    | 22             | 22           | 1                  | 2              | 1                | 2               | 4        | 12:00 PM     | 2:00 PM    | Packages require a 1-night minimum stay.                                      |
      | HC      | Memphis    | 331            | 339          | 1                  | 2              | 1                | 2               | 4        | 12:00 PM     | 2:00 PM    | Please enter new dates. Packages can only be booked within the next 330 days. |
