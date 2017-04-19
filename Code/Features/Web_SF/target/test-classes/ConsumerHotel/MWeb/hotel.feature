@MOBILE  @platform
Feature: Hotel Search through booking happy path for guests and registered users
   As a price hunter, I'd like to be able to search for hotels that are located at my destination so that
   I can book a hotel I choose.

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Guest users should be able to search for and book a hotel. RTC-5073
    Given I'm a guest user
    And I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 34 days from now and 35 days from now
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  @JANKY
  Scenario Outline: Guest users should be able to search & book a hotel in a given location, change rooms, sort and choose different currencies
    # RTC-5855
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I am a <persona>
    And I request quotes by <sortCriteria> in <currencyCode>
    When  I choose a hotel and purchase as <user status> a quote
    Then I receive immediate confirmation

  @LIMITED
  Examples: hotel search parameters
       | destinationLocation                                | startDateShift | endDateShift   | persona                     | sortCriteria  | currencyCode  | user status |
       | Seattle, WA                                        | 34             | 35             | business traveler           | star rating   | GBP           | guest       |
       | Seattle, WA                                        | 34             | 35             | business traveler           | star rating   | GBP           | user        |

#  Examples: hotel search parameters
#       | destinationLocation                                | startDateShift | endDateShift   | persona                     | sortCriteria  | currencyCode  |
#       | San Francisco, CA                                  | 34             | 35             | family man                  | price         | USD           |
#       | 37.7950898,-122.4034124                            | 34             | 35             | student                     | best value    | NOK           |
#       | LAX                                                | 34             | 35             | family group traveler       | distance      | SEK           |
#       | ORD                                                | 34             | 35             | shoe string budget traveler | neighborhood  | CAD           |
#       | 94111                                              | 34             | 35             | personal assistant          | best value    | DKK           |
#       | 10165                                              | 34             | 35             | family man                  | price         | EUR           |
#       | 5000 Forbes Avenue, Pittsburgh, PA 15213, USA      | 34             | 35             | business traveler           | star rating   | CHF           |
#       | 77 Massachusetts Avenue, Cambridge, MA 02139-4307  | 34             | 35             | student                     | neighborhood  | AUD           |

  Scenario Outline: Registered users should be able to search & book a hotel in a given location, change rooms, sort and choose different currencies

    Given I'm a registered user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I am a <persona>
    And I request quotes by <sortCriteria> in <currencyCode>
    When  I choose a hotel and purchase as user a quote
    Then I receive immediate confirmation

  Examples: hotel search parameters
       | destinationLocation                            | startDateShift | endDateShift   | persona           |sortCriteria  | currencyCode |
       | San Francisco, CA                              | 34             | 35             | family man        | price        | USD          |
       | 35.177401,-80.881348                           | 34             | 35             | student           | best value   | CAD          |
       | 75210                                          | 34             | 35             | family man        | distance     | NZD          |
       | Chicago, IL                                    | 34             | 35             | business traveler | neighborhood | AUD          |
       | ATL                                            | 34             | 35             | student           | price        | GBP          |
       | 45.538676,-122.728271                          | 34             | 35             | family man        | distance     | NOK          |
       | 84113                                          | 34             | 35             | business traveler | neighborhood | CHF          |
       | 18966                                          | 34             | 35             | business traveler | neighborhood | DKK          |
       | MXP                                            | 34             | 35             | student           | price        | SEK          |
       | Oregon, OH                                     | 34             | 35             | family man        | distance     | EUR          |

  @ACCEPTANCE
  Scenario: Registered users should be able to search & book a hotel in a given location while adding a new traveler to their itinerary.
    Given I'm a registered user
    And I'm searching for a hotel in "San Francisco, CA "
    And I want to travel between 34 days from now and 35 days from now
    And I request quotes
    And I add a new traveler
    When  I choose a hotel and purchase as user a quote
    Then I receive immediate confirmation

  @ACCEPTANCE
  Scenario Outline:RTC-5849: Given that a user enters a location when they perform a hotel search, 
             then they should see results and be able to book a hotel by selecting price module in details page

    Given I'm a <userType> user
    And I'm searching for a hotel in "San Francisco, CA "
    And I want to travel between 36 days from now and 38 days from now
    And I am a family man 
    And I request quotes
    And I choose a hotel result 
    And I click on price module of hotel details
    Then I should navigate to signin for purchase as guest or user      
        
  Examples: hotel search parameters
    | userType      | 
    | guest         | 
    | registered    | 

  @LIMITED @JANKY
  Scenario: RTC-5841: Mobile Hotel change search
    Given I'm a guest user
    And I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 34 days from now and 35 days from now
    And I am a family man
    And I request quotes
    When I change search
    Then I should see the hotel landing page with previous search destination
   
   @JANKY
  Scenario: RTC-6116: Registered users should be able to search & book a hotel in a given location with HotDollars.
    Given I'm a registered user
    And I'm searching for a hotel in "San Francisco, CA "
    And I want to travel between 34 days from now and 35 days from now
    And I request quotes
    And I select hotDollars payment option
    When  I choose a hotel and purchase as user a quote
    Then I receive immediate confirmation
    
  @JANKY
  Scenario: RTC-5855: Signed in user - Book your hotel - Pay with new card
    Given I'm a guest user
    And I'm searching for a hotel in "94111"
    And I want to travel between 36 days from now and 38 days from now
    And I request quotes
    And I filtered by Fisherman's Wharf hood name
    And I choose a hotel result
    When I review the itinerary as a guest user
    And I select the accessibilty needs
    Then I check hotel ADA amenity consist

  @MOBILE @ACCEPTANCE
  Scenario Outline:
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 3 days from now and 5 days from now
    And I request quotes
    And I choose a hotel result
    And I book that hotel
    And I am on the billing page
    When I navigate back from the billing page
    Then I will see <detailsType> details page

  Examples:
    | detailsType |
    | opaque      |
