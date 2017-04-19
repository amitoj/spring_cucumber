@TOOLS @c3Customer  @ACCEPTANCE
Feature: Verification of "Return to customer account link"
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario: Return to customer account verification.
    Given I login into C3 with username "csrcroz1"
    And I search for customer with "caps-express@hotwire.com" email
    And I click Site search for customer
    And I see hotwire home page in C3 frame
    When I return to Customer Account
    Then I see customer details page