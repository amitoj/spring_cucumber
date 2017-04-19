@TOOLS
Feature: Verify agent can find a hotel by Hotel ID
  Owner: Anastasia Snitko

  Background: 
    Given C3 application is accessible

  @LIMITED @criticalPriority
  Scenario: verify search hotel by valid Hotel ID
    Given I login into C3 with username "csrcroz1"
    Given some hotel from Database
    When I search hotel by ID
    Then I see Hotel Supplier info corresponds to Database

  Scenario: verify search hotel by invalid hotel ID
    Given I login into C3 with username "csrcroz1"
    Given some invalid hotel ID
    When I search hotel by ID
    Then I see message "Please enter a valid Hotel ID number containing 19 numbers or less."
