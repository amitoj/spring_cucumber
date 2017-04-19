@US  @SingleThreaded
Feature: Cancellation tatus checks in database
  RTC: 859

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: RTC-859:Confirm Retail Car Cancel in DB
    Given I have valid random credentials
    And I authenticate myself
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose a <carType> car
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation
    When I access my account information
    Then I cancel the past car purchase in "<detailedLocation>"
    When I access my account information
    Then I receive immediate DB 50045 status for the purchase

  Examples:
    | #   | pickUpLocation          | detailedLocation             | carType |
    | 859 | San Antonio, TX - (SAT) | San Antonio Intl. car rental | retail  |

  @ACCEPTANCE @STBL
  Scenario: Verify retail car cancellation email. RTC-860
    Given my name is qa_regression@hotwire.com and my password is hotwire333
    Then I authenticate myself
    And  I'm searching for a car in "San Antonio, TX - (SAT)"
    And  I want to travel in the near future
    Then I request quotes
    And  I choose a retail car
    Then I fill in all billing information
    And  I complete purchase with agreements
    Then I receive immediate confirmation
    And  I access my account information
    Then I cancel the past car purchase in "San Antonio Intl. car rental"
    And  I receive email cancellation letter for retail car