Feature: Intl Hotel Results Trip Advisor
    Trip advisor module for intl hotel results pages only.

  Background:
    Given default dataset
    Given the application is accessible

  Scenario Outline: Trip Advisor module on results. Intl site only.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    When I choose <resultstype> hotels tab on results
    Then I <state> see trip advisor ratings in search results

  Examples:
    | destinationLocation       | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | resultstype | state    |
    | San Francisco, CA - (SFO) | 3              |  5           | 1                  | 2              | 0                | opaque      | will     |
    | San Francisco, CA         | 3              |  5           | 1                  | 2              | 0                | retail      | will     |
