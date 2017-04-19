Feature: Hotel Landing Page Search
  Let customer search for hotel rooms from the hotel landing page.

  Background:
    Given default dataset
    Given the application is accessible

 # @US @ROW @LIMITED
 # Scenario Outline: Hotel search from converged hotel landing page.
 #   Given I want to go to the Hotels landing page
  #  And I'm searching for a hotel in "<destinationLocation>"
  #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
  #  When I request quotes
  #  Then I see hotel results

 # Examples:
 #   | destinationLocation  | startDateShift | endDateShift   |
  #  | San Francisco, CA    | 21             | 26             |
  #  | San Francisco, CA    | 62             | 63             |
    # 62-63 RTC-1450 V Yulun

  @US @LIMITED @CLUSTER2 @CLUSTER3
  Scenario Outline: HCom search popup
   # Given I want to go to the Hotels landing page
    And I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 5 days from now and 10 days from now
    And I want to <hcomState> Hotels.com search
   

  Examples:
    | hcomState | 
    | enable    | 

