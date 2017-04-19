@US
Feature: Verify information on car billing section for opaque cars
 Owner: Komarov I.

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Primary Driver field testing with valid and invalid values. RTC-1899
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "SFO"
    And I want to travel between 5 days from now and 8 days from now
    And I request quotes
    And I choose a opaque car
    Then I test Primary Driver field with valid and invalid values

  @ACCEPTANCE
  Scenario: Verify age confirmation error for primary driver. RTC-1900, 4340
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "SFO"
    And I want to travel between 5 days from now and 8 days from now
    And I request quotes
    And I choose a opaque car
    Then I verify age confirmation error for primary driver
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation

  @ACCEPTANCE
  Scenario: Primary phone number field testing. RTC-1901
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "SFO"
    And I want to travel between 5 days from now and 8 days from now
    And I request quotes
    And I choose a opaque car
    Then I test primary phone field with valid and invalid values
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation

  @ACCEPTANCE
  Scenario: Email address field testing for logged user. RTC-1902
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "SFO"
    And I want to travel between 5 days from now and 8 days from now
    And I request quotes
    And I choose a opaque car
    Then I test email address field with valid and invalid values
