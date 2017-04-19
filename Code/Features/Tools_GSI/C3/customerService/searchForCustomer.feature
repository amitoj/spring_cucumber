@TOOLS
Feature: verify c3/domestic searches
  Owner:Vladimir Yulun


  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario Outline: Hotel, Car - Seach by CSR - Y. RTC-499 ,RTC-496
    Given I login into C3 with username "csrcroz1"
    And customer with no <vertical> recent searches
    And I want to search customer by email
    When I click Site search for customer
    And I search for <vertical>
    And I save reference number from results page
    And I return to Customer Account
    And I get recent search tab
    Then I verify recent search is done by CSR

  Examples:
    | vertical | RTC |
    | car      | 496 |
    | hotel    | 499 |

  @STBL @ACCEPTANCE
  Scenario: View Past Purchases - No Past Purchases. RTC-1675, RTC-966, 1290
    Given I login into C3 with username "csrcroz1"
    And customer without purchases
    And I want to search customer by email
    And I get past bookings
    Then I have no any purchase

  @STBL @ACCEPTANCE
  Scenario Outline: Hotel, Car - Seach by domestic - N. RTC-1504 RTC-498
    Given I delete all cookies
    Given domestic application is accessible
    Given I am logged as non-express customer
    When I search for <vertical>
    Then I save reference number from results page
    And C3 application is accessible
    And I login into C3 with username "csrcroz1"
    When I search for customer with "caps-non-express@hotwire.com" email
    Then I get recent search tab
    Then I verify recent search is not done by CSR

  Examples:
    | vertical | RTC  |
    | car      | 498  |
  #  | hotel    | 1504 |

  @STBL @ACCEPTANCE
  Scenario: RTC-1180 Searched/Booked by CSR - N / N
    Given domestic application is accessible
    Given I am logged as express customer
    And I search for hotel
    And I save reference number from results page
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    Given c3 application is accessible
    Given I login into C3 with username "csrcroz1"
    When I want to search customer by email
    And I get hotel past booking
    And I see that last purchase "Booked by a CSR" is N
    And I select recent hotel purchase
    Then I see itinerary details page
    And I see "N/N" text on page

  @STBL @ACCEPTANCE
  Scenario: RTC-1181 Searched/Booked by CSR - Y / Y - Hotel
    Given I login into C3 with username "csrcroz1"
    And I search for customer with "caps-non-express@hotwire.com" email
    Then I click Site search for customer
    And I search for hotel
    Then I save reference number from results page
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    And I return to Customer Account
    And I see that last purchase "Booked by a CSR" is Y
    Then I get recent search tab
    Then I verify recent search is done by CSR

  @STBL @ACCEPTANCE
  Scenario: RTC-504 - First Name Only can not be searched
    Given I login into C3 with username "csrcroz1"
    When I want to search customer with "Test" firstname "" lastname
    Then I see "Enter first and last names of the travelers." error message
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    When I want to search customer with "Express" firstname "Hotwire" lastname
    Then I see account page for "Express Hotwire"

  @STBL @ACCEPTANCE
  Scenario: RTC-546 Traveler First and Last Name with Multiple Accounts
    Given I login into C3 with username "csrcroz1"
    When I want to search customer with "Hermin" firstname "Lalefar" lastname
    Then I see multiple accounts search results
    Then I choose car past purchases for first account

