@PLATFORM @US @ROW
Feature: Verify error messages on hotel billing page
#  Autor I. Komarov

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Credit card number with empty values.
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 10 days from now and 15 days from now
    And I request quotes
    When I choose a hotel result
    And I book selected hotel
    And I fill in credit card number with blank values
    When I complete a hotel as guest a quote
    Then I receive immediate "Credit card type is not accepted. Please use one of the card types displayed." error message

  @ACCEPTANCE @ARCHIVE
  Scenario: Credit card number contains a character. RTC-1381
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 10 days from now and 15 days from now
    And I request quotes
    When I choose a hotel result
    And I book selected hotel
    And I fill in credit card number with character in card number
    When I complete a hotel as guest a quote
    Then I receive immediate "Credit card type is not accepted. Please use one of the card types displayed." error message

  @ACCEPTANCE
  Scenario: Credit card CPV code contains letters. RTC-1384
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 10 days from now and 15 days from now
    And I request quotes
    When I choose a hotel result
    And I book selected hotel
    And I fill in credit card number with only characters in security code
    When I complete a hotel as guest a quote
    Then I receive immediate "Enter a valid security code. This is the 3 or 4-digit number on the back of your card." error message

  @ACCEPTANCE
  Scenario: Credit card expiration date is obsolete. RTC-1386
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 10 days from now and 15 days from now
    And I request quotes
    When I choose a hotel result
    And I book selected hotel
    And I fill in credit card number with obsolete credit card date
    When I complete a hotel as guest a quote
    Then I receive immediate "Credit card expiration date is incorrect." error message

  @ACCEPTANCE
  Scenario: Only numbers in Billing Address . RTC-1387
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 10 days from now and 15 days from now
    And I request quotes
    When I choose a hotel result
    And I book selected hotel
    And I fill in credit card number with only numbers in billing address
    When I complete a hotel as guest a quote
    Then I receive immediate "Enter a valid billing address." error message

  @ACCEPTANCE
  Scenario: Only characters in zip code. RTC-1389
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 10 days from now and 15 days from now
    And I request quotes
    When I choose a hotel result
    And I book selected hotel
    And I fill in credit card number with only characters in zip code
    When I complete a hotel as guest a quote
    Then I receive immediate "Enter a valid postal code." error message

  @ACCEPTANCE
  Scenario: Purchase a hotel using non-Canadian or non-US user. RTC-1296
    Given I'm a guest user
    And I have a valid LiveProcessor American Express credit card
    And I'm searching for a hotel in "SFO"
    And I want to travel in the near future
    And I request quotes
    When I choose a hotel result
    And I book selected hotel
    When I complete a hotel as guest a quote
    Then I receive immediate confirmation
