@TOOLS
Feature: Verify common actions for case history
  Owner:Vladimir Yulun


  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Verify case note created for customer in DB. RTC-640
    Given customer hotel purchase for search
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I see itinerary details page
    When I click on create workflow
    Then I create workflow entry
    And I check workflow entry in DB

  @STBL @ACCEPTANCE
  Scenario: Resubmit CPV. CPV Fail Only. RTC-641
    Given hotel purchase itinerary after AVC credit card failing
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    And I see itinerary details page
    Then There is no Resubmit CPV option on the itinerary

  @STBL @ACCEPTANCE
  Scenario: Itinerary's cases on Printer-Friendly and C3 pages are the same. RTC-1231
    Given customer hotel purchase for search
    And  I login into C3 with username "csrcroz1"
    Then I search for given customer purchase
    And  I see itinerary details page
    And  I edit itinerary case history
    Then I verify content of Printer-Friendly page

  @STBL @ACCEPTANCE
  Scenario: Verify case note content on the C3 case id result page. RTC-3886
    Given customer hotel purchase for search
    And   I login into C3 with username "csrcroz5"
    Then  I search for given customer purchase
    And   I see itinerary details page
    Then  I edit itinerary case history
    Then  I see case notes frame
    And   I select Outbound call method of contact
    And   I fill case notes mandatory fields
    Then  I enter "010102020303" value into Notes field
    And   I close notes
    Then  I search for recent case notes
    And   I verify case note content in results