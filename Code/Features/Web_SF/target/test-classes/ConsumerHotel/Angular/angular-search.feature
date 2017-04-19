@ANGULAR
Feature: Hotel Search through booking happy path on Angular site.
   As a price hunter, I'd like to be able to search for hotels that are located at my destination so that
   I can book a hotel I choose.

  Background: 
    Given default dataset
    Given the angular application is accessible
    Given the application is accessible

  @US @BUG53588 @SMOKE
  Scenario: 
    Then the old hotel index page is accessible

@ANGULAR @ACCEPTANCE
  Scenario Outline: Angular search
     #Given I want to go to the Hotels angular landing page
    # Given I'm searching for a hotel in "<destinationLocation>"
    #And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    # And I want <numberOfHotelRooms> room(s)
    # And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I request angular quotes
    Then I see angular hotel results
    And The map is loaded on angular results
    And I book the first hotel in the angular results
    And I will see the angular details page
    And The map is loaded on angular details

    Examples: 
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms |
      | Chicago, IL         | 3              | 5            | 2                  |

 # @US @SingleThreaded @ACCEPTANCE
 # Scenario Outline: Angular search with autocomplete
   # Given I want to go to the Hotels angular landing page
    #Given I'm searching for a hotel with autocomplete in "<destinationLocation>"
    #And I enter travel time between <startDateShift> days from now and <endDateShift> days from now
    #And I select <numberOfHotelRooms> room(s)
   # And I launch angular search
  #  Given I'm searching for a hotel in "<destinationLocation>"
  #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
  #  And I select <numberOfHotelRooms> room(s)
  #  And I request quotes
  #  Then I see angular hotel results
  #  And I book the first hotel in the angular results
  #  And I will see the angular details page

   # Examples: 
   #   | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms |
   #   | Chicago, IL         | 3              | 5            | 2                  |

 # @US @LIMITED @JANKY
 #Scenario Outline: Angular search on Angular results page.
    #  Given I want to go to the Hotels angular landing page
    #  Given I'm searching for a hotel in "<destinationLocation>"
    #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  And I want <numberOfHotelRooms> room(s)
    #  And I request angular quotes
   #Given I'm searching for a hotel in "<destinationLocation>"
   #And I want to travel between <startDateShift> days from now and <endDateShift> days from now
   #And I want <numberOfHotelRooms> room(s)
  # And I request quotes
  # Then I see angular hotel results

  # Examples: 
  #  | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | newDestinationLocation |
  #  | Chicago, IL         | 3              | 5            | 2                  | New York City, NY      |
