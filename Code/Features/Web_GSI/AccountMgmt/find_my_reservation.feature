@US @ACCEPTANCE
Feature: Get access to the past and future bookings with Find my reservation functionality.
  This could be done with last 5 credit card digits or with trip departure date.
  Owner: Nataliya Golodiuk

  Background: 
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: RTC-370 find reservations with valid trip departure date
    Given user with <tripType> bookings and known departure date
    And   I want to access my booked trips
    When  I log in with existing email and valid trip departure date
    Then  I see my recently booked trip from guest trip page

    Examples: 
      | tripType |
      | car      |

    Examples: 
      | tripType |
      | hotel    |

  Scenario: RTC-371 find reservations with trip departure date of invalid format
    Given user with hotel bookings and known departure date
    And   I want to access my booked trips
    When  I log in with existing email and invalid trip departure date
    Then  I get trip departure date validation error

  @MOBILE @JANKY
  Scenario Outline: RTC-368 find reservations with last credit card digits
    Given I want to access my booked trips
    When  I am logged in with <email-id> and my credit card <credit-card number>
    Then  my booked trip summaries are available

    Examples: quotable fares parameters
      | email-id                      | credit-card number |
      | savedcreditcard@hotwire.com      | 11111              |

  #BUG53898
  @BUG
  Scenario: RTC-369 Find reservation by invalid last 5 credit card digits
    Given activate car's version tests
    And   I want to access my booked trips
    And   I login with qa_regression@hotwire.com email and invalid credit card number to see error message

  @MOBILE @CLUSTER3 @JANKY
  Scenario: Attempt to access trip summaries for all vertical
    Given I am logged in
    When  I want to access my booked trips
    Then  my booked trip summaries are available

  @MOBILE @CLUSTER3 @JANKY
  Scenario Outline: Attempt to access trip details by vertical
    Given I am logged in
    When  I want to access my booked trips
    Then  my booked <bookingType> trip details are available

    Examples: example table
      | bookingType |
      | hotel       |
      | car         |
      | flight      |
