@US @SingleThreaded
Feature: car search tests that require any property changes
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: Local Car: Global On/Off switch. RTC-826
    Given set property "hotwire.biz.search.localCar.isEnabled" to value "false"
    When I'm searching for a car in "Dublin, CA"
    And I want to travel in the near future
    And I request quotes
    Then I see non-empty Refine your search module

  Scenario: Roll back changes
    Given set property "hotwire.biz.search.localCar.isEnabled" to value "true"

  @ACCEPTANCE
  Scenario: RTC-796 Search radius
    Given set property "hotwire.biz.search.car.maximumDistanceToLocalAirportInMiles" to value "2"
    Given I'm searching for a car in "Concord, CA"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    Then I see that "Include nearby airport" module is not present

  @ACCEPTANCE
  Scenario: Roll back changes
    Given set property "hotwire.biz.search.car.maximumDistanceToLocalAirportInMiles" to value "20"

