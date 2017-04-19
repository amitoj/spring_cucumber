@XnetAPI
Feature: Xnet API ARI on V1.1(Backward compatibility) Regression

  Background:
    Given the xnet service is accessible

  Scenario: Update Xnet Inventory using valid username and password every day
        Given the xnet service is accessible
        Given I'm a valid supplier
        And my hotel id is 8880
        And date range is between 1 days from now and 5 days from now every day
        And room type is STANDARD
        And total inventory available is 10
        And rate plan is XHW
        And xnet currency is USD
        And rate per day is 25
        And extra person fee is 0
        When I update Inventory
        Then I should get No error
        
  Scenario: Update Inventory as a super user by passing soap headers
    Given I'm a valid supplier
    And I pass request with headers
    And my hotel id is 10153
    And date range is between 1 days from now and 5 days from now every day
    And room type is STANDARD
    And total inventory available is 2
    When I update Inventory
    Then I should get No error
    
  Scenario: Update inventory as a normal user by passing soap headers
    Given I'm a normal supplier
    And I pass request with headers
    And my hotel id is 10153
    And date range is between 1 days from now and 5 days from now every day
    And room type is STANDARD
    And total inventory available is 2
    When I update Inventory
    Then I should get Invalid hotel id error