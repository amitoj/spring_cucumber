@MOBILE @ACCEPTANCE @JANKY
Feature: book a car on mobile web site
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given the application is accessible

  Scenario Outline: book a car as a guest user
    Given I'm a guest user
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I <negation> request insurance
    And I request quotes
    When I choose a <carType> car and purchase as guest a quote
    Then I receive immediate confirmation

  Examples:
    | location | carType | negation | startDateShift | endDateShift |
    | AIRPORT  | opaque  | don't    | 12             | 16           |
    | AIRPORT  | retail  |          | 24             | 26           |