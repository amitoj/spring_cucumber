@US
Feature: Hotel Results Tabs
    Test opaque and retail tabs of hotel results.
    
    Background:
        Given default dataset
        Given the application is accessible
        
    Scenario Outline: Hotel results toggle between opaque and retail.
        Given I'm searching for a hotel in "<destinationLocation>"
        And I want to travel between <startDateShift> days from now and <endDateShift> days from now
        And I want <numberOfHotelRooms> room(s)
        And I request quotes
        Then I will see opaque results page
        And I choose retail hotels tab on results
        Then I will see retail results page
        And I choose opaque hotels tab on results
        Then I will see opaque results page
        
    Examples:
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms |
      | San Francisco, CA   | 3              | 5            | 1                  |
    