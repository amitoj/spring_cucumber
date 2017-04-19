@TOOLS
Feature: Block\unblock hotel purchase.
  Owner: Komarov Igor

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Block button functionality. RTC-3977
    Given Hotel purchase for the blocking
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I click block hotel
    Then I block hotel
    And  I verify hotel is blocked in DB
    Then I click unblock hotel
    Then I unblock hotel
    When I verify hotel is unblocked in DB

  @STBL @ACCEPTANCE
  Scenario: Verifying DB fields for blocked and unblocked hotels. RTC-3978
    Given Hotel purchase for the blocking
    And   I login into C3 with username "csrcroz1"
    When  I search for given customer purchase
    And   I see itinerary details page
    Then  I click block hotel
    Then  I block hotel
    And   I get hotel past booking
    Then  I go to Blocked Hotels page
    And   I see hotel in blocked list
    And   I see correct date of block for blocked hotel
    Then  I verify hotel is blocked in DB
    And   I unblock hotel from blocked hotels page
    When  I verify hotel is unblocked in DB
    And   I don't see hotel in blocked list


  @STBL @ACCEPTANCE
  Scenario: Cancel hotel while blocking. RTC-3980
    Given Hotel purchase for the blocking
    And   I login into C3 with username "csrcroz1"
    When  I search for given customer purchase
    And   I see itinerary details page
    Then  I click block hotel
    Then  I cancel hotel while blocking
    And   I get hotel past booking
    Then  I go to Blocked Hotels page
    And   I don't see hotel in blocked list
