@TOOLS   @c3Finance
Feature:  FINANCE / View Billing History/
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Historical Billing page with expanded panels - Car. RTC-5444
    Given itinerary for opaque car confirmed purchase
    And I login into C3 with username "csrcroz8"
    When I search for given customer purchase
    Then I see itinerary details page
    And I could see billing page for itinerary


  @STBL @ACCEPTANCE
  Scenario: Historical Billing page with expanded panels - Air. RTC-5445
    Given customer air purchase for search
    And I login into C3 with username "csrcroz8"
    When I search for given customer purchase
    Then I see itinerary details page
    And I could see billing page for itinerary