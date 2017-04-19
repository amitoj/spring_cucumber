@US    @salesForce
Feature: Related questions verification.
  Owner: Oleksandr Zelenov

  Scenario: Related questions block should be displayed on article page
    Given default dataset
    And set version test "vt.SFHS1" to value "1"
    Given the application is accessible
    When I access Help Center
    And Salesforce Help Center is opened
    And I click any article
    Then I see list of related articles


