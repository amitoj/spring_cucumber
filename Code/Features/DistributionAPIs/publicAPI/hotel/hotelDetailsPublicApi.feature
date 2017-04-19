@API @DistributedOpaque
Feature: Public API Hotel Details.

  Background:
    Given I am Public API user

  @LIMITED   @JANKY
  Scenario: RTC - 4017: Hotel Search and Availability Check
    Given I send request to search hotels in SFO
    Then I get search results
    When I store first solution details
    And I request solutions availability
    Then I get solutions details
