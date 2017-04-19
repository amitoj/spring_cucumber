@TOOLS @customerSearch
Feature: Search for itinerary in C3
  Let CSR search for customer purchase with itinerary number.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

#This 2 scenarios match RTC-490 from silktest suite
#@bshukaylo

  @SMOKE   @criticalPriority
  Scenario Outline: Search for a customer purchase with itinerary number. RTC-1656,RTC-1749,RTC-740
    Given customer <vertical> purchase for search
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I see itinerary details page

  Examples:
    | vertical |
    | air      |
    | hotel    |
    | car      |

  @ACCEPTANCE
  Scenario: Search for a Hotwire customer by itinerary. Negative case. RTC-500
    Given I login into C3 with username "csrcroz1"
    And I want to search customer with "000" confirmation number
    Then I see "Your search did not match any customer records" error message