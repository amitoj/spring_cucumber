@TOOLS @customerSearch
Feature: View and created case notes
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible


  @STBL @ACCEPTANCE
  Scenario: Add note within Case notes. RTC-4121
    Given customer hotel purchase for search
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    And I edit itinerary case history
    And I edit case entries for customer
    Then I create and see new case note in history

