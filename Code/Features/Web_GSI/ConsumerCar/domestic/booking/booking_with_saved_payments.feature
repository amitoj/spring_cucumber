@US
Feature: car bookings using saved payment methods data

  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: RTC-6707 Booking with saved credit card data
    Given I'm a payable user
    And I authenticate myself
    When I'm searching for a car in "AIRPORT"
    And I want to travel between <startDate> and <endDate>
    And I <negation> request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a <carType> car
    When I fill in all savedVisa billing information
    And I complete purchase with agreements
    Then I receive immediate confirmation

  Examples:
    | carType | negation | startDate        | endDate          |
    | opaque  | don't    | 26 days from now | 28 days from now |
    | retail  |          | 27 days from now | 28 days from now |

#  BML is disabled on frontend (but NOT removed from the code) in 2016.02 branch.
#  TODO: Commenting this cases until they will be removed from the code.
#
#  Scenario: RTC-6707 Booking with saved BML data
#    Given I'm a payable user
#    And I authenticate myself
#    When I'm searching for a car in "AIRPORT"
#    And I want to travel between 24 days from now and 25 days from now
#    And I request quotes
#    Then I see a non-empty list of search results
#    And I choose a opaque car
#    When I fill in all savedBML billing information
#    And I complete purchase with agreements
#    Then I receive immediate confirmation
