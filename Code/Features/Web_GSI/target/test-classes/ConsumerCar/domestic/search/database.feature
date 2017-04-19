@US
Feature: Verifying different Data Base values for the car searching
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE @JANKY
  Scenario: Verify Data in DB for Search. RTC-995/996
    Given I am a new user
    Given I create an account
    When I am on home page
    When I am on car index page
    And I'm searching for a car in "SFO"
    And I want to travel between 5 days from now and 10 days from now
    And I want to pick up at 12:30pm and drop off at 12:30pm
    And I request quotes
    Then I see a non-empty list of search results
    Then I verify necessary fields into DB for the car search

  @ACCEPTANCE
  Scenario: Commission Checks. RTC-844
    Then I verify commissions for retail car in DB

  @STBL @ACCEPTANCE
  Scenario: search_solution for car search. RTC-997
    Given I'm searching for a car in "SFO"
    And I want to travel in the near future
    And I request quotes
    And I note search_id for car results
    And I verify that STATUS_CODE from DB is equal to "20040"

  @STBL @ACCEPTANCE
  Scenario: Car Pricing Validation vs. DB RTC-1016
    Given I'm searching for a car in "MIA"
    And I want to travel between 4 days from now and 7 days from now
    And I request quotes
    And I select a opaque car with cd code "ECAR"
    And I compare car taxes and fees on details page with DB values

  @STBL @ACCEPTANCE
  Scenario: Opaque - Daily rate verification RTC-1017
    Given  I'm searching for a car in "MIA"
    And I want to travel between 4 days from now and 7 days from now
    And I request quotes
    And I select a opaque car with cd code "ECAR"
    And I compare car daily rate on details page with DB values

  @ACCEPTANCE @JANKY
  Scenario:  RTC-1068 Location - Opaque result
    #If this test fails, do not hurry to ack or janky it - the problem may be in deblobber
    Given  I'm searching for a car in "Denver, CO - (DEN)"
    And I want to travel between 6 days from now and 9 days from now
    And I request quotes
    And I choose a opaque car
    And I verify locations for opaque car on details page with DB

