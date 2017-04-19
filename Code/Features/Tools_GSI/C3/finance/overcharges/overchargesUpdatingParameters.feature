@TOOLS  @c3Finance
Feature: RTC-6525:Overcharges - Updating Parameters
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario: Overcharges Happy path. Default search range. Assigning. Search for assigned. Unassigning.
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    When I start search for overcharges
    Then I see overcharges results
    And I update any overcharge with csrcroz9
    When I click on search for overcharges information
    And I choose csrcroz9 as assignee
    When I start search for overcharges
    Then I see overcharges results
    Then I unassign all overcharges
    When I search for unassigned overcharges
    Then I see overcharges search result page
    And updated overcharge is displayed in the list
