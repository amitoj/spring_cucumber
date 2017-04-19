@US
Feature: Trip watcher module verifying for the all verticals and result/details pages
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible



  @US @ACCEPTANCE
  Scenario: RTC-548 WTT module on the Hotel Results Page for the logged user
    Given customer without watched trips
    Given I authenticate myself
    When I am on hotel index page
    And  I'm searching for a hotel in "SFO"
    And  I want to travel in the near future
    And  I want 1 room(s)
    And  I will be traveling with 2 adults
    And  I will be traveling with 1 children
    And  I request quotes
    Then I will see opaque results page
  #if we want to see Trip Watcher layer we should make another search because the VT=SAL12 doesn't work
    And  I'm searching for a hotel in "SFO"
    And  I want to travel in the near future
    And  I want 1 room(s)
    And  I will be traveling with 2 adults
    And  I will be traveling with 1 children
    Then I do a hotel search from result page
  #
    Then I will see opaque results page
    Then I will see the trip watcher layer in the opaque results
    Then I want to watch this trip for current email on results
    When I access my account information
    Then I check the past trip in "San Francisco, CA" has been watched
    Then I check watched trip in DB for hotel
    Then I stop watching all trips


  @US @ACCEPTANCE
  Scenario: RTC-553 WTT module on the Car Result Page for the logged user
    Given customer without watched trips
    Given I authenticate myself
    When I am on car index page
    And  I'm searching for a car in "SFO"
    And  I want to travel in the near future
    And  I request quotes
    Then I see a non-empty list of search results
    Then I don't type email and watch the trip for authenticated user
    When I access my account information
    Then I check the past trip in "San Francisco Intl" has been watched
    Then I check watched trip in DB for car
    Then I stop watching all trips


  @US @ACCEPTANCE @ARCHIVE
  Scenario: RTC-555 WTT module on the Car Details Page for the not logged user
    Given customer without watched trips
    Given I authenticate myself
    Given I want to logout
    When I am on car landing page
    And  I'm searching for a car in "SFO"
    And  I want to travel in the near future
    And  I request quotes
    And  I choose a opaque car and hold on details
    Then I type email and watch the trip for authenticated user
    And  I authenticate myself
    When I access my account information
    Then I check the past trip in "San Francisco Intl" has been watched
    Then I check watched trip in DB for car
    Then I stop watching all trips

  @US  @ACCEPTANCE
  Scenario: RTC-554 WTT module on the Car Details Page for the logged user
    Given customer without watched trips
    Given I authenticate myself
    When I am on car index page
    And  I'm searching for a car in "SFO"
    And  I want to travel in the near future
    And  I request quotes
    And  I choose a opaque car and hold on details
    Then I don't type email and watch the trip for authenticated user
    When I access my account information
    Then I check the past trip in "San Francisco Intl" has been watched
    Then I check watched trip in DB for car
    Then I stop watching all trips



  @US  @ACCEPTANCE
  Scenario: RTC-558 WTT module on the Air Result Page for the logged user
    Given customer without watched trips
    Given I authenticate myself
    When  I am on air index page
    And   I'm searching for a flight from "SFO" to "LAX"
    And   I want to travel between 1 weeks from now and 2 weeks from now
    And   I will be flying with 1 passengers
    And   I request quotes
    When  I land on air results page
    Then  I don't type email and watch the air trip
    When  I access my account information
    And   I close all existing pop-up windows
    Then  I check the past trip in "Los Angeles" has been watched
    Then  I check watched trip in DB for air
    Then I stop watching all trips

  @US  @ACCEPTANCE @ARCHIVE
  Scenario: RTC-559 WTT module on the Air Details Page for the logged user
    Given customer without watched trips
    Given I authenticate myself
    Given I am logged in
    When  I am on air index page
    And   I'm searching for a flight from "SFO" to "LAX"
    And   I want to travel between 1 weeks from now and 2 weeks from now
    And   I will be flying with 1 passengers
    And   I request quotes
    And   I choose retail flight
    Then  I don't type email and watch the air trip
    When  I access my account information
    Then  I check the past trip in "Los Angeles" has been watched
    Then  I check watched trip in DB for air
    Then I stop watching all trips

  @US @ACCEPTANCE @ARCHIVE
  Scenario: RTC-560 WTT module on the Air Details Page for the not logged user
    Given customer without watched trips
    Given I authenticate myself
    Given I want to logout
    Given I am on home page
    When  I am on air index page
    And   I'm searching for a flight from "SFO" to "LAX"
    And   I want to travel between 1 weeks from now and 2 weeks from now
    And   I will be flying with 1 passengers
    And   I request quotes
    And   I choose retail flight
    Then  I type email and watch the air trip
    And   I authenticate myself
    When  I access my account information
    Then  I check the past trip in "Los Angeles" has been watched
    Then  I check watched trip in DB for air
    Then I stop watching all trips

  @US @ACCEPTANCE @ARCHIVE
  Scenario Outline: Check TripWatcher module is suppressed with dates less then 3 days from current date - One way search. RTC-4300, 4294
    Given I'm a guest user
    #At first Verify Trip Watcher exists with usual search
    Given I'm searching for a car from "<pick up>" to "<drop off>"
    And I want to travel between 3 days from now and 4 days from now
    And I request quotes
    Then I see a non-empty list of search results
    Then I see TripWatcher module
    And I choose a <type> car
    Then I see TripWatcher module
    # Now set new dates and verify Trip Watcher  is absent
    And I am on home page
    Given I'm searching for a car from "<pick up>" to "<drop off>"
    And I want to travel between 1 days from now and 3 days from now
    And I request quotes
    Then I see a non-empty list of search results
    Then I don't see TripWatcher module
    And I choose a <type> car
    Then I don't see TripWatcher module


  Examples: car rental locations
    | type         |pick up    |drop off   |
    | opaque       |SFO        |LAX        |
    | retail       |SFO        |LAX        |


  @US @ACCEPTANCE @ARCHIVE
  Scenario: Check TripWatcher module is suppressed with dates less then 3 days from current date - Round search. RTC-556
    Given I'm a guest user
  #At first Verify Trip Watcher exists with usual search
    And  I'm searching for a car in "SFO"
    And I want to travel between 3 days from now and 4 days from now
    And I request quotes
    Then I see a non-empty list of search results
    Then I see TripWatcher module
    And I choose a opaque car
    Then I see TripWatcher module
  # Now set new dates and verify Trip Watcher  is absent
    And I am on home page
    And I'm searching for a car in "SFO"
    And I want to travel between 1 days from now and 3 days from now
    And I request quotes
    Then I see a non-empty list of search results
    Then I don't see TripWatcher module
    And I choose a <type> car
    Then I don't see TripWatcher module


  @US @ACCEPTANCE
  Scenario: RTC-540 Hotel with Matching Type 1 Location & Date conditions
    Given customer without watched trips
    Given I authenticate myself
    When I am on hotel index page
    And  I'm searching for a hotel in "SFO"
    And  I want to travel in the near future
    And  I want 1 room(s)
    And  I will be traveling with 2 adults
    And  I will be traveling with 1 children
    And  I request quotes
    Then I will see opaque results page
  #if we want to see Trip Watcher layer we should make another search because the VT=SAL12 doesn't work
    And  I'm searching for a hotel in "SFO"
    And  I want to travel in the near future
    And  I want 1 room(s)
    And  I will be traveling with 2 adults
    And  I will be traveling with 1 children
    Then I do a hotel search from result page
  #
    Then I will see opaque results page
    Then I will see the trip watcher layer in the opaque results
    Then I want to watch this trip for current email on results
    Then I verify trip_type in DB is equals 1
    When I am on home page
    When I am on hotel index page
    And  I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation
    Then I verify trip_type in DB is equals 3 and active date is one day after end date

