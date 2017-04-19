@MobileApi
Feature: Hotel Mobile API details

  Scenario Outline: verify opaque hotel details response
    Given destination location is <originalLocation>
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    And I set country code <countryCode>
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    And I set country code <countryCode>
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And hotel details meta info is present
    And opaque hotel summary of charges is present in details
    And opaque details number of hotel amenities is greater than 1
    And opaque details number of sample hotels is greater than 1
    And opaque details hotel additional info is present
    And opaque details accessibility copy is present
    And opaque details know before you go copy is present
    And opaque details cancellation policy is present
    And opaque details legal links are present
    And opaque details nearby airports section is present

  @SMOKE
  Examples:
  | countryCode | originalLocation |
  | US          | LAS              |

  Examples:
  | countryCode | originalLocation |
  | US          | LHR              |
  | US          | London, UK       |
  | US          | CDG              |
  | UK          | Berlin, Germany  |
  | UK          | SFO              |
  | UK          | LHR              |
  | AU          | Berlin, Germany  |


  Scenario Outline: verify retail hotel details response
    Given destination location is <originalLocation>
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    And I set country code <countryCode>
    When I execute retail hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    And I set country code <countryCode>
    When I execute retail hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And retail hotel summary of charges is present in details
    And retail details number of sample hotels is greater than 1
    And retail details number of sample hotels is greater than 1
    And number of trip advisor reviews is greater than 1
    And retail details hotel additional info is present
    And retail details accessibility copy is present
    And retail details know before you go copy is present
    And retail details cancellation policy is present
    And retail details legal links are present
    And hotel address is present
    And number of hotel images is greater than 1

  @ACCEPTANCE
  Examples:
  | countryCode | originalLocation |
  | US          | LAX              |



  Scenario Outline: verify INTL retail hotel details response
    Given destination location is <originalLocation>
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    And I set country code <countryCode>
    When I execute retail hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    And I set country code <countryCode>
    When I execute retail hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And retail hotel summary of charges is present in details
    And retail details number of sample hotels is greater than 1
    And retail details number of sample hotels is greater than 1
    And retail details hotel additional info is present
    And retail details accessibility copy is present
    And retail details know before you go copy is present
    And retail details cancellation policy is present
    And retail details legal links are present
    And hotel address is present
    And number of hotel images is greater than 1

  @ACCEPTANCE
  Examples:
  | countryCode | originalLocation |
  | US          | LHR              |

  Examples:
  | countryCode | originalLocation |
  | US          | London, UK       |
  | US          | CDG              |
  | UK          | Berlin, Germany  |
  | UK          | SFO              |
  | UK          | LHR              |
  | AU          | Berlin, Germany  |

  @LIMITED
  Scenario Outline: verify hotel name, address and image URLs for a MOLO solution
    Given destination location is MIA
    And date and time range is between 10 days from now and 16 days from now
    And number of rooms is 1
    And number of adults is 2
    And I set the channel ID to <channelID>
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And there is Trip Advisor rating for MOLO hotels
    And I use the MOLO result id for details
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I see proper fields are revealed for the solution

  Examples:
    | channelID        |
    | HW_IOS           |
    | HW_ANDROID       |

  @SMOKE
  Scenario: verify details of fully opaque hotel solution
    Given destination location is JFK
    And date and time range is between 10 days from now and 16 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use the fully opaque result id for details
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I see solution is not revealed

  @ACCEPTANCE
  Scenario: verify last hotel booked data for opaque hotel
    Given destination location is LAX
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And hotel details meta info is present
    And last hotel booked data is present

  Scenario: verify hotel rooms and associated deals data in retail hotel details response
    Given destination location is SFO
    And date and time range is between 10 days from now and 14 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute retail hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    When I execute retail hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And room types info is available
    And associated deals are present


  Scenario Outline: verify nearby hotels info and measurement unit
    Given destination location is LAX
    And date and time range is between 6 days from now and 8 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    And I set country code <countryCode>
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And opaque details distance measurement unit is <unit>

  Examples:
    | countryCode | unit |
    | US          | mi   |
    | UK          | km   |