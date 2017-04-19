@US
Feature: Hotwire customer can see other customers recommendations
  Owner: Intl team

  Background:
    Given default dataset
    Given Hotwire Recommendations are available
    Given the application is accessible

  @SEARCH
  Scenario:
    Given I'm searching for a hotel in "Back Bay Fens, Boston"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    When I notice and select solution with Hotwire customer reviews
    Then I see Hotwire customer reviews are the same I noticed
