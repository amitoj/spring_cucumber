@TOOLS
Feature: verify c3/domestic searches
  Owner:Vladimir Yulun

  @STBL @ACCEPTANCE
  Scenario: Searched/Booked by CSR - N / Y - Hotel. RTC-479
    Given domestic application is accessible
    Given I am logged as "qa_dvalov@hotwire.com" with "hotwire333"
    When I search for hotel
    Then I save reference number from results page
    And C3 application is accessible
    And I login into C3 with username "csrcroz1"
    When I search for customer with "qa_dvalov@hotwire.com" email
    Then I get recent search tab
    Then I verify recent search is not done by CSR
    Then I complete recent search through c3
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    And I return back to recent search page
    Then I verify recent search is not done by CSR
    And I get hotel past booking
    And I see that last purchase "Booked by a CSR" is Y


  @STBL @ACCEPTANCE
  Scenario: Searched/Booked by CSR - Y / Y - Hotel. RTC-481
    And C3 application is accessible
    And I login into C3 with username "csrcroz1"
    When I search for customer with "tkachaeva@hotwire.com" email
    And I click Site search for customer
    When I search for hotel
    Then I save reference number from results page
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    And I return to Customer Account
    And I get recent search tab
    Then I verify recent search is done by CSR
    And I get hotel past booking
    And I see that last purchase "Booked by a CSR" is Y

  @STBL @ACCEPTANCE
  Scenario: View Recent Searches - Hotel - City Name. RTC-488
    Given domestic application is accessible
    And I am logged as "qa_regression@hotwire.com" with "hotwire333"
    When I'm searching for a hotel in "Miami"
    And I launch search
    And I save reference number from results page
    And C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I search for customer with "qa_regression@hotwire.com" email
    And I get recent search tab
    Then I verify hotel parameters for recent search

  @STBL @ACCEPTANCE
  Scenario: View Recent Searches - Hotel - Zip. RTC-489
    Given domestic application is accessible
    And I am logged as "qa_regression@hotwire.com" with "hotwire333"
    When I'm searching for a hotel in "90210"
    And I launch search
    And I save reference number from results page
    And C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I search for customer with "qa_regression@hotwire.com" email
    And I get recent search tab
    Then I verify hotel parameters for recent search


  @STBL @ACCEPTANCE
  Scenario: Verify Customer Info Section. RTC-1353
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    Then I search for customer with "caps-non-express@hotwire.com" email
    And I click Site search for customer
    Then I search for hotel
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    Given C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I search for customer with "caps-non-express@hotwire.com" email
    Then I check customer information section for non express customer

  @STBL @ACCEPTANCE
  Scenario: Air Booked by Customer - View Recent Searches, Past Bookings. RTC-1679
    Given domestic application is accessible
    And I am logged as "qa_regression@hotwire.com" with "hotwire333"
    And I will be flying with 1 passengers
    And I'm searching for a flight from "MIA" to "SFO"
    And I search for air
    And I process the results page
    And I save reference number from details page
    And I process the details page
    And I process the billing page
    When I receive immediate confirmation
    And C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I search for customer with "qa_regression@hotwire.com" email
    And I get recent search tab
    Then I verify air parameters for recent search
    And I get air past booking
    And I verify past booking by itinerary

  @STBL @ACCEPTANCE
  Scenario: View Recent Searches - Car. Specific time. RTC-292
    Given domestic application is accessible
    And I am logged as non-express customer
    And I'm searching for a car in "OAK"
    And I want to pick up at 4:00pm and drop off at 4:30pm
    And I search for car
    And I save reference number from results page
    When C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I search for given customer email
    And I get recent search tab
    Then I verify car parameters for recent search
    Then I verify recent search is not done by CSR

  @STBL @ACCEPTANCE
  Scenario: View Recent Searches - Searched by CSR - Y - Car. RTC-1845
    Given C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I search for customer with "qa_dvalov@hotwire.com" email
    And I click Site search for customer
    And I search for car
    When I save reference number from results page
    And I return to Customer Account
    And I get recent search tab
    Then I verify recent search is done by CSR