@MobileApi
Feature: Hotel Coupon MobileAPI

  @ACCEPTANCE
  Scenario: validate the amount hotel coupon
    Given I use LatLong 37.658155,-122.396959
    And destination location is LAX
    And date and time range is between 5 days from now and 10 days from now
    And number of rooms is 1
    And number of adults is 2
    And number of children is 0
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    When I use result id of the first solution
    And I validate correct USA USD hotel amount coupon code from US
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see currency of coupon is USD
    And I see hotel coupon has been applied to the trip total price

  @LIMITED
  Scenario: validate the percent hotel coupon
    Given I use LatLong 37.658155,-122.396959
    And destination location is JFK
    And date and time range is between 3 days from now and 15 days from now
    And number of rooms is 1
    And number of adults is 2
    And number of children is 0
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And I use result id of the first solution
    And I validate correct USA hotel percent coupon code from US
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see hotel coupon has been applied to the trip total price

  Scenario: validate the expired hotel percent coupon
    Given I use LatLong 37.658155,-122.396959
    And destination location is JFK
    And date and time range is between 5 days from now and 10 days from now
    And number of rooms is 1
    And number of adults is 2
    And number of children is 0
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And I use result id of the first solution
    When I validate expired USA hotel percent coupon code from US
    Then I see there is no response

  Scenario: Validate percent cross-vertical coupon for hotel
    Given I use LatLong 37.658155,-122.396959
    And destination location is JFK
    And date and time range is between 3 days from now and 15 days from now
    And number of rooms is 1
    And number of adults is 2
    And number of children is 0
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And I use result id of the first solution
    When I validate correct USA cross-vertical percent coupon code for hotel from US
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see hotel coupon has been applied to the trip total price

  @ACCEPTANCE
  Scenario: Validate amount cross-vertical coupon for hotel
    Given I use LatLong 37.658155,-122.396959
    And destination location is LAX
    And date and time range is between 5 days from now and 10 days from now
    And number of rooms is 2
    And number of adults is 2
    And number of children is 0
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And I use result id of the first solution
    When I validate correct USA USD cross-vertical amount coupon code for hotel from US
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see currency of coupon is USD
    And I see hotel coupon has been applied to the trip total price

  @ACCEPTANCE
  Scenario: Validate amount cross-vertical coupon for MOLO solution
    Given I use LatLong 37.658155,-122.396959
    And destination location is MIA
    And date and time range is between 5 days from now and 10 days from now
    And number of rooms is 2
    And number of adults is 2
    And number of children is 0
    And I set the channel ID to HW_IOS
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And I use the MOLO result id for details
    When I validate correct USA USD cross-vertical amount coupon code for hotel from US
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see currency of coupon is USD
    And I see hotel coupon has been applied to the trip total price


  Scenario: Validate amount cross-vertical coupon which is valid only for first time App purchase for hotel
    Given I use LatLong 37.658155,-122.396959
    And destination location is LAX
    And date and time range is between 5 days from now and 10 days from now
    And number of rooms is 2
    And number of adults is 2
    And number of children is 0
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And I use result id of the first solution
    When I validate correct USA cross-vertical percent coupon code which is valid for first purchase for hotel from US
    Then I see there is no response


  Scenario: validate the amount EUR hotel coupon
    Given I use LatLong 37.658155,-122.396959
    And destination location is MIA
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 2
    And number of adults is 4
    And number of children is 0
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And I use result id of the first solution
    When I validate correct USA EUR hotel amount coupon code from US
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see currency of coupon is EUR
    And I see hotel coupon has been applied to the trip total price

  @ACCEPTANCE
  Scenario: validate the amount UK GBP hotel coupon
    Given I use LatLong 37.658155,-122.396959
    And destination location is MIA
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 2
    And number of adults is 4
    And number of children is 0
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And I use result id of the first solution
    When I validate correct UK GBP hotel amount coupon code from UK
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see currency of coupon is GBP
    And I see hotel coupon has been applied to the trip total price



