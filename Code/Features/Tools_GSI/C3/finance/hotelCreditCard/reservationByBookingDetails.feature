@TOOLS   @c3Finance
Feature:  FINANCE / Hotel Credit Card Admin / Reservation By Hotwire Booking Details /
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Check Hotel Reservation information by reservation num. RTC-1417
    Given I have valid OrderID
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by reservation number
    Then I check hotel Reservation Information

  @STBL @ACCEPTANCE
  Scenario: Hotel Reservation information by confirmation num. Check total charge Popup. RTC-1500
    Given I have valid OrderID
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by confirmation number
    Then I check total charge on hotel Reservation Information
