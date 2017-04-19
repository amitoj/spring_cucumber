 @MOBILE @platform
Feature: Hotel Search through booking for guests happy path for international mobile user
   As a International mobile user, I'd like to be able to search for hotels that are located at my destination so that
   I can book a hotel I choose.

  Background:
    Given the application is accessible    

   Scenario Outline: International guest users should be able to search & book a hotel in a given location, change rooms, sort and choose different currencies

    Given I visit Hotwire from <countryCode> on a mobile device
    And I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I am a <persona>
    And I request quotes by <sortCriteria> in <currencyCode>
    When  I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

   Examples: hotel search parameters

       | destinationLocation                            | startDateShift | endDateShift   | persona 	      |sortCriteria  | currencyCode | countryCode  |
       | San Francisco, CA                              | 34             | 35             | family man        | price 	     | USD          | us           |
       | BOS                                            | 34             | 35             | business traveler | star rating  | EUR          | ie           |
       | 94040                                          | 34             | 35             | family man        | star rating  | NZD          | uk           |
       | Chicago, IL                                    | 34             | 35             | business traveler | neighborhood | AUD          | dk           |
       | ATL                                            | 34             | 35             | student           | best value   | GBP          | se           |
   #    | 45.538676,-122.728271                          | 34             | 35             | family man        | distance     | NOK          | no           |
       | 84113                                          | 34             | 35             | business traveler | neighborhood | CHF          | au           |
       | CVG                                            | 34             | 35             | family man        | distance     | HKD          | hk           |
       | 18966                                          | 34             | 35             | business traveler | neighborhood | DKK          | us           |
       | MXP                                            | 34             | 35             | student           | best value   | SEK          | uk           |
       | Oregon, OH                                     | 34             | 35             | family man        | distance     | EUR          | de           |
       | Brussels, Belgium                              | 34             | 35             | business traveler | best value   | SGD          | sg           |

   Scenario: Mexico international guest user should be able to search & book a hotel in a given location

    Given I visit Hotwire from mx on a mobile device
    And I'm a guest user
    And I'm searching for a hotel in "SFO" 
    And I want to travel between 34 days from now and 35 days from now
    And I am a family man
    And I request quotes by price in MXN
    And I pay with a valid MasterCard credit card
    When I choose a hotel and purchase as guest a quote 
    Then I receive immediate confirmation
   
