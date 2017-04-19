@TOOLS @c3Car @ACCEPTANCE
Feature: Car booking for registered user in C3.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @JANKY
  Scenario: Car booking for registered user in C3.  RTC-1659
    Given I login into C3 with username "csrcroz1"
    And I search for customer with "caps-non-express@hotwire.com" email
    And I click Site search for customer
    And I search for car
    And I process the results page
    And I process the billing page
    Then I receive immediate confirmation

  @STBL @ACCEPTANCE
  Scenario: CSR purchases a new search on C3 site RTC-1664
    Given I login into C3 with username "csrcroz1"
    And I search for customer with "caps-non-express@hotwire.com" email
    And I click Site search for customer
    And I search for car
    And I process the results page
    And I process the billing page
    Then I receive immediate confirmation
    And I return to Customer Account
    And I want see car tab
    Then I see that last purchase "Booked by a CSR" is Y

  @STBL @ACCEPTANCE
  Scenario: CSR search for local car zipcode purchases as masked user. RTC-1668
    Given I login into C3 with username "csrcroz1"
    And I search for customer with "caps-non-express@hotwire.com" email
    And I click Site search for customer
    And I'm searching for a car in "94135"
    And I search for car
    And I verify area map on car result page
    And I process the results page
    And I process the billing page
    Then I receive immediate confirmation
    Then I verify destination location on car confirmation page contains "San Francisco, CA"

