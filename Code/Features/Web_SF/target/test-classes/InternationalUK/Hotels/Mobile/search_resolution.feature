@JANKY
Feature: Search resolution
  Allows customers to search hotels by nearby cities, airports, landmarks, zip codes, latitude and longitude, address.
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

 @ACCEPTANCE @SEARCH @RTC-6380
  Scenario Outline: Search a hotel by city name
    Given I visit Hotwire from <site> on a mobile device
    And I want to travel between 14 days from now and 21 days from now
    When I am searching for a hotel in "<location>" city
    Then I see first suggested location is "<suggestedLocation>"
    When I am searching for a hotel in "<suggestedLocation>" city
    And I request quotes
    Then search results contain "<hotelName>" in location

  Examples:
    | site | location  | suggestedLocation         | hotelName            |
    | uk   | Cambridge | Cambridge, United Kingdom | Cambridge area hotel |


  @SEARCH  @RTC-6381
  Scenario Outline: Search a hotel by airport code
    Given I visit Hotwire from <site> on a mobile device
    And I want to travel between 14 days from now and 21 days from now
    When I am searching for a hotel near "<airport>" airport
    Then I see first suggested location is "<suggestedLocation>"
    When I am searching for a hotel in "<suggestedLocation>" city
    And I request quotes
    Then search results contain "<hotelName>" in location

  Examples:
    | site | airport | suggestedLocation               | hotelName                                |    
    | mx   | MEX     | Mexico City, DF, Mexico - (MEX) | Ciudad de México area hotel              |

  @SEARCH @RTC-6382
  Scenario Outline: Search a hotel by address
    Given I visit Hotwire from <site> on a mobile device
    And I want to travel between 14 days from now and 21 days from now
    Given I am searching for a hotel by "<address>" address
    When I request quotes
    Then search results contain "<hotelName>" in location

  Examples:
    | site | address                                     | hotelName                                    |
    | uk   | in87-135 Brompton Road Knightsbridge London | Chelsea - Knightsbridge - Harrods area hotel |
    | mx   | 158 18th Ave, San Francisco, Ca. 94121      | Union Square West (oeste) area hotel         |


  @SEARCH @RTC-6383
  Scenario Outline: Search a hotel by geolocation
    Given I visit Hotwire from <site> on a mobile device
    And I want to travel between 14 days from now and 21 days from now
    Given I am searching for a hotel by "<coordinates>" geolocation
    When I request quotes
    Then search results contain "<hotelName>" in location

  Examples:
    | site | coordinates          | hotelName       |
    | uk   | 50.455755, 30.511565 | Kiev area hotel |

  @SEARCH  @RTC-6384
  Scenario Outline: Search a hotel by postal code
    Given I visit Hotwire from <site> on a mobile device
    And I want to travel between 14 days from now and 21 days from now
    Given I am searching for a hotel by "<postalCode>" postal code
    When I request quotes
    Then search results contain "<hotelName>" in location

  Examples:
    | site | postalCode | hotelName                           |
    | uk   | SW1X7XL    | Kensington - Earls Court area hotel |