@TOOLS @customerSearch
Feature: Search with Hotwire reference Id number
  Hotwire customer say to CSR reference number and CSR see customer search result
  Owner: Oleksandr Zelenov

  @criticalPriority    @ACCEPTANCE
  Scenario: Search Car by reference id.
    #passed 5 times in STBL CI
    Given Domestic application is accessible
    And   I search for car
    And   I save reference number from results page
    #C3 verification
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search with reference number
    Then  I see results page in frame

  @STBL @ACCEPTANCE
  Scenario: Search Car by reference id. The same search. RTC-4366
    Given Domestic application is accessible
    Given I am logged as non-express customer
    And   I search for car
    And   I save reference number from results page
 #C3 verification
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search with reference number
    Then  I see results page with the same search parameters


  @criticalPriority    @ACCEPTANCE    @BUG
  Scenario: Search Hotel by referenced id
  #BUG53677, 53688
    Given Domestic application is accessible
    And   I search for hotel
    And   I save reference number from results page
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search with reference number
    Then  I see results page in frame

    @ACCEPTANCE
  Scenario: RTC-492 - Search for a Customer by Ref# - Car - Negative Case
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search with a "2423432" reference number
    Then  I see "Your search did not match any customer records" error message

  @STBL @ACCEPTANCE
  Scenario: Search by Ref# Inside Account - Customer validation. RTC-5506
    Given Domestic application is accessible
    Given I am logged as non-express customer
    And   I search for car
    And   I save reference number from results page
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search for customer with "caps-express@hotwire.com" email
    And   I start search with Search Reference Number on customer info page
    Then  I see reference number customer validation error

  @STBL @ACCEPTANCE
  Scenario: Search by Ref# Inside Account. RTC-5508
    Given Domestic application is accessible
    Given I am logged as express customer
    And   I search for car
    And   I save reference number from results page
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search for customer with "caps-express@hotwire.com" email
    And   I start search with Search Reference Number on customer info page
    Then  I see results page with the same search parameters

  @STBL @ACCEPTANCE
  Scenario: Search by Ref# Inside Account - Error Messages. RTC-5507
    Given I get incorrect reference number
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search for customer with "caps-express@hotwire.com" email
    And   I start search with Search Reference Number on customer info page
    Then  I see incorrect reference number error

