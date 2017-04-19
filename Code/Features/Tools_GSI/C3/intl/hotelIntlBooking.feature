@TOOLS  @c3Intl
Feature: C3 Hotel International booking.
  Owner: Oleksandr Zelenov

  @ACCEPTANCE @JANKY
  Scenario: C3 Hotel International Guest booking. Happy Path.
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I switch to UK site using C3 Fare Finder
    And I go to United Kingdom site
    And I search for hotel
    When I choose a hotel and purchase as guest
    Then I receive immediate confirmation

  Scenario: Full intl booking via C3. RTC-6500
    Given C3 application is accessible
    Given customer with qa_regression@hotwire.com email
    Given I login into C3 with username "csrcroz1"
    And I switch to UK site using C3 Fare Finder
    And I go to United Kingdom site
    And I search for hotel
    When I choose a hotel and purchase as existing customer
    Then I receive immediate confirmation
    And I receive an email with recent itinerary in body

    #author Vladimir Y
  @STBL @ACCEPTANCE
  Scenario: International flag in Itinerary. RTC-4746
    Given international application is accessible
    Given I am logged as non-express customer
    And I search for hotel
    When I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    Given C3 application is accessible
    And I login into C3 with username "csrcroz1"
    When I want to search customer by email
    And I get hotel past booking
    And I check international site booking flag

