@US @JOB
Feature: Hotel Reservation Reminder Jobs

   Scenario Outline: HotelReservationReminderJob
    Given I set the following job params:
      | jspringjobDebug                  |
      | jobs/HotelReservationReminderJob |
      | s-hwqa_testuser1@expedia.com     |
    And job logs will be stored in <dir>
    And I want to refer to it as HRRJ
    When I run the job on 1005 port for 150 seconds
    Then I get 2 emails from <sender> with subject <subject> since last 5 minutes with following in the body:
      | hotel's waiting for you |

    Examples: data
      | dir                                               | success                                             | sender               | subject              |
      | hotwire.biz.jobs.jobs.HotelReservationReminderJob | Finished 'HotelReservationReminderJob' successfully | YourTrip@hotwire.com | Your Hotwire Trip to |