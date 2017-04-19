@US @SingleThreaded
Feature: sold out message is displayed in cases when vendor that was picked has already sold out the car.
  Owner: Iuliia Neiman
  Owner: Nataliya Golodiuk

  Background:
    Given set property "hotwire.biz.search.car.forceSoldOutOnPriceCheck" to value "true"
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: validate sold out message for opaque car. RTC-6834
    Given I'm searching for a car in "AIRPORT"
    And I want to travel between 2 days from now and 7 days from now
    And I request quotes
    And I see a non-empty list of search results
    When I choose a opaque car and hold on details
    Then I receive immediate "The car you selected is unavailable. See other great deals below." priceCheck message
    And I am redirected to the lowest price solution

#  WAITING FOR SIMULATOR UPDATE
#  Scenario:validate sold out message for retail car that is the last in the vendor grid. RTC-6836

#    Given I'm searching for a car in "AIRPORT"
#    And I want to travel in the near future
#    And I request quotes
#    And I see a non-empty list of search results
#    When I choose a retail car and hold on details
#    When I click 'Continue' button and wait for price check
#    Then I receive immediate "The car you selected is unavailable. Please, select another car." priceCheck message
#    And I am redirected to the lowest price solution


  Scenario: validate sold out message for retail car that is not the last in the vendor grid. RTC-6835
    Given I'm searching for a car in "AIRPORT"
    And I want to travel in the near future
    And I don't request insurance
    And I request quotes
    And I see a non-empty list of search results
    When I choose a retail car
    Then I fill in all billing information
    Then I complete purchase with agreements
    Then I receive immediate "We're sorry: The rental agency you selected is sold out" priceCheck message

  Scenario: Roll back changes...
    Given set property "hotwire.biz.search.car.forceSoldOutOnPriceCheck" to value "false"
    Given the application is accessible




