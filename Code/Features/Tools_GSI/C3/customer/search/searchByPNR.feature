@TOOLS  @customerSearch
Feature: Search for a customer by PNR number in C3.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE   @criticalPriority
  Scenario Outline: Search for a Hotwire customer by PNR number. RTC-505,RTC-577
    Given the customer with known <vertical> PNR purchase number
    When I login into C3 with username "csrcroz1"
    And I search customer with existing PNR number
    Then I see PNR on itinerary details page

  Examples:
    | vertical |
#    | air      |
    | car      |
    | hotel    |


  @ACCEPTANCE
  Scenario: Search for a non-existing PNR number. RTC-506
    Given the customer with known air PNR purchase number
    When I login into C3 with username "csrcroz1"
    And I search customer with invalid PNR number
    When I check customer search error messages