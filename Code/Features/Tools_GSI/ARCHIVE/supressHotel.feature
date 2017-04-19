
Feature: Suppress opaque hotel after refund
  Let CSR search for and purchase hotel, refund it and search another.
  Owner: Alex V. Budyak

  Background:
    Given C3 application is accessible

  Scenario: Find and purchase a hotel room as a guest user in C3.
    #Need to refactor TestDesign in last step.
    Given I login into C3 with username "csrcroz1"
    And I'm looking for a hotel
    And I'm searching for a hotel in "OTH"
    And I want to travel between 7 days from now and 14 days from now
    And I want 2 room(s)
    And I will be traveling with 2 adults
    And I will be traveling with 1 children
    And I launch search
    And I switch to retail hotels
    When I choose a hotel and purchase as guest
    Then I receive immediate confirmation
    Given C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I want to search for a customer
    And I want to search customer with existing confirmation number
    And I choose service recovery
    And I choose Wrong Name recovery reason
    When I do hotel full refund and suppress
    Then I don't see suppressed hotel


