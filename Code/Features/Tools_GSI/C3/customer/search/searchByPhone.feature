@TOOLS   @customerSearch
Feature: Agent search by phone number
  Let CSR search a customer by phone number.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario: Error message verification
    Given I login into C3 with username "csrcroz1"
    When I want to search customer with "123456789" phone number
    Then I see "Phone number must be from 10 to 11 digits long." error message

  Scenario: No one customer is not found by phone number
    Given I login into C3 with username "csrcroz1"
    When I want to search customer with "0100000000" phone number
    Then I see "Your search did not match any customer records." error message

  @ACCEPTANCE   @criticalPriority
  Scenario:  More than 1 customer is found by phone number
    Given I login into C3 with username "csrcroz1"
    When I want to search customer with "415-3431221" phone number
    Then I see less than 21 search results
    When I select some customer
    Then I see the entered phone number in Primary Phone No. field
