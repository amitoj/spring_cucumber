@US
Feature: RTC-5366:Search by City name in NO

  Background: 
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE @ARCHIVE
  Scenario: RTC-5366:Search by City name in NO
    Given I have load international NO hotwire site
    And I'm searching for a hotel in "København, Danmark"
    When I request quotes
    Then I choose a hotel result
