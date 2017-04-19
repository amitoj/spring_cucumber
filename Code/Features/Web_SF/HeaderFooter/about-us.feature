 @US @ACCEPTANCE
Feature: About us link on home page

  Background:
  Given default dataset
  Given the application is accessible

  Scenario: About us link is accessible to all customers. RTC-671
    Given I'm a guest user
    When I access About us 
    Then I verify About us Texts
    Then I verify Company Links
    Then I verify Legal Links
    Then I verify Hotel partner Overview Link
    Then I verify Affiliates Link
