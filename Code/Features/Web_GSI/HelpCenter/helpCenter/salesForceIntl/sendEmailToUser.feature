@US    @salesForce
Feature: Check Sales Force sendUSEmail form.
  Happy Path & Validation.
  Owner: Oleksandr Zelenov

  Scenario: Sales Force sendUSEmail form Happy Path
    Given default dataset
    Given the application is accessible
    Given I'm from "United Kingdom" POS
    When I access Help Center
    Then I see INTL SF Help Center
    When I click on "Send us an email"
    And I fill in all the fields
    When click Send button
    Then I see successful message

  Scenario: Sales Force sendUSEmail form validation
    Given default dataset
    Given the application is accessible
    Given I'm from "United Kingdom" POS
    When I access Help Center
    Then I see INTL SF Help Center
    When I click on "Send us an email"
    When I click Send button
    Then I receive an error message
    And I see firstName field is highlighted
    And I see lastName field is highlighted
    And I see email field is highlighted
    And I see subject field is highlighted
    And I see message field is highlighted
