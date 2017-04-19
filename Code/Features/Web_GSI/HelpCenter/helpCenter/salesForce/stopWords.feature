@US      @salesForce
Feature: Sales Force help center search by stop words
  Customer searches with only "stop words" and combine with ordinary words.
  Owner: Oleksandr Zelenov

  Scenario: Salesforce Help Center: SFSH1 version test verification
    Given default dataset
    And set version test "vt.SFHS1" to value "1"
    Given the application is accessible
    When I access Help Center
    And Salesforce Help Center is opened
    When I search with phrase "one"
    Then I see error message "Search returns too many results. Please, try to search with other words combination."
    When I search with phrase "one two "
    Then I see error message "Search returns too many results. Please, try to search with other words combination."
    When I search with phrase "test"
    Then I see search result for phrase "test"


