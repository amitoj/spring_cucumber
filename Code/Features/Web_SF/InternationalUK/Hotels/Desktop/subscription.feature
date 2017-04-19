@US
Feature: Subscription
  Customers are able to subscribe to Hotwire newsletters during registration and booking.
  Owner: Intl team

  Background: 
    Given default dataset
    Given the application is accessible

  Scenario Outline: Default checkbox state and proposal during registration may vary between different sites
    Given I'm from "<country>" POS
    And I am a new user
    When I go to register and set country
    Then I see newsletter subscription checkbox is <state>
    And subscription proposal is <proposal>

    Examples: Subscription proposal should be negative on Ireland site
      | country        | state   | proposal                                                                            |
      | United Kingdom | checked | Yes! I want to hear about great deals, special offers, and news from Hotwire.       |
      | Ireland        | checked | No! I do not wish to hear about great deals, special offers, and news from Hotwire. |

    @RTC-6310
    Examples: Subscription checkbox should be unchecked by default on Germany
      | country     | state   | proposal                                                                                       |
      | Deutschland | checked | Ja! Ich möchte von fantastischen Deals, Spezialangeboten und Neuigkeiten von Hotwire erfahren. |

  Scenario Outline: Default checkbox state and proposal during booking may vary between different sites
    Given I'm from "<country>" POS
    And I'm searching for a hotel in "London"
    And I want to travel between 20 days from now and 22 days from now
    And I request quotes
    And I choose a opaque hotel
    Then I see newsletter subscription checkbox is <state>
    And subscription proposal is <proposal>

    Examples: Subscription proposal should be negative on Ireland site
      | country        | state     | proposal                                                                            |
      | United Kingdom | checked   | Yes! I want to hear about great deals, special offers, and news from Hotwire.       |
      | Ireland        | unchecked | No! I do not wish to hear about great deals, special offers, and news from Hotwire. |

    @RTC-6310
    Examples: Subscription checkbox should be unchecked by default on Germany
      | country     | state     | proposal                                                                                       |
      | Deutschland | unchecked | Ja! Ich möchte von fantastischen Deals, Spezialangeboten und Neuigkeiten von Hotwire erfahren. |
  
  @JANKY
  @RTC-6556
  Scenario Outline: subscription option is hidden on billing when user is already subscribed to newsletters
    And I'm from "<country>" POS
    Given I'm authenticated <state> to newsletters user
    And I'm searching for a hotel in "London, United Kingdom"
    And I want to travel between 20 days from now and 22 days from now
    And I request quotes
    When I choose a opaque hotel
    Then I <have> an option to change my subscription state

    Examples: 
      | country        | state        | have        |
      | United Kingdom | subscribed   | do not have |
      | Ireland        | subscribed   | do not have |
      | United Kingdom | unsubscribed | have        |
      | Hong Kong      | unsubscribed | have        |

  Scenario: unsubscribe from Hotwire deals
    Given I'm from "United Kingdom" POS
    And I'm authenticated subscribed to newsletters user
    And I access my account information
    And I manage e-mail subscriptions
    And I unsubscribe from Hotwire deals email subscriptions
    Then I receive confirmation my subscriptions were updated

  @PURCHASE @RTC-5549
  Scenario: Subscription layer that appears on the results page after N searches
    Given I'm a guest user
    And I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Paris, France"
    And I want to travel between 20 days from now and 22 days from now
    When I request quotes
    Then I see subscription layer and do subscribe

  @ACCEPTANCE
  Scenario: RTC-4894:Subscribe new customer
    Given I have load international UK hotwire site
    When I enter an email address in subscription email field
    Then I verify in DB that columns WANTS_NEWS_LETTER is set to Y and THIRD PARTY is null

  @ACCEPTANCE
  Scenario: RTC-4897:Change Intl customer subscription
    Given I have load international UK hotwire site
    When I enter an intl site registered email in subscription email field
    Then I verify in DB that columns WANTS_NEWS_LETTER is set to Y and THIRD PARTY is null
