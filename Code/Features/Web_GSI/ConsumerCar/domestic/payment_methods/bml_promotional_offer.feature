@ARCHIVE @WIP @US @simulator @SingleThreaded @ACCEPTANCE
Feature: Checking promotional checkbox for BML payment method.
  Owner: Iuliia Neiman
  
  @JANKY
  Scenario: Check promotional offer is displayed for new BML. RTC-6870
    Given set property "hotwire.view.bill.minPurchaseAmtToQualifyForBillMeLaterPromotion" to value "70"
    Given default dataset
    Given activate car's version tests
    Given the application is accessible
    Given I'm a guest user
    And I'm searching for a car in "LAX"
    And I want to travel between 2 days from now and 7 days from now
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    When I click a opaque car with total cost more than 70 USD
    And I choose BML payment method
    Then promotional checkbox is displayed and checked
    And I fill in all BML billing information
    And I complete purchase with agreements
    And I confirm booking on BML website
    Then I receive immediate confirmation

  Scenario: Check promotional offer is not displayed. RTC-6870
    Given set property "hotwire.view.bill.minPurchaseAmtToQualifyForBillMeLaterPromotion" to value "120"
    Given default dataset
    Given activate car's version tests
    Given the application is accessible
    Given I'm a guest user
    And I'm searching for a car in "SFO"
    And I want to travel between 29 days from now and 30 days from now
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    When I click a opaque car with total cost less than 120 USD
    And I choose BML payment method
    Then promotional checkbox is hidden

  Scenario: Check promotional offer is displayed for saved BML. RTC-6870
    Given set property "hotwire.view.bill.minPurchaseAmtToQualifyForBillMeLaterPromotion" to value "70"
    Given default dataset
    Given activate car's version tests
    Given the application is accessible
    Given I'm a payable user
    And I authenticate myself
    And I'm searching for a car in "JFK"
    And I want to travel between 2 days from now and 7 days from now
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    When I click a opaque car with total cost more than 70 USD
    And I choose saved BML payment method
    Then promotional checkbox is displayed and unchecked

  Scenario: revert property
    Given set property "hotwire.view.bill.minPurchaseAmtToQualifyForBillMeLaterPromotion" to value "250"



