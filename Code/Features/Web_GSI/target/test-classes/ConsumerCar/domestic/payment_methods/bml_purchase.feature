@US @platform @LIMITED @STBL
Feature: Car Domestic. BML purchases.
  This test cases check if the user is able to choose BML payment.
  Also it verify that a customer is able to successfully purchase a rental car, using BML account.
  Owner: Oleksandr Zelenov

#  BML is disabled on frontend (but NOT removed from the code) in 2015.02 branch.
#  TODO: Commenting this cases until they will be removed from the code.
#
#  Background:
#    Given default dataset
#    Given activate car's version tests
#    Given the application is accessible
#
#
#  Scenario: BML Happy Path. Available for opaque car without insurance.
#     RTC-5374  #RTC-4718
#    When I'm searching for a car in "SFO"
#    And I want to travel between 5 days from now and 7 days from now
#    And I don't request insurance
#    And I request quotes
#    Then I see a non-empty list of search results
#    And I choose a opaque car
#    When I fill in all BML billing information
#    And I complete purchase with agreements
#    And I confirm booking on BML website
#    Then I receive immediate confirmation