Feature: Hotel Search And Purchase (Happy Path) with paypal
  Let customer search for and purchase cars.

 Background: 
    Given default dataset
    Given the application is accessible

  #https://jira/jira/browse/HLES-611 @bshukaylo
 @US @ACCEPTANCE @BUG
  Scenario Outline: Find and purchase a hotel room as a guest user using paypal.  
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    When I choose a hotel and purchase with PayPal as guest a quote
    And I confirm booking on PayPal sandbox
    Then I receive immediate confirmation

  Examples: quotable fares parameters
    | destinationLocation         | startDateShift | endDateShift   | numberOfHotelRooms | numberOfAdults | numberOfChildren |
    | San Francisco, CA           | 30             | 31            | 1                  | 2              | 1                |
