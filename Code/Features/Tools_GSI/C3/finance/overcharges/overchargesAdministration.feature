@TOOLS  @c3Finance
Feature: Overcharges - update Hotel Overcharges Information
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: 'Hotel Overcharges Information' page => Bulk Editor correctly work. RTC-6777
    Given I login into C3 with username "csrcroz9"
    And I go to Hotel Overcharges Admin
    And I search for unassigned overcharges
    And I see overcharges search result page
    And I open any overcharge details
    When I update any overcharge itinerary with bulk editor
    Then I check overcharge itinerary update in DB
