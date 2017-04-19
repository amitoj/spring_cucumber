@US
Feature: Insurance and etc.

  Background:
    Given default dataset
    Given the application is accessible

  @LIMITED @US
  Scenario Outline: Find and purchase a hotel room with insurance as a guest user using a credit card. RTC-1434
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose a hotel result
    And I book that hotel
    When I choose insurance
    Then the billing page trip summary will show insurance
    And  I purchase as guest a quote
    Then I receive immediate confirmation
    Then Trip insurance will show on confirmation

  Examples: opaque quotable fares parameters
    | destinationLocation | startDateShift | endDateShift |
    | San Francisco, CA   | 30             | 35           |

  @ACCEPTANCE
 Scenario Outline: Verify that user can edit saved credit card on billing page. RTC-1436
  Given my name is qa_regression@hotwire.com and my password is hotwire333
  When I authenticate myself
  And I'm searching for a hotel in "<destinationLocation>"
  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
  And I request quotes
  And I choose a hotel result
  And I book that hotel
  When I don't choose insurance
  When I edit saved credit card information
  Then I complete purchase with agreements
  Then I receive immediate confirmation


Examples: opaque quotable fares parameters
  | destinationLocation | startDateShift | endDateShift |
  | San Francisco, CA   | 30             | 35           |

  @ACCEPTANCE
  Scenario Outline: User saves new credit card information on the billing page. RTC-1437
    Given I am a new user
    Given I create an account
    When I am on home page
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose a hotel result
    And I book that hotel
    When I fill credit card and save billing information for new registered user
    Then I complete purchase with agreements
    Then I receive immediate confirmation


  Examples: opaque quotable fares parameters
    | destinationLocation | startDateShift | endDateShift |
    | San Francisco, CA   | 30             | 35           |

