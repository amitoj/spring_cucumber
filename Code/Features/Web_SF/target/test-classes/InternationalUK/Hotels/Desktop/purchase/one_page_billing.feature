@ROW @REGRESSION
Feature: One page billing allows customer to type in all necessary traveller and payment information within single page.

  Background:
    Given default dataset
    Given the application is accessible

  @RTC-5441
  Scenario: Customer attempts to purchase a hotel room with invalid MasterCard.
    Given I'm a guest user with invalid MasterCard credit card
    And I choose my currency code to USD
    And I'm searching for a hotel in "London"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive error message

  @RTC-5583 @RTC-5584
  Scenario Outline: End-to-end retail and opaque hotel booking
    Given I'm a guest user
    And I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Paris, France"
    And I want to travel between 20 days from now and 22 days from now
    And I request quotes
    And I choose <type> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  Examples:
    | type   |
    | retail |
    | opaque |

  @RTC-6300
  Scenario: No Maestro logo for Germany POS

    Given I'm from "Deutschland" POS
    And I'm searching for a hotel in "Paris, France"
    And I want to travel between 15 days from now and 16 days from now
    When I request quotes
    And I choose opaque hotels tab on results
    And I choose a hotel result
    And I process the details page
    And I don't see logo Maestro card
    And I am logged in
    And I don't see logo Maestro card

  @ACCEPTANCE 
  Scenario: Inline validation for one page billing RTC-5402
    Given I'm a guest user
    And   I'm searching for a hotel in "Paris, France"
    And   I want to travel between 20 days from now and 22 days from now
    And   I request quotes
    And   I choose opaque hotels tab on results
    And   I choose a hotel result
    And   I proceed to hotel billing
    Then  I receive errors for blank fields on hotel billing page
    Then  I receive errors for too shot traveler name on hotel billing page
    Then  I receive errors for too shot card holder name on hotel billing page
    Then  I receive errors for empty zip code on hotel billing page
    Then  I receive errors for special characters in fields on hotel billing page
    Then  I fill in billing page with valid data and receive confirmation
