@TOOLS
Feature: Self-service indicator in C3
  Let agent see that purchase was cancelled / refunded by customer or CSR
  Owner: Anastasiia Snitko

  Background:
    Given C3 application is accessible

  Scenario Outline: Verify agent see "Refunded by CSR"
    Given <vertical> purchase refunded by CSR
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I see CSR in Refunded by field

  Examples:
    | vertical |
    | Hotel    |
    | Car      |

  Scenario: Verify agent see "Refunded by CUSTOMER"
    Given Hotel purchase refunded by customer
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I see CUSTOMER in Refunded by field
