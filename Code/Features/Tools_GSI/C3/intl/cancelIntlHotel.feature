@TOOLS  @c3Intl
Feature: Cancellation of International hotel purchase in C3
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @LIMITED
  Scenario: International hotel purchase cancellation
    Given customer INTL hotel purchase
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    When I cancel itinerary
    Then I see "itinerary has been cancelled in the GDS" in confirmation message
    And I see purchase status is "Cancelled"