@MOBILE @ACCEPTANCE @JANKY
Feature: Option to save payment information on billing

  Owner: Jorge Lopez

  Background:
    Given default dataset
    Given set version test "vt.CMD14" to value "2"
    Given the application is accessible

  Scenario Outline: Save Payment information on billing.
    Given I am a new user
    And   I create an account
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose the first <carType> car
    And I <negation> request insurance
    When I continue to billing as user a quote
    And I fill payment info
    And I save my payment info
    And I proceed with the payment
    Then I receive immediate confirmation
    And  I am on home page
    And I request quotes
    When I choose the first <carType> car
    And I <negation> request insurance
    When I continue to billing as user a quote
    And I verify payment method is saved

  Examples:
    | location | carType | startDateShift | endDateShift | negation |
    | SFO      | opaque  | 12             | 16           |         |
    | SFO      | retail  | 24             | 26           |         |
