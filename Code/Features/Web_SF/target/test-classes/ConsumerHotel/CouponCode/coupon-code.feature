@US
Feature: Hotel coupon code. Who doesn't like coupon savings?

  Background:
    Given default dataset
    Given the application is accessible

  @US @LIMITED @JANKY
  Scenario Outline: Happy path with valid coupon code
    Given I'm a guest user
    And I choose <currency> currency
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose a opaque hotel
    When I apply a valid hotel coupon code
    Then I will see the coupon code discount applied in billing trip summary
    When I purchase as guest a quote
    Then I receive immediate confirmation

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   | currency |
    | San Francisco, CA   | 10             | 11             | USD      |
    #| San Antonio, TX     | 10             | 11             | CAD      |

  @US @LIMITED @JANKY
  Scenario Outline: Happy path with valid coupon code choosing or not choosing insurance.
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I save the hotel search id
    And I choose a hotel result
    And I book that hotel
    When I <insuranceChoice> insurance
    Then the correct discount will be applied to the trip summary total with/without insurance after applying a valid hotel coupon code

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   | insuranceChoice   |
    | New York City, NY   | 10             | 11             | choose            |
    | New York City, NY   | 10             | 11             | don't choose      |

  # Need backend support for final check in purchasing to not use hard DB sql call.
  @US @LIMITED @JANKY
  Scenario Outline: Happy path purchase with generated valid coupon code
    Given I'm a guest user
    And I want to use a brand new valid hotel coupon code
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose a opaque hotel
    And I fill in credit card number with validCreditCardNumber
    And I apply the brand new coupon code
    And I purchase as guest a quote
    And I receive confirmation of hotel purchase with the brand new coupon discount applied

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   |
    | San Francisco, CA   | 10             | 11             |

  #JIRA issues # HWAUTO-175 & FUN-129
  @US @ACCEPTANCE @BUG
  Scenario Outline: Minimum and site id tests.
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose sort by Star rating from low to high
    And I choose a opaque hotel
    When I apply a <couponCodeType> hotel coupon code
    And I will not see the coupon code discount applied in billing trip summary

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   | couponCodeType        |
    | San Francisco, CA   | 10             | 11             | minimum 3 star rating |
    | New York City, NY   | 10             | 11             | valid mobile          |
    | San Antonio, TX     | 10             | 11             | valid native app      |
    | Chicago, IL         | 10             | 11             | minimum amount        |
    | San Francisco, CA   | 10             | 11             | invalid start date    |
    | New York City, NY   | 10             | 11             | invalid end date      |

  @ROW @ACCEPTANCE @JANKY
  Scenario Outline: Intl POS will not see coupon code
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a opaque hotel
    Then coupon code entry is disabled

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   |
    | Chicago, IL         | 5              | 7              |

  @US @ACCEPTANCE @JANKY
  Scenario Outline: Coupon code entry disabled with valid dccid
    Given I append good discount dccid code
    And I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a opaque hotel
    Then coupon code entry is disabled

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   |
    | Los Angeles, CA     | 35             | 38             |

  @US @ACCEPTANCE
  Scenario Outline: Retail hotel will not see coupon code
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose retail hotels tab on results
    When I choose a retail hotel
    Then coupon code entry is disabled

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   |
    | Chicago, IL         | 5              | 7              |
