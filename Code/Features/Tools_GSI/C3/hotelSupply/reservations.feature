@TOOLS
Feature: Case Management/
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Find all reservations for the specified time. RTC-4324
    Given I login into C3 with username "csrcroz26"
    And I get a hotel ID with reservations in near future
    And I search hotel by ID
    When I open "Reservations" link in common tasks
    Then hotel reservation information is correct
