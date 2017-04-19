Feature: Hotel Results Marketing/Deals
    Marketing and deals for hotel results.

  Background:
    Given default dataset

  @US
  Scenario: Hotel results here's your deal
    Given the application is accessible
    And I view a hotel neighborhood deal from a marketing email
    Then I will see here's your deal in the opaque hotel results page
