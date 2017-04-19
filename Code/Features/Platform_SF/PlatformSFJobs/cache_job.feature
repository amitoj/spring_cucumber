@US @JOB @ACCEPTANCE @JANKY
Feature: Validate the visibility of loading layer for specific dest

  Scenario Outline: run job, run search, validate db
    Given I set the following job params:
      | jspringjobtcp                    |
      | hotel/cache/HotelPGoodCachingJob |
    And job logs will be stored in <dir>
    And I want to refer to it as HPCJ
    And I run the job on 7104 port for 180 seconds
    And I'm a guest user
    And the application is accessible
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I launch a search
    And I wait for loading layer

    Examples: 
      | destinationLocation | startDateShift | endDateShift | dir                                               |
      | Seattle, WA         | 1              | 2            | hotwire.biz.jobs.hotel.cache.HotelPGoodCachingJob |
