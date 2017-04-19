@TOOLS @c3Finance
Feature: Check fax reports for hotels
  Owner: Yulun Vladimir

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Invalid fax number - negative scenarious. RTC-4798
    Given some hotel from Database
    And I login into C3 with username "csrcroz1"
    And I search hotel by ID
    Then I see "Hotel Reports Delivery" link in common tasks
    When I open hotel reports delivery page
    Then I try to send hotel report by invalid fax number

  @ACCEPTANCE
  Scenario: Invalid email - negative scenarious. RTC-4799
    Given some hotel from Database
    And I login into C3 with username "csrcroz1"
    And I search hotel by ID
    When I open hotel reports delivery page
    Then I try to send hotel report by invalid email

  @STBL @ACCEPTANCE @BUG
  Scenario: Block report by hotel_id. RTC-4800
    Given delete hotel reports config exception for "630" hotel_id from DB
    And I login into C3 with username "csrcroz1"
    And I search hotel by ID
    When I open hotel reports delivery page
    And I uncheck CMR report on delivery page
    Then I verify hotel report status for CMR in DB
    And I uncheck DAR report on delivery page
    Then I verify hotel report status for DAR in DB





