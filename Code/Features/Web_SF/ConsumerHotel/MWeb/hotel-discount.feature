@MOBILE @ACCEPTANCE
Feature: Hotel discount on a mobile site.
  Discount is activated by entering a mobile site using URL with 'dccid' parameter containing encrypted discount hash.
  Discount banner should appear on results, details and billing pages.

  Background:
    Given a valid hotel discount code
    Given the application is accessible

  Scenario: Verify discount banners as a guest user
    And I'm searching for a hotel in "San Francisco, CA"
    And I want to travel in the near future
    And I request quotes
    Then I see the discount information on the hotel results
    And I choose a hotel result
    Then I see the discount information on the hotel details
    When I review the itinerary as a guest user
    Then I see the discount information on the guest itinerary

  Scenario: Verify discount banners as a registered user
    Given I have valid credentials
    And I'm searching for a hotel in "San Francisco, CA"
    And I want to travel in the near future
    And I request quotes
    Then I see the discount information on the hotel results
    And I choose a hotel result
    Then I see the discount information on the hotel details
    When I review the itinerary as a registered user
    Then I see the discount information on the registered itinerary