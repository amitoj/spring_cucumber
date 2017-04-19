@API @DistributedOpaque
Feature: Hotel Api Booking.

  Background:
    Given I am Private API user

  @ACCEPTANCE @STBL
  Scenario: RTC - 4021/4037. Hotel Search, Book and Status Check
    Given I send request to search hotels in Las Vegas
    Then I get search results
    When I store first solution details
    And I request solutions availability
    Then I get solutions details
    When I book it
    Then I get confirmation response
    When I check the status of purchase
    Then I see that it was booked successfully