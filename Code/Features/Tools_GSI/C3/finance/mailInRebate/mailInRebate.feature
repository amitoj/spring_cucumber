@TOOLS   @c3Finance
Feature:  FINANCE / Mail-In Rebate Admin/
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Mail-In Rebate. Duplicate itinerary. RTC-1550
    Given I have valid OrderID
    Given I login into C3 with username "csrcroz10"
    Given I go to Mail-In Rebate admin page
    And I try to add Mail-In Rebate record with A status code
    And I try to add Mail-In Rebate record with A status code
    And I see "This mail-in rebate is a duplicate!" text on page

  @ACCEPTANCE
  Scenario: Mail-In Rebate. Resubmission. Missing codes. RTC-1548
    Given I have valid OrderID
    Given I login into C3 with username "csrcroz10"
    Given I go to Mail-In Rebate admin page
    And I try to add Mail-In Rebate record with R status code
    And I see "Select at least one missing item codes, if rebate requires resubmission." text on page
