@US @ACCEPTANCE
Feature: Checking FareFinder validations on results page
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: Compare parameters of secondary search with trip summary block on billing page. RTC-6342
    Given I'm searching for a car in "New York City, NY - (JFK)"
    And I want to travel between 2 days from now and 6 days from now
    And I want to pick up at 5:00am and drop off at 3:00am
    And I request quotes
    Then I see a non-empty list of search results
    Then I choose a retail car
    Then Trip summary before booking is
      | New York John F Kennedy Intl. (JFK) | 2 days from now | 5:00am | 6 days from now | 3:00am |
    Given the application is accessible
    And I'm searching for a car in "San Francisco, CA - (SFO)"
    And I want to travel between 5 days from now and 17 days from now
    And I want to pick up at 8:00am and drop off at 6:00pm
    And I request quotes
    Then I see a non-empty list of search results
    Then I choose a opaque car
    Then Trip summary before booking is
      | San Francisco Intl. (SFO) | 5 days from now | 8:00am | 17 days from now | 6:00pm |


  Scenario Outline: Invalid location value. RTC-6687
    Given I'm searching for a car in "AIRPORT"
    And I want to travel between 5 days from now and 9 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I'm searching for a car in "<location>"
    And I make car search from result page
    Then I receive immediate "<error>" error message

  Examples:
    | location  | error                                                                                                                                                                         |
    |           | Pick up location is blank.                                                                                                                                                    |
    | xdsxdsxds | Where are you going? Please choose your location from the list. If your location is not listed, please check your spelling or make sure it is on our destination cities list. |


  Scenario: Test of rental times. RTC-6687
    Given I'm searching for a car in "AIRPORT"
    And I want to travel between 5 days from now and 9 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I'm searching for a car in "AIRPORT"
    And I want to travel between 5 days from now and 5 days from now
    And I want to pick up at 3:00pm and drop off at 2:00pm
    And I make car search from result page
    Then I receive immediate "Drop off time must be after the pick up time for same-day rentals." error message


  Scenario Outline: Different start and end date's shift of trip. RTC-6687
    And I'm searching for a car in "AIRPORT"
    And I want to travel between 5 days from now and 9 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I'm searching for a car in "AIRPORT"
    And I want to travel between <startDateShift> and <endDateShift>
    And I make car search from result page
    Then I receive immediate "<error>" error message

  Examples:car search options
    | startDateShift    | endDateShift      | error                                                                                      |
    | 340 days from now | 5 days from now   | Please enter new dates. Cars can only be reserved within the next 330 days.                |
    | 5 days from now   | 345 days from now | Drop off date is not valid. The drop off date must be within 60 days of your pick up date. |
    | 12 days from now  | 5 days from now   | Drop off date is not valid. The drop off date must be after the pick up date.              |
    | 7 days from now   | 70 days from now  | Drop off date is not valid. The drop off date must be within 60 days of your pick up date. |
