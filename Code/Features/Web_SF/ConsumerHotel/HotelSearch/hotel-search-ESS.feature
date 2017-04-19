@US
Feature: Hotel Search to test ESS and non-ESS features from phoenix homepage and phoenix results page.
   
  Background: 
    Given default dataset
    Given ESS is enabled
    Given the application is accessible

    @ACCEPTANCE @US
    Scenario Outline: Phoenix search with ESS enabled and going to Phoenix results page
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between 3 days from now and 5 days from now
    And I request quotes
    Then  I should see hotel search results page
    And   I should see hotel results for "<expectedDestinationLocation>" location
    
    Examples: 
      | destinationLocation               | expectedDestinationLocation |
      | San Francisco, CA                 |  San Francisco              |
      | SFO                               |  San Francisco              |
      | Golden Gate Bridge                |  Golden Gate Bridge         |
      
@ACCEPTANCE @US @JANKY
    Scenario Outline: Phoenix search with ESS enabled going to BEX Results page
    #does not work without angular enabled
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between 3 days from now and 5 days from now
    And I request quotes
    Then I verify destination location on BEX results page is San Francisco
    
    Examples: 
      | destinationLocation               |
      | Hilton San Francisco Union Square |

  @US @ACCEPTANCE
  Scenario Outline: Phoenix search with ESS enabled and not using dropdown
    Given I type "<destinationLocation>" hotel location
    And   I set hotel dates between 3 days from now and 5 days from now
    And   I want 1 room(s)
    When  I start hotel search without specifying the destination
    Then  I should see hotel search results page
    And   I should see hotel results for "<expectedDestinationLocation>" location

  Examples:
    | destinationLocation                                                       |  expectedDestinationLocation |
    | San Francisco, CA, United States (SFO-San Francisco Intl.)                |  San Francisco               |
    | Golden Gate Bridge, San Francisco, California, United States of America   |  Golden Gate Bridge          |
    | Hilton San Francisco Union Square                                         |  Hilton San Francisco        |

  @JANKY
  Examples:
    | destinationLocation                                                       |  expectedDestinationLocation |
    | San Francisco (and vicinity), California, United States of America        |  San Francisco               |
