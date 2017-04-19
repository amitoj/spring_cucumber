@US
Feature: Feedback page comments

  Background: 
    Given default dataset
    Given the application is accessible

  @JANKY @ACCEPTANCE @SingleThreaded
  Scenario: RTC-4308: UHP - Opinion Lab link/popup functionality
    Given I click on "Feedback"
    Then I submit a comment card with comment: "Test" and page ratings: 5,4,3,2
