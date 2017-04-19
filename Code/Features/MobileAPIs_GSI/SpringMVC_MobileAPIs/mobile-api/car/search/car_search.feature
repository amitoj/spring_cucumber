@MobileApi
Feature: Car search Mobile API

  @LIMITED
  Scenario: roundtrip airport car search
    Given pickup location is SFO
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And car opaque results are present
    And car retail results are present
    And car search metadata is present
    Then number of car types is greater than 1
    Then number of rental agencies is greater than 1
    And centroid is present in search response
    And agency longitude and latitude is present in search response
    And car info contains car type image URLs

  @CLUSTER3
  Scenario: roundtrip car search Production Cluster 3 BDD
    Given I use the following car search url https://api.hotwire.com/mobi/v1/search/car/all?apikey=bcuawwjmp7hbf22bjx6an95n
    And I use client id 12345
    And I use customer 2290530351
    And I use LatLong 37.658155,-122.396959
    And pickup location is SFO
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then I see search results are present
    And car search has been executed
    And search criteria is present
    And car opaque results are present
    And car retail results are present
    And car search metadata is present
    Then number of car types is greater than 1
    Then number of rental agencies is greater than 1
    And agency longitude and latitude is present in search response
    And car info contains car type image URLs


  @LIMITED
  Scenario: roundtrip local car search
    Given pickup location is San Francisco, CA
    And I use LatLong 37.658155,-122.396959
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then I see search results are present
    And car search has been executed
    Then at least one local destination search exists
    And search criteria is present
    And car opaque results are present
    And car retail results are present
    And car search metadata is present
    Then number of car types is greater than 1
    Then number of rental agencies is greater than 1
    And agency longitude and latitude is present in search response
    And centroid is present in search response
    And car info contains car type image URLs


  Scenario Outline: one way car search for US Locations
    And pickup location is <originalLocationStart>
    And dropoff location is <originalLocationEnd>
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then I see search results are present
    And car search has been executed
    Then number of car types is greater than 1
    Then number of rental agencies is greater than 1
    Then number of search results is greater than 1
    And car retail results are present
    And car search metadata is present
    And centroid is present in search response
    And agency longitude and latitude is present in search response
    And car info contains car type image URLs

  @LIMITED
  Examples:
  | originalLocationStart          |   originalLocationEnd          |
  | SFO                            |   LAS                          |

  @ACCEPTANCE
  Examples:
  | originalLocationStart          |   originalLocationEnd          |
  | San Francisco, CA              |   Las Vegas, NV                |
  | 94111                          |   Las Vegas, NV                |

  Examples:
  | originalLocationStart          |   originalLocationEnd          |
  | San Francisco, CA              |   LAS                          |
  | SFO                            |   Las Vegas, NV                |


  @ACCEPTANCE
  Scenario Outline: one way car search for non-US Locations
    And pickup location is <originalLocationStart>
    And dropoff location is <originalLocationEnd>
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then I see search results are present
    And car search has been executed
    Then number of car types is greater than 1
    Then number of rental agencies is greater than 1
    Then number of search results is greater than 1
    And car retail results are present
    And car search metadata is present
    And centroid is present in search response
    And agency longitude and latitude is present in search response
    And car info contains car type image URLs

  Examples:
  | originalLocationStart          |   originalLocationEnd          |
  | MXP                            |   FCO                          |
  | Milan, Italy                   |   Rome, Italy                  |
  | Milan, Italy                   |   FCO                          |
  | FCO                            |   Milan, Italy                 |
  | LPL                            |   LHR                          |
  | Liverpool, United Kingdom      |   London, United Kingdom       |
  | Liverpool, United Kingdom      |   LHR                          |
  | LHR                            |   Liverpool, United Kingdom    |



  Scenario Outline: local car search, check distanceFromAddress for airport solutions
    Given pickup location is <originalLocation>
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then for airport car I do have distance from my address information
    And agency longitude and latitude is present in search response

  Examples: Different types of local Search
    | originalLocation      |
    | San Francisco, CA     |
    | 37.658155,-122.396959 |
    | statue of liberty     |
    | 94111                 |


  Scenario Outline: airport car search, check distanceFromAddress for airport solutions is absent
    Given pickup location is <originalLocation>
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then for airport car I do not have distance from my address information

  Examples: Different types of airport Search
    | originalLocation |
    | SFO              |
    | JFK              |

  @ACCEPTANCE
  Scenario: disambiguation on car search
    Given pickup location is Golden Gate Park
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then I get list of locations in disambiguation response

  @ACCEPTANCE
  Scenario: opaque car search response contains strike-through price
    And pickup location is SFO
    And date and time range is between 14 days from now and 20 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then number of search results is greater than 1
    And opaque car solutions have strike through price info

  @ACCEPTANCE
  Scenario Outline: verify agency lat/long for roundtrip, non-US car locations
    And pickup location is <originalLocation>
    And date and time range is between 14 days from now and 20 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    Then number of search results is greater than 1
    And centroid is present in search response
    And agency longitude and latitude is present in search response
    And car info contains car type image URLs

  Examples:
    | originalLocation |
    | LHR              |
    | London, UK       |
    | MXP              |
    | Milan, Italy     |