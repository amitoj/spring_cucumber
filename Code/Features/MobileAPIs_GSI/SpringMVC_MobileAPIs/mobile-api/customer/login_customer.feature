@MobileApi
Feature: login with wrong password

  Scenario: login with wrong password, check the error
    Given I want to register new customer:
      | Test | User | 5555555 | apitestuser+{rand}@hotwire.com | 123456 | 123456 |
    When I execute registration request
    Then I should see customer is registered
    When I retrieve profile with account data
    Then retrieve profile response is present
    And account data is correct
    When I try to login with correct email and incorrect password
    Then login response is not present

  Scenario: login with wrong email and password, check the error
    When I try to login with incorrect email and incorrect password
    Then login response is not present



