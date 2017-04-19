@US
Feature: Verify the hotel planner layer opens up under certain conditions
  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE @CLUSTER3
  Scenario Outline: Search for 7+ rooms on UHP page
    Given I'm searching for a hotel in "<destinationLocation>"   
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want 7 room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    Then I should see hotel planner layer

  Examples:
    | destinationLocation         | startDateShift | endDateShift  | numberOfAdults | numberOfChildren |
    | San Francisco, CA           | 5              | 7             | 7              | 0                |

  @ACCEPTANCE @CLUSTER3 @ARCHIVE
  Scenario Outline: Search for 7+ rooms on HLP page
    Given I want to go to the Hotels landing page
    Given I'm searching for a hotel in "<destinationLocation>"   
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want 7 room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    Then I should see hotel planner layer

  Examples:
    | destinationLocation         | startDateShift | endDateShift  | numberOfAdults | numberOfChildren |
    | San Francisco, CA           | 5              | 7             | 7              | 0                |

  Scenario Outline: Verify that hotel planner layer opens up when length of stay is for more than 30 days
    Given I'm searching for a hotel in "<destinationLocation>"    
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    Then I should see hotel planner layer

  Examples:
    | destinationLocation         | startDateShift | endDateShift   | numberOfHotelRooms | numberOfAdults | numberOfChildren |
    | San Francisco, CA           | 5              | 37             | 1                  | 2              | 0                |

  @ARCHIVE
  Scenario Outline: Hotel planner layer opens on research from hotel results page when length of stay is more than 30 days
    Given I'm searching for a hotel in "<destinationLocation>"    
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <newEndDateShift> days from now
    And I request search quotes
    Then I should see hotel planner layer

  Examples:
    | destinationLocation         | startDateShift | endDateShift   | resultstype  | newEndDateShift |
    | San Francisco, CA           | 5              | 7              | opaque       | 36              |
    | San Francisco, CA           | 5              | 7              | retail       | 36              |
