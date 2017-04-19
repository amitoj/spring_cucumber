@US  @simulator
Feature: PayPal validations.
  Owner:Oleksandr Zelenov

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: PayPal empty fields validation. RTC-6863
    Given I'm searching for a car in "SFO"
    And I want to travel in the near future
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    And I select PayPal payment option
    And I complete purchase with agreements
    Then I receive immediate "Enter missing information in the blank fields indicated below." error message

  Scenario Outline: PayPal payment section validation. RTC-6863
    Given I'm searching for a car in "SFO"
    And I want to travel in the near future
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    And I fill in all PayPal billing information
    And I write to <fieldType> PayPal field <text>
    And I complete purchase with agreements
    Then I receive immediate "<message>" error message

  Examples:
    | fieldType | text              | message                                                                                                         |
    | firstName | Jo@{hn            | Enter the name as it appears on the credit card.                                             |
    | lastName  | Smith*            | Enter the name as it appears on the credit card.     |
    | street    | 1!% | Please make sure your street address contains a number and street name.                                      |
    | city      | assaut13          | Enter a valid city.                                                                                             |
    | zip       | 1000              | Enter a valid zip code.                                                                                         |
    | zip       | 123456            | Enter a valid zip code.                                                                                         |


  Scenario: Checking clicking on cancel on PayPal sandbox site.
    When I'm searching for a car in "SFO"
    And I want to travel in the near future
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    And I fill in all PayPal billing information
    And I complete purchase with agreements
    And I cancel booking on PayPal sandbox
    Then I see car billing page