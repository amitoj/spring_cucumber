@US
Feature: Insurance module protection for flights
  Owner: Juan Hernandez

  Background: 
    Given default dataset
    Given the application is accessible
    Given the application window is maximized

  @STBL @ACCEPTANCE @ARCHIVE
  Scenario Outline: RTC - 288 Insurance fee for flights back end validation
    Given my name is qa_regression@hotwire.com and my password is hotwire333
    When I authenticate myself
    Then I am authenticated
    When I'm searching for a flight from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I will be flying with <numberOfPassenger> passengers
    And I request quotes
    And I select flight above $300 USD
    And I book selected flight
    And I fill in the traveler information as logged user
    And I choose insurance
    And I pay with saved credit card
    And I get insurance cost from review details fragment
    And I complete purchase with agreements
    Then I validate protection details on the Db

    Examples: quotable fares parameters
      | fromLocation | toLocation | startDateShift  | endDateShift    | numberOfPassenger |
      | SFO          | MYF        | 2 days from now | 5 days from now | 1                 |

  @STBL @ACCEPTANCE @ARCHIVE
  Scenario Outline: RTC - 287 Insurance fee for flights back end validation - Fee per passenger
    Given my name is qa_regression@hotwire.com and my password is hotwire333
    When I authenticate myself
    Then I am authenticated
    When I'm searching for a flight from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I will be flying with <numberOfPassenger> passengers
    And I request quotes
    And I select flight above $100 USD
    And I book selected flight
    And I fill in the traveler information as logged user
    And I get insurance cost multiplied by <numberOfPassenger> passengers
    And I choose insurance
    And I pay with saved credit card
    And I complete purchase with agreements
    Then I validate protection details on the Db

    Examples: quotable fares parameters
      | fromLocation | toLocation | startDateShift  | endDateShift    | numberOfPassenger |
      | SFO          | LAX        | 4 days from now | 9 days from now | 2                 |
