@US
Feature: Details page validation. Opaque solutions
  Owner: Boris Shukaylo

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: RTC-1026 Copy - KBYG, Guaranteed a car from
    Given I'm searching for a car in "SFO"
    And I want to travel between 8 days from now and 12 days from now
    When I request quotes
    Then I see a non-empty list of search results
    When I choose a opaque car
    Then I verify Guaranteed car section on details page

  @ACCEPTANCE
  Scenario: RTC-1033 Opaque details - Tab and header verification
    Given I'm searching for a car in "SFO"
    And I want to travel between 8 days from now and 12 days from now
    When I request quotes
    Then I see a non-empty list of search results
    When I choose a opaque car
    Then I verify header for car details page

  #This testcase is failed due to database is returns always value 5.
  #Not sure that this test case is applicable to devbld3, because we are using simulator.
  @ACCEPTANCE @BUG
  Scenario: RTC-4041 Opaque - Airport info verification RTC-1029 Location field
    Given I'm searching for a car in "SFO"
    And I want to travel between 8 days from now and 12 days from now
    When I request quotes
    Then I see a non-empty list of search results
    When I choose a opaque car
    And I fill in all billing information
    And I complete purchase with agreements
    Then I verify airport locations for opaque car on details page with DB

  @ACCEPTANCE
  Scenario: RTC-1034 - Review Hotwire Discount Rate details
    Given I'm searching for a car in "SFO"
    And I want to travel between 10 days from now and 12 days from now
    When I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    Then I compare total price between Review your trip and trip summary

