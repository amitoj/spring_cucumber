@TOOLS
Feature:  "Hotel Finance" tab
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Find guests for a specified time. RTC-4337
    Given I login into C3 with username "csrcroz25"
    And I get a hotel ID with reservations in near future
    And I search hotel by ID
    When I open "Hotel Finance" link in common tasks
    Then hotel finance information is correct


  @STBL @ACCEPTANCE
  Scenario: Verify guest's information. RTC-4338
    Given I login into C3 with username "csrcroz25"
    And I get a hotel ID with existing purchase
    And I search hotel by ID
    When I open "Hotel Finance" link in common tasks
    Then I search hotel guest information by itinerary
    Then Hotel Reservation Details is correct

  @STBL @ACCEPTANCE
  Scenario: Check tax update email is sent. RTC-4344
    Given I setup property "hotwire.biz.c3.fn.hotel.taxupdate.email" to "qa_regression@hotwire.com"
    Then I apply properties using RefreshUtil
    Given I login into C3 with username "csrcroz1"
    And I get a hotel ID with existing purchase
    And I search hotel by ID
    When I open "Hotel Finance" link in common tasks
    Then I update Hotel Tax Rates
    Then I get 1 emails from hoteladmin@hotwire.com with subject Tax Rate Change since last 2 minutes

  @STBL @ACCEPTANCE
  Scenario: Reverting properties after static insurance checking.
    Given I setup property "hotwire.biz.c3.fn.hotel.taxupdate.email" to "testing@hotwire.com"
    Then I apply properties using RefreshUtil






