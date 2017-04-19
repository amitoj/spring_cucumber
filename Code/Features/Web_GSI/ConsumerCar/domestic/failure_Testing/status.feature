@US @SingleThreaded @JANKY
Feature: Failures of car booking
  "hotwire.eis.crs.rw.car.createPnrEnabled.simulateType" = "status"
  916, 917

  Scenario Outline:
    Given set property "hotwire.eis.crs.rw.car.createPnrEnabled.simulateType" to value "status"
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
      We're sorry, but we are experiencing difficulties exchanging information with our
      car rental partners and are temporarily unable to complete your transaction.
      Your credit card has not been charged. We apologize for the inconvenience.

      Please retry this purchase in 10 minutes. In the meantime, our flight and hotel services are still available.

      Note that this price is not guaranteed until the purchase is complete.
      Hotwire Customer Care Associates will not be able to assist you with this problem. Error #109
      """

  Examples: car rental locations
    | #    | pickUpLocation  | carType   |
    | 916  | MKE             |  opaque   |
    | 917  | SMF             |  retail   |

  Scenario:
    Then set property "hotwire.eis.crs.rw.car.createPnrEnabled.simulateType" to value "createPnr"
