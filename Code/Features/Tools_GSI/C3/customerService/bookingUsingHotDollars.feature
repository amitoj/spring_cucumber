@TOOLS
Feature: View/Add/Remove HotDollars
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Booking of hotel using HotDollars. Check activity. RTC-4044
    Given I login into C3 with username "csrcroz1"
    And I search for customer with "tkachaeva@hotwire.com" email
    And I edit customer account information
    And I edit customer Hot Dollars
    Then I see Hot Dollars Form
    When I add to customer account 5 Hot Dollars
    And I return to Customer Account
    And I click Site search for customer
    And I search for hotel
    And I process the results page
    And I process the details page
    And I process the billing page with HotDollars
    Then I receive immediate confirmation
    And I return to Customer Account
    And I edit customer account information
    And I edit customer Hot Dollars
    Then I see Hot Dollars Form
    And I check HotDollar activity for last purchase

