@DistributedOpaque
Feature: API Cucumber integration test.

  Background:
    Given I am Public API user

  Scenario: Hotel Search - Airport code (SFO)
    Given I send request to search hotels in SFO