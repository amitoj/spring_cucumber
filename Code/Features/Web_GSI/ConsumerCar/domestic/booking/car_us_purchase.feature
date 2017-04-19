@US @platform @DEV
Feature: Car purchasing
  Owner: Vyacheslav Zyryanov

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @graphite-report-duration
  Scenario Outline: RTC-1019. Book car as a registered authenticated user
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I <negation> request insurance
    And I request quotes
    And I see a non-empty list of search results
    And I choose a <carType> car
    When I fill in all billing information
    And I complete purchase with agreements
    Then I receive immediate confirmation

  @LIMITED
  Examples: car rental locations
    | pickUpLocation | carType | negation | startDateShift | endDateShift |
    | AIRPORT        | opaque  | don't    | 16             | 18           |

  @SingleThreaded
  Examples: car rental locations
    | pickUpLocation | carType | negation | startDateShift | endDateShift |
    | AIRPORT        | opaque  |          | 11             | 18           |
    | CITY           | retail  |          | 9              | 13           |
    | CITY           | retail  | don't    | 10             | 13           |

  @graphite-report-duration
  Scenario Outline: book a car as a guest user
    Given I'm a guest user
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I <negation> request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a <carType> car
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation

  @LIMITED
  Examples:
    | location | carType | negation | startDateShift | endDateShift |
    | AIRPORT  | opaque  | don't    | 20             | 22           |
    | CITY     | retail  |          | 24             | 26           |

  @SingleThreaded
  Examples:
    | location | carType | negation | startDateShift | endDateShift |
    | ADDRESS  | retail  |          | 6              | 9            |
    | POSTAL   | retail  | don't    | 19             | 26           |

  Scenario Outline: Checking car bookings with valid credit cards. RTC-5221
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "AIRPORT"
    And I want to travel between <startDateShift> and <endDateShift>
    When I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    And I fill in all billing information
    And I write to cardNum payment field <number>
    And I write to securityCode payment field <code>
    When I complete purchase with agreements
    Then I receive immediate confirmation

  Examples:
    | number           | code | startDateShift   | endDateShift     |
    | 373235387881007  | 1111 | 7 days from now  | 12 days from now |
    | 5555555555554444 | 111  | 16 days from now | 19 days from now |
    | 4111111111111111 | 111  | 11 days from now | 13 days from now |
    | 6011000990139424 | 111  | 11 days from now | 14 days from now |
    | 3566111111111113 | 111  | 11 days from now | 16 days from now |