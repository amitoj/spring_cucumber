@US @platform
Feature: Car Domestic. HotDollars purchases.
  This test cases check if the user is able to pay using HotDollars for different purchase parameters.
  Owner: Alexandr Zelenov

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @LIMITED
  Scenario Outline: RTC-6868 HotDollars Happy Path with no insurance selected.
    Given I have HotDollars
    And I authenticate myself
    When I'm searching for a car in "AIRPORT"
    And I want to travel between 5 days from now and 7 days from now
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    When I fill in all HotDollars billing information
    And I see <message> in HotDollars Module
    And I complete purchase with agreements
    Then I receive immediate confirmation

  Examples:
    | #   | message                                          |
    | 888 | You have enough Hotwire Credit to book your trip |

  @ACCEPTANCE
  Scenario Outline: RTC-6868 HotDollars Happy Path with selected insurance.
    Given I have HotDollars
    And I authenticate myself
    When I'm searching for a car in "AIRPORT"
    And I want to travel between 9 days from now and 17 days from now
    And I request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    When I fill in all HotDollars billing information
    And I see <message> in HotDollars Module
    When I complete purchase with agreements
    Then I receive immediate confirmation

  Examples:
    | message                                                                                                                                                                               |
    | Hotwire credit can be used to book your trip, but cannot be used to pay for additional features. Please choose an additional payment method to cover the remaining cost of your trip. |

  @ACCEPTANCE
  Scenario: RTC-5329 Guest user has no hotdollars option on billing
    Given I'm a guest user
    And   I'm searching for a car in "AIRPORT"
    And   I want to travel between 5 days from now and 7 days from now
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And   I choose a opaque car
    When  I proceed to car billing
    Then  HotDollars payment method is not available on car billing page

  @ACCEPTANCE
  Scenario: RTC-5330 Logged in user with no hotdollars has no HD option on billing
    Given I am a new user
    And   I create an account
    And   I'm searching for a car in "AIRPORT"
    And   I want to travel between 5 days from now and 7 days from now
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And   I choose a opaque car
    When  I proceed to car billing
    Then  HotDollars payment method is not available on car billing page

  @ACCEPTANCE
  Scenario: RTC-5331 Not possible to buy rental car with hot dollars
    Given I have HotDollars
    And   I authenticate myself
    And   I'm searching for a car in "AIRPORT"
    And   I want to travel between 5 days from now and 7 days from now
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And   I choose a retail car
    When  I proceed to car billing
    Then  HotDollars payment method is not available on car billing page
