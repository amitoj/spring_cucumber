Feature: Mobile Authentication
  In order to allow access to User restricted capabilities,
  a User must be able to authenticate herself.

  Background:
    Given default dataset
    Given the application is accessible

  @MOBILE @ACCEPTANCE
  Scenario Outline: Attempt to authenticate
    Given my name is <username> and my password is <password>
    When I authenticate myself
    Then I <state> authenticated

    Examples: valid credentials
    | username                    | password      | state  |
    | savedCreditCard@hotwire.com | NotMyPassword | am not |
    
  @LIMITED @MOBILE @CLUSTER1 @CLUSTER2 @CLUSTER3 @CLUSTER4 @CLUSTERALL
  Scenario: Attempt to authenticate with known good credentials. RTC-5079
    Given I have valid credentials
    When I authenticate myself
    Then I am authenticated

  @US @MOBILE @ACCEPTANCE @JANKY
  Scenario: Attempt to authenticate with known valid email and  bad password. RTC-379
    #author VYulun
    Given I have an existing username and an incorrect password
    When I authenticate myself
    Then I am unable to log in

  @US @MOBILE @ACCEPTANCE @CLUSTER3 @ARCHIVE
  Scenario: Attempt to authenticate with known bad username and bad password. RTC-380
    #author VYulun
    Given I have an invalid username
    When I authenticate myself
    Then I am unable to log in

  @US @MOBILE @ACCEPTANCE @ARCHIVE
  Scenario: Attempt to authenticate with known bad username and correct password. RTC-384
    #author VYulun
    Given I have an invalid username and valid password
    When I authenticate myself
    Then I am unable to log in

  @US @ACCEPTANCE
  Scenario: Verified user attempts to log in repeatedly with wrong password. RTC-367
 #author VYulun
    Given my name is tkachaeva@hotwire.com and my password is wrongpassword
    Given I go to SignIn page
    When I have tried sign in 4 times
    Then I am not authenticated
    Then Prepopulated email field should be tkachaeva@hotwire.com

