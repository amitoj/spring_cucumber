@TOOLS
Feature: Verify that allowed recovery status for cars and airs
  Owner: Oleksandr Zelenov

  Scenario: customer with allowed recovery status "NO"
    Given customer purchase with allowed recovery status "NO"
    And C3 application is accessible
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    And Allowed Recovery status is Not allowed
    And I choose service recovery
    And I choose Test Booking recovery reason
    When I do a full refund
    Then I see refund confirmation message
    And I see purchase status is "Refunded"
    Then I see "Allowed Recovery displayed: No" in the notes