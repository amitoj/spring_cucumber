@TOOLS  @customerService
Feature: Full refund for a customer  purchase.
  Owner: Oleksandr Zelenov


  @LIMITED   @criticalPriority
  Scenario Outline: Full refund for a customer purchase. RTC-818, RTC-969, RTC-976, RTC-962.
    Given C3 application is accessible
    Given customer <vertical> purchase for refund
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    And   I choose service recovery
    And   I choose Test Booking recovery reason
    When  I do a full refund
    Then  I see refund confirmation message
    And   I see purchase status is "Refunded"
    And   I verify full refund status code for purchase in DB

  Examples:
    | vertical |
    | hotel    |
    | car      |
#    | air      |

  @JANKY @ACCEPTANCE
  Scenario Outline: Full refund for a customer purchase with case notes view link verification.
    Given C3 application is accessible
    Given customer <vertical> purchase for refund
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    And   I choose service recovery
    And   I choose Test Booking recovery reason
    When  I do a full refund
    Then  I see refund confirmation message
    And   I see purchase status is "Refunded"
    And   I verify full refund status code for purchase in DB
    When  I get <vertical> past booking
    And   I scroll past booking page to itinerary
    Then  I see View link in "Case attached" column for purchase
    When  I go to Case history page
    Then  I see Case for itinerary

  Examples:
    | vertical | # |
#    | air      | RTC-818, RTC-969, RTC-976|
    | hotel    ||
    | car      | RTC-962|

  @ARCHIVE @ACCEPTANCE
  Scenario: Full Void for a customer Air purchase. RTC-1010, RTC-1011, RTC-1012
    Given C3 application is accessible
    Given customer air purchase for void process
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    And   I choose service recovery
    And   I choose Test Booking recovery reason
    When  I do a full refund
    Then  I see refund confirmation message
    And   I see purchase status is "Void"
    And   I verify void full refund status code for air purchase in DB
    When  I get air past booking
    And   I scroll past booking page to itinerary
    Then  I see View link in "Case attached" column for purchase

  @STBL @ACCEPTANCE
  Scenario: RTC-46 partial refund. Check status and amount in DB
    Given domestic application is accessible
    When  I search for hotel
    Then  I process the results page
    Then  I process the details page
    Then  I process the billing page
    Then  I receive immediate confirmation
    And   C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search for given customer purchase
    And   I choose service recovery
    And   I choose Test Booking recovery reason
    When  I do a partial refund
    Then  I see refund confirmation message
    Then  I verify status code and amount for a refunded hotel purchase with DB

  @STBL @ACCEPTANCE
  Scenario: RTC-510 full refund. Check status in DB
    Given domestic application is accessible
    When  I search for hotel
    Then  I process the results page
    Then  I process the details page
    Then  I process the billing page
    Then  I receive immediate confirmation
    And   C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search for given customer purchase
    And   I choose service recovery
    And   I choose Test Booking recovery reason
    When  I do a full refund
    Then  I see refund confirmation message
    Then  I verify status code and amount for a refunded hotel purchase with DB

  @ARCHIVE @ACCEPTANCE
  Scenario: Verify that Service recovery cannot be completed with Test Booking reason made not by Hotwire. RTC-1025
    Given customer air purchase for refund
    And   C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search for given customer purchase
    And   I choose service recovery
    Then  I try to make refund with Test Booking reason made not by Hotwire
    And   I verify confirmation refund response window contains "Your response to a previous question falls outside of the service recovery guidelines. Service recovery cannot be completed for this customer." text
    Then  I close response window

  @ARCHIVE @ACCEPTANCE
  Scenario: Air pending Review. RTC-1757
    Given C3 application is accessible
    Given customer air purchase for refund
    Given I login into C3 with username "csrcroz1"
    When  I search for given customer purchase
    And   I choose service recovery
    And   I don't have all needed documents for refund
    And   I choose Court Summons or Jury Duty recovery reason
    When  I do a full refund
    Then  I see pending review confirmation message
    And   I get past bookings
    Then  I want see air tab
    And   I see recent purchase with Pending Review status
    Then  I click PNR link for recent air purchase
    And   I choose service recovery
    Then  I have all needed documents for refund
    And   I choose Court Summons or Jury Duty recovery reason
    When  I do a full refund
    Then  I see refund confirmation message
    And   I get past bookings
    And   I want see air tab
    Then  I see recent purchase with Refunded status

  @STBL @ACCEPTANCE
  Scenario: Verify zero refunded amount for the purchase made only with HotDollars. RTC-5183
      Given C3 application is accessible
      Given customer email with many Hot Dollars
      Given I login into C3 with username "csrcroz1"
      And   I search for given customer email
      Then  I click Site search for customer
      And   I search for hotel
      Then  I process the results page
      And   I process the details page
      Then  I process the billing page with HotDollars
      Then  I receive immediate confirmation
      When  I return to C3 Home page
      And   I search for given customer purchase
      Then  I save total HotDollars amount
      And   I choose service recovery
      Then  I choose Test Booking recovery reason
      And   I do a full refund with HotDollars
      Then  I see refund confirmation message with zero value
      And   I see refunded HotDollars in total HotDollars amount

  @STBL @ACCEPTANCE
  Scenario: Verify refunded amount for the purchase made with partial HotDollars. RTC-5184
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And   I create new customer with random email
    Given new customer
    And   I return to Customer Account
    And   I edit customer account information
    And   I edit customer Hot Dollars
    Then  I see Hot Dollars Form
    When  I add to customer account 5 Hot Dollars
    When  I close Hot Dollars Form
    And   I return to Customer Account
    Then  I click Site search for customer
    And   I search for hotel
    Then  I process the results page
    And   I process the details page
    Then  I process the billing page with HotDollars
    Then  I receive immediate confirmation
    When  I return to C3 Home page
    And   I search for given customer purchase
    Then  I save total HotDollars amount
    And   I choose service recovery
    Then  I choose Test Booking recovery reason
    And   I do a full refund with HotDollars
    Then  I see refund confirmation message
    And   I see refunded HotDollars in total HotDollars amount
