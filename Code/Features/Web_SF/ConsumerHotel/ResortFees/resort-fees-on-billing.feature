@US @ACCEPTANCE
Feature: Resort fees message display on billing page.
  As a user, I would like to see if there are any resort fees in the details page.

  Background:
    Given default dataset
    Given the application is accessible
  @JANKY
  Scenario Outline: Allow the user to see resort fees for opaque solution on the trip summary of the billing page .
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose <resultstype> hotels tab on results
    And I choose sort by Star rating from high to low
    And I want to filter results by Amenities
    And I filter hotels with Casino
    And I want to filter results by Amenities
    And I choose a hotel that has "Strip" in its name
    And I book that hotel
    Then I will see the resort fees on the billing page trip summary

  Examples:
    | destinationLocation  | startDateShift | endDateShift | resultstype |
    | Las Vegas, NV        | 7              | 10           | opaque      |

  Scenario Outline: Allow the user to see resort fees for retail solution on the trip summary of the billing page.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose <resultstype> hotels tab on results
    And I choose sort by Star rating from high to low
    And I choose a retail resort result
    And I book that hotel
    Then I will see the resort fees on the billing page trip summary

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
    And I book that hotel
    Then I will not see the resort fees on the billing page trip summary

  Examples:
    | destinationLocation  | startDateShift | endDateShift | resultstype |
    | San Francisco, CA    | 7              | 9            | opaque      |
    | Joshua Tree, CA      | 7              | 9            | retail      |
