@TOOLS
Feature: Adding customer to Black list
  Owner: Sergey Shtubey

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Add customer to Black list RTC-3981
    Given customer for adding to Black list
    And   I login into C3 with username "csrcroz1"
    When  I add customer account to Black list
    Then  I see message that customer was added to Black list
    And   verify that customer is in Black list in DB

  @ACCEPTANCE
  Scenario: Remove customer from Black list RTC-3982
    Given customer for removing from Black list
    And   I login into C3 with username "csrcroz1"
    When  I remove customer account from Black list
    Then  I see message that customer was removed from Black list
    And   verify that customer is not in Black list in DB

  @ACCEPTANCE
    Scenario: Wrong email format for black list. Checking validation. RTC-3983
    Given customer email with wrong format
    And   I login into C3 with username "csrcroz1"
    When  I add customer account to Black list
    Then  I see message that email is not valid for adding to Black list
