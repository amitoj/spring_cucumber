@TOOLS
Feature: verify c3 itinerary details
  Owner:Vladimir Yulun

  @STBL @ACCEPTANCE
  Scenario: Hotel - Itinerary Details - Travel Details. RTC-564
    Given domestic application is accessible
    Given I am logged as express customer
    And I search for hotel
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    Given C3 application is accessible
    And I login into C3 with username "csrcroz1"
    When I want to search customer by email
    And I get hotel past booking
    And I click GDS link for recent hotel purchase
    Then I verify necessary fields on hotel itinerary details page

  @STBL @ACCEPTANCE
  Scenario: Hotel - Itinerary Details - Check transaction Info fields are available. RTC-637
    Given C3 application is accessible
    And customer itinerary for hotel with email "caps-non-express@hotwire.com"
    And I login into C3 with username "csrcroz1"
    When I want to search customer with existing confirmation number
    Then I see itinerary details page
    And I see "PRICE" text on page
    And I see "Hotel subtotal" text on page
    And I see "Attraction or Activity subtotal" text on page
    And I see "HotDollars Used" text on page
    And I see "Amount Refunded" text on page
    And I see "Trip Protection" text on page
    And I see "Total Cost to Customer" text on page


  @STBL @ACCEPTANCE
  Scenario: Verify past car purchase details RTC-1655, 1657, 1658
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I search for customer with "caps-non-express@hotwire.com" email
    And I click Site search for customer
    And I search for car
    And I process the results page
    And I process the billing page
    Then I receive immediate confirmation
    And I return to Customer Account
    And I want to see car purchases of the customer
    Then I see itinerary details page
    Then I verify necessary fields on car itinerary details page

  @ACCEPTANCE @ARCHIVE
  Scenario: Air itinerary details - verify departing and returning segments. RTC-1299, 1298
    Given C3 application is accessible
    And customer air purchase for cancel
    And  I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    And  I see itinerary details page
    Then I see air departing segments
    Then I see air returning segments
    Then I verify content of air departing segments
    Then I verify content of air returning segments

  @ACCEPTANCE @ARCHIVE
  Scenario: C3 Air itinerary page. RTC-1781
    Given C3 application is accessible
    And customer air purchase for cancel
    And  I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    And  I see itinerary details page
    Then I verify necessary fields on air itinerary details page

  @ACCEPTANCE  @STBL
  Scenario Outline: Full authorization details page. RTC-1347, 1292
    Given C3 application is accessible
    Given itinerary for <opacity> <vertical> confirmed purchase
    And  I login into C3 with username "csrcroz1"
    And  I search for given customer purchase
    Then I see itinerary details page
    And  I verify template of payment details page for full authorization table
    And  I verify template of payment details page for bill table

    Examples:
    |vertical|opacity|
    |hotel   |retail|
    |car     |opaque|