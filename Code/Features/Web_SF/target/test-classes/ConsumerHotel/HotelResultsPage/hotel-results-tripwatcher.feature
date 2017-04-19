@ACCEPTANCE
Feature: Hotel Results Tripwatcher
    Trip watcher module in search results pages.

  Background:
    Given default dataset
    Given the application is accessible

  @US
  Scenario Outline: Test tripwatcher layer in results with the results speedbump.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose <resultstype> hotels tab on results
    Then I <tripwatcherState> see the tripwatcher module in the results list
    When I click the tripwatcher module in the results list
    Then I will see the trip watcher layer in the <page> results
    And the trip watcher layer will be from the speedbump

  Examples:
    | destinationLocation   | startDateShift | endDateShift | resultstype | newStartDateShift | newEndDateShift | tripwatcherState | page           |
    | San Francisco, CA     | 3              |  5           | opaque      | 4                 | 5               | will             | opaque         |
    | Chicago, IL           | 3              |  5           | retail      | 2                 | 3               | will             | retail         |

  @US
  Scenario Outline: Test tripwatcher speedbump visibility on non-visible dates.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose <resultstype> hotels tab on results
    Then I <tripwatcherState> see the tripwatcher module in the results list

  Examples:
    | destinationLocation   | startDateShift | endDateShift | resultstype | tripwatcherState |
    | San Francisco, CA     | 1              |  5           | opaque      | will not         |
    | New York, NY          | 1              |  5           | retail      | will not         |

  @ROW
  Scenario Outline: Test tripwatcher speedbump visibility on Intl POS with valid dates.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose <resultstype> hotels tab on results
    Then I <tripwatcherState> see the tripwatcher module in the results list

  Examples:
    | destinationLocation   | startDateShift | endDateShift | resultstype | tripwatcherState |
    | San Francisco, CA     | 3              |  5           | opaque      | will not         |
    | New York, NY          | 3              |  5           | retail      | will not         |

  @US @JANKY
  Scenario Outline: Test tripwatcher layer in results.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <newStartDateShift> days from now and <newEndDateShift> days from now
    And I request search quotes
    Then I <tripwatcherState> see the trip watcher layer in the <page> results
    And the trip watcher layer will not be from the speedbump

  Examples:
    | destinationLocation   | startDateShift | endDateShift | resultstype | newStartDateShift | newEndDateShift | tripwatcherState | page           |
    | Chicago, IL           | 3              |  5           | opaque      | 4                 | 5               | will             | opaque         |
    | Chicago, IL           | 3              |  5           | retail      | 4                 | 5               | will             | opaque         |

  @US @JANKY
  Scenario Outline: Test tripwatcher layer in results.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <newStartDateShift> days from now and <newEndDateShift> days from now
    And I request search quotes
    Then I <tripwatcherState> see the trip watcher layer in the <page> results

  Examples:
    | destinationLocation   | startDateShift | endDateShift | resultstype | newStartDateShift | newEndDateShift | tripwatcherState | page           |
    | Chicago, IL           | 1              |  3           | opaque      | 2                 | 3               | will not         | opaque         |
    #| San Francisco, CA     | 1              |  3           | retail      | 2                 | 3               | will not         | opaque         |


  @ROW @JANKY
  Scenario Outline: Test tripwatcher layer in international results.
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <newStartDateShift> days from now and <newEndDateShift> days from now
    And I request search quotes
    Then I will not see the trip watcher layer in the <page> results

  Examples:
    | destinationLocation   | startDateShift | endDateShift | resultstype | newStartDateShift | newEndDateShift | page           |
    | Chicago, IL           | 3              |  5           | opaque      | 4                 | 5               | opaque         |
    | Chicago, IL           | 3              |  5           | retail      | 4                 | 5               | opaque         |
