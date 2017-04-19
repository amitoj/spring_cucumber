@API @DistributedOpaque
Feature: Public API Hotel Search.

  Background:
    Given I am Public API user

  @LIMITED
  Scenario Outline: : RTC-3986, RTC-3988, RTC-3989, RTC-3987, RTC-3990. Hotel Search in different locations.
    Given I send request to search hotels in <location>
    Then I get search results

  Examples:
    | location                               |
    | SFO                                    |
    | San Francisco, Ca                      |
    | 94121                                  |
    | 158 18th Ave, San Francisco, Ca. 94121 |
    | Liberty Statue                         |
    | 36.121671, -115.096099                 |

  @ACCEPTANCE
  Scenario: RTC-3992. Hotel Search by Neighborhood Id
    Given I send request to search hotels in N87618
    Then I get search results
