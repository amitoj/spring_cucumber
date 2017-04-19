@US @platform @LIMITED @STBL
Feature: Air Migration - Air Search
  Air Search redirects to Expedia results.
  Owner: Jorge Lopez

  Background:
    Given default dataset
    Given set version test "vt.AMP14" to value "2"
    Given the application is accessible

  @CLUSTER3
  Scenario Outline: Round trip Search.
    Given I'm a guest user
    And I'm searching for a flight from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I will be flying with <numberOfPassenger> passengers
    And I request quotes
    Then I verify search parameters on Expedia air results page


  Examples: quotable fares parameters
    | fromLocation              | toLocation            | startDateShift   | endDateShift     | numberOfPassenger |
    | SFO          | LAS        | 1 weeks from now | 2 weeks from now | 1                 |

  @CLUSTER3
  Scenario Outline: International Search.
    Given I'm a guest user
    And I'm searching for a flight from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I will be flying with <numberOfPassenger> passengers
    And I request quotes
    Then I verify search parameters on Expedia air results page


  Examples: quotable international fares parameters
    | fromLocation | toLocation | startDateShift   | endDateShift     | numberOfPassenger |
    | GDL          | LHR        | 1 weeks from now | 2 weeks from now | 1                 |
    | SFO          | GDL        | 1 weeks from now | 2 weeks from now | 1                 |
    | GDL          | SFO        | 1 weeks from now | 2 weeks from now | 1                 |

  Scenario Outline: Do a multi-city flight, search for 5 routes
    Given I'm a guest user
    And I'm doing a search with flight number <one> from "<fromFirstLocation>" to "<toFirstLocation>" on <firstStartDate>
    And I'm doing a search with flight number <two> from "<fromSecondLocation>" to "<toSecondLocation>" on <secondStartDate>
    And I'm doing a search with flight number <three> from "<fromThirdLocation>" to "<toThirdLocation>" on <thirdStartDate>
    And I'm doing a search with flight number <four> from "<fromFourthLocation>" to "<toFourthLocation>" on <fourthStartDate>
    And I'm doing a search with flight number <five> from "<fromFifthLocation>" to "<toFifthLocation>" on <fifthStartDate>
    And I will be flying with <numberOfPassenger> passengers
    When I request quotes
    Then I validate MultiCity search results


  Examples: quotable fares parameters
    | one | fromFirstLocation | toFirstLocation | firstStartDate   | two | fromSecondLocation | toSecondLocation | secondStartDate  | three | fromThirdLocation | toThirdLocation | thirdStartDate   | four | fromFourthLocation | toFourthLocation | fourthStartDate  | five | fromFifthLocation | toFifthLocation | fifthStartDate   | numberOfPassenger |
    | 1   | SFO               | LAX             | 1 weeks from now | 2   | LAX                | JFK              | 2 weeks from now | 3     | JFK               | SEA             | 3 weeks from now | 4    | SEA                | SAN              | 4 weeks from now | 5    | SAN               | LAS             | 5 weeks from now | 1                 |

  Scenario: New MeSo Banners on Air Landing page
    Given I'm a guest user
    When I am on air landing page
    Then I verify MeSo Banners exist on air landing page
    
  Scenario: Maximum number of passengers should be reduced to six
    Given I'm a guest user
    When I am on air landing page
    Then I see that maximum number of passengers to select is six