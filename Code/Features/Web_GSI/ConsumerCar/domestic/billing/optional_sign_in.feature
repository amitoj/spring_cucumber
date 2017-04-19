@US @ACCEPTANCE
Feature: Optional sign in on the billing page.
  Owner:Oleksandr Zelenov

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: RTC-6604 Checking optional sign section availability for signedIn user.
    Given I have valid random credentials
    And I authenticate myself
    And I am authenticated
    When I'm searching for a car in "AIRPORT"
    And I want to travel between 30 days from now and 32 days from now
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a <solution> car
    Then Optional Sign in section is unavailable

  Examples:
    | solution |
    | opaque   |
    | retail   |


  Scenario Outline: RTC-6604, RTC-6703 Checking optional sign section availability for guest user.
    Given I'm a guest user
    When I'm searching for a car in "AIRPORT"
    And I want to travel between 17 days from now and 22 days from now
    And I request quotes
    Then I see a non-empty list of search results
    When I choose a <solution> car
    Then Optional Sign in section is available
    When I open Optional SignIn popup
    And I click on Password Assistance
    Then I am on password assistance page

  Examples:
    | solution |
    | opaque   |
    | retail   |

#  BML is disabled on frontend (but NOT removed from the code) in 2016.02 branch.
#  TODO: Commenting this cases until they will be removed from the code.

  #BUG https://jira/jira/browse/WGSI-344
  @BUG
  Scenario: Optional SignIn happy path. RTC-6704
    Given I'm a payable user
    When I'm searching for a car in "San Francisco, CA - (SFO)"
    And I want to travel between 19 days from now and 22 days from now
    And I don't request insurance
    And I request quotes
    And I see a non-empty list of search results
    And I choose a opaque car
    When I authenticate myself using Optional SignIn
    Then SavedVisa payment is available
  # And SavedBML payment is available
    And traveler information is populated
    When I fill in all savedVisa billing information
    And I complete purchase with agreements
    Then I receive immediate confirmation

#  BML is disabled on frontend (but NOT removed from the code) in 2016.02 branch.
#  TODO: Commenting this cases until they will be removed from the code.

  @STBL
  Scenario:Checking Optional SignIn validation. RTC-6705
    Given I'm a payable user
    When I'm searching for a car in "San Francisco, CA - (SFO)"
    And I want to travel between 10 days from now and 12 days from now
    And I don't request insurance
    And I request quotes
    And I see a non-empty list of search results
    And I choose a opaque car
    When I open Optional SignIn popup
    And I submit Optional SignIn form
    Then I receive immediate "Enter missing information in the blank fields indicated below." error message
    When I write savedCreditCard@hotwire.com to Optional SignIn email field
    And I submit Optional SignIn form
    Then I receive immediate "Enter a password." error message
    When I write savedCreditCard@hotwire.com to Optional SignIn email field
    And I write 12345 to Optional SignIn password field
    And I submit Optional SignIn form
    Then I receive immediate "The email address or password you entered is incorrect." error message
    When I write savedCreditCard to Optional SignIn email field
    And I write password to Optional SignIn password field
    And I submit Optional SignIn form
    Then I receive immediate "Email address is not valid." error message
    When I write savedCreditCard@hotwire.com to Optional SignIn email field
    And I write password to Optional SignIn password field
    And I submit Optional SignIn form
    Then SavedVisa payment is available
  #  And SavedBML payment is available
    And traveler information is populated

  @ACCEPTANCE
  Scenario: RTC-4567: Verify Password Assistance link on billing
    Given I'm a guest user
    Given I'm searching for a car in "San Francisco, CA - (SFO)"
    And I want to travel between 10 days from now and 12 days from now
    Then I request quotes
    Then I choose a opaque car
    When I open Optional SignIn popup
    And I click on Password Assistance
    Then I am on password assistance page
    And I click on In a hurry link
    Then I am on the billing page