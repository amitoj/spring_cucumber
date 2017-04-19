@US
Feature: Compare car option of selected solution with the same options on other pages

  Background:
    Given default dataset
    Given activate car's version tests

  @ACCEPTANCE
  Scenario Outline: Compare car options from results page with billing page, when new TripSummary enabled vt.CTS14
    Given set version test "vt.CTS14" to value "2"
    Given the application is accessible
    And I'm a guest user
    When  I'm searching for a car in "<pickUpDropOff>"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    Then I compare car options between results and details

  Examples: car rental locations
    | pickUpDropOff     |
    | SFO               |
    | LHR               |

  Scenario Outline: Compare car options from results page with same options on details/billing page
    Given the application is accessible
    Given I'm a guest user
    When  I'm searching for a car in "<pickUpDropOff>"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    Then I compare car options between results and details

  Examples: car rental locations
    | pickUpDropOff         |
    | AIRPORT               |
    | CITY                  |


  @LIMITED @JANKY
  Scenario Outline: Compare car options from results page with same options on booking confirmation page
    Given the application is accessible
    Given I'm a guest user
    And I'm searching for a car in "<pickUpDropOff>"
    And I want to travel between 7 days from now and 9 days from now
    And I request quotes
    And I choose a <carType> car and purchase as a quote
    Then I receive immediate confirmation
    Then I compare car options between results and confirmation

  Examples: car rental locations
    | pickUpDropOff         |  carType |
    | CITY                  | retail   |
    | AIRPORT               | opaque   |

  @LIMITED @JANKY
  Scenario Outline: verify trip summary for booking car as REGISTERED USER
    Given the application is accessible
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "<pickUpDropOff>"
    And I want to travel between 5 days from now and 7 days from now
    And I <negation> request insurance
    And I request quotes
    Then Trip summary is the same after booking

  Examples: car rental locations
    | pickUpDropOff    | negation   |
    # You aren't going to get any opaque results in CITY search.
#    | CITY             |            |
    | AIRPORT          |            |
    | AIRPORT          | don't      |

  Scenario Outline: verify trip summary for booking car as GUEST USER
    Given the application is accessible
    Given I'm a guest user
    And I'm searching for a car in "<pickUpDropOff>"
    And I want to travel between 5 days from now and 7 days from now
    And I <negation> request insurance
    And I request quotes
    Then Trip summary is the same after booking

  Examples: car rental locations
    | pickUpDropOff  | negation   |
    | CITY           |            |
    | AIRPORT        | don't      |

 @ACCEPTANCE
  Scenario Outline: 1056, 1057 | Confirm the rental days calculation is correct
    Given the application is accessible
    Given I'm a guest user
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want to pick up at <startTime> and drop off at <endTime>
    And I request quotes
    And I choose a retail car
    When I fill in all billing information
    Then Rental days count equals to <expectedRentalDaysCount> on billing page

  Examples: car rental locations
    | #    | pickUpLocation  | startDateShift | endDateShift | startTime | endTime | expectedRentalDaysCount |
    | 1056 | AIRPORT         | 3              |  5           | noon      | noon    | 2                       |
    | 1057 | ADDRESS         | 3              |  5           | noon      | 1:00pm  | 3                       |

  @ACCEPTANCE @SingleThreaded
  Scenario Outline:  RTC-1031:2 Rental Days - From 12pm till 12pm       RTC-1015:2 Rental Days - From 12pm till 12:30
    Given the application is accessible
    Given set property "hotwire.eis.crs.rw.car.retailSearchTaskExcludeCarTypes" to value "ECAR, CCAR"
    Given I'm a guest user
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want to pick up at <startTime> and drop off at <endTime>
    And I request quotes
    And I choose a retail car
    Then Rental days count equals to <expectedRentalDaysCount> on billing page

  Examples: car rental locations
    | #    | pickUpLocation | startDateShift | endDateShift | startTime | endTime | expectedRentalDaysCount |
    | 1031 | PHX            | 5              | 7            | noon      | noon    | 2                       |
    | 1015 | PHX            | 5              | 7            | noon      | 12:30pm | 3                       |

  @ACCEPTANCE @SingleThreaded
  Scenario:   Roll back changes
    Given set property "hotwire.eis.crs.rw.car.retailSearchTaskExcludeCarTypes" to value ""