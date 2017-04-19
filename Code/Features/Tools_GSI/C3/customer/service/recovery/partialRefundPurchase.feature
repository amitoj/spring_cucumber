@TOOLS   @customerService     @ACCEPTANCE
Feature: Partial refund for a customer purchase.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @criticalPriority
  Scenario Outline: Partial refund for a customer purchase. RTC-961
    Given customer <vertical> purchase for partial refund
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    And   I choose service recovery
    And   I choose Test Booking recovery reason
    When  I do a partial refund
    Then  I see refund confirmation message

  Examples:
    | vertical |
    | car      |
    | hotel    |
#    | air      |

  @ARCHIVE @ACCEPTANCE
  Scenario Outline: : Verify the inability to execute a refund when no departs segments have been used. RTC-378, 331
    Given customer air purchase for partial refund
    Given C3 application is accessible
    Then  I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    Then  I choose service recovery
    And   I choose Test Booking recovery reason
    Then  I do a partial refund only for return segment and <numberOfPassengers> passengers
    And   I verify confirmation refund response window contains "A partial Air refund cannot occur because there have been no segments used in this itinerary. Full refund is the only option." text
    Then  I close response window

  Examples:
    | numberOfPassengers |
    | first     |
    | both      |

  @ARCHIVE @ACCEPTANCE
  Scenario: Verify Partial Refunded status. RTC-315
    Given customer with air partial refund
    Given C3 application is accessible
    Then  I login into C3 with username "csrcroz1"
    And   I search for given customer email
    Then  I get past bookings
    And   I want see Air tab
    Then  I see partial refunded purchase with Partially Refunded status


  @ARCHIVE  @ACCEPTANCE
  Scenario: Make a full refund  from partial. RTC-59
    Given customer air purchase for partial refund
    And   I login into C3 with username "csrcroz1"
    Then  I search for given customer purchase
    And   I choose service recovery
    Then  I choose Test Booking recovery reason
    When  I do a partial refund
    And   I see refund confirmation message
    And   I verify status code and amount for a partial refunded air purchase with DB
    Given C3 application is accessible
    Then  I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    Then  I choose service recovery
    And   I make full air refund from partial with Medical recovery reason
    Then  I see refund confirmation message
    And   I verify status code and amount for a partial refunded air purchase with DB

  @ARCHIVE @ACCEPTANCE
  Scenario: Partial passenger/segment with used segment. RTC-316, RTC-315, RTC-314
    Given I setup property "hotwire.eis.crs.air.simulateOutboundSegmentStatusUsedTicketCount" to "2"
    Then  I apply properties using RefreshUtil
    Given customer air purchase for partial refund
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    And   I choose service recovery
    And   I choose Test Booking recovery reason
    When  I do a partial refund only for return segment and first passengers
    Then  I see refund confirmation message
    And   I see purchase status is "Partially Refunded"
    And   I verify status code and amount for a partial refunded air purchase with DB
    When  I get air past booking
    And   I scroll past booking page to itinerary
    Then  I see View link in "Case attached" column for purchase
    When  I go to Case history page
    Then  I see Case for itinerary

  @ARCHIVE @ACCEPTANCE
  Scenario: Reverting properties after simulate Outbound Segment.
    Given I setup property "hotwire.eis.crs.air.simulateOutboundSegmentStatusUsedTicketCount" to "0"
    Then  I apply properties using RefreshUtil


