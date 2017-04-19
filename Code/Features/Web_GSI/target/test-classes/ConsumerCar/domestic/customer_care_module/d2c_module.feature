@US @BUG
Feature: D2C Popups. Checking search options when I compare with other car's retailers
  Owner: Vyacheslav Zyryanov
  Owner: Nataliya Golodiuk
  #BUG while d2c partners are not displayed in d2c module on jslaves. Investigation needed.

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: Verify that D2C module leads to the partner sites opened with correct search parameters. RTC-6854, 4832, 4835
    Given I'm searching for a car in "JFK"
    And   I want to travel between 15 days from now and 18 days from now
    And   I request quotes
    Then  I see a non-empty list of search results
    When  I select next partners "CarRentals.com, Expedia, BookingBuddy" in <numberOfModule>
    And   I am able to compare all selected retailers from <numberOfModule>
    Then  I check search options when I compare with other car's retailers
      | CR      | John F. Kennedy International Airport - New York (JFK) |
      | EXPEDIA | New York, NY (JFK-John F. Kennedy Intl.)               |
      | BBUDDY  | New York City, NY (JFK)                                |
    Given I check partners in click tracking table in DB
      | CR | EX | BB |

   Examples:
    |numberOfModule  |
    |first D2C module|
    |second D2C module|

  Scenario: RTC-6851 select all partners for comparison
    Given I'm searching for a car in "JFK"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    When I select all partners for comparison
    Then all of car retailers are selected
    And I am able to compare all selected retailers from first D2C module
    Then I check search options when I compare with other car's retailers
      | CR      | John F. Kennedy International Airport - New York (JFK) |
      | EXPEDIA | New York, NY (JFK-John F. Kennedy Intl.)               |
      | BBUDDY  | New York City, NY (JFK)                                |
      | ORBITZ  | JFK                                                    |

  Scenario: RTC-6848 verify displaying partners according to search type
    Given I'm searching for a car in "New York City, NY"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    Then next partners "BookingBuddy" is invisible
    Given I want to go to the Cars landing page
    And I'm searching for a car in "New York City, NY - (LGA)"
    And I want to travel in the near future
    And I request quotes
    Then next partners "BookingBuddy" is visible


