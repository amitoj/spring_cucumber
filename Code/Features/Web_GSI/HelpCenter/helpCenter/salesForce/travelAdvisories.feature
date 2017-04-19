@US @salesForce
Feature: Check travel advisories logic on Sales Force Help center. Happy Path.
  Owner: Oleksandr Zelenov

  Scenario: Sales Force travel advisories.
    Given default dataset
    And set version test "vt.SFHS1" to value "1"
    Given the application is accessible
    When I access Help Center
    And I see SF Help Center
    Then I see travel advisories
    When I click on "See all travel advisories"
    Then I see travel advisories page
    When I click any article
    Then I see travel advisories page
    When I access Help Center
    And I see SF Help Center

  Scenario Outline: Verify travel SalesForce travel advisories are opened from site.
    Given default dataset
    Given the application is accessible
    And I'm from "<country>" country
    When I click on travel advisory alert
    Then Salesforce Help Center is opened
    Then I see travel advisories page
    And URL of the page contains categoryName=Travel_Advisories

  Examples:
    | country        |
    | US/Canada      |
    | United Kingdom |
