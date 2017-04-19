@API @DistributedOpaque
Feature: Public API Hotel Neighborhood Search.

  Background:
    Given I am Public API user

  Scenario: RTC-4911 Neigborhood Api by Airport Code
    Given I request neighborhoods in SFO
    Then I get neighborhoods info
