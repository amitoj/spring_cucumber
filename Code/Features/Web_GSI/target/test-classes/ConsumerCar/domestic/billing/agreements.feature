@US @ACCEPTANCE
Feature: Pop-ups in the "Agree and Book" section
  RTC: 5391, 5415, 5416, 1005
  Owner: Vyacheslav Zyryanov
  Description: User reads the content of popup windows by links <Terms Of Usage>
  and <Rules And Restrictions> from the "Agree and Book" section.

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: 5391
  Search for car as registered user with complete bookings.
  This test case verifies that there are neither errors nor exceptions when
  user opens any pop-ups with error messages being displayed on the page.

    Given my name is qa_regression@hotwire.com and my password is hotwire333
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request quotes
    And I choose a retail car
    When I complete purchase without agreements
    Then I receive immediate "At the bottom of the page, you must accept our Terms of Use." error message
    Then I receive immediate "Primary driver must be at least 25 years old at time of pick up. Driver age will be verified by the rental agency." error message
    Then I receive immediate "Rental Car Protection - Would you like to add insurance? Please select either yes or no." error message
    Then I change primary driver's name to random
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation

  @ACCEPTANCE
  Scenario: 1005, 5415
    Pop-ups in the "Agree and Book" section for opaque (authenticated user)

    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "CITY"
    And I want to travel in the near future
    And I request quotes
    And I choose a car
    When I fill in all billing information
    Then I read Terms of Use and Product Rules and Restrictions before booking
    When I complete purchase without agreements
    Then I receive immediate "At the bottom of the page, you must accept our Terms of Use." error message

  Scenario: 5416
    Pop-ups in the "Agree and Book" section for retail car (guest user)

    Given I'm a guest user
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request quotes
    And I choose a retail car
    When I fill in all billing information
    Then I read Terms of Use and Product Rules and Restrictions before booking