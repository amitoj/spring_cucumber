
Feature: Cybersource
  Cybersource is a payment gateway used to process purchases in a certain currencies.
  Owner: Intl team

  Background: 
    Given default dataset
    Given the application is accessible

  #Untag if/when we ever move GBP/UK to cybersource.
  @JANKY @ROW @PURCHASE
  Scenario Outline: Purchase a hotel using Cybersource with different credit cards.
    Given I have a valid <creditCard> credit card
    And I'm from "<pointOfSale>" POS
    And currency is <currencyCode>
    When I'm searching for a hotel in "London, United Kingdom"
    And I request quotes
    And I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: Cybersource supported Visa credit card
      | pointOfSale    | currencyCode | creditCard       | #    |
      | United Kingdom | GBP          | Cybersource Visa | 4588 |

    Examples: Other Cybersource supported credit cards
      | pointOfSale    | currencyCode | creditCard                   | #    |
      | United Kingdom | GBP          | Cybersource American Express | 4590 |
      | United Kingdom | GBP          | Cybersource Maestro          |      |
      | United Kingdom | GBP          | Cybersource MasterCard       | 4589 |

  @LIMITED @JANKY @ROW @PURCHASE
  Scenario Outline: Purchase a hotel using Cybersource supported currency.
    And I'm from "<pointOfSale>" POS
    And currency is <currencyCode>
    And I'm searching for a hotel in "<destinationLocation>"
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: Other sites using Cybersource
      | pointOfSale | currencyCode | destinationLocation |
      | México      | MXN          | Cancun, Mexico      |

  #Owner: Cristian Gonzalez Robles
  @US @STBL @ACCEPTANCE
  Scenario: RTC-4592:UK site Hotel purchases using GBP with DINERS Card
    Given I'm a guest user
    Given I have load international UK hotwire site
    And I'm searching for a hotel in "Liverpool, UK"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    And I choose a hotel result
    And I book selected hotel
    And I fill in credit card number with dinersCreditCardNumber
    When I complete a hotel as guest a quote
    Then I receive immediate confirmation
    Then I verify purchase status, amount and currency
