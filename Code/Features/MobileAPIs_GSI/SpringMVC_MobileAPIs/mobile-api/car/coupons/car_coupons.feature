@MobileApi
Feature: Car Coupon MobileAPI

  @ACCEPTANCE
  Scenario: validate the car amount coupon
    Given pickup location is JFK
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then car search has been executed
    And I see search results are present
    And I use result id of the first opaque solution
    And I validate correct USA USD car amount coupon code from US
    And I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see currency of coupon is USD
    And I see car coupon has been applied to the trip total price

  @LIMITED
  Scenario: validate the car percent coupon
    Given pickup location is SFO
    And date and time range is between 3 days from now and 15 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And I use result id of the first opaque solution
    And I validate correct USA car percent coupon code from US
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see car coupon has been applied to the trip total price


  Scenario: validate the expired car percent coupon
    Given pickup location is LAS
    And date and time range is between 2 days from now and 3 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then car search has been executed
    And I see search results are present
    And I use result id of the first opaque solution
    And I validate expired USA car percent coupon code from US
    And I see there is no response


  Scenario: validate percent cross-vertical coupon for car
    Given pickup location is SFO
    And date and time range is between 3 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And I use result id of the first opaque solution
    And I validate correct USA cross-vertical percent coupon code for car from US
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see car coupon has been applied to the trip total price

  @ACCEPTANCE
  Scenario: validate amount cross-vertical coupon for car
    Given pickup location is MIA
    And date and time range is between 16 days from now and 28 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And I use result id of the first opaque solution
    And I validate correct USA USD cross-vertical amount coupon code for car from US
    Then I see coupon validation response is present
    And I see coupon summary of charges are present
    And I see coupon trip charges are present
    And I see coupon details are present
    And I see car coupon has been applied to the trip total price

  Scenario: Validate amount cross-vertical coupon which is valid only for first time App purchase for car
    Given pickup location is MIA
    And date and time range is between 25 days from now and 28 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And I use result id of the first opaque solution
    When I validate correct USA cross-vertical percent coupon code which is valid for first purchase for car from US
    Then I see there is no response
