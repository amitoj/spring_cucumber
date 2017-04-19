@US  @salesForce
Feature: Article breadcrumb verification
  Sales Force article breadcrumb verification
  Owner: Oleksandr Zelenov

  Scenario: Article breadcrumb verification
    Given default dataset
    And set version test "vt.SFHS1" to value "1"
    Given the application is accessible
    When I access Help Center
    And Salesforce Help Center is opened
    And I click any article
    Then I see breadcrumb module

