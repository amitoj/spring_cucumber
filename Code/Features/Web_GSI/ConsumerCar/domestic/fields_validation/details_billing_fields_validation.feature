@US
Feature: Billing page validations verification
  Owner: Oleksandr Zelenov
  Owner: Nataliya Golodiuk

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: Empty fields validation RTC-6683, RTC-6684
    Given I'm searching for a car in "San Francisco, CA - (SFO)"
    And I want to travel between 6 days from now and 10 days from now
    When I request quotes
    Then I see a non-empty list of search results
    When I choose a <carType> car
    And I complete purchase without agreements
    Then I receive immediate "Enter missing information in the blank fields indicated below." error message
    And I receive immediate "At the bottom of the page, you must accept our Terms of Use." error message
    And I receive immediate "Primary driver must be at least 25 years old at time of pick up. Driver age will be verified by the rental agency." error message
    And I receive immediate "Rental Car Protection - Would you like to add insurance? Please select either yes or no." error message

  Examples:
    | carType |
    | opaque  |
    | retail  |

  Scenario Outline: Drivers info validations. RTC-6685
    Given I'm searching for a car in "AIRPORT"
    And I want to travel between 6 days from now and 10 days from now
    When I request quotes
    Then I see a non-empty list of search results
    When I choose a car
    And I fill in all billing information
    And I write to <fieldType> driver's field <text>
    And I complete purchase with agreements
    Then I receive immediate "<message>" error message

  Examples:
    | fieldType | text           | message                                                                                                          |
    | firstName | John&          | Names cannot contain any special characters or numbers, and last name must be at least 2 characters.             |
    | lastName  | Smith3         | Names cannot contain any special characters or numbers, and last name must be at least 2 characters.             |
    #| email     | agdnbh         | Enter a valid email address. Please make sure there are no spaces, and that the '@' is included.        |  BUG53349
    | confEmail | test@gmail.com | The two email addresses need to match.                                                                           |
    | email     | alex@test      | Enter a valid email address. Please make sure there are no spaces, and that the '@' is included.                 |
    | phone     | 1234567        | Please enter a 10-digit phone number beginning with area code. Do not include any letters or special characters. |
    | phone     | 1234#894       | Please enter a 10-digit phone number beginning with area code. Do not include any letters or special characters. |


  Scenario Outline: Payment block validations. RTC-6686
    Given I'm searching for a car in "AIRPORT"
    And I want to travel between 6 days from now and 10 days from now
    When I request quotes
    Then I see a non-empty list of search results
    When I choose a opaque car
    And I fill in all billing information
    And I write to <fieldType> payment field <text>
    When I complete purchase with agreements
    Then I receive immediate "<message>" error message

  Examples:
    | fieldType    | text              | message                                                                                                                                                                                                         |
    | cardNum      | 411111111111111   | Please verify that the Credit Card Number you entered is either 16 digits (for MasterCard, Visa, Discover and JCB cards) or 15 digits (for American Express cards). Also, verify that it contains only numbers. |
    | securityCode | 11                | This card requires a 3-digit security code.                                                                                                                                                                     |
    | securityCode | 1111              | This card requires a 3-digit security code.                                                                                                                                                                     |
    | securityCode | 1a1               | The security code can only contain the numbers 0-9.                                                                                                                                                             |
    | firstName    | Nooo 1Goo         | Enter the name as it appears on the credit card.                                                                                                                                                                |
    | lastName     | B@ddy             | Please enter the billing name exactly as it appears on your credit card. Do not include any special characters.                                                                                                 |
    | city         | 123               | Enter a valid city.                                                                                                                                                                                             |
    | zip          | 1000              | Enter a valid zip code.                                                                                                                                                                                         |
    | zip          | 123456            | Enter a valid zip code.                                                                                                                                                                                         |
    | zip          | 123%5             | Enter a valid zip code.                                                                                                                                                                                         |

  Scenario Outline: Credit card negative cases. RTC-5222
    Given I'm searching for a car in "AIRPORT"
    And I want to travel between 6 days from now and 10 days from now
    And I request quotes
    Then I see a non-empty list of search results
    And I choose a opaque car
    And I fill in all billing information
    And I write to cardNum payment field <number>
    And I write to securityCode payment field <code>
    When I complete purchase with agreements
    Then I receive immediate "<message>" error message

  Examples:
    | number           | code | message                                                                                                                                                                                                                                                                                          |
    | 4001277777777777 | 111  | The billing address you entered does not match what is on file with your card's issuing bank. Your card has not been charged. Please verify that you entered your billing address correctly. If it is correct, we recommend calling your bank. Or, select a different credit card and try again. |
    | 4831110847500000 | 111  | The credit card number, expiration date, or security code you entered does not match what is on file with your card's issuing bank. Your card has not been charged. Please verify that you entered your credit card number, expiration date, and security code correctly.                        |

