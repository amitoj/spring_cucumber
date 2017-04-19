@TOOLS @customerSearch
Feature: Search for a customer by email in C3.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE  @criticalPriority @JANKY
  Scenario: Search for a Hotwire Customer by Email. Happy Path. RTC-573, RTC-1285, RTC-1300, RTC-1298, RTC-1297
    Given I login into C3 with username "csrcroz1"
    Given customer email with hotel purchases
    When I search for given customer email
    Then I see correct customer information
    And I want to see hotel purchases of the customer
    Then I see itinerary details page
    And I see correct customer information

  Scenario: Search for a Hotwire Customer by invalid Email. Checking validation.
    Given I login into C3 with username "csrcroz1"
    When I search for customer with "qa" email
    Then I see "The Email address you entered is not valid" error message


