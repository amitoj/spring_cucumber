@US @JOB
Feature: Reservation Reminder Jobs

  Scenario Outline: CarReservationReminderJob
    Given I set the following job params:
      | jspringjobDebug                |
      | jobs/CarReservationReminderJob |
      | s-hwqa_testuser1@expedia.com   |
    And job logs will be stored in <dir>
    And I want to refer to it as CRRJ
    When I run the job on 1003 port for 150 seconds
    And I get 2 emails from <sender> with subject <subject> since last 2 minutes with following in the body:
      | car's reserved |

    Examples: data
      | dir                                             | sender               | subject              |
      | hotwire.biz.jobs.jobs.CarReservationReminderJob | YourTrip@hotwire.com | Your Hotwire Trip to |
