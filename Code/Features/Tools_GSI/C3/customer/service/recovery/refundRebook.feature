@TOOLS  @customerService     @ACCEPTANCE
Feature: Checking refund/rebook for customer purchase.
  Let CSR search for and purchase hotel, refund it and search another.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @c3Hotel  @criticalPriority   @BUG
  Scenario: Refund/rebook hotel
    #HWTL-492
    Given customer hotel purchase for refund
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    And I choose service recovery
    And I choose Wrong Name recovery reason
    When I do a full refund
    And I rebook hotel
    Then I see refund/rebook message on confirmation page

  @c3Car @criticalPriority @BUG
  Scenario: Refund/rebook car
    #BUG53197
    Given customer car purchase for refund
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    And I choose service recovery
    And I choose Wrong Name recovery reason
    When I do a full refund
    And I rebook car
    Then I see refund/rebook message on confirmation page