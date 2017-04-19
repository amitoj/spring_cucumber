@TOOLS    @SingleThreaded
Feature: Supervisor operate with CSR
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @highPriority
  Scenario Outline: Deactivate and activate CSR account. RTC-814
    And I login into C3 with username "<user>"
    And I go to Edit CSR account page
    Given agent account
    And I search Active CSR account
    When I make CSR Inactive
    Then I see CSR account in Inactive section
    And I search Inactive CSR account
    When I make CSR Active
    Then I see CSR account in Active section

  Examples:
    | user     |
    | csrcroz1 |
    | csrcroz9 |
