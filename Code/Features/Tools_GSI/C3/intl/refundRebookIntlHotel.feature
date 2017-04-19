@TOOLS  @c3Intl
Feature: Checking refund/rebook for international hotel purchase.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @JANKY
  Scenario: Refund/rebook of International Hotel purchase
    #TODO: Investigate "We couldn't process your regquest" on jslave11. Probably wrong configuration.
    Given customer INTL hotel purchase
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    And I choose service recovery
    And I choose Wrong Name recovery reason
    When I do a full refund
    And I'm looking for a hotel
    And I rebook hotel
    Then I see refund/rebook message on confirmation page