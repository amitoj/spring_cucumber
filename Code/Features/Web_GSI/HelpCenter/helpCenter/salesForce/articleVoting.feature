@US @salesForce
Feature: Article voting and feedback in Salesforce helpcenter
  Owner: Oleksandr Zelenov

  Scenario: Article voting component verification
    Given default dataset
    And set version test "vt.SFHS1" to value "1"
    Given the application is accessible
    When I access Help Center
    And Salesforce Help Center is opened
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

  Scenario: Decline Article feedback form submit
    Given default dataset
    And set version test "vt.SFHS1" to value "1"
    Given the application is accessible
    When I access Help Center
    And Salesforce Help Center is opened
    And I click any article
    And I click any star
    And I don't want to add article feedback
    Then I see feedback response message "You may leave you feedback next time."
