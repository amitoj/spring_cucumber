@US @ROW @ACCEPTANCE
Feature: Deals on converged Hotel landing page.

  Background:
    Given default dataset
    Given the application is accessible

  Scenario: Deals module on Hotel landing page
    Given I want to go to the Hotels landing page
    Then I will see the hotel deals module on the landing page
