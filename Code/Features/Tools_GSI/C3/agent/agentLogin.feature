@TOOLS  @c3Agent
Feature: C3 Login page verification.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Try to log into C3 with incorrect username and password
    Given I'm tried to login into C3 with username "fake_user" and password "Admin"
    Then I see C3 Login Page validation message "CSR Login: Username and Password pair not found"
    And no records for this agent created in Database

  @ACCEPTANCE
  Scenario: C3 Login empty fields validation verification.
    Given I'm tried to login into C3 with username "" and password ""
    Then I see C3 Login Page validation message "CSR Login: Field validations, none of these can be blank - Username, Password"

  Scenario: Try to log into C3 as valid CSR with incorrect password
    Given valid CSR agent
    Given I'm tried to login into C3 as given CSR with password "incorrect password"
    Then I see C3 Login Page validation message "CSR Login: Username and Password pair not found"
    And agent login failures in Database is 1

  @highPriority
  Scenario: Locking C3 agent account and unlocking by admin agent in C3. Happy path. RTC-563,771
#    Locking CSR account
    Given valid CSR agent
    Given I'm tried to login into C3 as given CSR with password "whatever"
    Then I see C3 Login Page validation message "CSR Login: Username and Password pair not found"
    And agent login failures in Database is 1
    Given I'm tried to login into C3 as given CSR with password "12345"
    Then I see C3 Login Page validation message "CSR Login: Username and Password pair not found"
    And agent login failures in Database is 2
    Given I'm tried to login into C3 as given CSR with password "' OR 1=1;--"
    Then I see C3 Login Page validation message "CSR Login: Username and Password pair not found"
    And agent login failures in Database is 3
    Given I'm tried to login into C3 as given CSR with password "qwerty"
    Then I see C3 Login Page validation message "CSR account has been locked due to repeated failed login attempts"
    And agent login failures in Database is 4
    And the agent account is locked
    Given I'm tried to login into C3 as given CSR with password "hack me last time"
    Then I see C3 Login Page validation message "CSR account has been locked due to repeated failed login attempts"
    And the agent account is locked
    Given C3 application is accessible
    And I login into C3 with username "csrcroz1"
    #Add case notes verification according to RTC-563
    And I click on unlock csr account
    Then I see CSR unlock form
    And I unlock given user with validation verification
    And agent login failures in Database is 0
    And the agent account is unlocked

  @STBL @ACCEPTANCE
  Scenario: Override CSR lockout when password is reset/3011 Permissions. RTC-772
    Given valid CSR11 agent
    Given I'm tried to login into C3 as given CSR with password "whatever"
    Then I see C3 Login Page validation message "CSR Login: Username and Password pair not found"
    And agent login failures in Database is 1
    Given I'm tried to login into C3 as given CSR with password "12345"
    And agent login failures in Database is 2
    Given I'm tried to login into C3 as given CSR with password "' OR 1=1;--"
    And agent login failures in Database is 3
    Given I'm tried to login into C3 as given CSR with password "qwerty"
    And agent login failures in Database is 4
    And the agent account is locked
    Given I'm tried to login into C3 as given CSR with password "hack me last time"
    Then I see C3 Login Page validation message "CSR account has been locked due to repeated failed login attempts"
    And the agent account is locked
    Given C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I click on unlock csr account
    Then I see CSR unlock form
    And I unlock given user with validation verification
    And agent login failures in Database is 0
    And the agent account is unlocked