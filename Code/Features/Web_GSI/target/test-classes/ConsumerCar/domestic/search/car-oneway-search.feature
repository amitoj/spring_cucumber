@US
Feature: One way car search

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @CLUSTER2 @CLUSTER3
  Scenario Outline:  RNT-35:Search - One-way Car (Local to Local)
    Given I am logged in
    And I'm searching for a car from "<pickUp>" to "<dropOff>"
    And I want to travel in the near future
    And I request quotes
    And I choose a car
    When I fill in all billing information
    Then I compare car options between results and billing

  Examples: quotable fares parameters
    | pickUp            | dropOff       |
    | San Francisco, CA | Las Vegas, NV |

  @CLUSTER2 @CLUSTER3
  Scenario Outline:  RNT-36:Search - One-way Car (Local to Airport)
    Given I am logged in
    And I'm searching for a car from "<pickUp>" to "<dropOff>"
    And I want to travel in the near future
    And I request quotes
    And I choose a car
    When I fill in all billing information
    Then I compare car options between results and billing

  Examples: quotable fares parameters
    | pickUp            | dropOff |
    | San francisco, CA | SJC     |