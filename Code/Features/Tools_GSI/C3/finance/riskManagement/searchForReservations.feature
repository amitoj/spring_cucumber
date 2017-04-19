@TOOLS   @c3Finance
Feature:  FINANCE / Risk Management Administration (Fraud) / Search for Reservations section
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @ACCEPTANCE @JANKY
  Scenario: Dummy name. RTC-1301
    Given customer with ansnitko@gmail.com email
    Given I login into C3 with username "csrcroz8"
    Given I go to Risk Management admin page
    And I make search for reservations for hotel vertical via email
    Then I choose reservation from search list
    And I create new test Case Note
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz18"
    When I search for case notes by itinerary
    Then I see case note text in results

  @ACCEPTANCE
  Scenario: Search for reservations by email. RTC-1499
    Given email with few purchases
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via email
    When I choose reservation from search list

  @ACCEPTANCE
  Scenario: Search for reservations by itinerary. RTC-1542
    Given purchase case note itinerary
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via itinerary
    When I choose reservation from search list

  @ACCEPTANCE
  Scenario: Search for reservations by credit card number. RTC-1539
    Given I have valid 4...0028 credit card
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via credit card number
    When I choose reservation from search list

  @ACCEPTANCE
    Scenario: Search for reservations by phone number. RTC-1558
    Given customer with existing phone number with few purchase
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via phone number
    When I choose reservation from search list

  @ACCEPTANCE
  Scenario: Search for reservations by password. RTC-1559
    Given I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via password
    When I choose reservation from search list

  @ACCEPTANCE
  Scenario: Search for reservations by credit card bin lookup. RTC-1563
    Given I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via credit card bin
    When I see "THE CHASE MANHATTAN BANK (USA)" text on page

  @ACCEPTANCE
  Scenario: Search for reservations by passenger last name. RTC-1560
    Given rarely passenger last name
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via passenger last name
    When I choose reservation from search list

  @ACCEPTANCE
  Scenario: Search for reservations by payment holder last name. RTC-1561
    Given rarely payment holder last name from customers
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via payment holder last name
    When I choose reservation from search list

  @ACCEPTANCE
  Scenario: Search for reservations by customer last name. RTC-1562
    Given rarely last name from customers
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via customer last name
    When I choose reservation from search list

  @ACCEPTANCE
  Scenario: Search for reservations by IP Address. RTC-1540
    Given I have valid IP Address with few purchase
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via IP address
    When I choose reservation from search list


  @ACCEPTANCE @BUG
  Scenario:    IN PROGRESS    Search for reservations by IP GEO. RTC-1541
    Given purchase case note itinerary
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via itinerary
    When I choose reservation from search list
    When I check IP address content

  @ACCEPTANCE
  Scenario: Search for reservations by order ID, all tab. RTC-6283
    Given I have valid OrderID
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for all vertical via orderID
    When I choose reservation from search list
    And I see according orderID info on page

  @ACCEPTANCE
  Scenario: Search for reservations by order ID, hotel tab. RTC-6284
    Given I have valid OrderID
    And I login into C3 with username "csrcroz9"
    And I go to Risk Management admin page
    Then I make search for reservations for hotel vertical via orderID
    When I choose reservation from search list
    And I see according orderID info on page

  @ACCEPTANCE @STBL
  Scenario: Action buttons for Itineraries in Risk Management view. RTC-3892
    Given customer hotel purchase for search
    And   I login into C3 with username "csrcroz8"
    Then  I go to Risk Management admin page
    And   I make search for reservations for hotel vertical via itinerary
    And   I choose reservation from search list
    Then  I see action buttons at the bottom of Itinerary