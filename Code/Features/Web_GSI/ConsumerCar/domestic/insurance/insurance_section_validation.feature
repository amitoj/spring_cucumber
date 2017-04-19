@US @ACCEPTANCE
Feature: Validation of insurance section on the billing page
  Owner:Vyacheslav Zyryanov

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: RTC-6454 Insurance selection validation
    Given I'm searching for a car in "AIRPORT"
    And I want to travel between 3 days from now and 6 days from now
    And I skip insurance request
    When I request quotes
    And I see a non-empty list of search results
    And I choose a opaque car
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate "Rental Car Protection - Would you like to add insurance? Please select either yes or no." error message

  @ACCEPTANCE @STBL
  Scenario: RTC-5392 Insurance is still selected - Errors on the page
    Given I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request insurance
    And I request quotes
    And I see a non-empty list of search results
    And I choose a retail car
    And I fill in insurance
    When I complete purchase without agreements
    Then I receive immediate "Primary driver must be at least 25 years old at time of pick up. Driver age will be verified by the rental agency." error message
    Then I receive immediate "At the bottom of the page, you must accept our Terms of Use." error message
    And I see insurance is still selected


