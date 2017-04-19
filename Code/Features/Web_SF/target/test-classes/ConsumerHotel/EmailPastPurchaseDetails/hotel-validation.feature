@US
Feature: Hotel information validation for guest and registered users
  User input validation from search thru booking

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario Outline: Hotel user traveler info input validation
    #created VYulun
    #blank                 RTC-1334, 1329
    #special characters  RTC-1333, 1328
    #invalid phone number
    #digital name
    #too long name
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel in the near future
    And I request quotes
    And I choose a opaque hotel
    When I am a guest user trying to populate the traveler information page with <userBookingProfile> traveler information
    When I purchase as guest a quote
    Then I should see <userBookingProfile> traveler information errors

  Examples: traveler info validation information
    | userBookingProfile   |
    | blank                |
    #| special characters   |
    #| invalid phone number |
    #| digital name         |
    #| too long name        |


  @ACCEPTANCE
  Scenario Outline: Hotel registered user booking input validation
    #created VYulun
    #RTC 1330, 1323, 1324, 1325, 1314
    Given I am a new user
    Given I create an account
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel in the near future
    And I request quotes
    And I choose a opaque hotel
    When I am a <userType> user trying to populate with <userBookingProfile> billing information
    Then I should see <userBookingProfile> billing information errors for a <userType> user

  Examples: billing validation information
    | userType   | userBookingProfile |
    | registered | blank              |
    | registered | special characters |
   # | registered | obsolete           |


  @ACCEPTANCE
  Scenario Outline: Hotel guest user booking input validation
      #created VYulun
      #RTC 1330, 1323, 1324, 1325, 1314
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel in the near future
    And I request quotes
    And I choose a opaque hotel
    When I am a <userType> user trying to populate with <userBookingProfile> billing information
    Then I should see <userBookingProfile> billing information errors for a <userType> user

  Examples: billing validation information
    | userType   | userBookingProfile |
    | guest      | blank              |
    | guest      | special characters |
    #| guest      | obsolete           |
    | guest      | numbers in name   |
    | guest      | numbers in address |
    | guest      | numbers in city    |
    | guest      | AAA in security    |

  @ACCEPTANCE
  Scenario: Hotel display of Province or Region field in Billing's Payment Information panel for Canada. RTC-1313
  #created VYulun
    Given I am canadian user
    Given I'm searching for a hotel in "Miami, FL"
    And I want to travel in the near future
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation