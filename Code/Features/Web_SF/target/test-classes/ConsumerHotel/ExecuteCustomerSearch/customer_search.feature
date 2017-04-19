@US @ACCEPTANCE
Feature: Hotel Search errors on UHP and landing

  Background:
    Given default dataset
    Given the application is accessible


  Scenario: Hotel search from converged hotel landing page. RTC-1438
    #author VYulun
    Given I want to go to the Hotels landing page
    When I start hotel search without specifying the destination
    Then I receive immediate "Enter a destination." error message


  Scenario: Hotel search with past dates. RTC-1439
    #author VYulun
    Given I want to go to the Hotels landing page
    And I'm searching for a hotel in "SFO"
    Given I type hotel dates between 2 week before now and 15 days from now
    When I start hotel search without specifying the destination
    Then I receive immediate "Enter a valid check-in date." error message


  Scenario: Hotel search with dates beyond 330 days from today. RTC-1440
    #author VYulun
    Given I want to go to the Hotels landing page
    And I'm searching for a hotel in "SFO"
    Given I set hotel dates between 335 day from now and 345 days from now
    When I start hotel search without specifying the destination
    Then I receive immediate "Enter a valid check-in date." error message
    Then I receive immediate "You can only reserve hotels within the next 330 days." error message


  Scenario: Hotel search with check-out date before check-in date. RTC-1441
    #author VYulun
    Given I want to go to the Hotels landing page
    And I'm searching for a hotel in "SFO"
    Given I type hotel dates between 60 day from now and 59 days from now
    When I start hotel search without specifying the destination
    Then I receive immediate "Enter a valid check-out date." error message
    Then I receive immediate "The check-out date should be after the check-in date" error message

  @BUG
  Scenario: Modify the search(rooms or adults) within session. RTC-1443
    #BUG52541: 	Modify the search within session looks broken.
    #author VYulun
    Given I want to go to the Hotels landing page
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 5 days from now and 10 days from now
    And I request quotes
    And I choose opaque hotels tab on results
    Then I will see opaque results page
    And  I choose sort by Price from low to high
    Then I remember search solutions list
    And I want to travel between 5 days from now and 10 days from now
    And I want 2 room(s)
    And I will be traveling with 3 adults
    And I request search quotes
    Then I will see opaque results page
    And  I choose sort by Price from low to high
    Then The search results should be the same like previous

  Scenario: Hotel FF default value is always 2 for adults, 0 for children. RTC-1131
    #author VYulun
    Given I want to go to the Hotels landing page
    Then The FareFinder default adult value should be 2
    Then The FareFinder default children value should be 0

  @US
  Scenario: Search a hotel by zip. RTC-1267
    #author VYulun
    Given I'm searching for a hotel in "90210"
    And I want to travel in the near future
    When I request quotes
    Then I will see opaque results page
    Then search results contain "90210" in location

  @US @ACCEPTANCE
  Scenario: Error message for invalid Zip Code for Hotel searches. RTC-1268
  #author VYulun
    Given I'm searching for a hotel in "7946512"
    And I want to travel in the near future
    When I request quotes
    Given I receive error message for the incorrect location

  @US @ACCEPTANCE
  Scenario: Error message for invalid Canadian Zip Code for Hotel searches. RTC-1269
#author VYulun
    Given I'm searching for a hotel in "AAA A1A"
    And I want to travel in the near future
    When I request quotes
    Given I receive error message for the incorrect location



