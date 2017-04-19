@US
Feature: Car addon
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  #BUG53786 - Flight w/ the car addon cannot be purchased
  @ARCHIVE
  Scenario: car addon for flight with vt.FPC02 - RTC6821
    Given set version test "vt.FPC02" to value "2"
    And I'm a guest user
    When I'm searching for "AC" vacation from "SFO" to "SEA"
    And I want to travel in the near future
    And I will be flying with 1 passengers
    And I request quotes
    When I choose retail flight and added rental car to purchase as guest
  #  When I choose retail flight with rental car as package
    Then I receive immediate confirmation

  @ACCEPTANCE @ARCHIVE
  Scenario: Verify car addon module doesn't exist for special airport. RTC-882
    Given I'm searching for a flight from "SFO" to "TAO"
    And   I want to travel in the near future
    Then  I request quotes
    And   I choose retail flight
    When  I verify car addon module doesn't exist on the page
