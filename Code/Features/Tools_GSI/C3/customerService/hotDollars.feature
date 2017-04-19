@TOOLS
Feature: Verify Hot Dollars from
  Owner: Komarov Igor


  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario:Verify reaction on Cancel button on Hot Dollars form. RTC-4043
    Given customer email with Hot Dollars
    Then I login into C3 with username "csrcroz1"
    And  I want to search customer by email
    Then I go to the customer account info
    And  I edit customer account information
    And  I verify Hot Dollars form is opened and closed correctly

  @STBL @ACCEPTANCE
  Scenario: Verify if user has no HotDollars available than in My Account he will see proper message. RTC-4047
    Given domestic application is accessible
    And  I login as customer with known credentials
    Then I go to My Trips
    And  I manage payment info on domestic site
    Then I verify no hotdollars are available on domestic site
    Given C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I search for given customer email
    Then I go to the customer account info
    And  I edit customer account information
    Then I edit customer account information
    And  I edit customer Hot Dollars
    Then I verify no hotdollars are available

  @ACCEPTANCE @JANKY
  Scenario: Check expired HotDollars. RTC-4046
    Given domestic application is accessible
    Given customer with expired HotDollars
    And I am logged in
    Then I go to My Trips
    And  I manage payment info on domestic site
    Then I verify expired hotdollars for customer on domestic site
