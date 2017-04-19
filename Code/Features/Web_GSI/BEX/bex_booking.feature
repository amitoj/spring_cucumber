@STBL
Feature: BEX - Booking
  Create Expedia Bookings from BEX.
  Owner: Jorge Lopez

  Background:
    Given BEX hotel vertical is accessible

  Scenario Outline: Hotel booking
    Given I want to book hotel for the <Time> future
    And I perform search for hotels near <City>
    And I select hotel from result list
    And I select recommended room
    And I select pay now option
    When I complete Booking as name:<Name>, phone:<Phone>, email:<Email>
    Then I verify that hotel is booked successfully


  Examples:
    | City          | Time | Name     | Phone      | Email                 |
    | San Diego     | far  | Test BEX | 6461608179 | v-jolopez@expedia.com |



