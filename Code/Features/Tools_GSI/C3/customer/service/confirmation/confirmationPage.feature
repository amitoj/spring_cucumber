@TOOLS @customerService   @ACCEPTANCE
Feature: View purchase confirmation page
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario Outline: Purchase confirmation page can be opened in C3
    Given customer <vertical> purchase for search
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I see itinerary details page
    When I click View itinerary page upon confirmation link
    Then I see the confirmation page for that purchase

  Examples:
    | vertical |
    | air      |
    | car      |
    | hotel    |