@API @DistributedOpaque
Feature: Private Hotel API Search.

  Background:
    Given I am Private API user

  @STBL @WIP
  Scenario: Hotel Search for Expedia Partner
    Given I send request to search hotels in DCA
    Then I get search results
