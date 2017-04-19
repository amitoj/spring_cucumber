@MobileApi
Feature: Hotel Deal search Mobile API

  Scenario Outline: perform hotel opaque deal search and verify different deal statuses in response
    And I use LatLong 37.658155,-122.396959
    And date and time range is between 5 days from now and 7 days from now
    And dealhash is <hash>
    And number of rooms is 1
    And number of adults is 2
    And number of children is 0
    When I execute hotel deal search request
    Then I see search results are present
    And hotel search has been executed
    And deal <dealStatus> status is present

  @LIMITED
  Examples:
    | hash                                             | dealStatus |
    | MTAwOjE0NjU3OjkzOTMxOjQuMDoyOS4wMDAwMDI6WTpZOlk= | UP         |
    | MTAwOjE0NjU3OjkzOTMxOjQuMDoxNTAuMDAwMDAyOlk6WTpZ | DOWN       |


  @ACCEPTANCE
  Scenario: perform hotel opaque search and verify deal status is NON_APPLICABLE in response
    And I use LatLong 37.658155,-122.396959
    And date and time range is between 5 days from now and 7 days from now
    And destination location is JFK
    And number of rooms is 2
    And number of adults is 4
    And number of children is 0
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And deal NON_APPLICABLE status is present
