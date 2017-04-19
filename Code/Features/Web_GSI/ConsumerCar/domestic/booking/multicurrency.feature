@US
Feature:
  Allow a customer to purchase a rental car with other currency
  Owner: Vyacheslav Zyryanov
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE  @BUG
  Scenario Outline: 1007 | Booking a car as REGISTERED user with other currency.
  #	BUG53945: 	Booking car in non-USD currency fails
    Given I have valid random credentials
    When I authenticate myself
    And I am authenticated
    And I choose <currency> currency
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between 15 days from now and 19 days from now
    And I <negation> request insurance
    And I request quotes
    And I choose a <carType> car and purchase as a quote
    Then I receive immediate confirmation
    And currency is <currency>

  Examples: car rental locations
    | pickUpLocation            | carType | currency | negation |
    | San Francisco, CA - (SFO) | opaque  | GBP      |          |
    | San Francisco, CA         | retail  | GBP      |          |


  @ACCEPTANCE @BUG
  Scenario Outline: 1007 | Booking a car as GUEST user with non-default currency.
    #	BUG53945: 	Booking car in non-USD currency fails
    Given I'm a guest user
    When I choose <currency> currency
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between 10 days from now and 11 days from now
    And I <negation> request insurance
    And I request quotes
    And I choose a <carType> car and purchase as a quote
    Then I receive immediate confirmation
    And currency is <currency>

  Examples: car rental locations
    | pickUpLocation | carType | currency | negation |
    | SFO            | retail  | EUR      | don't    |
    | SFO            | opaque  | EUR      |          |
