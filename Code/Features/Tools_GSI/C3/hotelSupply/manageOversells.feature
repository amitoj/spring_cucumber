@TOOLS
Feature: / Manage Oversells (Workflow) /
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Check all the reservations can be found for the specified time. RTC-4327
    Given I login into C3 with username "csrcroz26"
    And I get a hotel ID with oversells in near future
    And I search hotel by ID
    When I open "Oversells" link in common tasks
    Then hotel oversells information is correct

