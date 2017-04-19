@TOOLS @dmu   @ACCEPTANCE
Feature: DMU procedure Happy Path and DB verification.
  Owner: Oleksandr Zelenov

  Background:
    Given DMU application is accessible

  Scenario: Database management utility. Happy Path.
    Given I login into DMU
    Then I see DMU index page
    And I choose "Car" DMU menu
    When I choose "Change Car Vendor Is Partner?" procedure from the list
    Then I see procedure page
    And I change partner status for vendor
    And I run on non-prod environment
    Then I see that procedure operations is correct
    When I commit on non-prod environment
    Then I see that partner status is changed in database

