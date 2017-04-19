@TOOLS
Feature: Case Management/
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Check "Create a New Inbound Hotel Call Case" works properly. RTC-4321
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see unique CaseID

