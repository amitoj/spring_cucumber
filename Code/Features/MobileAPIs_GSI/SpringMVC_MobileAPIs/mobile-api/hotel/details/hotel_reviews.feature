@MobileApi
Feature: Hotel Mobile API reviews

  Scenario: verify opaque hotel reviews response
    Given destination location is LAX
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    Then I execute hotel reviews request
    And hotel reviews response is present
