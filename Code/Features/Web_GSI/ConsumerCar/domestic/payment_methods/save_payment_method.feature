@US
Feature: User has ability to save his payment method data on the billing page and then reuse it without typing the data again

  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: RTC-6706 Save credit card info and verify its availability on the billing page for the next purchase
    Given I'm a guest user
    When I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request insurance
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a retail car
    And I fill in all billing information
    And I save my information
    And I save my payment information
    Then I enter name for credit card
    And I complete purchase with agreements
    Then I receive immediate confirmation
    When I authenticate myself
    And  I am on home page
    And I request insurance
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a retail car
    Then traveler information is populated
    And SavedVisa payment is available

#  BML is disabled on frontend (but NOT removed from the code) in 2016.02 branch.
#  TODO: Commenting this cases until they will be removed from the code.

#  Scenario: RTC-5376 Save BML payment data and verify its availability on the billing page for the next purchase
#    Given I'm a guest user
#    And I'm searching for a car in "AIRPORT"
#    And I want to travel between 5 days from now and 7 days from now
#    When I request quotes
#    Then I see a non-empty list of search results
#    And I choose a opaque car
#    And I fill in all BML billing information
#    And I save my information
#    And I save my payment information
#    When I complete purchase with agreements
#    And I confirm booking on BML website
#    Then I receive immediate confirmation
#    When I authenticate myself
#    And  I am on home page
#    And I request quotes
#    Then I see a non-empty list of search results
#    And I choose a opaque car
#    And traveler information is populated
#    And BML payment is available

