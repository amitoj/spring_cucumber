@TOOLS @c3Finance
Feature: Overcharge details verification
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE @STBL
  Scenario: Overcharge details verification
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    When I start search for overcharges
    Then I see overcharges results
    When I open any overcharge details
    Then I see Hotel Overcharge Information
    And I see less or equal than 500 overcharge results
    When I click on overcharge amount
    Then I see overcharge amount pop-up

  Scenario: Overcharges status history verification
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    When I start search for overcharges
    Then I see overcharges results
    When I open hotel with minimum overcharges
    Then I see Hotel Overcharge Information
    And I fix Supplier Contact Information
    When I change status of overcharge
    And I see overcharge in notes
    And I don't see changed overcharge
    When I click on show History
    And I see changed overcharge