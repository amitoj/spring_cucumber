Feature: Hotel Search
    Let customer search for retail and opaque hotel rooms.

  Background: 
    Given default dataset
    Given the application is accessible

  @US @BUG53588 @LIMITED
  Scenario: 
    Then the old hotel index page is accessible

  Scenario Outline: UHP or landing page fare finder search.
    Given I'm searching for a hotel in "<destinationLocation>"
    And   I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And   I want <numberOfHotelRooms> room(s)
    And   I request quotes
    And   I choose <resultstype> hotels tab on results
    Then  I will see <resultstype> results page

   @US @SMOKE @graphite-report-duration @CLUSTER1 @CLUSTER2 @CLUSTER3 @CLUSTER4
    Examples: 
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | resultstype |
      | San Francisco, CA   | 3              | 5            | 1                  | opaque      |

    @US @SMOKE @graphite-report-duration @JANKY
    Examples: 
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | resultstype |
      | Chicago, IL         | 3              | 5            | 1                  | opaque      |

    @ROW @SMOKE @CLUSTER1 @CLUSTER2 @CLUSTER3 @CLUSTER4
    Examples: 
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | resultstype |
      | San Francisco, CA   | 3              | 5            | 1                  | opaque      |
    @ROW @SMOKE @CLUSTER1 @CLUSTER2 @CLUSTER3 @CLUSTER4 @JANKY
    Examples: 
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | resultstype |
      | Chicago, IL         | 3              | 5            | 1                  | retail      |
  @CLUSTER1 @CLUSTER3
  Scenario Outline: Results page fare finder search.
    Given I'm searching for a hotel in "<destinationLocation>"
    And   I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And   I want <numberOfHotelRooms> room(s)
    And   I request quotes
    And   I choose <resultstype> hotels tab on results

    @US @ACCEPTANCE
    Examples: 
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | resultstype | newStartDateShift | newEndDateShift |
      | Chicago, IL         | 3              | 5            | 1                  | opaque      | 5                 | 7               |
      | Chicago, IL         | 3              | 5            | 1                  | retail      | 5                 | 7               |

  @US @ACCEPTANCE
  Scenario Outline: RTC-1253: Hotel search by Airport code
    Given I type "<destinationLocation>" hotel location
    And   I set hotel dates between 10 days from now and 15 days from now
    And   I want 1 room(s)
    When  I start hotel search without specifying the destination
    Then  I should see hotel search results page
    And   I should see hotel results for "<expectedDestinationLocation>" location

    Examples: 
      | destinationLocation | expectedDestinationLocation |
      | SFO                 | SFO                         |

  @ACCEPTANCE
  Scenario: Verifying CHAIN_NAME in DB. RTC-1144
    Then I verify CHAIN_NAME from hotel_chain has correct values in DB

  @US @LIMITED @CLUSTER2 @CLUSTER3
  Scenario Outline: HCom search popup
    Given I'm searching for a hotel in "San Francisco, CA"
    And   I want to travel between 5 days from now and 10 days from now
    And   I want to <hcomState> Hotels.com search
   
    Examples: 
      | hcomState | 
      | enable    | 
    
  @US @ACCEPTANCE
  Scenario: Spaces preceding the first character in the hotel destination. RTC-410
    Given I type " den" hotel location
    And   I set hotel dates between 10 days from now and 15 days from now
    And   I want 1 room(s)
    When  I start hotel search without specifying the destination
    And   I should see hotel search results page
    Then  I should see hotel results for "den" location

  @US @ACCEPTANCE
  Scenario: Spaces preceding the first character in the hotel destination. RTC-411
    Given I type " Tulsa,  Oklahoma" hotel location
    And   I set hotel dates between 10 days from now and 15 days from now
    And   I want 1 room(s)
    When  I start hotel search without specifying the destination
    And   I should see hotel search results page
    Then  I should see hotel results for "Tulsa, Oklahoma" location

  @US @ACCEPTANCE
  Scenario: Spaces after the last character in the hotel destination. RTC-412
    Given I type "Austin " hotel location
    And   I set hotel dates between 10 days from now and 15 days from now
    And   I want 1 room(s)
    When  I start hotel search without specifying the destination
    And   I should see hotel search results page
    Then  I should see hotel results for "Austin" location

  @US @ACCEPTANCE
  Scenario: Do not enter a hotel destination. RTC-413
    Given I type "" hotel location
    And   I set hotel dates between 10 days from now and 15 days from now
    And   I want 1 room(s)
    When  I start hotel search without specifying the destination
    And   I receive immediate "Destination: Check out our destination cities list to ensure your city is listed. Click your city to choose it." error message

  @US @ACCEPTANCE
  Scenario: Enter one letter in the hotel destination. RTC-414
    Given I type "C" hotel location
    And   I set hotel dates between 10 days from now and 15 days from now
    And   I want 1 room(s)
    When  I start hotel search without specifying the destination
    And   I receive immediate "We couldn't complete your search. Please check the city name and try again." error message

  @US @ACCEPTANCE
  Scenario: Enter more than 3 letters without a comma not recognized by the application as an exact match. RTC-420
    When I am on car index page
    And   I'm searching for a car in "CXC"
    And   I want to travel in the near future
    And   I request quotes
    Then  I receive immediate "Where are you going? Please choose your location from the list. If your location is not listed, please check your spelling or make sure it is on our destination cities list." error message

  @ACCEPTANCE
  Scenario Outline: Hotel-location of 2 letters on uhp not recognized by the application as an exact match RTC-416
    Given I type "<location>" hotel location
    And   I set hotel dates between 10 days from now and 15 days from now
    And   I want 1 room(s)
    When  I start hotel search without specifying the destination
    And   I receive immediate "Enter a destination." error message

    Examples: 
      | location |
      | KS       |
      | GL       |
      | FK       |

    #Created a bug https://jira/jira/browse/FUN-140 @bshukaylo
  @US @ACCEPTANCE @BUG @CLUSTER2 @CLUSTER3
  Scenario Outline: Clickable Hotel MFI layer for no results searches from Hotel LP RTC-4136
    Given I'm a guest user
    And   I'm searching for a hotel in "<destinationLocation>"
    And   I want to travel in the near future
    And   I request quotes
    Then  I should see a hotel MFI layer
    And   I choose <partner> partner
    And   I launch hotel partners search
    Then  Subscription layer is displayed for <partner>
    And   verify <partner> partner search record in DB from MFI

    Examples: quotable fares parameters
      | destinationLocation | partner |
      | Islamabad, Pakistan | EXPEDIA |

    #https://jira/jira/browse/FUN-146 bug by @bshukaylo
  @US @ACCEPTANCE @BUG
  Scenario Outline: RTC-5363 Search by zip code
    Given I'm a guest user
    When  I'm searching for a hotel in "<destinationLocation>"
    And   I want to travel in the near future
    And   I request quotes
    Then  I should see hotel results for "<destinationLocation>" location

    Examples: opaque quotable fares parameters
      | destinationLocation |
      | 94121               |

  #Author Juan Hernandez
  @JANKY
  Scenario Outline: Hotel search by Address RTC-1263 - opaque and retail.
    Given I'm searching for a hotel in "<destinationLocation>"
    And   I want to travel in the near future
    And   I want 1 room(s)
    And   I request quotes
    And   I choose <resultstype> hotels tab on results
    Then  I will see <resultstype> results page

  Examples:
    | destinationLocation                 | resultstype |
    | 655 Montgomery St San Francisco, CA | opaque      |
    | 655 Montgomery St San Francisco, CA | retail      |
