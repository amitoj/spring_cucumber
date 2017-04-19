@US @ACCEPTANCE
Feature: Only for results from Hertz in Caribbean locations Hertz tour number should be displayed on Confirmation page
  Owner:Vyacheslav Zyryanov

# Hertz confirmation code
# - Only for results from Hertz in Caribbean locations Hertz tour
#  number should be displayed on Confirmation page and email
# - Hertz tour number should be ITHOTWCARB

  Background:
    Given default dataset
    And activate car's version tests
    And the application is accessible

  Scenario: verify Hertz tour number on confirmation page
    Given I'm searching for a car in "FPO"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    Then I choose a opaque car
    When I fill in all billing information
    When I complete purchase with agreements
    Then I receive immediate confirmation
    Then Hertz confirmation code is displayed