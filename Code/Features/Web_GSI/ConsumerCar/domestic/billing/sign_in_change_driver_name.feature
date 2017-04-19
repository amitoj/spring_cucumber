@US @ACCEPTANCE
Feature: Change primary driver first and last name for authenticated user.
  Owner: Nataliya Golodiuk

  Background: 
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: user is able to book a car with one of the existing primary driver first and last name
    #    RTC-6606
    Given I'm a payable user
    And I authenticate myself
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    When I request quotes
    And I see a non-empty list of search results
    And I choose a retail car
    Then traveler information is populated
    And I choose driver name from existing drivers
    When I fill in all billing information
    And I complete purchase with agreements
    And I receive immediate confirmation
    And driver name on the confirmation page is new

  @JANKY
  Scenario: user is able to book a car with a new primary driver first and last name
    #    RTC-6606
    Given my name is primarydriverstest@hotwire.com and my password is password
    #This user is expected to have Test Booking primary driver name
    And I authenticate myself
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    When I request quotes
    And I see a non-empty list of search results
    And I choose a retail car
    Then traveler information is populated
    When I change my primary driver to "New Driver"
    When I fill in all billing information
    And I complete purchase with agreements
    And I receive immediate confirmation
    And driver name on the confirmation page is new
