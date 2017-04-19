@TOOLS   @c3Finance @ACCEPTANCE
Feature: Display billing page for finance agents.
  Owner: Oleksandr Zelenov


  @criticalPriority @JANKY
  Scenario Outline: Display billing page happy path with/without insurance. RTC-1359
    Given C3 application is accessible
    Given <opacity> hotel purchase <insurance> insurance
    And I login into C3 with username "csrcroz9"
    When I search for given customer purchase
    Then I see itinerary details page
    When I open billing page
    Then I see hotel billing page in frame
    And I see hotwire terms bullets list

  Examples:
    | opacity | insurance |
    | opaque  | with      |
    | opaque  | without   |

  Scenario: Hotel retail purchase with insurance display billing page verification.
    #PreBooking
    Given domestic application is accessible
    When I search for hotel
    And I see results page
    And I switch to retail hotels
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    #Verificaton
    Given C3 application is accessible
    And I login into C3 with username "csrcroz9"
    When I search for given customer purchase
    Then I see itinerary details page
    When I open billing page
    Then I see hotel billing page in frame
    And I see hotwire terms bullets list


  @c3Hotel  @criticalPriority
  Scenario: Display billing page with HotDollars
#    #PreBooking
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz9"
    And customer email with Hot Dollars
    And I search for given customer email
    And I click Site search for customer
    And I search for hotel
    And I process the results page
    And I process the details page
    And I process the billing page with HotDollars
    Then I receive immediate confirmation
    #Verificaton
    Given C3 application is accessible
    And I login into C3 with username "csrcroz9"
    When I search for given customer purchase
    Then I see itinerary details page
    When I open billing page
    Then I see hotel billing page in frame
