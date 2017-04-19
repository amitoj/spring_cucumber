@US @ROW @ACCEPTANCE
Feature: Resort fees message display on hotel details page.
  As a user, I would like to see if there are any resort fees in the details page.

  Background:
    Given default dataset
    Given the application is accessible

  Scenario Outline: Allow the user to see resort fees on a solution's details page if a resort solution is selected from the results page.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose <resultstype> hotels tab on results
    And I choose sort by Star rating from high to low
    And I want to filter results by Amenities
    And I filter hotels with Casino
    And I want to filter results by Amenities
    And I choose a hotel that has "Strip" in its name
    Then I will see the resort fees on the details page

  Examples:
    | destinationLocation  | startDateShift | endDateShift | resultstype |
    | Las Vegas, NV        | 7              | 10           | opaque      |

  Scenario Outline: Allow the user to see resort fees on a solution's details page if a resort solution is selected from the results page.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose <resultstype> hotels tab on results
    And I choose sort by Star rating from high to low
    And I choose a retail resort result
    Then I will see the resort fees on the details page

  Examples:
    | destinationLocation  | startDateShift | endDateShift | resultstype |
    | Las Vegas, NV        | 7              | 10           | retail      |

  @ARCHIVE
  Scenario Outline: The user will not see resort fees when no resort is present.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose <resultstype> hotels tab on results
    And I choose a hotel result
    Then I will not see the resort fees on the details page

  Examples:
    | destinationLocation  | startDateShift | endDateShift | resultstype |
    | San Francisco, CA    | 7              | 9            | opaque      |
    | Joshua Tree, CA      | 7              | 9            | retail      |
