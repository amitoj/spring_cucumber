Feature: Car Search And Purchase (Happy Path) - Registered and guest users using  all available credit cards
  Let customer search for and purchase cars.

  Background: 
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  # master
  @US @ACCEPTANCE 
  Scenario Outline: Booking a car as registered user - Master card
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm a master card user
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between 15 days from now and 16 days from now
    And I <negation> request insurance
    And I request quotes
    And I choose a <carType> car and purchase as a quote
    Then I receive immediate confirmation

    Examples: car rental locations
      | pickUpLocation        | carType | currency | negation |
      | San Francisco, CA - (SFO) | opaque  | USD      |          |

  # visa
  @US @ACCEPTANCE 
  Scenario Outline: Booking a car as a guest user - Visa card - retail 
    Given I'm a visa card user
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between 15 days from now and 16 days from now
    And I <negation> request insurance
    And I request quotes
    And I choose a <carType> car and purchase as a quote
    Then I receive immediate confirmation

    Examples: car rental locations
      | pickUpLocation       | carType | currency | negation |
      | San Francisco, CA - (SFO) | retail  | USD      |          |

  # american exp
  @US @ACCEPTANCE 
  Scenario Outline: Booking a car as a guest user - American exp card
    Given I'm a americanexp card user
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between 15 days from now and 16 days from now
    And I <negation> request insurance
    And I request quotes
    And I choose a <carType> car and purchase as a quote
    Then I receive immediate confirmation

    Examples: car rental locations
      | pickUpLocation            | carType | currency | negation |
      | San Francisco, CA - (SFO) | opaque  | USD      |          |

  # discover
  @US @ACCEPTANCE 
  Scenario Outline: Booking a car as a guest user - Discover card
    Given I'm a discover card user
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between 15 days from now and 16 days from now
    And I <negation> request insurance
    And I request quotes
    And I choose a <carType> car and purchase as a quote
    Then I receive immediate confirmation

    Examples: car rental locations
      | pickUpLocation        | carType | currency | negation |
      | San Francisco, CA - (SFO) | opaque  | USD      |          |

  # jcb
  @US @ACCEPTANCE 
  Scenario Outline: Booking a car as a guest user - JCB card
    Given I'm a jcb card user
    And I'm searching for a car in "<pickUpLocation>"
    And I want to travel between 15 days from now and 16 days from now
    And I <negation> request insurance
    And I request quotes
    And I choose a <carType> car and purchase as a quote
    Then I receive immediate confirmation

    Examples: car rental locations
      | pickUpLocation        | carType | currency | negation |
      | San Francisco, CA - (SFO) | opaque  | USD      |          |

  @US @ACCEPTANCE @STBL
  Scenario: RTC-513 3 credit cards purchase
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a car in "San Francisco, CA - (SFO)"
    And I want to travel between 15 days from now and 16 days from now
    And I don't request insurance
    And I request quotes
    And I choose a opaque car
    Then I fill in billing information to cause a AVS error
    Then I complete purchase with agreements
    Then I receive immediate "The billing address you entered does not match what is on file with your card's issuing bank." error message
    Then I fill in billing information to cause a CPV error
    Then I complete purchase with agreements
    Then I receive immediate "The credit card number, expiration date, or security code you entered does not match what is on" error message
    Then I fill in billing information to cause a Auth error
    Then I complete purchase with agreements
    Then I receive immediate "The credit card number, expiration date, or security code you entered does not match what is on" error message
    Then I fill in all billing information
    Then I complete purchase with agreements
    Then  the purchase was failed due of
    """
    Your bank was unable to authorize the credit card security information you entered. For your protection, this purchase has been cancelled. Please try another search.

    Note: Hotwire Customer Care is unable to assist you with credit card security verification questions. Please contact your card issuer if you are having trouble. Error #216
    """
    Then I verify status and network codes for failed car purchase with DB




