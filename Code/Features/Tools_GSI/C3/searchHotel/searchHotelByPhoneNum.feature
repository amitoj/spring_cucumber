@TOOLS
Feature: Verify agent can find a hotel by hotel primary phone number

  Background:
    Given C3 application is accessible

  @ACCEPTANCE  @criticalPriority
  Scenario: verify search hotel by unique Hotel phone number
    Given I login into C3 with username "csrcroz1"
    Given hotel with unique phone number
    When I search hotel by phone number
    Then I see Hotel Supplier info corresponds to Database

  Scenario: verify search hotel by incorrect phone number
    Given I login into C3 with username "csrcroz1"
    Given hotel with incorrect phone number
    When I search hotel by phone number
    Then I see message "Please enter a valid phone number."