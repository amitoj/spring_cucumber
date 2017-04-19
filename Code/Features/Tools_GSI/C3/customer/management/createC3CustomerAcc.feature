@TOOLS   @c3Management    @SingleThreaded
Feature: Supervisor creates C3 customer
  Supervisor try to create C3 customer account.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @highPriority
  Scenario: Verify new created customer. Edit Customer account. Search customer by primary phone number.
    #TODO: Activate customer by email link
    #create customer
    Given I login into C3 with username "csrcroz1"
    And   I create new customer in C3
    #add phone
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer email
    And   I edit customer account information
    And   I add some phones to customer account
    And   I see the message that account updated
    #verify customer information using search by phone
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I want to search customer with primary phone number
    Then  customer information is correct

  @ACCEPTANCE
  Scenario: Creating Upper level CSR accounts. RTC-1321
    #Author VYulun
    Given I login into C3 with username "csrcroz9"
    And   I go to Edit CSR account page
    When  I create new CSR with 'Hotel Credit Card (3007)' user level
    Then  I see "A new account for TestRTC CSR was successfully created and is now active." create csr message
    When  I create new CSR with 'Risk Management (3008)' user level
    Then  I see "A new account for TestRTC CSR was successfully created and is now active." create csr message
    When  I create new CSR with 'Mail-In Rebate (3010)' user level
    Then  I see "A new account for TestRTC CSR was successfully created and is now active." create csr message
    When  I create new CSR with 'Manual Billing/Credit Admin (3011)' user level
    Then  I see "A new account for TestRTC CSR was successfully created and is now active." create csr message

  @ACCEPTANCE @BUG
  Scenario: Create customer account validation verification. All fields blank. RTC-471
    # Bug HWTL-559: C3: Fields validation is broken on Edit Customer account and Add Hotdollars forms.
    Given I login into C3 with username "csrcroz1"
    When  I try create C3 customer with blank fields
    Then  I see error messages for blank fields on C3 customer creating page
    When  I fill data for new customer and submit
    Then  verify customer in DB

  @ACCEPTANCE
  Scenario: Create customer account validation verification. Different email and confirmation email. RTC-470
    Given I login into C3 with username "csrcroz1"
    When  I try create C3 customer with wrong confirm email
    Then  I see "The two email addresses need to match." create customer error message
    When  I fill data for new customer and submit
    Then  verify customer in DB

  @ACCEPTANCE
  Scenario: Create new customer account. Newsletter - Opt In. RTC-468
    Given I login into C3 with username "csrcroz1"
    When  I create new customer with subscription
    Then  I see in DataBase that customer subscription is Y

  @ACCEPTANCE
  Scenario: Create new customer account. Newsletter - Opt out. RTC-469
    Given I login into C3 with username "csrcroz1"
    When  I create new customer without subscription
    Then  I see in DataBase that customer subscription is N
