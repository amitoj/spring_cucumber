@US
Feature: Xnet UI regression test

  Scenario Outline: Search and purchase a XNET hotel.
    Given the application is accessible
    Given I have valid credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <adults> adults
    And I request quotes
    When I select the <neighborhoodName> neighborhood with <starRating> star rating and CRS <CRS Ref>
    And I book that hotel
    And I complete the booking with saved user account
    Then <room_type> room is successfully booked for <base_amount> dollars

    Examples: opaque quotable fares parameters
      | destinationLocation     | numberOfHotelRooms | adults | startDateShift | endDateShift | resultstype | neighborhoodName               | starRating | CRS Ref | room_type | base_amount |
      | Killington, Vermont     | 1                  | 3      | 5              | 6            | opaque      | Killington - Mendon area hotel | 3          | XMN     | STANDARD  | 40          |
      | Killington, Vermont     | 1                  | 2      | 5              | 6            | opaque      | Killington - Mendon area hotel | 3          | XMN     | STD KING  | 34          |
      | Ottawa, Ontario, Canada | 1                  | 1      | 6              | 7            | opaque      | Downtown Ottawa area hotel     | 3          | XMN     | ONE p     | 50          |
