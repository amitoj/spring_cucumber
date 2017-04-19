@US @JANKY
Feature: guestPurchase
  Guest Purchase section

  Background:
    Given default dataset
    Given the application is accessible

  Scenario:
    Given I'm a guest user
    And I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 10 days from now and 11 days from now
    And I want 2 room(s)
    And I will be traveling with 2 adults
    And I will be traveling with 1 children
    And I request quotes
    And I choose a hotel result
    And I book that hotel
    And I choose insurance
    And I check that insurance cost depends on number of rooms
    And I purchase as guest a quote


