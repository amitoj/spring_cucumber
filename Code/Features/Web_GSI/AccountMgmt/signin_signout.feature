@US
Feature: Testing sign in and sign out functionality
  TL: Regression Testing / Site - GSI / Account management / Sign In/Sign out
  Owner: Boris Shukaylo

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
    Scenario: RTC-663 Password assistance - press Cancel button
    Given my name is account_lockout@hotwire.com and my password is adsadas
    And I want to access my booked trips
    And I log in 4 times to get error message
    And I am on home page

  @ACCEPTANCE
    Scenario: RTC-4353 Validation: try to sign in with too short email
    Given my name is test@test.com and my password is 12
    And I authenticate myself
    And a validation error exists on password
