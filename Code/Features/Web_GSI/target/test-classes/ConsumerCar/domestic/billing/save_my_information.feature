@US
Feature: Option to save user's information on the billing page

  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: 'Save my information' section negative validation. RTC-6706
    Given I'm a guest user
    And I'm searching for a car in "Los Angeles, CA - (LAX)"
    And I want to travel between 13 days from now and 14 days from now
    When I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    And I fill in all billing information
    When I don't save my information
    And I save my payment information
    When I complete purchase with agreements
    Then I receive immediate "Enter missing information in the blank fields indicated below." error message
    When I save my information
    And I write to securityCode payment field 111
    And I complete purchase with agreements
    Then I receive immediate confirmation


  Scenario: RTC-6708 Saved credit card validation.
    Given I'm a payable user
    And I authenticate myself
    When I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    When I fill in traveler's information
    And I fill in insurance
    And I select savedVisa payment option
    And I complete purchase with agreements
    Then I receive immediate "This card requires a 3-digit security code." error message
    When I write to savedSecurityCode payment field 11
    And I complete purchase with agreements
    Then I receive immediate "This card requires a 3-digit security code." error message
    When I write to savedSecurityCode payment field 1111
    And I complete purchase with agreements
    Then I receive immediate "This card requires a 3-digit security code." error message
    When I write to savedSecurityCode payment field aaa
    And I complete purchase with agreements
    Then I receive immediate "The security code can only contain the numbers 0-9." error message
    When I write to savedSecurityCode payment field --111
    And I complete purchase with agreements
    Then I receive immediate "This card requires a 3-digit security code." error message
    When I fill in all savedVisa billing information
    And I complete purchase with agreements
    Then I receive immediate confirmation