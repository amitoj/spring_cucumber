@US    @salesForce
Feature: INTL article voting and feedback in Salesforce help center
  Owner: Oleksandr Zelenov

  Scenario: INTL article voting and feedback component verification
    Given default dataset
    Given the application is accessible
    Given I'm from "United Kingdom" POS
    When I access Help Center
    And I see INTL SF Help Center
    And I click any article
    Then I see "Vote for this article" component
    And it contains 5 blank stars
    Then Article's feedback form is not displayed
    And I click any star
    Then stars changes their color
    And component becomes disabled
    Then Article's feedback form is displayed
    And I enter article feedback and submit
    Then I see feedback response message "Your feedback was successfully submitted!"