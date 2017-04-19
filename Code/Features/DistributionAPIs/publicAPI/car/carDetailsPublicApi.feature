@API @DistributedOpaque
Feature: Public API Car Details verification.

  Background:
    Given I am Public API user

  @ACCEPTANCE
  Scenario: RTC - 4019. Car Search and Availability Check
    Given I send request to search cars in Statue of Liberty
    Then I get search results
    When I store first solution details
    And I request solutions availability
    Then I get solutions details
