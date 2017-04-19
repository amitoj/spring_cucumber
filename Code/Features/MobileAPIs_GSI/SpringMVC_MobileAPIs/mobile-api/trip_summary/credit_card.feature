@MobileApi
Feature: Mobile API add credit card endpoint

  @ACCEPTANCE
  Scenario: add and delete random credit card
    Given I am logged in as random user with booked trips
    And I have unique oauth token for this customer
    And I add new credit card with random credentials
    And I see card was added
    When I execute trip summary request with sorting by default
    Then trip summary is present
    Then I see random card is present in Trip Summary response
    And I want to get information about payment cards in customer's profile
    Then customer profile response is present
    And I see random card is present in Customer Profile response
    And I delete added random credit card
    Then I see deleting of credit card was successful

  Scenario Outline: try to add invalid random credit card
    Given I am logged in as random user with booked trips
    And I have unique oauth token for this customer
    And I try to add new credit card with invalid <failCriteria>
    And I see card was not added

  Examples:
    | failCriteria      |
    | number            |
    | expiration date   |