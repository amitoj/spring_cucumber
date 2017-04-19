@TOOLS
Feature: Resend Booking Confirmation
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @criticalPriority
  Scenario Outline: Verification of resending booking confirmation for different product verticals and csr levels. RTC-572
    Given I login into C3 with username "<agent>"
    Given customer <vertical> purchase for refund
    When I search for given customer purchase
    When I resend booking confirmation
    Then I see "A confirmation message containing the reservation details has been sent to the email address" in confirmation message
    Then I see customer email in confirmation message

  @LIMITED
  Examples:
    | agent    | vertical |
    | csrcroz1 | hotel    |

  @ACCEPTANCE
  Examples:
    | agent     | vertical |
#    | csrcroz1  | air      |
    | csrcroz22 | car      |


  @STBL @ACCEPTANCE
  Scenario: RTC-71 Resend Emails for check is simply clicked
    Given I login into C3 with username "csrcroz1"
    Given customer with many hotel purchases
    When I want to search customer by email
    And I click "Resend Emails for checked"
    Then I see "Please check at least one itinerary." text on page

  @STBL @ACCEPTANCE
  Scenario: Resend Purchase Confirmation Email for One Itinerary. RTC-847
    #MAIL verification
    Given  customer itinerary for hotel with email "qa_regression@hotwire.com"
    Given  I login into C3 with username "csrcroz1"
    When   I search for given customer purchase
    And    I resend booking confirmation
    Then   I see "A confirmation message containing the reservation details has been sent to the email address" in confirmation message
    And    I see customer email in confirmation message
    Then   I receive an email with recent itinerary in body

  @STBL @ACCEPTANCE
  Scenario: Resend Purchase Confirmation Email for Multiple Itineraries. RTC-804
    #MAIL verification
    Given I login into C3 with username "csrcroz1"
    Given customer with qa_regression@hotwire.com email
    When  I want to search customer by email
    And   I get hotel past booking
    Then  I resend emails for multiply itineraries and check the ones

  @ACCEPTANCE   @STBL
  Scenario: Resend purchase confirmation email and compare with original one. RTC-788
    Given I login into C3 with username "csrcroz1"
    And   I search for customer with "qa_regression@hotwire.com" email
    Then  I click Site search for customer
    And   I search for hotel
    Then  I process the results page
    And   I process the details page
    Then  I process the billing page
    Then  I receive immediate confirmation
    And   I return to C3 Home page
    When  I search for given customer purchase
    And   I resend booking confirmation
    Then  I should receive two confirmation emails with same information