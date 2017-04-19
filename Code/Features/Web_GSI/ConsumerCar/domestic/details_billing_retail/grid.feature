@US
Feature:Details page grid validation
Owner: Boris Shukaylo
  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

    #RTC-1053 consist of  check of grid columns. RTC-1055 does this aswell
  @ACCEPTANCE
  Scenario:1055/1053 - Another vendor is selected/Columns verification
    And I'm searching for a car in "Denver, CO - (DEN)"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a retail car
    And I compare car options between grid and trip summary section

  @ACCEPTANCE
  Scenario: RTC-1063/1054 Review car details
    And I'm searching for a car in "Denver, CO - (DEN)"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a retail car
    And I compare car options between results and billing

    #don't change DEN to fullname in this testcase
    #it's all because AIRPORT_CODE in DB
  @STBL @ACCEPTANCE
  Scenario: RTC-4039 Airport info verification
    And I'm searching for a car in "DEN"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a retail car
    And I verify locations in retail car grid with DB

  @STBL @ACCEPTANCE
  Scenario: RTC-1052 Details - Grid - Winner verification
    And I'm searching for a car in "Denver, CO - (DEN)"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a retail car
    And I verify cheapest retail car price in the grid with DB

  @ACCEPTANCE @STBL
  Scenario: RTC-5396 User is logged in - info is prepopulated
    Given I have valid random credentials
    And I authenticate myself
    And I'm searching for a car in "Denver, CO - (DEN)"
    And I want to travel in the near future
    And I request quotes
    And I choose a retail car
    And I verify that user logged in and traveller info is prepopulated

  @ACCEPTANCE @STBL
  Scenario: RTC-5397 User signs in on Retail Details page
    Given I have valid random credentials
    And I'm searching for a car in "Denver, CO - (DEN)"
    And I want to travel in the near future
    And I request quotes
    And I choose a retail car
    And I authenticate myself
    And I verify that user logged in and traveller info is prepopulated

  @STBL @ACCEPTANCE
  Scenario Outline: RTC-5398/5399 User signs up with/out saving payment info
  Given I'm a guest user
  And I request insurance
  And I'm searching for a car in "Denver, CO - (DEN)"
  And I want to travel in the near future
  And I request quotes
  And I choose a retail car
  And I fill in all billing information
  And I save my information
  And I <negation> save my payment information
  And I complete purchase with agreements
  Then I receive immediate confirmation
  And I am logged in after saving my information
  And I verify that email on Account Overview is correct
  Examples:
    |#     |negation|
    |5398  |        |
    |5399  |don't   |



