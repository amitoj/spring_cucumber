@MobileApi @JANKY
Feature: Hotel Mobile API last bookings

  Scenario: check number of bookings
    Given I use the following url /mobi/v1/search/hotel/opaque
    And I set the device type to iphone
    And I use client id 12345
    And I use customer 2402489501
    And I use LatLong 37.658155,-122.396959
    And destination location is Miami
    And date and time range is between 22 days from now and 23 days from now
    And number of rooms is 1
    And number of adults is 2
    And number of children is 0
    When I execute the request
    Then search results are present
    And I am logged in as payable user
    And I use the following url /mobi/v1/hotel/booking
    And I use the result id for booking 4 star hotel from neighborhood 12350
    And I use new Visa card
    When I execute the booking request
    Then booking result is present
    Given I set the following job params:
      | jrunjobtcp      |
      | task.ExecuteJob |
    And job logs will be stored in hotwire.biz.jobs.task.ExecuteJob
    And I want to refer to it as EXJ
    And I run the job on port 4051 and wait for success Finished 'ExecuteJob' successfully
    Given I use the following url /mobi/v1/search/hotel/opaque
    And I set the device type to iphone
    And I use client id 12345
    And I use customer 2402489501
    And I use LatLong 37.658155,-122.396959
    And destination location is Miami
    And date and time range is between 22 days from now and 23 days from now
    And number of rooms is 1
    And number of adults is 2
    And number of children is 0
    When I execute the request
    Then search results are present
    And I use the following url /mobi/v1/details/hotel/opaque
    And I set the device type to iphone
    And I use the result id for details 4 star hotel from neighborhood 12350
    When I execute the details request
    Then details result is present
    And number of booking in last 24 hours is present
