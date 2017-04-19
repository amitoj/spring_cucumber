@US @SingleThreaded @JANKY
Feature: Failures of car booking
  "hotwire.eis.crs.rw.car.createPnrEnabled.simulateType" = "statusCodeFail"
  910, 911

  Scenario Outline:
    Given set property "hotwire.eis.crs.rw.car.createPnrEnabled.simulateType" to value "statusCodeFail"
    Given default dataset
    Given activate car's version tests
    Given the application is accessible
    Given I'm a guest user
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel in the near future
    And I request quotes
    When I choose a <carType> car and purchase as guest a quote
    Then the purchase was failed due of
      """
      We're sorry, but the itinerary you selected is no longer available.
      Your card has not been charged. We apologize for the inconvenience.
      You may retry your search by modifying your date, location or car type and we
      will search all other cars to find the best available rate.
      Note that this price is not guaranteed until the purchase is complete.
      Hotwire Customer Care Associates will not be able to assist you with this problem.
      """

  Examples: car rental locations
    | #    | pickUpLocation  | carType   |
    | 910  | CLE             |  opaque   |
    | 911  | SMF             |  retail   |

  Scenario:
    Then set property "hotwire.eis.crs.rw.car.createPnrEnabled.simulateType" to value "createPnr"