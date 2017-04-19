@ROW
Feature: Basic functionality
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Currency changes with site RTC-4733
    When  I'm from "Ireland" site
    Then  currency is EUR

  @ACCEPTANCE 
  Scenario: Site does not change with currency RTC-4734
    When I choose my currency code to EUR
    Then selected country is "United Kingdom"