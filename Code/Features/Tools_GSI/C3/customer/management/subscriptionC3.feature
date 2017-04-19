@TOOLS  @ACCEPTANCE
Feature: Subscribe account via C3
  Owner: Oleksandr Zelenov
  Author: Yulun Vladimir

  Background:
    Given C3 application is accessible

  Scenario: CSR activate subscription for user, DB verify. RTC-4233
    #passed 5 times in STBL CI
    Given I login into C3 with username "csrcroz1"
    Given unsubscribed customer account
    And I search for given customer email
    And I go to the customer account info
    When I subscribe account to newsletters from C3
    Then I see the message that customer information changed successfully
    And I see in DataBase that customer subscription is Y
  #Reverting changes
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I search for given customer email
    And I go to the customer account info
    When I unsubscribe account to newsletters from C3
    Then I see the message that customer information changed successfully
    And I see in DataBase that customer subscription is N



