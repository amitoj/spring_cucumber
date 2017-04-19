@ANGULAR
Feature: Hotel Search through booking happy path on Angular site.
   As a price hunter, I'd like to be able to search for hotels that are located at my destination so that
   I can book a hotel I choose.

  Background: 
    Given default dataset
    Given the angular application is accessible
    Given the application is accessible

@ANGULAR @ACCEPTANCE
  Scenario Outline: Angular results filtering by Star Rating, Amenities, etc.
    # Given I want to go to the Hotels angular landing page
    # Given I'm searching for a hotel in "<destinationLocation>"
    # And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  And I want <numberOfHotelRooms> room(s)
    #  And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I request angular quotes
    And I see angular hotel results
    When I filter angular results with <value> <filterType>
    Then The angular results will only contain <value> <filterType> solutions
    And The angular results count will be correct for <value> <filterType>

    Examples: 
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | value           | filterType  |
      | Chicago, IL         | 3              | 5            | 2                  | 3               | Star Rating |
      | Chicago, IL         | 3              | 5            | 2                  | 2               | Star Rating |
      | Joshua Tree, CA     | 3              | 5            | 2                  | Free Breakfast  | Amenities   |
      | Joshua Tree, CA     | 3              | 5            | 2                  | Business Center | Amenities   |

  @ANGULAR @LIMITED
  Scenario Outline: Angular results filtering by Accessibility Amenities
    #  Given I want to go to the Hotels angular landing page
    #  Given I'm searching for a hotel in "<destinationLocation>"
    #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  And I want <numberOfHotelRooms> room(s)
    #  And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I request angular quotes
    And I see angular hotel results
    When I filter angular results with <value> <filterType>
    And I filter angular hotel results by Star Rating
    And I book the first hotel in the angular results
    Then The angular details page will contain <value> <filterType>
    When I book the hotel in the angular details page
    And I select the accessibilty needs
    Then I see accessibility options

    Examples: 
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | value               | filterType            |
      | Irvine, CA          | 3              | 5            | 2                  | Accessible bathroom | Accessibility Options |

 @ANGULAR @ACCEPTANCE
  Scenario Outline: Angular results filtering by Areas
    # Given I want to go to the Hotels angular landing page
    # Given I'm searching for a hotel in "<destinationLocation>"
    #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    When I filter angular hotel results by Areas
    Then The angular results will be filtered by Areas
    And The angular results count will be correct for <filterType>
    When I reset the Areas filter
    Then The angular results will not be filtered by Areas

    Examples: 
      | destinationLocation | startDateShift | endDateShift | filterType |
      | Chicago, IL         | 3              | 5            | Areas      |

  @ANGULAR @ACCEPTANCE
  Scenario Outline: Angular results Areas filter options with 0 count are disabled.
    #  Given I want to go to the Hotels angular landing page
    #   Given I'm searching for a hotel in "<destinationLocation>"
    #   And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #   And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    And I see angular hotel results
    When I filter angular results with Accessible bathroom Accessibility Options
    Then All filter options for <filterType> with zero count will be disabled

    Examples: 
      | destinationLocation | startDateShift | endDateShift | filterType  |
      | Irvine, CA          | 3              | 5            | Areas       |
      | Irvine, CA          | 4              | 5            | Star Rating |
      | Irvine, CA          | 5              | 7            | Amenities   |

  @ANGULAR @ACCEPTANCE
  Scenario Outline: Angular results filtering with Reset filters link
    #   Given I want to go to the Hotels angular landing page
    #  Given I'm searching for a hotel in "<destinationLocation>"
    #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    And I do not see Reset filters link
    When I filter angular hotel results by <filterType>
    Then The angular results will be filtered by <filterType>
    And I do see Reset filters link
    When I Reset filters
    Then The angular results will not be filtered by <filterType>
    And I do not see Reset filters link

    Examples: 
      | destinationLocation | startDateShift | endDateShift | filterType  |
      | Chicago, IL         | 3              | 5            | Areas       |
      | Chicago, IL         | 3              | 5            | Star Rating |
      | Chicago, IL         | 3              | 5            | Amenities   |
