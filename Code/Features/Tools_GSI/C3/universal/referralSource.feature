@TOOLS @customerSearch
Feature:  Capture C3 Referral Source /
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible


  @STBL @ACCEPTANCE
  Scenario: Case note and referral source. confirmationNumber is blank. RTC-5427
    Given I login into C3 with username "csrcroz1"
    And I search for hotel
    And I save reference number from results page
    And I following URL with blank confirmationNumber
    When I see case notes frame
    And I fill case notes mandatory fields
    And I close notes
    Then New case note is created and saved in DB with correct referral source

  @STBL @ACCEPTANCE
  Scenario: Case note and referral source. searchReferenceNumber is blank. RTC-5428
    Given I login into C3 with username "csrcroz1"
    And customer hotel purchase for search
    And I following URL with blank searchReferenceNumber
    When I see case notes frame
    And I fill case notes mandatory fields
    And I close notes
    Then New case note is created and saved in DB with correct referral source

  @STBL @ACCEPTANCE
  Scenario: ConfirmationNumber and SearchReferenceNumber are blank. RTC-5429
    Given I login into C3 with username "csrcroz1"
    And I following URL with both blank searchReferenceNumber and confirmationNumber
    When I see case notes frame
    And I fill case notes mandatory fields
    And I close notes
    Then New case note is created and saved in DB with correct referral source

  @STBL @ACCEPTANCE
  Scenario: ReferralSource > 10 characters. RTC-5430
    Given I login into C3 with username "csrcroz1"
    And customer hotel purchase for search
    And I following URL with incorrect referral source
    When I see case notes frame
    And I fill case notes mandatory fields
    And I close notes
    Then New case note is created and saved in DB with correct referral source
