@US
Feature: Tripstarter

  Background:
    Given default dataset
    Given the application is accessible

  @CLUSTER2 @CLUSTER3 @ACCEPTANCE
  Scenario Outline: RNT-68:Confirm Planning Tools
    Given I access TripStarter
    And I'm searching locations from "<fromLocation>" to "<toLocation>" via tripstarter page
    Then I should see TripStarter details page

  Examples: quotable fares parameters
    | fromLocation | toLocation |
    | SFO          | JFK        |