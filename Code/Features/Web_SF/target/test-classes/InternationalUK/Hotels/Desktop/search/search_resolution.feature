@ROW @ACCEPTANCE
Feature: Search resolution
  Allows customers to search hotels by nearby cities, airports, landmarks, zip codes, latitude and longitude, address.
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  @SEARCH @RTC-5360 @SingleThreaded
  Scenario: Search a hotel by airport code RTC-5360
    When I am searching for a hotel near "LGA" airport
    Then I see first suggested location is "New York, NY, United States (LGA-LaGuardia) (LGA)"

  @ACCEPTANCE @SEARCH @RTC-5361 @SingleThreaded
  Scenario: Search a hotel by city name RTC-5361
    When I am searching for a hotel in "Cambridge" city
    Then I see first suggested location is "Cambridge, Massachusetts, United States of America"

  @SEARCH @RTC-5362 @JANKY
  Scenario: Search a hotel by latitude and longitude RTC-5362
    Given I am searching for a hotel at "36.121671, -115.096099" latitude and longitude
    When I request quotes
    Then search results contain "Near the Strip" in location

  @SEARCH @RTC-5363 @JANKY
  Scenario: Search a hotel by zip code RTC-5363
    Given I am searching for a hotel by "94121" postal code
    When I request quotes
    Then search results contain "94121" in location

  @SEARCH @RTC-5364 @JANKY
  Scenario: Search a hotel by address RTC-5364
    Given I am searching for a hotel by "158 18th Ave, San Francisco, Ca. 94121" address
    When I request quotes
    Then search results contain "Japantown - Civic Center North" in location

  @SEARCH @RTC-5365 @JANKY
  Scenario: Search a hotel by landmark RTC-5365
    Given I am searching for a hotel by "Hidropark" landmark
    When I request quotes
    Then search results contain "Kiev" in location

  @SEARCH @RTC-5366 @JANKY
  Scenario: Search a hotel by City name in NO RTC-5366
    Given I'm from "Norge" POS
    Given I am searching for a hotel by "Hidropark" landmark
    When I request quotes
    Then search results contain "Kiev" in location

 @SEARCH @RTC-6473 @SingleThreaded
  Scenario: Misspelled search destination RTC-6473
    Given I am searching for a hotel in "kopenhagen danmarc"
    Then I see first suggested location is "Copenhagen, Denmark"