@MobileApi
Feature: Hotel Mobile API search


  Scenario Outline: opaque hotel search
    Given destination location is <originalLocation>
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And search criteria is present
    And hotel search metadata is present
    And number of neighborhoods is greater than 1
    And number of search results is greater than 1
    And opaque solution details are present
    And opaque results hotel amenities are present
    And results neighborhoods contain city id
    And opaque cost summary information is present
    And tag line for neighborhood is present
    And number of star rating hotels is greater than 1

  @SMOKE
  Examples:
  | originalLocation |
  | SFO              |

  Examples:
  | originalLocation |
  | LHR              |
  | London, UK       |
  | CDG              |
  | Berlin, Germany  |


  Scenario Outline: retail hotel search
    Given destination location is <originalLocation>
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute retail hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And search criteria is present
    And hotel search metadata is not present
    And number of search results is greater than 1
    And retail solution details are present
    And retail results hotel amenities are present
    And retail cost summary information is present

  @ACCEPTANCE
  Examples:
  | originalLocation |
  | SFO              |

  Examples:
  | originalLocation |
  | London, UK       |
  | CDG              |
  | Berlin, Germany  |

  @CLUSTER3
  Scenario: opaque hotel search Production testing on Cluster 3
    Given I use the following opaque hotel search url https://api.hotwire.com/mobi/v1/search/hotel/opaque?apikey=bcuawwjmp7hbf22bjx6an95n
    And I use client id 12345
    And I use customer 2290530351
    And destination location is SFO
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And search criteria is present
    And hotel search metadata is present
    And number of neighborhoods is greater than 1
    And number of search results is greater than 1
    And opaque solution details are present


  @CLUSTER3
  Scenario: retail hotel search Production testing on Cluster 3
    Given I use the following retail hotel search url https://api.hotwire.com/mobi/v1/search/hotel/retail?apikey=bcuawwjmp7hbf22bjx6an95n
    And I use client id 12345
    And I use customer 2290530351
    And destination location is sfo
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute retail hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And search criteria is present
    And hotel search metadata is not present
    And number of search results is greater than 1
    And retail solution details are present

  @LIMITED
  Scenario Outline: search opaque hotels, check MOLO solutions
    Given destination location is MIA
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    And I set the channel ID to <channelID>
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And search criteria is present
    And hotel search metadata is present
    And number of neighborhoods is greater than 1
    And number of search results is greater than 1
    And proper fields are revealed for MOLO solutions
    And there is Trip Advisor rating for MOLO hotels
    And opaque solution details are present


  Examples:
    | channelID        |
    | HW_IOS@2.2       |
    | HW_ANDROID       |


  @ACCEPTANCE
  Scenario Outline: search opaque hotels, check that for Angular there are no MOLO solutions
    Given destination location is MIA
    And date and time range is between 20 days from now and 25 days from now
    And number of rooms is 1
    And number of adults is 2
    And I set the channel ID to <channelID>
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And search criteria is present
    And hotel search metadata is present
    And number of neighborhoods is greater than 1
    And number of search results is greater than 1
    And MOLO solutions are absent

  Examples:
    | channelID        |
    | HFE@1.14.0       |
    | HW_22IOS@2.2     |
    |                  |


  @STBL
  Scenario: API opaque hotel search with XML injection exploit
    When I execute xml exploit request body from hotel_search_xml_injection.xml file and receive UnmarshallingFailureException

  @STBL
  Scenario: API opaque hotel search with XML expansion exploit
    When I execute xml exploit request body from hotel_search_xml_expansion.xml file and receive UnmarshallingFailureException

