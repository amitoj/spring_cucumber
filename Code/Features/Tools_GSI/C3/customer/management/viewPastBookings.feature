@TOOLS
Feature: verify c3/domestic past bookings only (no recent searches)
  Owner:Vladimir Yulun

  @ACCEPTANCE @STBL
  Scenario: View Past Bookings - GDS Confirmation # Link - Hotel. RTC-1179
    Given C3 application is accessible
    And customer itinerary for hotel with email "caps-express@hotwire.com"
    And I login into C3 with username "csrcroz1"
    When I want to search customer by email
    And I get hotel past booking
    And I click GDS link for recent hotel purchase
    Then I see itinerary details page
    And I see PNR on itinerary details page

  @ACCEPTANCE  @STBL
  Scenario: View Past Bookings - Car RTC-967
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    Given customer email with car purchases
    When I search for given customer email
    And I get past bookings
    Then I should see Air tab
    And I want to see car purchases of the customer
    Then I see itinerary details page


