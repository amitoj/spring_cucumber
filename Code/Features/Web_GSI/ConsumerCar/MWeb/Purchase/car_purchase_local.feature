@MOBILE @platform
Feature: Local car search thru booking
  As a price hunter, I'd like to be able to search for cars that are located near me so that
  I don't have to go all the way to the airport to pick up a car.

  Background:
    Given the application is accessible

  Scenario Outline: Given that a guest user searches for retail cars at a non-airport location they should see local results and be able to book a car with/without insurance.
    Given I'm a guest user
    When I'm searching for a car in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want to pick up at <startTime> and drop off at <endTime>
    And I request quotes
    Then I should be taken to the local results page
    And I <negation> request insurance
    When I choose a retail car and purchase as guest a quote
    Then I receive immediate confirmation

  @LIMITED
  Examples: car search parameters
    | destinationLocation                             | startDateShift | endDateShift | startTime | endTime | negation |
    | San Francisco, CA                               | 2              | 3            | 1200      | 1200    |          |

  @ACCEPTANCE
  Examples: car search parameters
    | destinationLocation                             | startDateShift | endDateShift | startTime | endTime | negation |
    | 94111                                           | 10             | 12           | 1700      | 1700    | don't    |
    | 600 Montgomery St, San Francisco, CA 94111, USA | 37             | 42           | 900       | 1100    | don't    |

  Scenario Outline: Given that a registered user searches for retail cars at a non-airport location they should see local results and be able to book a car with/without insurance.
    Given I'm a registered user
    When I'm searching for a car in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want to pick up at <startTime> and drop off at <endTime>
    And I request quotes
    Then I should be taken to the local results page
    And I <negation> request insurance
    When I choose a retail car and purchase as user a quote
    Then I receive immediate confirmation

  @ACCEPTANCE
  Examples: car search parameters
    | destinationLocation | startDateShift | endDateShift | startTime | endTime | negation |
    | San Francisco, CA   | 2              | 3            | 1200      | 1200    |          |
    | 94111               | 10             | 12           | 1700      | 1700    | don't    |

  @LIMITED
  Examples: car search parameters
    | destinationLocation                             | startDateShift | endDateShift | startTime | endTime | negation |
    | 600 Montgomery St, San Francisco, CA 94111, USA | 37             | 42           | 900       | 1100    | don't    |
