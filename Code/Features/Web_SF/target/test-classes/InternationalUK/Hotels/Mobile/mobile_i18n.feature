Feature: Mobile Hotels International

  Background:
    Given default dataset
    Given the application is accessible

  Scenario Outline: Dates on mobile site should be in customer's local date format
    When I visit Hotwire from <site> on a mobile device
    Then I see dates in <format> local format

  Examples:
    | site | format     |
    | uk   | dd/MM/yyyy |
    | ie   | dd/MM/yyyy |
    | no   | dd.MM.yyyy |
    | se   | yyyy-MM-dd |
    | dk   | dd-MM-yyyy |
    | au   | dd/MM/yyyy |
    | nz   | dd/MM/yyyy |
    | hk   | MM/dd/yyyy |
    | de   | dd.MM.yyyy |
    | mx   | d/MM/yyyy  |
    | sg   | M/d/yyyy   |

  Scenario Outline: Prices on mobile site should be in customer's local currency
    Given I visit Hotwire from <site> on a mobile device with default currency
    And I want to travel between 14 days from now and 21 days from now
    And I am searching for a hotel in "Paris, France" city
    When I request quotes
    And I will see the same <currency> currency

  Examples:
    | site | currency |
    | uk   | GBP      |
    | ie   | EUR      |
    | no   | NOK      |
    | se   | SEK      |
    | dk   | DKK      |
    | au   | AUD      |
    | nz   | NZD      |
    | hk   | HKD      |
    | de   | EUR      |
    | mx   | MXN      |
    | sg   | SGD      |