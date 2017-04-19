@MOBILE @ACCEPTANCE @JANKY
Feature: New Car details Page for mobile website
  Verifications for New Mobile Car details page (version test CMD14=2)
  Owner: Jorge Lopez


  Background:
    Given default dataset
    Given set version test "vt.CMD14" to value "2"
    Given the application is accessible

  Scenario Outline: book a car as a guest user
    Given I'm a guest user
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I <negation> request insurance
    When I choose the first <carType> car
    Then Car details page is displayed
    When I continue to billing as guest a quote
    And I fill payment info
    And I proceed with the payment
    Then I receive immediate confirmation

  Examples:
    | location | carType | startDateShift | endDateShift | negation |
    | SFO      | opaque  | 2              | 16           |          |
    | SFO      | retail  | 24             | 26           |          |