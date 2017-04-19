@TOOLS   @c3Finance
Feature:  FINANCE / Hotel Credit Card Admin / Reservation By Hotel and Guest Details /
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Modify credit card. RTC-1502
    Given I have display number with valid credit card
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by guest name and dates
    And I select hotel reservation search from results
    Then I could to modify credit card

  @STBL @ACCEPTANCE
  Scenario: Update Tax Rates. RTC-1501
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by guest name and dates
    And I select hotel reservation search from results
    Then I could to update tax rate

  @STBL @ACCEPTANCE
  Scenario: Check hotel reservation information by itinerary. RTC-1416
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by guest name and dates
    And I select hotel reservation search from results
    Then I check hotel Reservation Information


  @STBL @ACCEPTANCE
  Scenario: Verify hotel Reservation Information for guest. RTC-1415
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by guest name and dates
    Then I check hotel Reservation Information for guest

  @STBL @ACCEPTANCE
  Scenario: Reservation by hotel name and state. RTC-1453
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by hotel name and state
    Then I see results for according hotel in expected state

  @STBL @ACCEPTANCE
  Scenario: Reservation for international hotel. RTC-1452
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by international supplier
    Then I see results for according hotel in expected state

  @STBL @ACCEPTANCE
  Scenario: Send a fax to Hotel. RTC-1503
    Given I have display number with valid credit card
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by confirmation number
    Then I could to change fax information


