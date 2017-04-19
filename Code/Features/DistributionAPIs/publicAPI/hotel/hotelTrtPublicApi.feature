@API @DistributedOpaque
Feature: Public API hotel TripStarter history search

  Background:
    Given I am Public API user

  @LIMITED @STBL
  Scenario: Hotel TRT - RTC-3928
    Given I request hotel trt in SFO
    Then I get hotel trt info