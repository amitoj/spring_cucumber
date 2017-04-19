@TOOLS
Feature: Verify that allowed recovery status is not displayed for cars and airs
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario Outline: checks allowed recovery status wasn't appear if vertical didn't equal to hotels
    Given customer <vertical> purchase for search
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then Allowed Recovery status is NOT DISPLAYED

  Examples:
    | vertical  |
    | car       |
    | air       |

  Scenario: customer with allowed recovery status "NOT DISPLAYED"
    Given customer purchase with allowed recovery status "NOT DISPLAYED"
    And C3 application is accessible
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    And Allowed Recovery status is NOT DISPLAYED