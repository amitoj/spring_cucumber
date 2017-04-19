@API @DistributedOpaque
Feature: Public API Car Deeplink verification.

  Background:
    Given I am Public API user

  @ACCEPTANCE
  Scenario: Validate Car Deeplink
    Given I send request to search cars in San Francisco
    Then I get search results
    When I open the deeplink
    Then I get details page html
  
