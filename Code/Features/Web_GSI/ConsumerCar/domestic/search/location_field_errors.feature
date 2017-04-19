@US @ACCEPTANCE
Feature: Cars - Search - Location field tests
  RTC-7, RTC-8

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: RTC-7, RTC-8 | Location is left blank
    And I am on car <pageType> page
    And I'm searching for a car in "<location>"
    And I want to travel in the near future
    And I request quotes
    Then I receive immediate "<error>" error message

  Examples:
    | #     | location  | pageType | error                                                                                                                                                                         |
    | RTC-7 |           | index    | Pick up location is blank.                                                                                                                                                    |
    | RTC-7 |           | landing  | Pick up location is blank.                                                                                                                                                    |
    | RTC-8 | xdsxdsxds | index    | Where are you going? Please choose your location from the list. If your location is not listed, please check your spelling or make sure it is on our destination cities list. |
    | RTC-8 | xdsxdsxds | landing  | Where are you going? Please choose your location from the list. If your location is not listed, please check your spelling or make sure it is on our destination cities list. |