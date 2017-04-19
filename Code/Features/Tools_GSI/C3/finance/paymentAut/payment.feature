@TOOLS
Feature: Overriding uncompleted purchases via C3 site
  Owner: Komarov Igor


  @ACCEPTANCE
  Scenario: Purchase using a bad CPV credit card and override purchase via C3. RTC-519
    Given C3 application is accessible
    And  I login into C3 with username "csrcroz1"
    When I have bad CPV credit card
    And  I search for customer with "caps-express@hotwire.com" email
    And  I click Site search for customer
    When I search for hotel
    And  I process the results page
    When I save reference number from details page
    And  I process the details page
    When I process the billing page without insurance
    And  I receive immediate "The credit card number, expiration date, or security code you entered does not match what is on file with your card's issuing bank. Your card has not been charged." error message
    Then I return to C3 Home page
    And  I search for given customer purchase
    When I do a cpv override on itinerary details page
    Then I verify status codes in DB for cpv overridden purchase

  @ACCEPTANCE
  Scenario: Purchase using a bad AVS credit card and override purchase via C3. RTC-517, RTC-515
    Given C3 application is accessible
    And  I login into C3 with username "csrcroz1"
    When I have bad AVS credit card
    And  I search for customer with "caps-express@hotwire.com" email
    When I click Site search for customer
    And  I search for hotel
    When I process the results page
    And  I save reference number from details page
    When I process the details page
    And  I process the billing page without insurance
    When I receive immediate "The billing information you entered does not match what is on file with your card's issuing bank. Your card has not been charged." error message
    And  I return to C3 Home page
    When I search for given customer purchase
    And  I do an avs override on itinerary details page
    Then I verify status codes in DB for avs overridden purchase