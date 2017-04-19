@US
Feature: Subscription Management

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Attempt to subscribe from all email subscriptions. RTC-4227
    #author VYulun
    Given I am a new user
    And I create an account
    And I access my account information
    And I manage e-mail subscriptions
    When I subscribe from Trip Watcher email subscriptions
    And I subscribe from Personalized Hot Rate® Alerts email subscriptions
    And I subscribe from Savings Notices email subscriptions
    And I subscribe from Big Deals email subscriptions
    And I subscribe from Cruise News email subscriptions
    Then I receive confirmation my subscriptions were updated
    And I check subscription on options in DB


  @ACCEPTANCE
  Scenario: Attempt to unsubscribe from all email subscriptions. RTC-4228
    #author VYulun
    Given I am a new user
    And I create an account
    And I access my account information
    And I manage e-mail subscriptions
    When I unsubscribe from Trip Watcher email subscriptions
    And I unsubscribe from Personalized Hot Rate® Alerts email subscriptions
    And I unsubscribe from Savings Notices email subscriptions
    And I unsubscribe from Big Deals email subscriptions
    And I unsubscribe from Cruise News email subscriptions
    Then I receive confirmation my subscriptions were updated
    And I check subscription off options in DB