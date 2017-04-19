@ACCEPTANCE
Feature: Hotel property search Happy Path
   As a user, I want to search by a specific property.

  Background:
    Given default dataset
    Given the application is accessible

  @US @MOBILE @JANKY
  Scenario Outline: Property search from UHP
    # Mobile is not ready yet. Issues need to be worked out by backend devs.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request property quotes
    Then I see the featured hotel in the results

  Examples: hotel search parameters
       | destinationLocation                            | startDateShift | endDateShift   |
       | Fairmont Super 8                               | 34             | 35             |

  @US @ROW
  Scenario Outline: Property search from Hotel landing pages.
    Given I want to go to the Hotels landing page
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request property quotes
    Then I see the featured hotel in the results

  Examples: hotel search parameters
       | destinationLocation                            | startDateShift | endDateShift   |
       | Fairmont Super 8                               | 34             | 35             |
