@TOOLS
Feature: Work with extranet
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @ACCEPTANCE @STBL
  Scenario: Check an Extranet hotel can be viewed from C3. RTC-4341
    Given some hotel from Database
    And I login into C3 with username "csrcroz25"
    And I search hotel by ID
    Then I see "Extranet" link in common tasks
    When I open "Extranet" link in common tasks
