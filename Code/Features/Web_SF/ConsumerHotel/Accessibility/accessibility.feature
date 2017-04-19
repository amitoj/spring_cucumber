@US @ROW
Feature: Hotel accessibility purchase
  As a user I would like to be presented with accessibility options for a hotel.

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE @CLUSTER3
  Scenario Outline: Filter results by hotel features.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I request quotes
    And I choose <resultstype> hotels tab on results
    And I want to filter results by Amenities
    When I choose the recommended Accessibility filter value
    Then I will see that all results will have the recommended Accessibility filter value

  Examples:
    | destinationLocation  | startDateShift | endDateShift | numberOfHotelRooms | resultstype |
    | Irvine, California   | 21             | 26           | 1                  | opaque      |