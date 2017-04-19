@MOBILE
Feature: Mobile car error validation

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Drop-off date must be after pick-up date. RTC-5167
  #Author. Komarov I
  Given I'm a registered user
  When I'm searching for a car in "SFO"
  And I want to travel between 2 days from now and 1 days from now
  And I want to pick up at 1200 and drop off at 1200
  And I request quotes
  Then I see a invalid drop-off validation error on date

  @ACCEPTANCE
  Scenario: Drop-off time must be 30 min after pick-up time. RTC-5170
  #Author. Komarov I
  Given I'm a registered user
  When I'm searching for a car in "SFO"
  And I want to travel between 2 days from now and 2 days from now
  And I want to pick up at 1200 and drop off at 1200
  And I request quotes
  Then I see a invalid car time validation error on time

  @ACCEPTANCE
  Scenario: Misspelled city name handling. RTC-5164
  #Author. Komarov I
    Given I'm a registered user
    When I'm searching for a car in "Dallos"
    And I want to travel between 2 days from now and 3 days from now
    And I want to pick up at 1200 and drop off at 1200
    And I request quotes
    Then I verify suggested location for misspelled city contains a "Dallas" in name

  @ACCEPTANCE
  Scenario:  Search dates appears correctly in the search result. RTC-5166, 5168
  #Author. Komarov I
    Given I'm a registered user
    When I'm searching for a car in "SFO"
    And I want to travel between 2 days from now and 3 days from now
    And I want to pick up at 800 and drop off at 1700
    And I request quotes
    Then I see the same values in FareFinder as I filled