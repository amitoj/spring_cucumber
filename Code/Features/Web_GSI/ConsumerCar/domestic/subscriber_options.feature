@US @ACCEPTANCE
Feature: Subscriber options.

  Owner: Oleksandr Zelenov
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: Checking subscriber checkbox logic for guest user. RTC-6842.
    Given I'm a guest user
    When I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a retail car
    Then subscriber checkbox is checked
    When I refresh page
    Then subscriber checkbox is checked

  Scenario: Checking subscriber checkbox logic for newly registered user whose country is US.
    Given I am a new user
    When I create an account
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a retail car
    Then subscriber checkbox is unavailable

  Scenario: Checking subscriber checkbox logic for subscribed authorised user. RTC-6843.
    Given my name is subscribed_test@hotwire.com and my password is password
    And I authenticate myself
    When I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a retail car
    Then subscriber checkbox is unavailable