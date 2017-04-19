@NO_PREPROD
Feature: Deals

  Background:
    Given default dataset
    Given the application is accessible

  @US @CLUSTER3
  Scenario: Deals on vacation landing page
    Given I want to find vacation package deals from landing page
    Then I will see the vacation package deal from the landing page

