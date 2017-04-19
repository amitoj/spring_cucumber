@US
Feature: Verify that debit card explanation works correctly.
  Owner: Vyacheslav Zyryanov
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: Verify debit/credit card More details link RTC-6700
    Given I'm searching for a car in "Milwaukee, WI - (MKE)"
    And I want to travel in the near future
    When I request quotes
    Then I see a non-empty list of search results
    When I select DEBIT card option
    When I open debit deposit explanation popup
    Then Debit deposit explanation popup contains text
    """
    If you are traveling to the area, your search results will show rental agencies that accept debit/check card
    deposits with proof of a round-trip travel ticket.
    If you are local, your search results will only show agencies that accept debit card deposits for local renters.
    """
    When I close debit deposit explanation popup
    Then The debit deposit explanation popup is closed

  Scenario: Debit deposit card explanation contains pick-up location without any text modification for one-way search. RTC-6700
    Given I'm searching for a car from "Denver, CO - (DEN)" to "AIRPORT"
    And I want to travel between 7 days from now and 10 days from now
    When I request quotes
    Then I see a non-empty list of search results
    When I select DEBIT card option
    Then Debit deposit explanation contains text
    """
    Are you traveling or local to the Denver, CO (DEN) area?
    """
