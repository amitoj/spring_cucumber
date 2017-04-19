@TOOLS @customerSearch
Feature: Search for car itinerary in C3 and verify car itinerary information module.
  Owner: Anastasiia Snitko

  Background:
    Given C3 application is accessible

  @STBL
  Scenario Outline: Verify car itinerary info module.
    Given customer <vertical> purchase for search
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I see itinerary details page
    And I receive purchase details from database
    And I compare data on module with value from DB

  Examples:
    | vertical |
    | car      |