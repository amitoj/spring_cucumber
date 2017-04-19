@US @ACCEPTANCE
Feature: Planning tools
   As a user, I might want to check out various Hotwire planning tools.

  Background: 
    Given default dataset
    Given the application is accessible

  @STBL @ACCEPTANCE
  Scenario: RTC-611 Validate that user is redirected to proper page
    When I go to the Hotwire Trip Starter page
    Then I validate I am redirected to proper page
    When I browse one page back
    When I go to the Hotwire Trip Watcher page
    Then I validate I am redirected to proper page
    When I browse one page back
    When I go to the Travel Value Index page
    Then I validate I am redirected to proper page
    When I browse one page back
    When I go to the Hotwire Travel Savings Indicator page
    Then I validate I am redirected to proper page
    When I browse one page back
    When I go to the Go Local Search page
    Then I validate I am redirected to proper page

  @CLUSTER3
  Scenario: Verify Hotwire Travel Savings Indicator page.
    Given I go to the Hotwire Travel Savings Indicator page
    Then I will see the Hotwire Travel Savings Indicator page

  @CLUSTER3
  Scenario: Verify Travel Value Index page.
    Given I go to the Travel Value Index page
    Then I will see the Travel Value Index page

  @CLUSTER3
  Scenario: Go Local Search page.
    Given I go to the Go Local Search page
    Given I am searching San Francisco, CA location via GoLocalSearch page
    Given I want to travel via GoLocalSearch page between 10 days from now and 15 days from now
    And I launch a search on the GoLocalSearch page
