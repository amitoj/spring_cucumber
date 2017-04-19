@MOBILE @ACCEPTANCE @platform
Feature: Verification of legal pages accessibility

  Background:
    Given the application is accessible

  Scenario:  RTC-5084:Legal
    When I go to legal page
    Then Legal index page is accessible

  Scenario:  RTC-5085, 5086, 5087, 5088:Privacy Policy
    Given I go to legal page
    Then Legal index page is accessible
    When I go to Privacy Policy page
    Then Page Privacy Policy is accesible
    When I go to Terms of Use page
    Then Page Terms of Use is accesible
    When I go to Rules and Restrictions page
    Then Page Rules and Restrictions is accesible
    When I go to Low Price Guarantee page
    Then Page Low Price Guarantee is accesible

  Scenario Outline: Legal copy regarding insurance for international car reservations

    Given the following copy
    """
    Some international car rentals have mandatory insurance requirements that result in additional charges at the time of pick up.
    """
    And I'm a guest user
    When I'm searching for a car in "<destination>"
    And I want to travel between 1 days from now and 2 days from now
    And I request quotes
    Then I should be taken to the airport results page
    When I choose a retail car and hold on details
    Then I <negation> see the copy


  Examples:
    | destination                    | negation |
    | London, United Kingdom - (LHR) |          |
    | San Francisco, CA - (SFO)      | don't    |


  Scenario Outline: Legal copy regarding prices for opaque and retail details.

    Given the following copy
    """
    Rates are shown in US dollars. Total cost for Hotwire Rates includes applicable tax recovery charges and fees. Total cost for retail rate reservations is an estimate based on the agencys published rates, taxes and fees, and are subject to change. There is no guarantee that this price will be in effect at the time of your booking.
    """
    And I'm a guest user
    When I'm searching for a car in "<destination>"
    And I want to travel between 1 days from now and 2 days from now
    And I request quotes
    Then I should be taken to the <pageType> results page
    When I choose a <opaqueRetail> car and hold on details
    Then I <negation> see the copy


  Examples:
    | destination               | negation | opaqueRetail | pageType |
    | San Francisco, CA - (SFO) |          | retail       | airport  |
    | San Francisco, CA         |          | retail       | local    |
    | San Francisco, CA - (SFO) | don't    | opaque       | airport  |


  Scenario Outline: Legal copy regarding prices for results

    Given the following copy
    """
    Rates are shown in US dollars. Total cost for Hotwire Rates includes applicable tax recovery charges and fees. Total cost for retail rate reservations is an estimate based on the agencys published rates, taxes and fees, and are subject to change. There is no guarantee that this price will be in effect at the time of your booking.
    """
    And I'm a guest user
    When I'm searching for a car in "<destination>"
    And I want to travel between 1 days from now and 2 days from now
    And I request quotes
    Then I should be taken to the <pageType> results page
    Then I  see the copy

  Examples:
    | destination               | pageType |
    | San Francisco, CA - (SFO) | airport  |
    | San Francisco, CA         | local    |
