@API @DistributedOpaque
Feature: Public API Hotel Deeplinks validation.

  Background:
    Given I am Public API user

  @ACCEPTANCE
  Scenario: Validate hotel Deeplink
    Given I send request to search hotels in SFO
    Then I get search results
    When I open the deeplink
    Then I get details page html

