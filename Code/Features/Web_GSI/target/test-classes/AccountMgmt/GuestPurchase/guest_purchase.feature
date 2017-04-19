@US
Feature: guestPurchase
  Guest Purchase section

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Guest purchase with new customer registration on the Billing Page RTC-194
    #author VYulun
    Given I am a new user
    Given I create an account
    Given I am on home page
    And I'm searching for a car in "SFO"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a retail car
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation
    Given I want to logout
    Given I authenticate myself
    And I access my account information
    Then I check the past car purchase in "San Francisco Intl." has been reserved

  @ACCEPTANCE @STBL
  Scenario: Guest purchase with new, unregistered email address RTC-198
    #author VYulun
    Given I'm a guest user
    And I'm searching for a car in "AIRPORT"
    And I want to travel between 4 days from now and 10 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation
    Given I navigate to sign in page
    Given I am logged in with existing email and my credit card 11111
    When I see my recently booked trip

  @ACCEPTANCE @STBL
  Scenario: Create Account - Optional RTC-195
    #archive
    #author VYulun
    Given I'm a registered user
    And I have valid credentials
    And I authenticate myself
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a car
    Then Optional Sign in section is unavailable
    And traveler information is populated

  @ACCEPTANCE
  Scenario: Validate that users can access past purchase RTC-197
    #author VYulun
    Given I'm a guest user
    And I'm searching for a car in "AIRPORT"
    And I want to travel between 9 days from now and 15 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation
    And I want to access my booked trips
    Given I log in with existing email and valid trip departure date
    When I see my recently booked trip from guest trip page
