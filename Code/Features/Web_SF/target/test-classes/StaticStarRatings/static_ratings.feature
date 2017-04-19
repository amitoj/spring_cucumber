@US
Feature: Static star ratings and Amenities on both user site and admin

  Background: 
    Given default dataset
    Given the application is accessible
    Given the application window is maximized

  @STBL @ACCEPTANCE
  Scenario: RTC-1335 Ensure that a user only sees the star rating and amenities that they saw at the time of purchase
    Given my name is qa_regression@hotwire.com and my password is hotwire333
    And I authenticate myself
    And I am authenticated
    And I'm searching for a hotel in "New York"
    And I request quotes
    And I choose a hotel result
    And I book selected hotel
    And I complete a hotel as user a quote
    Given I update opaque amenity codes in DB for recent purchase
    And I want to access my trips
    When I select trip details from the first hotel trip summary
    Then I verify that the listed amenities remained unchanged
