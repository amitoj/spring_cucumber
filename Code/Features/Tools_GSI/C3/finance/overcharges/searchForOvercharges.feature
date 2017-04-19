@TOOLS  @c3Finance
Feature: Search for overcharges.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible


  Scenario Outline: Overcharges results list sorting verification.
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    And I search overcharges by Last Overcharge Date over last year
    And I see overcharges search result page
    When I sort results "<column>" field asc
    Then I verify results sorted correctly
    When I sort results "<column>" field desc
    Then I verify results sorted correctly

  Examples:
    | column            |
    | Overcharge Count  |
    | Hotel Status      |
    | Hotel Name        |
    | Total Overcharges |
    | Country           |


  Scenario: Search by last date modified for unassigned, use datepicker
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    When I search overcharges by Last Modified Date for recent 120 days
    Then I see overcharges results

  Scenario: Search by last date modified for unassigned, use dates selector
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    When I search overcharges by Last Modified Date over last 6 months
    Then I see overcharges results

  Scenario: Search for specific Hotel name and state
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    And I enter a part of overcharge hotel name
    And I set overcharge state
    When I start search for overcharges
    Then I see overcharges results
    And overcharge hotel name contains "The"

    #author V.Yulun
  @STBL @ACCEPTANCE
  Scenario: Overcharges - Search - All unassigned. RTC-6725
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    When I search for unassigned overcharges
    Then I see overcharges search result page
    Then I check unassigned overcharges results according DB

