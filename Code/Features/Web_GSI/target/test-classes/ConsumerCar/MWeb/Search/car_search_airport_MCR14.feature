@MOBILE @platform
Feature: Airport car search with MCR14=2 - new car results page.

  Background:
    Given default dataset

  @ACCEPTANCE
  Scenario Outline: Given that a registered user searches for opaque/retail cars at an airport location they should see airport results and be able to book a car with/without insurance.
    Given set version test "vt.MCR14" to value "2"
    Given the application is accessible
    Given I'm a registered user
    When I'm searching for a car in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want to pick up at <startTime> and drop off at <endTime>
    And I request quotes
    Then I should be taken to the airport results page
    And I <negation> request insurance
    When I choose a <opacity> car and purchase as user a quote
    Then I receive immediate confirmation

  Examples: car search parameters
    | destinationLocation | startDateShift | endDateShift | startTime | endTime | opacity | negation |
    | LAX                 | 36             | 38           | 1700      | 1700    | retail  |          |
    | SFO                 | 40             | 45           | 1700      | 1700    | opaque  | don't    |

