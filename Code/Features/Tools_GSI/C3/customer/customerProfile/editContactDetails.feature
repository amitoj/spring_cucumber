@TOOLS
Feature: Edit Contact Details
  Owner: Sergey Shtubey

  @ACCEPTANCE @STBL
  Scenario: Trying to update account with wrong format email RTC-734
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    And   I search for customer with "g2loadtest111@hotwire.com" email
    And   I go to the customer account info
    When  I update email for customer with wrong format
    Then  I see error for incorrect email for updated contact details for customer
    When  I update email for customer with valid format
    Then  I see confirmation for updated contact details for customer

  @ACCEPTANCE @STBL
  Scenario: Trying to update account with email already registered RTC-735
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    And   I search for customer with "g2loadtest111@hotwire.com" email
    And   I go to the customer account info
    When  I update email for customer with email that already registered
    Then  I see error for email that this email already registered for updated contact details for customer
    When  I update email for customer with valid format
    Then  I see confirmation for updated contact details for customer

  @ACCEPTANCE @STBL
  Scenario: Trying to update account with empty/valid information RTC-736
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    And   I search for customer with "g2loadtest111@hotwire.com" email
    And   I go to the customer account info
    When  I clear all contact details for customer
    Then  I see errors for blank contact details for customer
    When  I update contact details for customer
    Then  I see confirmation for updated contact details for customer

  @ACCEPTANCE
  Scenario: Verify that user can Opt-Out in C3 RTC-4234
    Given subscribed customer account
    And   C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer email
    And   I go to the customer account info
    When  I unsubscribe account to newsletters from C3
    Then  I see confirmation for updated contact details for customer
    And   I verify that customer fully unsubscribed in DB

