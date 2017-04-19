@TOOLS @ACCEPTANCE
Feature: Customer password reset in C3.
  Owner: Anastasiia Snitko

  Background:
    Given C3 application is accessible

  @highPriority
  Scenario: Verify agent can reset customer password and email checking RTC-285
    Given customer with qa_regression@hotwire.com email
    And   I login into C3 with username "csrcroz1"
    When  I want to search customer by email
    And   I go to the customer account info
    And   I click Reset Password button
    And   I confirm password reset
    Then  I see message about password reset
    Then  I receive password assistance email

  Scenario: Verify agent can cancel password reset for a customer
    Given customer account for password reset
    And   I login into C3 with username "csrcroz1"
    When  I want to search customer by email
    And   I go to the customer account info
    And   I click Reset Password button
    And   I cancel customer password reset
    Then  I don't see message about password reset

  @STBL
  Scenario: Verify agent can reset CSR customer password. RTC-815
    Given I login into C3 with username "csrcroz1"
    And   I go to Edit CSR account page
    When  I create new CSR with 'qa_regression@hotwire.com' email
    Then  I see "A new account for TestRTC CSR was successfully created and is now active." create csr message
    And   I search Active CSR account
    And   I click Reset Password on CSR account page
    Then  I see "The account password was reset." create csr message
    Then  I receive an email with temporary password for CSR account