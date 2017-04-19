@US @ACCEPTANCE
Feature: Verify error messages on hotel traveler info

  Background:
    Given default dataset
    Given the application is accessible

  Scenario: Blank last name in Billing's Traveler Information. RTC-1428
    Given I'm searching for a hotel in "Miami, FL"
    And I want to travel between 10 days from now and 15 days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    When I choose a blank last name field for hotel traveler
    Then I receive immediate "Enter the last name." error message

  Scenario: Error message for blank Email in Billing's Traveler Information. RTC-1429
    Given I'm searching for a hotel in "Miami, FL"
    And I want to travel between 10 days from now and 13 days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    When I choose a blank email field for hotel traveler
    Then I receive immediate "Enter a valid email address." error message
    Then I receive immediate "The two email addresses need to match." error message

  Scenario: Error message for all blank fields in Billing's Traveler Information. RTC-1430
    Given I'm searching for a hotel in "Miami, FL"
    And I want to travel between 10 days from now and 13 days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    When I choose a blank hotel travelers section like guest
    Then I receive immediate "Enter a phone number." error message
    Then I receive immediate "Enter the first name." error message
    Then I receive immediate "Enter the last name." error message
    Then I receive immediate "Enter a valid email address." error message
    Then I receive immediate "The two email addresses need to match." error message

  Scenario: Submission of Traveler Information with new traveler name. RTC-1446
    Given I'm searching for a hotel in "Miami, FL"
    And I want to travel between 10 days from now and 13 days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    When I choose a blank hotel travelers section like user
    Then I am authenticated

  @JANKY
  Scenario: Error message when password in Billing's Sign-In module is blank. RTC-1447
    Given I'm searching for a hotel in "Miami, FL"
    And I want to travel between 10 days from now and 13 days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    When I authenticate with "tkachaeva@hotwire.com" user and "" password on billing page
    Then I receive immediate "Password must contain 6-30 characters and no spaces. It is case-sensitive" error message


  Scenario: User want to change the guest name after a credit card authorization problem RTC-1393
    Given I'm searching for a hotel in "Miami, FL"
    And   I want to travel between 10 days from now and 13 days from now
    And   I request quotes
    And   I choose a hotel result
    And   I book that hotel
    When  I am a guest user trying to populate with obsolete billing information
    And   I receive immediate "Credit card expiration date is incorrect." error message
    And   I change traveler name to FirstTwo LastTwo
    And   I'm a guest user
    And   I purchase as guest a quote
    Then  I receive immediate confirmation
