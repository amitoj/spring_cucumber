@US
Feature: Verifying the back button processing
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Verifying browser back processing from car results page to UHP. RTC-655
    Given I am on car index page
    Then  I'm searching for a car in "AIRPORT"
    And   I want to travel in the near future
    Then  I request quotes
    And   I see a non-empty list of search results
    Then  I go back to the previous page
    And   I see UHP page

  @ACCEPTANCE @STBL
  Scenario: Verifying browser back processing from car details to car result page. RTC-667
    Given I am on car index page
    Then  I'm searching for a car in "SFO"
    And   I want to travel in the near future
    Then  I request quotes
    And   I see a non-empty list of search results
    Then  I make car search from result page
    And   I see a non-empty list of search results
    And   I choose a opaque car
    Then  I go back to the previous page
    And   I see a non-empty list of search results
