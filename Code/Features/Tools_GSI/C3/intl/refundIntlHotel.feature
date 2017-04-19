@TOOLS  @c3Intl @ACCEPTANCE
Feature: Full refund for international hotel purchase.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @BUG
  Scenario: Full refund for international hotel purchase. RTC-5379
    #https://jira/jira/browse/HWTL-607
    Given customer INTL hotel purchase
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    And   I choose service recovery
    And   I choose Test Booking recovery reason
    When  I do a full refund
    Then  I see refund confirmation message
    And   I see purchase status is "Refunded"