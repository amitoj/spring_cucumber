# Tag as JANKY as selecting the test hotel may or may not show up in the results.
@ROW @JANKY
Feature: Mandatory fees are displayed only to customers from certain POSas.
  Fees are included into total price on results page and are displayed on details page,
  confirmation page and confirmation message.
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE @PURCHASE
  Scenario: Book retail hotel with mandatory fees RTC-6487
    Given I am from "Australia" site
    And   I'm searching for a hotel in "Kattengat 1, Amsterdam , Netherlands , 1012 SZ"
    And   I want to travel between 7 days from now and 8 days from now
    When  I request quotes
    And   I choose retail hotels tab on results
    And   I choose "Amsterdam Renaissance Hotel" hotel
    Then  price is the same I noticed on results
    And   mandatory hotel-imposed fees include "This hotel charges a city local tax percent of about 5.5% per room per stay"
    When  I book that hotel
    Then  I see hotel-imposed taxes and fees are included in total price
    When  I purchase as guest a quote
    Then  I receive immediate confirmation
    And   I am not charged for hotel-imposed taxes and fees