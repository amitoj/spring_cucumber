@US @ACCEPTANCE
Feature: Making sure links on site map page render correctly.

  Background: 
    Given default dataset
    Given the application is accessible

  Scenario: 
    When I access the Global Footer Page
    And I click on the SiteMap Link
    And I click on the Rome Link
    Then Validate that "Rome Hotel Deals" text is present
