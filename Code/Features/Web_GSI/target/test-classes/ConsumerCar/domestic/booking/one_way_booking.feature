@US @platform @simulator @SingleThreaded
Feature: Car Search And Purchase
  One-way retail car booking
 
  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: Booking a one-way car as registered user
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car from "<pickUpLocation>" to "<dropOffLocation>"
    And I want to travel in the near future
    And I <negation> request insurance
    And I request quotes
    And I choose a retail car and purchase as a quote
    Then I receive immediate confirmation

    Examples: car rental locations
    | #    | pickUpLocation  | dropOffLocation | negation |
    | 5372 | TUS            | SAN             |  don't   |
#    | 5373 | SFO             | Miami, FL       |          |

#   Waiting for test data on Hardness server for canadian airports
#
#  Scenario Outline: Booking a retail car as REGISTERED user with other currency.
#    Given I have valid random credentials
#    And I authenticate myself
#    And I am authenticated
#    And I choose <currency> currency
#    And I'm searching for a car in "<pickUpLocation>"
#    And I want to travel in the near feature
#    And I <negation> request insurance
#    And I request quotes
#    And I choose a <carType> car and purchase as a quote
#    Then I receive immediate confirmation

#  Examples: car rental locations
#    | #    | pickUpLocation  |  carType  | currency   | negation |
#    | 5371 | YQB             |  retail   | CAD        |          |
#    |      | YYZ             |  retail   | CAD        | don't    |
#    |      | YYZ             |  opaque   | CAD        |          |
#    |      | YUL             |  opaque   | CAD        | don't    |
  

