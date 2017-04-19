@API @DistributedOpaque
Feature: Public/Private Hotel API Refund.

  Background:
    Given I am Private API user

  @ACCEPTANCE @STBL
  Scenario: RTC - 4022/4037: Hotel Search, Book, Cancel and Status Check
    #Prebooking
    Given I send request to search hotels in New York
    Then I get search results
    When I store first solution details
    And I request solutions availability
    Then I get solutions details
    When I book it
    Then I get confirmation response
    When I check the status of purchase
    Then I see that it was booked successfully
    #Refund with cancellation
    When I refund it
    And I check the status of purchase
    Then I see the purchase was refunded
