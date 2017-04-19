@US @ROW @ACCEPTANCE
Feature: Hotel Results SortBy
    Sorting results from results pages.

    Background:
      Given default dataset
      Given the application is accessible

    Scenario Outline: Sort results by various criteria. RTC-1425, 1426
      Given I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I want <numberOfHotelRooms> room(s)
      And I request quotes
      And I choose <resultstype> hotels tab on results
      When I choose sort by <sortBy> <orderBy>
      Then I will see results sorted by <sortBy> <orderBy>

    Examples:
      | destinationLocation       | startDateShift | endDateShift | numberOfHotelRooms | resultstype | sortBy       | orderBy          |
      | San Francisco, CA         | 3              |  5           | 1                  | opaque      | Star rating  | from high to low |
      | Chicago, IL               | 3              |  5           | 1                  | retail      | Star rating  | from high to low |
      | San Francisco, CA         | 3              |  5           | 1                  | opaque      | Star rating  | from low to high |
      | New York, NY              | 3              |  5           | 1                  | retail      | Star rating  | from low to high |
      | San Francisco, CA         | 3              |  5           | 1                  | opaque      | Price        | from high to low |
      | Boston, MA                | 3              |  5           | 1                  | retail      | Price        | from high to low |
      | San Francisco, CA         | 3              |  5           | 1                  | opaque      | Price        | from low to high |
      | Miami, FL                 | 3              |  5           | 1                  | retail      | Price        | from low to high |
      | San Francisco, CA - (SFO) | 3              |  5           | 1                  | opaque      | Distance     |                  |
      | Chicago, IL - (ORD)       | 3              |  5           | 1                  | retail      | Distance     |                  |
