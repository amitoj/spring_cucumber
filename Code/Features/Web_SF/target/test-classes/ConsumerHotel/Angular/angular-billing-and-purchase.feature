@ANGULAR
Feature: Hotel Search through booking happy path on Angular site.
   As a price hunter, I'd like to be able to search for hotels that are located at my destination so that
   I can book a hotel I choose.

  Background: 
    Given default dataset
    Given the angular application is accessible
    Given the application is accessible

 #@US @ROW @LIMITED @JANKY
 #Scenario Outline: Find and purchase a hotel room as a guest user with Angular VT enabled but don't go through angular flow.
 #   Given I'm a guest user
 #   And I'm searching for a hotel in "<destinationLocation>"
 #   And I want to travel between <startDateShift> days from now and <endDateShift> days from now
 #   And I request quotes
 #   And I choose <resultstype> hotels tab on results
 #   When I choose a hotel and purchase as guest a quote
 #   Then I receive immediate confirmation

 #  Examples: opaque quotable fares parameters
 #     | state | destinationLocation | startDateShift | endDateShift | resultstype |
 #     | is    | Charleston, SC      | 30             | 35           | opaque      |
     # | is    | Paris, France       | 30             | 35           | retail      |

@ANGULAR @ACCEPTANCE
  Scenario Outline: Angular search and book to billing page
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
  Then I see angular hotel results
   And I book the first hotel in the angular results
    And I will see the angular details page
    When I book the hotel in the angular details page
    Then I will see the hotel billing page from the angular details page

   Examples: 
      | destinationLocation | startDateShift | endDateShift |
      | Chicago, IL         | 3              | 5            |

@ANGULAR @SMOKE @JANKY
 Scenario Outline: Complete purchase in angular
   #Given I'm a guest user
  #  And I'm searching for a hotel in "<destinationLocation>"
  #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
  #  And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
   And I want to travel between <startDateShift> days from now and <endDateShift> days from now
   And I request angular quotes
    And I book the first hotel in the angular results
    And I book the hotel in the angular details page
   When I purchase a hotel as guest a quote from angular site
    Then I receive immediate confirmation

   Examples: 
      | destinationLocation | startDateShift | endDateShift |
      | Chicago, IL         | 3              | 5            |
