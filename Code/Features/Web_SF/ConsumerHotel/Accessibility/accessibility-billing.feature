Feature: Hotel accessibility purchase - Billing page.
  As a user I would like to be presented with accessibility options for a hotel.

  Background:
    Given default dataset
    Given the application is accessible

  @US @MOBILE @ACCEPTANCE 
  Scenario Outline: Allow the user to book a hotel with accessibility options.
    Given I am logged in
    And I am authenticated
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose an accessibility friendly hotel
    When I book that hotel
    And I select the accessibilty needs
    Then I see accessibility options
    
  @CLUSTER3
  Examples:
    | destinationLocation  | startDateShift | endDateShift   |
    | San Francisco, CA    | 21             | 26             |
    
  
