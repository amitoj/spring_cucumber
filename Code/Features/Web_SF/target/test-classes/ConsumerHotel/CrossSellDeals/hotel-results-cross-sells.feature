@ROW @SEARCH @JANKY
Feature: Hotel results cross sells.
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  Scenario Outline: deal data is consistent when going from results page
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Brussels, Belgium"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    And I choose <resultsType> hotels tab on results
    When I navigate to the cross sell deal from search results
    Then I get <dealType> deal as a search winner

  Examples:
    | resultsType | dealType |
    | opaque      | retail   |
    | retail      | opaque   |
