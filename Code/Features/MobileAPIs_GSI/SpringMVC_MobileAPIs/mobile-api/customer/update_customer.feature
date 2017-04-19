@MobileApi
Feature: update customer profile with API

  Scenario Outline: update customer email subscriptions
    Given I am logged in as random user with booked trips
    When I update customer email subscriptions to <subscriptionsValue>
    When I execute retrieve profile request
    Then email subscriptions data is correct

  Examples:
    | subscriptionsValue |
    | all true           |
    | all false          |

  Scenario: update customer general info
    Given I am logged in as random user with booked trips
    When I update customer general info
    When I execute retrieve profile request
    Then customers general info is correct

  Scenario: update customer payment card billing address
    Given I am logged in as payable user
    And I execute retrieve profile request for payment data
    When I update customers card billing info
    And I execute retrieve profile request for payment data
    Then customers payment data is correct

  Scenario: update customer favorite airport
    Given I am logged in as random user with booked trips
    When I update customers favorite airport
    And I execute retrieve profile request for favorite airport
    Then customers favorite airport data is correct

  Scenario: delete customers favorite airport
    Given I am logged in as random user with booked trips
    When I delete customers favorite airport
    And I execute retrieve profile request for favorite airport
    Then customer does not have favorite airport

  Scenario: update customer's traveler info
    Given I am logged in as random user with booked trips
    When I update customers traveler info
    And I execute retrieve profile request for traveler info
    Then customers primary traveler info is correct