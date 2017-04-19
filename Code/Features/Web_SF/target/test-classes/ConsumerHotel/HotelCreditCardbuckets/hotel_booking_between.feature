@US
Feature: Hotel Search And Purchase (Happy Path)
  Let customer search for and purchase hotel rooms.

  Background:
    Given default dataset
    Given the application is accessible

  @US @ACCEPTANCE
  Scenario Outline: Purchase a hotel room between price.
    Given I am logged in
    And I am authenticated
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose hotel result with price less <less_price> and more <more_price>
    And I book that hotel
    And I complete the booking with saved user account
    Then I receive immediate confirmation

  Examples: opaque quotable fares parameters
    | RTC  | destinationLocation | startDateShift | endDateShift | less_price | more_price | total  |
    | 1354 | Greenville, SC      | 1              | 2            | 0          | 249        | 500    |
    #| 1348 | Greenville, SC      | 1              | 11           | 51         | 99         | 1000   |
    #| 1349 | Greenville, SC      | 1              | 21           | 51         | 99         | 2000   |
    | 1350 | Miami, FL           | 1              | 25           | 101        | 180        | 5000   |
  #  | 1351 | New York City, NY   | 1              | 31           | 701        | 5000       | 20000+ |
