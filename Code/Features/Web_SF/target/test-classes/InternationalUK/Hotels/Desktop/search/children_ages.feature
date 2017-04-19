Feature: New fare finder allows customers to choose ages for their children (if any).
  Owner: Intl team
  # TODO: redesign this test as farefinder.feature

  Background:
    Given default dataset
    Given the application is accessible

  @SEARCH
  Scenario: Customer should see search results matching search criteria entered on home page
    Given I'm searching for a hotel in "London"
    And I want to travel between 5 days from now and 7 days from now
    And I would place adults and children as following:
      | adultsCount | childrenCount | childrenAges |
      | 2           | 1             | 7            |
    And I request quotes
    And I see search results match search criteria