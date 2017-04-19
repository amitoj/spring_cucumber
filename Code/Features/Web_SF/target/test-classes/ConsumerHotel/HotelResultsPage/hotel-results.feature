Feature: Basic hotel results tests

    Background:
      Given default dataset
      Given the application is accessible

  #@US
  #Scenario Outline: Verify reference prices for results
  #  Given I am directed by <partnerUrl> to Hotwire
  #  Then I will see <resultstype> results page
  #  And The reference module will be displayed in the results
  #  And The reference price will be the same as the first result after clicking the reference price module

  #Examples:
  #  | partnerUrl              | resultstype    |
    # Opaque may not work because there is no differentiation from a featured deal and the reference price does not
    # take the featured deal value. Uncomment when devs pull featured deal result from main results list.
    #| opaque results sidedoor | opaque         |
    #| retail results sidedoor | retail         |

    @US @ROW @ACCEPTANCE @CLUSTER3 @CLUSTER4
    Scenario Outline: Access the hotel results.
      Given I am logged in
      And I am authenticated
      And I choose <currency> currency
      And I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I want <numberOfHotelRooms> room(s)
      And I will be traveling with <numberOfAdults> adults
      And I will be traveling with <numberOfChildren> children
      And I request quotes
      When I choose <resultstype> hotels tab on results
      Then I will see <page> results page
      And the map will be rendered correctly in the results
      And I will see the same <currency> currency

    Examples:
      | currency | destinationLocation  | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | resultstype | page           |
      | USD      | Joshua Tree, CA      | 3              |  5           | 1                  | 2              | 0                | opaque      | opaque         |
      | AUD      | Joshua Tree, CA      | 3              |  5           | 1                  | 2              | 0                | retail      | retail         |
#      | NOK      | Joshua Tree, CA      | 3              |  5           | 1                  | 2              | 0                | opaque      | opaque         |

    @US @STBL
    Scenario Outline: Access the hotel results and verify distance from.
      Given I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I want <numberOfHotelRooms> room(s)
      And I will be traveling with <numberOfAdults> adults
      And I will be traveling with <numberOfChildren> children
      And I request quotes
      When I choose <resultstype> hotels tab on results
      Then distance <state> be seen in search results

    Examples:
      | destinationLocation       | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | resultstype | state    |
      | San Francisco, CA - (SFO) | 3              |  5           | 1                  | 2              | 0                | opaque      | will     |
      | San Francisco, CA - (SFO) | 3              |  5           | 1                  | 2              | 0                | retail      | will     |
      | San Francisco, CA         | 3              |  5           | 1                  | 2              | 0                | opaque      | will not |
      | San Francisco, CA         | 3              |  5           | 1                  | 2              | 0                | retail      | will not |
