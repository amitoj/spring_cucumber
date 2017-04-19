@TOOLS      @SingleThreaded
Feature: CSR deactivate and reactivate customer account
  Supervisor try to deactivate customer account.
  Owner: Oleksandr Zelenov


  Background:
    Given C3 application is accessible


  @ACCEPTANCE   @highPriority
  Scenario: CSR account activation/deactivation.
    Given I login into C3 with username "csrcroz1"
    Given customer account for deactivation
    And I want to search customer by email
    And I go to the customer account info
    When I deactivate account
    Then I see message that account is now inactive.
    When I reactivate account
    Then I see message that account is now active.
    When I cancel deactivation
    Then I see no message displayed about customer account

  @ACCEPTANCE
  Scenario: CSR deactivate and reactivate customer account with my account verification. RTC-749
    #IKomarov
    Given domestic application is accessible
    And I login as customer with known credentials
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I want to search customer by email
    And I go to the customer account info
    When I deactivate account
    Then I see message that account is now inactive.
    Given I delete all cookies
    Given domestic application is accessible
    And  I am logged in
    Then I am not authenticated
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I want to search customer by email
    And I go to the customer account info
    When I reactivate account
    Then I see message that account is now active.
    When I cancel deactivation
    Then I see no message displayed about customer account
    Given I delete all cookies
    Given domestic application is accessible
    And  I am logged in
    Then I am authenticated