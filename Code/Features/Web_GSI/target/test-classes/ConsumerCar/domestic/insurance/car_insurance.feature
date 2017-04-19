@US
Feature: Car insurance, damage protection module verifications
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario Outline: Verify insurance module of specified rental period
    Given I'm a guest user
    And I choose <currency> currency
    And I'm searching for a car in "<pickUpDropOff>"
    And I want to travel between 7 days from now and <endOffset> days from now
    And I request quotes
    And I choose a retail car
    Then I <state> see insurance in car billing

  Examples: car rental locations
    | currency | pickUpDropOff | endOffset | state    |
    | USD      | AIRPORT       | 51        | will     |
    | USD      | AIRPORT       | 54        | will not |
    | GBP      | AIRPORT       | 51        | will not |

  @ACCEPTANCE
  Scenario Outline: RTC-5388  Insurance - trip length equals 45 days / RTC-5387 Insurance - trip length equals 44 days
    And I'm searching for a car in "SFO"
    And I want to travel between <startDateShift> and <endDateShift>
    And I <negation> request insurance
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a <carType> car
    Then Rental days count equals to <number> on billing page
    Then Rental car damage protection was <protection>

  Examples:
    | #    | startDateShift | endDateShift     | carType | number | protection  | negation |
    | 5388 | 1 day from now | 46 days from now | retail  | 45     | unavailable | don't    |
    | 5387 | 1 day from now | 45 days from now | retail  | 44     | appear      |          |

  @ACCEPTANCE
  Scenario Outline: RTC-887 Stand alone car purchase - Verify Rental Car Protection
    And I'm searching for a car from "<pickUpLocation>" to "<dropOffLocation>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I select a <carType> car with cd code "ECAR"
    And I fill in insurance
    Then Rental car protection cost equals to <cost> on billing page

  Examples:
    |#            |startDateShift    |endDateShift       |cost       |pickUpLocation      |dropOffLocation       |carType|
    |887retail    |41 day from now   |46 days from now   |54         |SFO                 |LAX                   |retail |
    |887opaque    |3 days from now   | 8 days from now   |54         |SFO                 |SFO                   |opaque |