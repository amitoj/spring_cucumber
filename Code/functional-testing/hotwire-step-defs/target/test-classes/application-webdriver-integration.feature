Feature: Hotel Landing Page Search
  Let customer search for hotel rooms from the hotel landing page.

  Background:
    Given default dataset
    Given the application is accessible
    
 @US @LIMITED @JANKY @CLUSTER2 @CLUSTER3
  Scenario Outline: HCom search popup
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 5 days from now and 10 days from now
    And I want to <hcomState> Hotels.com search
    When I request quotes
    Then there <displayState> be a Hotels.com window popup

    Examples: 
      | hcomState | displayState |
      | enable    | will         |
     # | disable   | will not     |