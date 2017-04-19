@US
Feature: Verify deposit filter and copies presenting logic related to car service deposit options
  Owner: Nataliya Golodiuk

#These scenarios depend heavily on runway simulator's data - specific vendor in the specific location
#for the specific rental period

  Background:
    Given default dataset
    And activate car's version tests
    And the application is accessible

  @ACCEPTANCE
  Scenario Outline: verify deposit-related copies and payment info presence for airport search results RTC-6699
    Given I'm searching for a car in "<localDestination>"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    And I see a non-empty list of search results
    And I choose an local <CDcode> result
    Then rules and conditions are correct when <depositType> is allowed
    Then Deposit agreement checkbox is <checkboxState>

  Examples:
    | localDestination          | CDcode | depositType           | checkboxState |
    | San Francisco, CA - (SFO) | FCAR   | traveler's debit card | unchecked     |
    | Seattle, WA - (SEA)       | MVAR   | credit card           | unchecked     |
    | San Francisco, CA - (SFO) | PCAR   | card of any type      | absent        |

  @ACCEPTANCE
  Scenario Outline: verify deposit-related copies and payment info presence for airport results of local search RTC-6699
    Given I'm searching for a car in "<localDestination>"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    And I see a non-empty list of search results
    And I choose an airport <CDcode> result
    Then rules and conditions are correct when <depositType> is allowed
    Then Deposit agreement checkbox is <checkboxState>

  Examples:
    | localDestination  | CDcode | depositType           | checkboxState |
    | San Francisco, CA | FCAR   | traveler's debit card | unchecked     |
    | Seattle, WA       | MVAR   | credit card           | unchecked     |
    | San Francisco, CA | PCAR   | card of any type      | absent        |

  Scenario: verify deposit checkbox validation on billing page RTC-6702
    Given I'm searching for a car in "San Francisco, CA"
    And I want to travel between 3 days from now and 7 days from now
    When I request quotes
    And I see a non-empty list of search results
    And I choose an airport FCAR result
    Then Deposit agreement checkbox is unchecked
    When I complete purchase without agreements
    Then I receive immediate "To continue, you must accept the deposit terms." error message
    Then Deposit agreement checkbox is unchecked
