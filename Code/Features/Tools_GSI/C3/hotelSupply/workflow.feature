@TOOLS
Feature: Submit Finance, Hotel Ops, & PSG Workflows
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Create Workflow for some hotel. RTC-4326
    Given some hotel from Database
    And I login into C3 with username "csrcroz25"
    And I search hotel by ID
    When I open "Create Workflow for Hotel" link in common tasks
    And I create workflow for hotel
    Then I check current workflow for hotel

