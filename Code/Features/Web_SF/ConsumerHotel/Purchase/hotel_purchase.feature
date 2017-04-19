@platform
Feature: Hotel Search And Purchase (Happy Path)
  Let customer search for and purchase hotel rooms.

  Background:
    Given default dataset
    Given the application is accessible

  @graphite-report-duration @JANKY
  Scenario Outline: Find and purchase a hotel room as a guest user.
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I would place adults and children as following:
      | adultsCount | childrenCount | childrenAges |
      | 2           | 1             | 7            |
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  @US @ROW @LIMITED
  Examples: opaque quotable fares parameters
    | state  | destinationLocation | startDateShift | endDateShift | resultstype |
    | is     | Dublin, Ireland     | 30             | 35           | opaque      |
    | is     | Paris, France       | 30             | 35           | retail      |

  @US @ACCEPTANCE
  Scenario Outline: Purchase hotel from different POS to test billing elements
    Given I'm a guest user
    And I'm from "<pos>" POS
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  Examples: opaque quotable fares parameters
    | pos       | state  | destinationLocation | startDateShift | endDateShift | resultstype |
    | US        | is     | Dublin, Ireland     | 30             | 35           | opaque      |
    | Canada    | is     | Paris, France       | 30             | 35           | retail      |

  Examples: opaque quotable fares parameters
    | pos       | state  | destinationLocation | startDateShift | endDateShift | resultstype |
    | Australia | is     | Dublin, Ireland     | 30             | 35           | opaque      |

  @LIMITED @US
  Scenario Outline: Check payment options availablity when insurance is or isn't selected.
    Given I am logged in
    And I am authenticated
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    And I <choice> insurance
    Then <group> payment options will be available

  Examples:
    | destinationLocation  | startDateShift | endDateShift   | choice       | group        |
    | Carmel, CA           | 3              | 5              | choose       | credit card  |
    | Carmel, CA           | 3              | 5              | don't choose | all          |

  @US @ACCEPTANCE
  Scenario Outline: Test choosing insurance and updating of billing page trip summary.
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    And I choose a hotel result
    And I book that hotel
    Then the billing page trip summary will not show insurance
    When I choose insurance
    Then the billing page trip summary will show insurance
    When I don't choose insurance
    Then the billing page trip summary will not show insurance

  Examples: opaque quotable fares parameters
    | destinationLocation | startDateShift | endDateShift | resultstype |
    | Dublin, Ireland     | 30             | 35           | opaque      |
    | Paris, France       | 30             | 35           | retail      |

  @MOBILE
  Scenario Outline: Given that a user enters a location when they perform a hotel search, then they should see results and be able to book a hotel
    Given I'm a <userType> user
    And I'm searching for a hotel in "San Francisco, CA "
    And I want to travel between 36 days from now and 38 days from now
    And I am a <persona>
    And I request quotes
    When I choose a hotel and purchase as <purchaseType> a quote
    Then I receive immediate confirmation

  Examples: hotel search parameters
    | userType      | persona           | purchaseType  |
    | guest         | family man        | guest         |

  Examples: hotel search parameters
    | userType      | persona           | purchaseType  |
    | registered    | family man        | user          |

  @MOBILE @ACCEPTANCE
  Scenario Outline: International guest users should be able to search & book a hotel in a given location.
    Given I visit Hotwire from <pos> on a mobile device
    And I'm a guest user
    And I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 34 days from now and 35 days from now
    And I am a business traveler
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  Examples:
    | pos   |
    | uk    |
    | ca    |
    

  @US @MOBILE
  Scenario Outline: Given that a user enters a location when they perform a hotel search
    then they should see results and be able to book a hotel

    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I am a <persona>
    And I request quotes in <currencyCode>
    When  I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  Examples: hotel search parameters
    | destinationLocation                            | startDateShift | endDateShift   | persona            | currencyCode |
    | San Francisco, CA                              | 34             | 35             | family man         | USD          |
    | San Francisco, CA                              | 38             | 42             | business traveler  | GBP          |

  @LIMITED @US  @JANKY
  Scenario Outline: Purchase with saved account.
    Given I am logged in
    And I am authenticated
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    And I complete the booking with saved user account
    Then I receive immediate confirmation

  Examples:
    | destinationLocation  | startDateShift | endDateShift   | numberOfHotelRooms | numberOfAdults | numberOfChildren |
    | Carmel, CA           | 3              | 5              | 1                  | 2              | 1                |

  @ACCEPTANCE @US
  Scenario Outline: Purchase with saved account using add a new traveler selection.
    Given my name is savedVisa1111@hotwire.com and my password is hotwire333
    When I authenticate myself
    Then I am authenticated
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    And I choose to add a new traveler
    And I complete the booking with saved user account
    Then I receive immediate confirmation

  Examples:
    | destinationLocation  | startDateShift | endDateShift   | numberOfHotelRooms | numberOfAdults | numberOfChildren |
    | Carmel, CA           | 3              | 5              | 1                  | 2              | 1                |

 # Commenting v.me case, because it is disabled on frontend in 2015.01 branch.
 # @ACCEPTANCE @US @ARCHIVE
 # Scenario Outline: Find and purchase a hotel room as a guest user using v.me
 #   Given I'm a guest user
 #   And I'm searching for a hotel in "<destinationLocation>"
 #   And I want to travel between <startDateShift> days from now and <endDateShift> days from now
 #   And I want <numberOfHotelRooms> room(s)
 #   And I will be traveling with <numberOfAdults> adults
 #   And I will be traveling with <numberOfChildren> children
 #   And I request quotes
 #   When I choose a hotel and purchase with V.me as guest a quote
 #   Then I receive immediate confirmation
 #
 # Examples: quotable fares parameters
 #   | destinationLocation         | startDateShift | endDateShift   | numberOfHotelRooms | numberOfAdults | numberOfChildren |
 #   | San Francisco, CA           | 10             | 11             | 1                  | 2              | 1                |

  Scenario Outline: Find and purchase a hotel room as a guest user using an invalid credit card
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    And I choose a opaque hotel
    And I fill in credit card number with invalid MasterCard
    When I purchase as guest a quote
    Then I receive immediate error message

  Examples: quotable fares parameters
    | destinationLocation         | startDateShift | endDateShift   | numberOfHotelRooms | numberOfAdults | numberOfChildren |
    | San Francisco, CA           | 10             | 11             | 1                  | 2              | 1                |
    

    #https://jira/jira/browse/HC-342 bug  by @bshukaylo
  @US @ACCEPTANCE @BUG
  Scenario Outline: Find and purchase a hotel room as a guest user using paypal.  RTC-1687
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    When I choose a hotel and purchase with PayPal as guest a quote
    And I confirm booking on PayPal sandbox
    Then I receive immediate confirmation

  Examples: quotable fares parameters
    | destinationLocation         | startDateShift | endDateShift   | numberOfHotelRooms | numberOfAdults | numberOfChildren |
    | San Francisco, CA           | 30             | 35             | 1                  | 2              | 1                |

  @US @ROW @ARCHIVE
  Scenario Outline: Verify email fields are filled in for cookied user sign in.
    Given I am logged in
    And I am authenticated
    And I logout to cookied mode
    And I am in cookied mode
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    Then the emails are populated in traveler info

  Examples: quotable fares parameters
    | destinationLocation         | startDateShift | endDateShift   |
    | San Francisco, CA           | 10             | 11             |

  @US @ROW @ACCEPTANCE
  Scenario Outline: Verify saved billing info on Billing page as signed in user.
    Given I am logged in
    And I am authenticated
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    Then the option to save billing info is not displayed

  Examples: quotable fares parameters
    | destinationLocation         | startDateShift | endDateShift   |
    | San Francisco, CA           | 10             | 11             |

  @US @ACCEPTANCE @ARCHIVE
  Scenario Outline: Verify saved billing info on Billing page as guest.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a hotel result
    And I book that hotel
    Then the option to save billing info is displayed

  Examples: quotable fares parameters
    | destinationLocation         | startDateShift | endDateShift   |
    | San Francisco, CA           | 10             | 11             |

 @ACCEPTANCE @STBL
  Scenario:Verify hotel purchase details on billing and confirmation pages and email. RTC-1413
   Given my name is qa_regression@hotwire.com and my password is hotwire333
   When I authenticate myself
   Then I am authenticated
   And I'm searching for a hotel in "San Francisco, CA - (SFO)"
   And I want to travel between 12 days from now and 20 days from now
   And I request quotes
   When I choose a hotel result
   And I book that hotel
   Then I verify purchase details on hotel billing and confirmation pages
   When I should see correct information in the hotel confirmation letter


  @US
  Scenario: Verify that there is no $1 auth and only actual amount auth and debit when purchase is less than $300
    Given I am logged in
    And I am authenticated
    Given I'm searching for a hotel in "San Jose, CA"
    And I want to travel between 21 days from now and 22 days from now
    And I request quotes
    And I choose a opaque hotel that costs less than 300
    When I complete the booking with saved user account
    Then I should not be able to see one dollar auth in db

  @US
  Scenario: Verify that there is $1 auth and actual amount auth and debit when purchase is more than $300
    Given I am logged in
    And I am authenticated
    Given I'm searching for a hotel in "San Jose, CA"
    And I want to travel between 21 days from now and 26 days from now
    And I request quotes
    And I choose a opaque hotel that costs less than 300
    When I complete the booking with saved user account
    Then I should be able to see one dollar auth in db

  @JANKY @US @ACCEPTANCE
  Scenario: User wants to view a past purchase of a hotel that has been turned off after the time of purchase. RTC-1339
  #author VYulun
    Given I am a new user
    Given I create an account
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 11 days from now and 22 days from now
    And I request quotes
    And I choose retail hotels tab on results
    When I get hotel reference(display) numbers list
    When I choose a hotel result
    When I book that hotel
    And I fill in credit card number with validCreditCardNumber
    When I purchase as guest a quote
    Then I receive immediate confirmation
    And I set hotel inactive in DB
    When I access my account information
    Then I check the past hotel purchase in "San Francisco, CA" has been booked
    And I set hotel active in DB
    #And I turned on current hotel


  @US @ROW @ACCEPTANCE
  Scenario Outline:
    Given I'm searching for a hotel in "Chicago, IL"
    And I want to travel between 3 days from now and 5 days from now
    And I request quotes
    And I choose <resultsType> hotels tab on results
    And I choose a hotel result
    And I book that hotel
    And I am on the billing page
    When I navigate back from the billing page
    Then I will see <resultsType> details page

  Examples:
    | resultsType |
    | opaque      |
    | retail      |

    #author VYulun
  @US @ACCEPTANCE
  Scenario: Cookied User - Retail Hotel Booking. RTC-4558
    Given I'm a guest user
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 11 days from now and 22 days from now
    And I request quotes
    And I choose retail hotels tab on results
    When I choose a hotel result
    When I book that hotel
    When I open Optional SignIn popup
    And I click on Password Assistance
    Then I am on password assistance page
    And I click on In a hurry link
    Then I am on the billing page

