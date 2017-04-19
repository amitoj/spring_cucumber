Feature: Converged Hotel Landing TripAdvisor module.
  Let customer search for hotel rooms from the hotel landing page.

  Background:
    Given default dataset
    Given the application is accessible

  @JANKY
  Scenario Outline: Trip Advisor badge is displayed for intl POS only.
    Given  I want to go to the Hotels landing page
    Then I <state> see the trip advisor badge on the hotel landing page

  @ROW @JANKY
  Examples:
    | state   |
    | will    |

  @US
  Examples:
    | state    |
    | will not |