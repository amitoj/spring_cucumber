@TOOLS
Feature:  Manual credit section
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Create Manual Credit Action. Check DB. RTC-61
    Given I login into C3 with username "csrcroz9"
    And I get a hotel ID with existing purchase
    When I search for given customer purchase
    And I see itinerary details page
    And I click Manually Bill/Credit Itinerary link
    Then I successfully create Manual Credit
    Then  I verify status code and amount for a manual credit in DB

  @STBL @ACCEPTANCE
  Scenario: Create Manual Credit Action. Subsequent credits. RTC-62
    Given I login into C3 with username "csrcroz9"
    And I get a hotel ID with existing purchase
    When I search for given customer purchase
    And I see itinerary details page
    And I click Manually Bill/Credit Itinerary link
    Then I successfully create Manual Credit
    Then  I verify status code and amount for a manual credit in DB
    And I click Manually Bill/Credit Itinerary link
    Then I successfully create Manual Credit
    Then  I verify status code and amount for a manual credit in DB


