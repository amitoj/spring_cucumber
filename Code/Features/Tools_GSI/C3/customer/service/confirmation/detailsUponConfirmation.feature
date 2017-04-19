@TOOLS @customerService @ACCEPTANCE
Feature: Verify details upon confirmation link.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario: Verify details upon confirmation link.
    Given customer hotel purchase for search
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I see itinerary details page
    When I click on details upon confirmation
    Then I see details page upon confirmation
 #   And I see the map