@TOOLS
Feature: Hotel Deals Engine
  Owner: Vladimir Yulun


  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Tabs in C3 as Deal Admin. RTC-1118
    Given I login into C3 with username "csrcroz13"
    Then The Air Deal Maintenance tab available
    Then The Hotel Deal Maintenance tab available

  @STBL @ACCEPTANCE
  Scenario: XID is not listed when previewing queries. RTC-1119
    Given I login into C3 with username "csrcroz13"
    And I click Hotel Deal Maintenance tab
    When I click "Preview" button
    Then I see XID is not displayed at the top of the page

  @STBL @ACCEPTANCE
  Scenario: Display of XID upon execution of a query. RTC-1120
    Given I login into C3 with username "csrcroz13"
    And I click Hotel Deal Maintenance tab
    When Execute any query from the dropdown menu
    Then I see XID is displayed at the top of the page

  @STBL @ACCEPTANCE
  Scenario: Display of deals upon preview of a query. RTC-1121
    Given I login into C3 with username "csrcroz13"
    And I click Hotel Deal Maintenance tab
    When I click "Preview" button
    Then Deals appear at the bottom of the page