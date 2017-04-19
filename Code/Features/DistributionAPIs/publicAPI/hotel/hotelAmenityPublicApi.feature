@API @DistributedOpaque
Feature: Public API Hotel Amenity verification.

  Background:
    Given I am Public API user

  Scenario: RTC-4035 : Amenity API for Hotels
    Given I request list of hotel amenities
    Then I get amenities info
