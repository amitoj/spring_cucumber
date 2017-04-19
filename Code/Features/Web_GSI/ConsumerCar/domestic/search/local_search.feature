@US @platform
Feature: Car smoke searches
  Owner: Vyacheslav Zyryanov
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: RTC-827:Local Search External Link
    Given I'm searching for a car in "San Diego, CA"
    When I navigate to car results page using external link
    Then I see a non-empty list of search results

  @ACCEPTANCE
  Scenario: RTC-4144 Nearby Airport too far
    Given I'm searching for a car in "Concord, CA"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    Then I see that "Include nearby airport" module is not present

  @ACCEPTANCE
  Scenario: RTC-4145 Nearby Airport is around
    Given I'm searching for a car in "Phoenix, AZ"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    Then I see that "Include nearby airport" module is present

  @ACCEPTANCE
  Scenario: RTC-820:search when no results returned
    Given I'm searching for a car in "Lebanon, Lebanon"
    And I want to travel in the near future
    And I request quotes
    Then the purchase was failed due of
    """
    We're sorry: We currently don't offer cars in your requested origin country. Error #9001
    """
