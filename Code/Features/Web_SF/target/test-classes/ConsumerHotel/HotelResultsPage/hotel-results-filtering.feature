@US @ROW @ACCEPTANCE
Feature: Hotel Results Filtering
    Hotel results pages filtering tests.

  Background:
    Given default dataset
    Given the application is accessible

  Scenario Outline: Verify visibility of Reset filters
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    And I want to filter results by <filtering-type>
    And I choose the recommended <filtering-type> filter value
    And Reset filters will be seen
    When I click Reset filters
    Then Reset filters will not be seen

  Examples:
    | destinationLocation  | startDateShift | endDateShift | resultstype | filtering-type   |
    # QACI data issue for price. Every result is below filtering threshold so reset all will not display.
    #| San Francisco, CA    | 3              |  5           | opaque      | Price            |
    | San Francisco, CA    | 3              |  5           | opaque      | Star rating      |
#    | San Francisco, CA    | 3              |  5           | retail      | Amenities        |
    | San Francisco, CA    | 3              |  5           | opaque      | Areas            |

  Scenario Outline: Verify Reset filters.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    And I want to filter results by <filtering-type>
    And I choose the recommended <filtering-type> filter value
    And I get the filtered search results
    When I click Reset filters
    And I get search results
    Then search results are not filtered

  Examples:
    | destinationLocation  | startDateShift | endDateShift | resultstype | filtering-type   |
    # QACI data issue for price. Every result is below filtering threshold so reset all will not display.
    #| San Francisco, CA    | 3              |  5           | opaque      | Price            |
    | San Francisco, CA    | 3              |  5           | opaque      | Star rating      |
    | San Francisco, CA    | 3              |  5           | opaque      | Amenities        |
    | San Francisco, CA    | 3              |  5           | opaque      | Areas            |

    Scenario Outline: Filter results by hotel features.
      Given I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I want <numberOfHotelRooms> room(s)
      And I request quotes
      And I choose <resultstype> hotels tab on results
      And I want to filter results by Amenities
      When I filter hotels with <feature>
      Then I will see that all results will have <feature>

    Examples:
      | destinationLocation       | startDateShift | endDateShift   | numberOfHotelRooms | resultstype | feature         |
      | San Francisco, CA - (SFO) | 3              |  5             | 1                  | opaque      | Free Breakfast  |
      # Data issue with retail and any amenities causing 0 results
      | Chicago, IL               | 3              |  5             | 1                  | retail      | Free Parking    |

    Scenario Outline: Choose a hotel that has free features and amenities in opaque
      Given I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I request quotes
      And I choose opaque hotels tab on results
      And I want to filter results by Amenities
      When I filter hotels with <feature>
      And I choose a hotel result
      Then I will see <feature> highlighted in the hotel details page

    Examples:
      | destinationLocation   | startDateShift | endDateShift   | feature         |
      | San Francisco, CA     | 3              |  5             | Free Breakfast  |

    Scenario Outline: Verify filtering of results by price and star rating.
      Given I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I request quotes
      And I choose <resultstype> hotels tab on results
      And I want to filter results by <filtering-type>
      When I choose the recommended <filtering-type> filter value
      Then I will see only the recommended <filtering-type> filter in the hotel results

    Examples:
      | destinationLocation  | startDateShift | endDateShift | resultstype | filtering-type   |
      #| San Francisco, CA    | 3              |  5           | opaque      | Price            |
      #| San Francisco, CA    | 3              |  5           | retail      | Price            |
      | New Orleans, LA      | 3              |  5           | opaque      | Star rating      |
      | New Orleans, LA      | 3              |  5           | retail      | Star rating      |

    Scenario Outline: Verify filtering of results by neighborhood areas for opaque only.
      Given I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I request quotes
      And I choose <resultstype> hotels tab on results
      And I want to filter results by <filtering-type>
      When I choose the recommended <filtering-type> filter value
      Then I will see that all results will be of the recommended area

    Examples:
      | destinationLocation  | startDateShift | endDateShift | resultstype | filtering-type   |
      | San Francisco, CA    | 3              |  5           | opaque      | Areas            |

    Scenario Outline: Verify toggling of "All areas" checkbox.
      Given I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I request quotes
      And I choose <resultstype> hotels tab on results
      And I want to filter results by <filtering-type>
      And the "All areas" checkbox will be selected
      And I choose the recommended <filtering-type> filter value
      And the "All areas" checkbox will not be selected
      When I unselect the recommended <filtering-type> filter value
      Then the "All areas" checkbox will be selected

    Examples:
      | destinationLocation  | startDateShift | endDateShift | resultstype | filtering-type   |
      | San Francisco, CA    | 3              |  5           | opaque      | Areas            |
    
    Scenario Outline: Verify going back to results from the hotel details page after filtering.
      Given I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I request quotes
      And I choose <resultstype> hotels tab on results
      And I want to filter results by <filtering-type>
      And I choose the recommended <filtering-type> filter value
      And I choose a hotel result
      And I will see the details for the hotel chosen
      When I go back to the previous page
      Then I will see <page> results page

    Examples:
      | destinationLocation  | startDateShift | endDateShift | resultstype | page           | filtering-type   |
      #| San Francisco, CA    | 3              |  5           | opaque      | opaque         | Price            |
      #| San Francisco, CA    | 3              |  5           | retail      | retail         | Price            |
      | New Orleans, LA      | 3              |  5           | opaque      | opaque         | Star rating      |
      | San Francisco, CA    | 3              |  5           | opaque      | opaque         | Areas            |  
      | Chicago, IL          | 3              |  5           | retail      | retail         | Amenities        |
    @CLUSTER3  
    Examples:
      | destinationLocation  | startDateShift | endDateShift | resultstype | page           | filtering-type   |      
      | New Orleans, LA      | 3              |  5           | retail      | retail         | Star rating      |
      | Chicago, IL          | 3              |  5           | opaque      | opaque         | Amenities        |
