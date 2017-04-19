@MobileApi
Feature: create customer with Mobile API

  Scenario: create customer happy-path and login under this newly created account
    Given I want to register new customer:
      | Test | User | 5555555 | apitestuser+{rand}@hotwire.com | 123456 | 123456 |
    When I execute registration request
    Then I should see customer is registered
    When I retrieve profile with account data
    Then retrieve profile response is present
    And account data is correct

  Scenario: create non-US customer
    Given I want to register new non-US customer
    When I execute registration request
    Then I should see customer is registered
    When I retrieve profile with account data
    Then retrieve profile response is present
    And account data is correct

  Scenario: create customer with different subscription options
    Given I want to register customer with different set of subscriptions
    When I execute registration request
    Then I should see customer is registered
    When I retrieve profile with account data
    Then retrieve profile response is present
    And account data is correct

  Scenario: try to create customer without password
    When I want to register new customer:
      | Test | User | 5555555 | apitestuser+{rand}@hotwire.com |  |  |
    Then I get wrong password error when try to execute registration request

  Scenario: try to create customer when password confirmation does not match
    When I want to register new customer:
      | Test | User | 5555555 | apitestuser+{rand}@hotwire.com | 123456 | 123457 |
    Then I get password mismatch error when try to execute registration request

  @LIMITED
  Scenario: try to create customer with already registered email address
    When I want to register new customer:
      | Test | User | 5555555 | savedcreditcard@hotwire.com | 123456 | 123456 |
      | Test | User | 5555555 | gp.holodiuk@gmail.com       | 123456 | 123456 |
    Then I get account exists error when try to execute registration request

  Scenario: try to create customer with invalid email address
    When I want to register new customer:
      | Test | User | 5555555 | apitestuser+{rand}hotwire.com | 123456 | 123456 |
    Then I get wrong email error when try to execute registration request

