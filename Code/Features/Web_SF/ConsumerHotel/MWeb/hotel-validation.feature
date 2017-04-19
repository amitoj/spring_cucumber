@MOBILE
Feature: Mobile information validation for guest and registered users
  User input validation from search thru booking

  Background:
    Given default dataset
    Given the application is accessible


  Scenario Outline: Attempt to launch hotel search with invalid city parameters
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between 5 days from now and 6 days from now
    And I launch a search
    Then I see a <validationError> validation error on <field>

  @JANKY
  Examples: hotel search parameters
    | destinationLocation  | validationError     | field |
    |                      | empty destination   | city  |
    | sfo%                 | invalid character   | city  |
    | ab                   | minimum length      | city  |
  @CLUSTER4 @CLUSTER3
  Examples: hotel search parameters
    | destinationLocation  | validationError     | field |
    | onantarticaca        | invalid destination | city  |

  Scenario Outline: Attempt to launch hotel search with invalid dates
    Given I'm a guest user
    And I'm searching for a hotel in "SFO"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I launch a search
    Then I see a <validationError> validation error on date

  Examples: hotel search parameters
    | startDateShift | endDateShift | validationError   |
    | 5              | 3            | invalid check-out |
    | 4              | 4            | invalid check-out |

  Scenario Outline: Given that a user attempts to sign in in the billing path
  with blank or invalid or special characters in credentials
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 5 days from now and 6 days from now
    And I request quotes
    And I choose a opaque hotel
    When I am a registered user trying to populate with <credentialType> credentials
    Then I should see <credentialType> credential information errors

  Examples: validation parameters
    | credentialType     |
    | blank              |
    | invalid            |
    | special characters |

  @STBL
  Scenario Outline: Mobile user traveler info input validation
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 5 days from now and 6 days from now
    And I request quotes
    And I choose a opaque hotel
    When I am a guest user trying to populate the traveler information page with <userBookingProfile> traveler information
    Then I should see <userBookingProfile> traveler information errors

  Examples: traveler info validation information
    | userBookingProfile   |
    | blank                |
    | special characters   |
    | invalid phone number |

   @JANKY
  Scenario Outline: Mobile user booking input validation
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 5 days from now and 6 days from now
    And I request quotes
    And I choose a opaque hotel
    When I am a <userType> user trying to populate with <userBookingProfile> billing information
    Then I should see <userBookingProfile> billing information errors for a <userType> user

  Examples: billing validation information
    | userType   | userBookingProfile |
    | registered | blank              |
    | guest      | blank              |
    | registered | special characters |
    | guest      | special characters |
    | registered | obsolete           |
    | guest      | obsolete           |
    | guest      | numbers in name    |


  Scenario Outline: Mobile user traveler info input validation RTC-6147, 6139, 6138
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 5 days from now and 6 days from now
    And I request quotes
    And I choose a opaque hotel
    When I am a guest user trying to populate the traveler information page with <userBookingProfile> traveler information
    Then I should see <userBookingProfile> traveler information errors

  Examples: traveler info validation information
    | userBookingProfile   |
    | blank country        |
    | digital name         |
    | too long name        |