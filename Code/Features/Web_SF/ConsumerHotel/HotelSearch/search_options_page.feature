@US @ACCEPTANCE
Feature: Check a number of adults and children will correspond to room-based limitations on page reload after an improper search.
  Owner: Cristian Gonzalez Robles
  Background: 
    Given default dataset
    Given the application is accessible

  Scenario: RTC-4356: FareFinder dynamic guests should default to dynamic limitations after error
    When I start hotel search without specifying the destination
    And I click on room adults menu
    Then no more than 4 adults can be chosen from room menu
    And I click on room children menu
    Then no more than 2 children can be chosen from room menu
