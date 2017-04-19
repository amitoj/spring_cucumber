@TOOLS      @c3Hotel   @ACCEPTANCE
Feature: Interstitial page stories
  Let CSR makes purchase and inputs user data on interstitial page
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @JANKY
  Scenario: CSR purchase hotel room for new Hotwire user in C3.
    Given new customer
    Given I login into C3 with username "csrcroz1"
    And I search for hotel
    And I process the results page
    And I process the details page
    And I process the interstitial page
    And I process the billing page
    Then I receive immediate confirmation

  Scenario:  Search for customer with too many same last name on interstitial page
    Given I login into C3 with username "csrcroz1"
    And I search for hotel
    And I process the results page
    And I process the details page
    When I search for customer with last name "Snitko" on interstitial page
    Then I see search result on interstitial page

  Scenario: Interstitial Page - More than 1 customer is found by phone number
    Given I login into C3 with username "csrcroz1"
    And I search for hotel
    And I process the results page
    And I process the details page
    And I search for customer with phone number "(234) 567-8999" on interstitial page
    And I process the billing page
    Then I receive immediate confirmation

  Scenario: CSR purchase hotel room for express Hotwire customer in C3.
    Given non-express customer
    Given I login into C3 with username "csrcroz1"
    And I search for hotel
    And I process the results page
    And I process the details page
    And I process the interstitial page
    And I process the billing page
    Then I receive immediate confirmation

