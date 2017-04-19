@US @ACCEPTANCE
Feature: Payment methods state depending on car type and other conditions.
  Owner: Iuliia Neiman
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

#  BML is disabled on frontend (but NOT removed from the code) in 2016.02 branch.
#  TODO: Commenting this cases until they will be removed from the code.
  @CLUSTER3
  Scenario: Check displaying Payment methods for Retail car for guest user. RTC-6866
    Given I'm a guest user
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    When I request quotes
    And I see a non-empty list of search results
    And I choose a retail car
    And I don't choose insurance
    Then none payment is available
    When I choose insurance
    Then CreditCard payment is available
  #  And BML payment is unavailable
    And PayPal payment is unavailable
    When I don't choose insurance
    Then none payment is available

  Scenario: Check displaying Payment methods for retail car for logged in user. RTC-6866
    Given my name is payableuser3@hotwire.com and my password is testing
    And I authenticate myself
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    When I request quotes
    And I see a non-empty list of search results
    And I choose a retail car
    And I don't choose insurance
    Then none payment is available
    When I choose insurance
    Then CreditCard payment is available
    And SavedVisa payment is available
    And SavedMasterCard payment is available
 #  And BML payment is unavailable
    And PayPal payment is unavailable
 #   And SavedBML payment is unavailable
  @CLUSTER3
  Scenario: Check displaying Payment methods for opaque car for guest user. RTC-6866
    Given I'm a guest user
    And I'm searching for a car in "AIRPORT"
    And I want to travel between 9 days from now and 14 days from now
    When I request quotes
    And I see a non-empty list of search results
    And I choose a opaque car
    And I don't choose insurance
    Then CreditCard payment is available
  #  And BML payment is available
    And PayPal payment is available
    And I choose insurance
    Then CreditCard payment is available
  #  And BML payment is disabled
    And PayPal payment is disabled

  Scenario: Check displaying Payment methods for Opaque car for logged in user. RTC-6866
    Given I'm a payable user
    And I authenticate myself
    And I'm searching for a car in "AIRPORT"
    And I want to travel between 17 days from now and 19 days from now
    When I request quotes
    And I see a non-empty list of search results
    And I choose a opaque car
    And I don't choose insurance
    Then CreditCard payment is available
    And SavedVisa payment is available
    And HotDollars payment is available
 #  And BML payment is available
    And PayPal payment is available
    And SavedCreditCard payment is available
 #  And SavedBML payment is available
    When I choose insurance
    Then CreditCard payment is available
    And SavedVisa payment is available
    And HotDollars payment is available
 #  And BML payment is disabled
    And PayPal payment is disabled
 #  And SavedBML payment is disabled

  Scenario: Verify that CC information not stored in the account. RTC-196
    Given I am a new user
    Given I create an account
    Given the application is accessible
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request quotes
    And I choose a opaque car
    Then CreditCard payment is available
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation
    Given the application is accessible
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request quotes
    And I choose a opaque car
    Then SavedCreditCard payment is unavailable

