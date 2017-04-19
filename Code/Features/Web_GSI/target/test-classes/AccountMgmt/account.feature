@platform
Feature: Account
  In order to provide access to travel history and hot dollar balance
  Users must be able to an access restricted account pages with historic data.

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @US @ACCEPTANCE
  Scenario: Test HFC user.
  # Default known_good_username is an HFC customer.
    Given I am logged in
    Then I will see my HFC status

  @US @ACCEPTANCE
  Scenario: Attempt to create new user and remove favorite airport
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account preferences
    And I add "Boston, MA - (BOS)" as a favorite airport
    And I remove my favorite airport
    Then I receive confirmation my favorite airport was removed

  @US @ACCEPTANCE
  Scenario: Attempt to create new user and add favorite airport
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account preferences
    And I add "Boston, MA - (BOS)" as a favorite airport
    Then I receive confirmation my favorite airport was updated

  @US @MOBILE @ACCEPTANCE
  Scenario: Attempt to create new user and change password | RTC-1573
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    And I change my password
    Then I receive confirmation my password was updated

  @US @MOBILE @ACCEPTANCE
  Scenario: Attempt to create new user and change my password to an invalid password
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    And I change my password to an invalid password
    Then I receive confirmation the password I chose is invalid

  @US @MOBILE @ACCEPTANCE
  Scenario: Attempt to create new user and change e-mail address
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    And I change my e-mail address
    Then I receive confirmation my e-mail address was updated

  @US @MOBILE @ACCEPTANCE
  Scenario: Attempt to unsubscribe from all email subscriptions
    Given I am logged in
    And I access my account information
    And I manage e-mail subscriptions
    And I unsubscribe from all email subscriptions
    Then I receive confirmation my subscriptions were updated

  @US  @ACCEPTANCE
  Scenario: Attempt to subscribe to Trip Watcher email subscription
    Given I am logged in
    And I access my account information
    And I manage e-mail subscriptions
    And I subscribe to Trip Watcher email subscription
    Then I receive confirmation my subscriptions were updated

  @MOBILE
  Scenario: Attempt to subscribe to Trip Watcher email subscription
    Given I am logged in
    And I access my account information
    And I manage e-mail subscriptions
    And I subscribe to Trip Watcher email subscription
    Then I receive confirmation my subscriptions were updated

  @US @ROW @MOBILE @LIMITED @SANITY
  Scenario: Attempt to access account information
    Given I am logged in
    When I access my account information
    Then my account information is available

  @MOBILE @LIMITED @SANITY @CLUSTER1 @CLUSTER3 @CLUSTERALL
  Scenario: Attempt to create new user with known good credentials
    Given I am a new user
    When I create an account
    Then I am authenticated
    And the new user is created

  @US @ROW @LIMITED @SANITY
  Scenario: Attempt to create new user with known good credentials
    Given I am a new user
    When I create an account
    Then I am authenticated
    And the new user is created

  @US @MOBILE @ACCEPTANCE
  Scenario: Attempt to access account information for Canadian user
    Given I'm from "Hong Kong" POS
    And I have valid credentials
    And I authenticate myself
    Then I am authenticated

  @US @MOBILE @ACCEPTANCE
  Scenario Outline: Attempt to book a trip as a logged in user and go to my trips page
    Given I'm a registered user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    And I choose a hotel and purchase as user a quote
    And I receive immediate confirmation
    When I access my account information
    And I attempt to navigate to <tabName> page
    Then I see my recently booked trip

  Examples: hotel search and My account tab name parameters
    | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | tabName |
    | Dublin, Ireland     | 30             | 35           | 1                  | 2              | 1                | Trips   |

  @US @ACCEPTANCE @STBL
  Scenario Outline: Attempt to click on Add to My stay link on My trips page
    Given I'm a registered user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    And I choose a hotel and purchase as user a quote
    And I receive immediate confirmation
    When I access my account information
    And I attempt to navigate to <tabName> page
    And I select add to my stay option for a booked trip
    Then I am taken to add hotel options page

  Examples: hotel search and My account tab name parameters
    | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | tabName |
    | Dublin, Ireland     | 30             | 35           | 1                  | 2              | 1                | Trips   |

  @MOBILE @JSLAVETEST @ACCEPTANCE
  Scenario Outline: Attempt to manage my subscription as a guest user from an email
    Given I access the subscription management link from an email
    When I <subscriptionState> e-mail subscriptions
    Then I receive confirmation my subscriptions were updated

  Examples: valid parameters
    | subscriptionState    |
    | subscribe to all     |
    | unsubscribe from all |

  Examples: other tests.
    | subscriptionState             |
    | subscribe to Big Deals        |
    | subscribe to Trip Watcher     |
    | unsubscribe from Trip Watcher |


  #account doesnt have any trip logged.
  @MOBILE @ACCEPTANCE @STBL
  Scenario Outline: Attempt to access trip details
    Given my name is bddbookedtrips@hotwire.com and my password is hotwirehot
    And I authenticate myself
    When I want to access my booked trips
    Then my booked trip summaries are available
    And my booked <bookingType> trip details are available

  Examples: example table
    | bookingType |
    | hotel       |
    | car         |

  @US @ACCEPTANCE
  Scenario: Change email and verify the one in DB. RTC-681
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    Then I change my email and verify the one in DB

  @US @ACCEPTANCE @STBL
  Scenario Outline: Verify opaque/retail car trip details page has correct information. RTC-900, 901
  Given I have valid random credentials
  And I authenticate myself
  And I am authenticated
  And I'm searching for a car in "San Francisco, CA - (SFO)"
  And I want to travel in the near future
  And I want to pick up at 1:30pm and drop off at 3:30pm
  And I request insurance
  And I request quotes
  And I see a non-empty list of search results
  And I choose a <carType> car
  When I fill in all billing information
  And I complete purchase with agreements
  Then I receive immediate confirmation
  And I save car confirmation information
  When I access my account information
  And  I attempt to navigate to Trips page
  When I select trip details from the first car trip summary
  When I should see correct information on the car trip details page

Examples: car type table
  | carType |
  | opaque  |
  | retail  |

