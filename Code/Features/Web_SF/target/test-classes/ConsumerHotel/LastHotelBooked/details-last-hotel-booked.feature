Feature: Last Booked Hotel message display on opaque hotel details page.
  As a user, I would like clues into what hotel I could be getting on the opaque details page.

  Background:
    Given default dataset
    Given the application is accessible

  # Need to handle data issue to unJANKY this test
  @US @ROW @JANKY
  Scenario Outline: Allow the user to see the last hotel booked in a solution's details page.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose opaque hotels tab on results
    And I choose a hotel result
    Then I will see the last hotel booked on the opaque details page

  Examples:
    | destinationLocation  | startDateShift | endDateShift |
    | San Francisco, CA    | 3              | 5            |
