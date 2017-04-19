@TOOLS
Feature:  Manual billing section
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Create Manual Bill Action. Check DB. RTC-63
    Given I login into C3 with username "csrcroz9"
    And I get a hotel ID with existing purchase
    When I search for given customer purchase
    And I see itinerary details page
    And I click Manually Bill/Credit Itinerary link
    Then I successfully create Manual Billing
    And  I verify status code and amount for a manual billing in DB


  @STBL @ACCEPTANCE
  Scenario: Create Manual Bill Action. Subsequent credits. RTC-64
    Given I login into C3 with username "csrcroz9"
    And I get a hotel ID with existing purchase
    When I search for given customer purchase
    And I see itinerary details page
    And I click Manually Bill/Credit Itinerary link
    Then I successfully create Manual Billing
    And  I verify status code and amount for a manual billing in DB
    And I click Manually Bill/Credit Itinerary link
    Then I successfully create Manual Billing
    And  I verify status code and amount for a manual billing in DB
