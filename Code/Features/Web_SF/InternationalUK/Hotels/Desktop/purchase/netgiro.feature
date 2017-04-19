@US
Feature: NetGiro
  NetGiro is a payment gateway used to process purchases in a certain currencies.

  Background:
    Given default dataset
    Given the application is accessible

  @PURCHASE @JANKY
  Scenario Outline: Purchase a hotel using NetGiro payment gateway.
    Given I'm a guest user
    And I'm from "<pointOfSale>" POS
    And currency is <currencyCode>
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  @LIMITED
  Examples: Default location
    | pointOfSale | currencyCode | destinationLocation | startDateShift | endDateShift |
    | Ireland     | EUR          | Dublin, Ireland     | 5              | 7            |

  Examples: NetGiro supported currencies
    | pointOfSale | currencyCode | destinationLocation   | startDateShift | endDateShift |
    | Sverige     | SEK          | Stockholm, Sweden     | 15             | 17           |
    | Norge       | NOK          | Oslo, Norway          | 15             | 17           |
    | Danmark     | DKK          | København, Denmark    | 5              | 7            |
    | Australia   | AUD          | Sydney, Australia     | 5              | 7            |
    | New Zealand | NZD          | Auckland, New Zealand | 5              | 7            |
    | Deutschland | EUR          | Berlin, Germany       | 5              | 7            |
    | Hong Kong   | HKD          | Hong Kong, Hong Kong  | 5              | 7            |
    | Singapore   | SGD          | Singapore, Singapore  | 5              | 7            |

  @ACCEPTANCE @PURCHASE  @BUG
  Scenario Outline: Purchase a hotel using different credit cards. RTC-4601, RTC-4603, 4598, 4599
  #BUG53939
    Given I'm a guest user
    And I have a valid <creditCard> credit card
    And I'm from "<pointOfSale>" POS
    And currency is <currencyCode>
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between 20 days from now and 22 days from now
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  @RTC-4601 @RTC-4603
  Examples: NetGiro supported credit cards
    | pointOfSale | currencyCode | destinationLocation    | creditCard       |#|
    | Ireland     | EUR          | Paris, France          | American Express |    |
    | Ireland     | EUR          | Stockholm, Sweden      | Visa Electron    |4601|
    | Ireland     | EUR          | Copenhagen, Denmark    | Maestro          |4603|

  @RTC-4598 @RTC-4599
  Examples:
    | pointOfSale | currencyCode | destinationLocation    | creditCard       |#|
    | Ireland     | EUR          |   Liverpool, UK        | Visa Electron    |4598|
    | Ireland     | EUR          |  Dublin, Ireland       | MasterCard       |4599|