@ACCEPTANCE @ANGULAR
Feature: Hotel Search through booking happy path on Angular site.
   As a price hunter, I'd like to be able to search for hotels that are located at my destination so that
   I can book a hotel I choose.

  Background: 
    Given default dataset
    Given the angular application is accessible
    Given the application is accessible

  Scenario Outline: Angular results sorting
    # Given I want to go to the Hotels angular landing page
    # Given I'm searching for a hotel in "<destinationLocation>"
    # And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    # And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    And I see angular hotel results
    And I get the angular results list of <listCriteria>
    When I sort angular results by <criteria>
    Then The angular results will be sorted by <criteria>

    Examples: 
      | destinationLocation | startDateShift | endDateShift | criteria                  | listCriteria        |
      | Chicago, IL         | 3              | 5            | Popular                   |                     |
      | Chicago, IL         | 3              | 5            | Price (low to high)       | prices              |
      | Chicago, IL         | 3              | 5            | Price (high to low)       | prices              |
      | Chicago, IL         | 3              | 5            | Recommended (high to low) | recommended ratings |
      | Chicago, IL         | 3              | 5            | Star rating (high to low) | star ratings        |
      | Chicago, IL         | 3              | 5            | Star rating (low to high) | star ratings        |
