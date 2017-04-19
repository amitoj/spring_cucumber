@MOBILE @platform
Feature: Airport car search thru booking
  As a price hunter,
  I'd like to be able to search for cars at the airport
  so that I can pick it up right after I land from my journey.

  Background:
    Given default dataset
    Given the application is accessible

  @SMOKE
  Scenario: search car in airport
    Given I visit Hotwire from us on a mobile device
    And I'm a guest user
    When I'm searching for a car in "SFO"
    And I want to travel in the near future
    And I request quotes
    Then I should be taken to the airport results page

  Scenario Outline: Given that a guest user searches for opaque/retail cars at an airport location they should see airport results and be able to book a car with/without insurance.
  #RTC-5075, 5076
    Given I visit Hotwire from <pos> on a mobile device
    And I'm a guest user
    When I'm searching for a car in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want to pick up at <startTime> and drop off at <endTime>
    And I request quotes
    Then I should be taken to the airport results page
    And I <negation> request insurance
    When I choose a <opacity> car and purchase as guest a quote
    Then I receive immediate confirmation

  @LIMITED @TEST
  Examples: car search parameters
    | pos | destinationLocation | startDateShift | endDateShift | startTime | endTime | opacity | negation |
    | us  | SFO                 | 34             | 35           | 1700      | 1700    | opaque  |          |
    | us  | JFK                 | 40             | 45           | 1700      | 1700    | retail  | don't    |

  @ACCEPTANCE @TEST
  Examples: car search parameters
    | pos | destinationLocation | startDateShift | endDateShift | startTime | endTime | opacity | negation |
    | ca  | BOS                 | 36             | 38           | 1700      | 1700    | opaque  | don't    |
    | ca  | ORD                 | 40             | 45           | 1700      | 1700    | retail  |          |


  Scenario Outline: Given that a registered user searches for opaque/retail cars at an airport location they should see airport results and be able to book a car with/without insurance.
    Given I'm a registered user
    When I'm searching for a car in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want to pick up at <startTime> and drop off at <endTime>
    And I request quotes
    Then I should be taken to the airport results page
    And I <negation> request insurance
    When I choose a <opacity> car and purchase as user a quote
    Then I receive immediate confirmation

  @ACCEPTANCE
  Examples: car search parameters
    | destinationLocation | startDateShift | endDateShift | startTime | endTime | opacity | negation |
    | SFO                 | 34             | 35           | 1700      | 1700    | opaque  |          |

  @ACCEPTANCE
  Examples: car search parameters
    | destinationLocation | startDateShift | endDateShift | startTime | endTime | opacity | negation |
    | BOS                 | 36             | 38           | 1700      | 1700    | retail  |          |
    | JFK                 | 40             | 45           | 1700      | 1700    | opaque  | don't    |
    | ORD                 | 40             | 45           | 1700      | 1700    | retail  | don't    |

