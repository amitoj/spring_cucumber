@US
Feature: Verify error messages on UHP for incorrect search
#author Komarov. I

  Background:
    Given default dataset
    Given the application is accessible


  @ACCEPTANCE
  Scenario: Error message for unsatisfied minimum of one adult per room for Hotel searches. RTC-1218
    Given I'm searching for a hotel in "SFO"
    And I want to travel in the near future
    And I want 1 room(s)
    And I verify that number of adults couldn't be less number of rooms on UHP
    And I want 2 room(s)
    And I verify that number of adults couldn't be less number of rooms on UHP
    And I want 3 room(s)
    And I verify that number of adults couldn't be less number of rooms on UHP