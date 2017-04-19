@MOBILE
Feature: Coupon code for mweb

  Background:
    Given default dataset
    Given the application is accessible

  @LIMITED @JANKY
  Scenario Outline: Happy path with valid coupon code
    Given I'm a guest user
    And I have a valid hotel coupon code
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive confirmation of hotel purchase with the brand new coupon discount applied

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   |
    | San Francisco, CA   | 10             | 11             |

  # https://jira/jira/browse/FUN-148 bug by @bshukaylo
  @ACCEPTANCE @BUG
  Scenario Outline: Coupon code entry disabled with valid dccid
    Given I append good discount dccid code
    And I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    And I fill in guest traveler info
    And I fill in guest billing info
    Then coupon code entry is disabled

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   |
    | Los Angeles, CA     | 35             | 38             |

  @ACCEPTANCE
  Scenario Outline: Coupon code entry disabled on mobile UK
    Given I visit Hotwire from uk on a mobile device
    And I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    And I fill in guest traveler info
    And I fill in guest billing info
    Then coupon code entry is disabled

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   |
    | San Francisco, CA   | 35             | 38             |

  #https://jira/jira/browse/FUN-157 @bshukaylo
  @ACCEPTANCE @BUG
  Scenario Outline: Minimum and site id tests.
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I filter by 2 star rating
    And I choose a 2 star hotel result
    And I book that hotel
    And I fill in guest traveler info
    And I fill in guest billing info
    When I apply a <couponCodeType> hotel coupon code
    And I <state> see the coupon code discount applied in billing trip summary

  Examples: quotable fares parameters
    | destinationLocation | startDateShift | endDateShift   | couponCodeType        | state    |
    | San Francisco, CA   | 10             | 11             | valid                 | will     |
    | San Francisco, CA   | 10             | 11             | minimum 3 star rating | will not |
    | San Francisco, CA   | 10             | 11             | valid native app      | will not |
    | San Francisco, CA   | 10             | 11             | minimum amount        | will not |
    | San Francisco, CA   | 10             | 11             | invalid start date    | will not |
    | San Francisco, CA   | 10             | 11             | invalid end date      | will not |
