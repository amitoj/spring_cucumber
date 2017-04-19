@US
Feature: guestPurchase
  Guest Purchase section

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Global On / Off - RCP RTC-278
    #author VYulun
    Given set property "hotwire.view.web.purchase.rentalCarProtectionInsuranceEnabled" to value "false"
    Given the application is accessible
    And I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a car
    Then Rental car damage protection was unavailable

  @ACCEPTANCE
  Scenario:   Roll back changes
    Given set property "hotwire.view.web.purchase.rentalCarProtectionInsuranceEnabled" to value "true"
    Given the application is accessible