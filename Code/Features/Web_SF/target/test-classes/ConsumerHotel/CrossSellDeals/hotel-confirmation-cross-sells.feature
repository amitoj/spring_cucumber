Feature: Hotel confirmation page cross sells

    Background:
        Given default dataset
        Given activate car's version tests
        Given the application is accessible

    @US @ACCEPTANCE
    Scenario Outline: Hotel purchase and navigation to car cross sell from confirmation page.
        Given I'm a guest user
        And I'm searching for a hotel in "<destinationLocation>"
        And I want to travel between <startDateShift> days from now and <endDateShift> days from now
        And I request quotes
        And I choose <resultstype> hotels tab on results
        When I choose a hotel and purchase as guest a quote
        Then I receive immediate confirmation
        And I will see the hotel confirmation car cross sell
        When I click the hotel confirmation page car cross sell
        Then I should see car search results page

    Examples: opaque quotable fares parameters
        | state  | destinationLocation | startDateShift | endDateShift | resultstype |
        | is     | Los Angeles, CA     | 30             | 35           | opaque      |
        | is     | SJC                 | 30             | 35           | retail      |

    @ROW @ACCEPTANCE @JANKY
    Scenario Outline: Hotel purchase and navigation to car cross sell from confirmation page.
        Given I'm a guest user
        And I'm searching for a hotel in "<destinationLocation>"
        And I want to travel between <startDateShift> days from now and <endDateShift> days from now
        And I request quotes
        And I choose <resultstype> hotels tab on results
        When I choose a hotel and purchase as guest a quote
        Then I receive immediate confirmation
        And I will not see the hotel confirmation car cross sell

    Examples: opaque quotable fares parameters
        | state  | destinationLocation | startDateShift | endDateShift | resultstype |
        | is     | Los Angeles, CA     | 30             | 35           | opaque      |
        | is     | SJC                 | 30             | 35           | retail      |
