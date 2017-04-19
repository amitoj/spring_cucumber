@US    @salesForce
Feature: Check travel advisories logic on Sales Force Help center INTL. Happy Path.
  Owner: Oleksandr Zelenov

  Scenario: Sales Force International travel advisories.
    Given default dataset
    Given the application is accessible
    Given I'm from "United Kingdom" POS
    When I access Help Center
    And I see INTL SF Help Center
    Then I see travel advisories
    When I click on "See all travel advisories"
    Then I see travel advisories page
    When I click any article
    Then I see travel advisories page
    When I click on "Help Centre"
    And I see INTL SF Help Center