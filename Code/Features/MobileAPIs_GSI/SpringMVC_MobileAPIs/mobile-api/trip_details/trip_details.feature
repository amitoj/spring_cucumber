@MobileApi
Feature: trip details endpoint tests

  @LIMITED
  Scenario: verify hotel trip details response
    Given I am logged in as random user with booked trips
    And I have unique oauth token for this customer
    When I execute trip details request for hotel itinerary for random user
    Then trip details response is present
    And I see itinerary number is present in trip details
    And I see booking status is present in trip details
    And I see product vertical is hotel
    And I see booking dates and location are present in trip details
    And I see address and latitude/longitude are present in trip details
    And I see charges are present in trip details

  @LIMITED
  Scenario: verify car trip details response
    Given I am logged in as random user with booked trips
    And I have unique oauth token for this customer
    When I execute trip details request for car itinerary for random user
    Then trip details response is present
    And I see itinerary number is present in trip details
    And I see booking status is present in trip details
    And I see product vertical is car
    And I see booking dates and location are present in trip details
    And I see car reservation details are present in trip details
    And I see charges are present in trip details

  Scenario: login as payable user, check hotel trip details response
    Given I am logged in as payable user
    And I have unique oauth token for this customer
    When I execute trip details request for hotel itinerary for payable user
    Then trip details response is present
    And I see itinerary number is present in trip details
    And I see booking status is present in trip details
    And I see product vertical is hotel
    And I see booking dates and location are present in trip details
    And I see address and latitude/longitude are present in trip details
    And I see charges are present in trip details

  Scenario: login as payable user, check car trip details response
    Given I am logged in as payable user
    And I have unique oauth token for this customer
    When I execute trip details request for car itinerary for payable user
    Then trip details response is present
    And I see itinerary number is present in trip details
    And I see booking status is present in trip details
    And I see product vertical is car
    And I see booking dates and location are present in trip details
    And I see car reservation details are present in trip details
    And I see charges are present in trip details


  @ACCEPTANCE
  Scenario Outline: verify trip details response is absent for random itinerary
    Given I am logged in as random user with booked trips
    And I have unique oauth token for this customer
    When I execute trip details request for random <vertical> itinerary
    Then trip details response is not present

  Examples:
    | vertical   |
    | hotel      |
    | car        |
    | air        |
