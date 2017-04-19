@US
Feature: Status checks of cars
  RTC: 895, 896
  Owner: Vyacheslav Zyryanov

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @JAR @ACCEPTANCE
  Scenario Outline: 895 | Verify car status after booking
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose a <carType> car
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation
    When I access my account information
    Then I check the past car purchase in "<location>" has been <status>   

  Examples:
    | #    | carType   |  pickUpLocation |   location                              |  status   |
    | 895  | retail    |  BWI            |   Baltimore Washington Intl. car rental |  reserved |
    |      | opaque    |  SFO            |   San Francisco Intl. car rental        |  booked   |

  @ACCEPTANCE
  Scenario Outline: 896 | Retail car reservations that were cancelled - status
    Given I have valid random credentials
    And I authenticate myself
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose a <carType> car
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation
    When I access my account information
    Then I cancel the past car purchase in "<detailedLocation>"
    Then I receive immediate "<message>" successful message

  Examples:
    | #    | pickUpLocation          | detailedLocation                | carType   | message                               |
    | 896  | San Antonio, TX - (SAT) | San Antonio Intl. car rental    |  retail   | Your reservation has been cancelled.  |