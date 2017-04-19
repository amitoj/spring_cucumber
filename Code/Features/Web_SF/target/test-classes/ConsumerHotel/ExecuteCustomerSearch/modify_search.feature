@US
Feature: Modify search on result page

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Error message when check-in date is before to current date on Hotel results re-search. RTC-1421
#author VYulun
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 5 days from now and 10 days from now
    And I request quotes
    And I want to travel between 15 days before now and 1 days from now
    And I do a hotel search from result page
    Then I receive immediate "Enter a valid check-in date." error message

  @ACCEPTANCE
  Scenario: Error message when check-out date is before current date on Hotel results re-search. RTC-1423
    #author VYulun
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 5 days from now and 10 days from now
    And I request quotes
    And I want to travel between 60 days from now and 1 days before now
    And I do a hotel search from result page
    Then I receive immediate "Enter a valid check-out date." error message
    Then I receive immediate "The check-out date should be after the check-in date" error message

  @ACCEPTANCE
  Scenario: Modified Hotel search from Results(rooms). RTC-1420
 #author VYulun
    Given I'm searching for a hotel in "SFO"
    And  I want to travel between 5 days from now and 10 days from now
    And  I request quotes
    And  I choose sort by Price from low to high
    When I remember search solutions list
    And  I want 2 room(s)
    And  I do a hotel search from result page
    And  I choose sort by Price from low to high
    Then The search results should not be the same like previous