@US @ACCEPTANCE
Scenario: RTC-523 Hotel with NO Matching Type 1 Location & Date conditions
  Given customer without watched trips
  Given I authenticate myself
  When I am on hotel index page
  And  I'm searching for a hotel in "SFO"
  And  I want to travel in the near future
  And  I want 1 room(s)
  And  I will be traveling with 2 adults
  And  I will be traveling with 1 children
  And  I request quotes
  Then I will see opaque results page
#if we want to see Trip Watcher layer we should make another search because the VT=SAL12 doesn't work
  And  I'm searching for a hotel in "SFO"
  And  I want to travel in the near future
  And  I want 1 room(s)
  And  I will be traveling with 2 adults
  And  I will be traveling with 1 children
  Then I do a hotel search from result page
#
  Then I will see opaque results page
  Then I will see the trip watcher layer in the opaque results
  Then I want to watch this trip for current email on results
  Then I verify trip_type in DB is equals 1
  When I am on home page
  When I am on hotel index page
  And  I'm searching for a hotel in "LAX"
  And  I request quotes
  When I choose a hotel and purchase as guest a quote
  Then I receive immediate confirmation
  Then I verify trip_type in DB is equals 1
  When I access my account information
  Then I check the past trip in "San Francisco, CA" has been watched
  Then I stop watching all trips

  @US  @ACCEPTANCE
  Scenario: RTC-524 Car with Matching Type 1 Location & Date conditions
    Given customer without watched trips
    Given I authenticate myself
    When I am on car index page
    And  I'm searching for a car in "SFO"
    And  I want to travel in the near future
    And  I request quotes
    Then I see a non-empty list of search results
    Then I don't type email and watch the trip for authenticated user
    Then I verify trip_type in DB is equals 1
    When I am on home page
    When I am on car index page
    And  I request quotes
    And  I choose a opaque car
    When I fill in all billing information
    And  I complete purchase with agreements
    Then I receive immediate confirmation
    Then I verify trip_type in DB is equals 3 and active date is one day after end date

 @US  @ACCEPTANCE
 Scenario: RTC-525 Car with NO Matching Type 1 Location & Date conditions
  Given customer without watched trips
  Given I authenticate myself
  When I am on car index page
  And  I'm searching for a car in "SFO"
  And  I want to travel in the near future
  And  I request quotes
  Then I see a non-empty list of search results
  Then I don't type email and watch the trip for authenticated user
  Then I verify trip_type in DB is equals 1
  When I am on home page
  When I am on car index page
  And  I'm searching for a car in "LAX"
  And  I request quotes
  And  I choose a opaque car
  When I fill in all billing information
  And  I complete purchase with agreements
  Then I receive immediate confirmation
  Then I verify trip_type in DB is equals 1
  When I access my account information
  Then I check the past trip in "San Francisco Intl" has been watched
   Then I stop watching all trips
