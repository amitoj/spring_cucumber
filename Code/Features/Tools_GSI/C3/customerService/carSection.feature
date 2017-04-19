@TOOLS
Feature: car search section
  Owner:Vladimir Yulun


  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Check Car Supplier Confirmation link. RTC-576
    Given I login into C3 with username "csrcroz1"
    And customer with existing purchases
    And I want to search customer by email
    And I get car past booking
    And I click first car confirmation link
    Then I see itinerary details page

  @STBL @ACCEPTANCE
  Scenario: Check Car 'View' case attached link. RTC-578
    Given I login into C3 with username "csrcroz1"
    When I search for customer with "caps-express@hotwire.com" email
    And I get car past booking
    And I am looking for purchase done by csr
    And I click View case attached link
    Then I see itinerary details page

  @STBL @ACCEPTANCE
  Scenario: Check Car 'View' case attached link. RTC-579
    Given I login into C3 with username "csrcroz1"
    When I search for customer with "tkachaeva@hotwire.com" email
    And I get car past booking
    And I am looking for purchase done not by csr
    Then I don't see View link in "Case attached" column for purchase

  @STBL @ACCEPTANCE
  Scenario: CSR sees same recent search results as customer. RTC-1660
    Given I login into C3 with username "csrcroz1"
    And customer without purchases
    And I want to search customer by email
    And I click Site search for customer
    And I search for car
    And I process the results page
    And I process the billing page
    Then I receive immediate confirmation
    And I return to Customer Account
    And I want see car tab
    Then I see that last purchase "Booked by a CSR" is Y
    And I see only 1 past purchase

  @STBL @ACCEPTANCE
  Scenario: CSR purchases a recent search on C3 site. Check email. RTC-1663
    Given domestic application is accessible
    And I am logged as "qa_regression@hotwire.com" with "hotwire333"
    When I search for car
    Then I save reference number from results page
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I want to search customer by email
    And I get recent search tab
    And I complete to purchase recent search
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    Then I receive an email with recent itinerary in body

