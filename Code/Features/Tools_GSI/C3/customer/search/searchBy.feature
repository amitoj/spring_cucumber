@TOOLS @customerSearch
Feature: Search for a customer by other fields in C3.
  Owner: Sergey Shtubey

  Background:
    Given C3 application is accessible

  @ACCEPTANCE @JANKY
  Scenario: Search for a customer by Customer ID. Checking validation. RTC-537
    Given the customer with known customer ID
    And   I login into C3 with username "csrcroz1"
    And   I want to search for a customer
    When  I search customer with invalid Customer ID
    Then  I check customer search error messages
    When  I search customer with existing Customer ID
    Then  I see account page related for current Customer ID

  @ACCEPTANCE @JANKY
  Scenario: Search for a customer by Case ID. Checking validation. RTC-541
    Given the customer with known case ID
    And   I login into C3 with username "csrcroz1"
    And   I want to search for a customer
    When  I search customer with invalid case ID
    Then  I check customer search error messages
    When  I search customer with existing case ID
    Then  I see account page related for current case ID

  @ACCEPTANCE @JANKY
  Scenario: Search for a purchase by hotel reservation number. Checking validation. RTC-542
    Given the customer with known GDS reservation number
    And   I login into C3 with username "csrcroz1"
    And   I want to search for a customer
    When  I search customer with invalid GDS reservation number
    Then  I check customer search error messages
    When  I search customer with existing GDS reservation number
    Then  I see account page related for current GDS reservation number

  @ACCEPTANCE @JANKY
  Scenario: Search for a purchase by air ticket number. Checking validation. RTC-547
    Given the customer with known air ticket number
    And   I login into C3 with username "csrcroz1"
    And   I want to search for a customer
    When  I search customer with invalid air ticket number
    Then  I check customer search error messages
    When  I search customer with existing air ticket number
    Then  I see account page related for current air ticket number

  @ACCEPTANCE @JANKY
  Scenario: Search for a purchase by car reservation number. Checking validation. RTC-543
    Given the customer with known car reservation number
    And   I login into C3 with username "csrcroz1"
    And   I want to search for a customer
    When  I search customer with invalid car reservation number
    Then  I check customer search error messages
    When  I search customer with existing car reservation number
    Then  I see account page related for current car reservation number
