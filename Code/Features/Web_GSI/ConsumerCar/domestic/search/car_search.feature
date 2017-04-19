@US @platform
Feature: Cars search
  Owner: Vyacheslav Zyryanov
  Owner: Nataliya Golodiuk


  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario Outline: one way car search
    Given I'm searching for a car from "<pickUp>" to "<dropOff>"
    When I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results

  @LIMITED
  Examples:
    | pickUp                | dropOff               |
    | CITY                  | CITY                  |
    | Las Vegas, NV - (LAS) | Milwaukee, WI - (MKE) |

  @ACCEPTANCE
  Examples:
    | pickUp                         | dropOff                        |
    | Las Vegas, NV                  | San Francisco, CA - (SFO)      |
    | Las Vegas, NV - (LAS)          | Santa Barbara, CA              |
    | 3601 Lyon St San Francisco, CA | SFO                            |
    | San Francisco, CA - (SFO)      | 3601 Lyon St San Francisco, CA |

    @STBL @ACCEPTANCE
    Examples:
    |#    | pickUp                              | dropOff               |
    |4256 |94605                                |Santa Barbara, CA      |
    |4248 |Las Vegas, NV - (LAS)                |Santa Barbara, CA      |
    |4255 |Santa Barbara, CA                    |94605                  |
    |4247 |San Francisco, CA - (SFO)            |94612                  |
    |4246 | Las Vegas, NV                       | Chicago, IL           |
    |4249 |Las Vegas, NV                        |   3601 Lyon St San Francisco, CA|
    |4253 |Santa Barbara, CA                    | Dallas, TX            |
    |4245 |San Francisco, CA - (SFO)            | 3601 Lyon St San Francisco, CA  |


  @graphite-report-duration
  Scenario Outline: RTC-6840 roundtrip car search
    Given I'm searching for a car in "<location>"
    When I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results

  @SMOKE
  Examples:
    | location |
    | AIRPORT  |

  @SMOKE @SingleThreaded @BUG
  Examples:
    | location |
    | CITY     |

  @ACCEPTANCE
  Examples:
    | location |
    | POSTAL   |
    | ADDRESS  |

  @ACCEPTANCE
  Scenario Outline: RTC-805 search cars by US and Intl address
    Given I'm searching for a car in "<location>"
    And I want to travel in the near future
    When I request quotes
    Then I see a non-empty list of search results
    Then car search results contain expected location <location>

  Examples:
    | location                                         |
    | 600 Montgomery St, San Francisco, CA 94111, USA  |
    | Hounslow, Greater London TW6 1AP, United Kingdom |

  @STBL @ACCEPTANCE
  Scenario Outline: One-way search: only retail results
    Given I'm searching for a car from "<pick up>" to "<drop off>"
    When I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    Then I see only retail solutions on results page

    Examples:
    |#      |pick up    |drop off             |
    |4250   |Anaheim,CA |Chicago, IL - (ORD)  |
    |4252   |94111      |LGA                  |
  @STBL @ACCEPTANCE
  Scenario:RTC-4243:Same pick-up and drop-off location
    Given I'm searching for a car from "LAX" to "LAX"
    When I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    And Car farefinder contain "LAX" in location
    And Roundtrip search is selected in Farefinder on car results page

  @ACCEPTANCE
  Scenario: RTC-5218 check if website can display no results page & message.
    Given I'm searching for a car in "Lviv, Ukraine"
    When I want to travel between 1 days from now and 2 days from now
    And I request quotes
    Then I see a empty list of search results

  @STBL @ACCEPTANCE
  Scenario: RTC-835:one-way search by place of interest
    Given I'm searching for a car from "Alcatraz Island, CA" to "Statue of Liberty National Monument, NY"
    And I want to travel in the near future
    And I request quotes
    And I see a non-empty list of search results

  @ACCEPTANCE
  Scenario: RTC-20 search intl location with no car service
    Given I'm searching for a car in "Snilow, Ukraine - (LWO)"
    And I want to travel in the near future
    And I request quotes
    And I see a empty list of search results
