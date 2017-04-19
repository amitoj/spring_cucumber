@TOOLS  @c3Finance
Feature: Overcharges - Back to Results link
  Let CSR search overcharges and operate with.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario: RTC-6727:Overcharges - Back to Results link
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    And I search for unassigned overcharges
    And I see overcharges search result page
    And I open any overcharge details
    When I return back to results
    Then I see overcharges search result page



