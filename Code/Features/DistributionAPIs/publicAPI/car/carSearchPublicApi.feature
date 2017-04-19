@API @DistributedOpaque
Feature: Public API Car Search.

  Background:
    Given I am Public API user

  @LIMITED
  Scenario Outline: : RTC-4002, RTC-4001, RTC-4004, RTC-4005, RTC-4006 . Car Search in different locations.
    Given I send request to search cars in <location>
    Then I get search results

  Examples:
    | location                               |
    | LAX                                    |
    | San Francisco, Ca                      |
    | 94111                                  |
    | 158 18th Ave, San Francisco, Ca. 94121 |
    | Liberty Statue                         |
    | 36.165269, -115.157851                 |




