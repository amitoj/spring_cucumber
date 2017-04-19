@US @ACCEPTANCE
Feature: Trip Watcher module on Cars vertical
  Owner: Iuliia Neiman
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: Displaying TW for round-trip airport search when log in user. RTC-6873
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    When I'm searching for a car in "AIRPORT"
    And I want to travel between 4 days from now and 6 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I see TripWatcher module
    And Trip Watcher module has copy for new trip
    And TripWatcher module has email by default

  Scenario: Displaying TW for round-trip airport search when quest user. RTC-6873
    When I'm searching for a car in "AIRPORT"
    And I want to travel between 4 days from now and 6 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I see TripWatcher module
    And Trip Watcher module has copy for new trip
    And TripWatcher module doesn't has email by default

  Scenario: Displaying TW module according to pick-up date. RTC-6875
    When I'm searching for a car in "AIRPORT"
    And I want to travel between 2 days from now and 6 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I don't see TripWatcher module

  Scenario: Displaying TW for one-way. RTC-6873
    And I'm searching for a car from "SFO" to "LAX"
    And I want to travel between 10 days from now and 20 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I don't see TripWatcher module

  Scenario: Watching trip for unauthorized user. RTC-6874
    Given I'm searching for a car in "AIRPORT"
    And I want to travel between 4 days from now and 6 days from now
    And I request quotes
    And I see a non-empty list of search results
    And I see TripWatcher module
    And Trip Watcher module has copy for new trip
    When I type email and watch the trip for random user
    Then Trip Watcher module has copy for watched trip

  Scenario: Watching trip for authorized user. RTC-6880
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "LHR"
    And I want to travel between 4 days from now and 6 days from now
    And I request quotes
    And I see a non-empty list of search results
    And I see TripWatcher module
    And Trip Watcher module has copy for new trip
    When I don't type email and watch the trip for random user
    When I access my account information
    And I check the past trip in "Heathrow" has been watched

  Scenario: Validation for Trip Watcher module. RTC-6877
    Given I'm searching for a car in "AIRPORT"
    And I want to travel between 4 days from now and 6 days from now
    And I request quotes
    And I see a non-empty list of search results
    And I see TripWatcher module
    When I don't type email and watch the trip for random user
    Then I receive Trip Watcher email field error validation

  @STBL
  Scenario: Deviations of pick-up/drop-off dates. RTC-6878
    Given I'm searching for a car in "New York City, NY - (JFK)"
    And I want to travel between 4 days from now and 6 days from now
    And I request quotes
    And I see a non-empty list of search results
    And I see TripWatcher module
    And Trip Watcher module has copy for new trip
    When I type email and watch the trip for random user
    Then Trip Watcher module has copy for watched trip
    And I want to travel between 5 days from now and 6 days from now
    And I launch a search
    And I see a non-empty list of search results
    Then Trip Watcher module has copy for watched trip