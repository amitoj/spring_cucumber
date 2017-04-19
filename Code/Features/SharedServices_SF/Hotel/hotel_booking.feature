Feature: Hotel Booking using various Payment Methods (Happy Path)
  Let customer search for and purchase hotel rooms using various Payment Methods.

  Background:
    Given default dataset
    Given the application is accessible
    Given the tripwatcher layer is not activated

@US @STBL
  Scenario Outline: On Desktop site, Guest user books Hotel using new Credit Card and don't save the card.
    Given I'm a guest user
    And I am on home page
    And I'm from "<pos>" POS
    And I'm a <cardtype> card user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel result
    Then I will see <resultstype> details page
    When I book that hotel
    Then I am on the billing page
    And I fill in billing information and don't save billing information with name TestCard1
    When I purchase as guest a quote
    Then I receive immediate confirmation

  Examples: opaque quotable fares parameters
    | pos            | destinationLocation    | resultstype | cardtype    |
    | United States  | Seattle, WA            | opaque      | americanexp |
    | United States  | Miami, FL              | retail      | discover    |
    | United Kingdom | Manchester, England    | opaque      | visa        |
    | United Kingdom | Manchester, England    | retail      | master      |

@US @STBL
  Scenario Outline: On Desktop site, Guest user books Hotel using new Credit Card and saves the card.
    Given I'm a guest user
    And I am on home page
    And I'm from "<pos>" POS
    And I'm a <cardtype> card user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel result
    Then I will see <resultstype> details page
    When I book that hotel
    Then I am on the billing page
    And I fill in credit card information
    And I save billing information for guest user with name TestCard1 and password hotwire333
    When I purchase as guest a quote
    Then I receive immediate confirmation

  Examples: opaque quotable fares parameters
    | pos            | destinationLocation    | resultstype | cardtype    |
    | United States  | Seattle, WA            | opaque      | americanexp |
    | United States  | Miami, FL              | retail      | discover    |
    | United Kingdom | Manchester, England    | opaque      | visa        |
    | United Kingdom | Manchester, England    | retail      | master      |

@US @STBL
  Scenario Outline: On Desktop site, Logged-in user books Hotel using new Credit Card and saves the card.
    Given I am a new user
    Given I create an account
    When I am on home page
    And I'm from "<pos>" POS
    And I'm a <cardtype> card user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel result
    Then I will see <resultstype> details page
    When I book that hotel
    Then I am on the billing page
    And I fill credit card and save billing information for new registered user
    And I complete purchase with agreements
    Then I receive immediate confirmation

  Examples: opaque quotable fares parameters
    | pos            | destinationLocation | resultstype | cardtype    |
    | United States  | Seattle, WA         | opaque      | americanexp |
    | United States  | Miami, FL           | retail      | discover    |
    | United Kingdom | Manchester, England | opaque      | visa        |
    | United Kingdom | Manchester, England | retail      | master      |

@US @STBL
  Scenario Outline: On Desktop site, Logged-in user books Hotels using existing Credit Card.
    Given I am a new user
    Given I create an account
    When I am on home page
    And I'm from "<pos>" POS
    And I'm a <cardtype> card user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel result
    Then I will see <resultstype> details page
    When I book that hotel
    Then I am on the billing page
    And I fill credit card and save billing information for new registered user
    And I complete purchase with agreements
    Then I receive immediate confirmation

    When I am on home page
    And I'm a <cardtype> card user

    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose <resultstype> results getting rid of tripwatcher
    When I choose a hotel result
    Then I will see <resultstype> details page
    When I book that hotel
    Then I am on the billing page
    And I fill credit card and save billing information for logged user
    And I complete booking with an existing account
    Then I receive immediate confirmation

  Examples: opaque quotable fares parameters
    | pos            | destinationLocation | resultstype | cardtype    |
    # fails with card security code | United States  | Seattle, WA            | opaque      | americanexp | 
    | United States  | Miami, FL           | retail      | discover    |
    | United Kingdom | Manchester, England | opaque      | master      |
    | United Kingdom | Manchester, England | retail      | visa        |

  @US @STBL
  Scenario Outline: On Domestic Desktop site, Guest user books Hotel using PayPal.
    Given I'm a guest user
    When I'm searching for a hotel in "<destinationLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose <resultsType> hotels tab on results
    When I choose a hotel and purchase with PayPal as guest a quote
    And I confirm booking on PayPal sandbox
    Then I receive immediate confirmation

  Examples: opaque quotable fares parameters
    | destinationLocation | resultsType |
    | Miami, FL           | retail      |
    | Seattle, WA         | opaque      |

  @US @STBL
  Scenario Outline: On Domestic Desktop site, Logged-in user books Hotel using PayPal.
    Given I am a new user
    Given I create an account
    When I am on home page
    Then I'm searching for a hotel in "<destinationLocation>"
    And I want to travel in the near future
    And I request quotes
    And I choose <resultsType> hotels tab on results
    When I choose a hotel and purchase with PayPal as guest a quote
    And I confirm booking on PayPal sandbox
    Then I receive immediate confirmation

  Examples: opaque quotable fares parameters
    | destinationLocation | resultsType |
    | Seattle, WA         | opaque      |
    | Miami, FL           | retail      |