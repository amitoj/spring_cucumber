@ROW
Feature: LiveProcessor
  LiveProcessor is a payment gateway used to process purchases in a certain currencies.
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE @PURCHASE
  Scenario Outline: Purchase a hotel using LiveProcessor payment gateway. RTC-4585, RTC-4586, RTC-4587, RTC-4595, RTC-4596, RTC-4597
    Given I'm a guest user
    And I have a <creditCard> credit card
    And I'm from "<pointOfSale>" POS
    And I choose my currency code to USD
    And I'm searching for a hotel in "<destinationLocation>"
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  @RTC-4585 @RTC-4586 @RTC-4587 @RTC-4595 @RTC-4596 @RTC-4597
  Examples: LiveProcessor supported credit cards
    | pointOfSale    | destinationLocation    | creditCard                           |#|
    | United Kingdom | London, United Kingdom | valid LiveProcessor VISA             |4585|
    | United Kingdom | Stockholm, Sweden      | valid LiveProcessor MasterCard       |4586|
    | United Kingdom | Singapore, Singapore   | valid LiveProcessor American Express |4587|
    | Ireland        | London, United Kingdom | valid LiveProcessor VISA             |4595|
    | Ireland        | Stockholm, Sweden      | valid LiveProcessor MasterCard       |4596|
    | Ireland        | Singapore, Singapore   | valid LiveProcessor American Express |4597|