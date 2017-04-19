@US @platform @simulator @LIMITED @STBL
Feature: Car Domestic. PayPal purchases.
  This test cases check if the user is able to choose PayPal payment.
  Also it verify that a customer is able to successfully purchase a rental car, using PayPal account.


  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

#    RTC-5356
  Scenario: PayPal Happy Path. Available for opaque car without insurance.
    When I'm searching for a car in "SFO"
    And I want to travel between 5 days from now and 7 days from now
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    Then PayPal payment is available
    When I fill in all PayPal billing information
    And I complete purchase with agreements
    And I confirm booking on PayPal sandbox
    #remove this step after the fix for: BUG53908: Paypal does not redirect back to Hotwire after payment
    And I return to the Hotwire site
    Then I receive immediate confirmation
