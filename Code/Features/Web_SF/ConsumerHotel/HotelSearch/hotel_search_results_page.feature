@US
Feature: Change search dates from hotel results page.
  Hotel search from hotel results page.

  Background: 
    Given default dataset
    Given the application is accessible

  Scenario Outline: RTC-1421: Error when check-out date is before check-in date for Hotel search from Results
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I request quotes
    And I should see hotel search results page
    When I set hotel dates between  <newStartDateShift> days from now and <newEndDateShift> days from now
    And I start hotel search without specifying the destination
    Then I should see hotel search results page
    And I receive immediate "Enter a valid check-in date." error message
    And I receive immediate "Enter a valid check-out date." error message
    And I receive immediate "The check-out date should be after the check-in date" error message

    Examples: 
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | newStartDateShift | newEndDateShift |
      | San Francisco, CA   | 3              | 5            | 1                  | 10                | 8               |

  @ACCEPTANCE
  Scenario: Check-in date is more than 330 days from current date. RTC-1130
    Given I want to go to the Hotels landing page
    And I'm searching for a hotel in "Miami, FL"
    And I want to travel between 0 days from now and 331 days from now
    And I launch a search
    Then Check-out field prepopulated with 330 days from now

 @ACCEPTANCE
  Scenario: Distance listed in each Result for Address-Specific Hotel searches. RTC-4028
    Given I want to go to the Hotels landing page
    And I'm searching for a hotel in "1201 Mason St, San Francisco, CA 94108"
    And I want to travel between 1 days from now and 3 days from now
    And I launch a search
    Then I check distances on hotel results

  #Author: Cristian Gonzalez Robles
  @ACCEPTANCE
  Scenario: RTC-656: Hotel - Browser Back Details Page
    Given I'm searching for a hotel in "New York (NYC), New York, United States of America"
    And I want to travel between 5 days from now and 8 days from now
    And I am looking for a hotel
    And I choose "Midtown Central area hotel" hotel
    When I hit the browser back button
    Then I will see opaque results page
