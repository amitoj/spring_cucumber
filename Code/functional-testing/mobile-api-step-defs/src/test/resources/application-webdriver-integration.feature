@MobileApi @US
Feature: Test cucumber glue, spring contexts, application steps

  Scenario: opaque hotel search
    Given destination location is SFO
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present