@MobileApi
Feature: Car Details Mobile API


  Scenario Outline: car details local roundtrip
    Given I use client id 1064108143041242077
    And pickup location is <location>
    And date and time range is between 16 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first <solutionType> solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I verify car details result ID
    And car type images are present in details response
    And car summary of charges is present in details
    Then car info metadata is present in details
    And search criteria info is present
    And car summary of charges is present in details
    And insurance info is present in details
    And terms of use are present in details
    And agency longitude and latitude is present in details response

  @LIMITED
  Examples:
    | solutionType | location              |
    | retail       | SFO                   |
    | opaque       | SFO                   |

  @ACCEPTANCE
  Examples:
    | solutionType | location              |
    | opaque       | San Francisco, CA     |
    | opaque       | 37.658155,-122.396959 |
    | opaque       | 94111                 |
    | retail       | San Francisco, CA     |
    | retail       | 37.658155,-122.396959 |
    | retail       | 94111                 |


  Scenario Outline: car details one-way trip in US, INTL
    Given pickup location is <pickupLocation>
    And dropoff location is <dropOffLocation>
    And date and time range is between 16 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first <solutionType> solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I verify car details result ID
    And car type images are present in details response
    And car summary of charges is present in details
    Then car info metadata is present in details
    And search criteria info is present
    And car summary of charges is present in details
    And insurance info is present in details
    And terms of use are present in details
    And agency longitude and latitude is present in details response


  Examples:
    | solutionType | pickupLocation                 |  dropOffLocation               |
    | retail       | SFO                            |  LAS                           |
    | retail       | JFK                            |  Las Vegas, NV                 |
    | retail       | San Francisco, CA              |  Las Vegas, NV                 |
    | retail       | San Francisco, CA              |  JFK                           |
    | retail       | MXP                            |   FCO                          |
    | retail       | Milan, Italy                   |   Rome, Italy                  |
    | retail       | Milan, Italy                   |   FCO                          |
    | retail       | FCO                            |   Milan, Italy                 |


  Scenario Outline: car details one-way response in UK
    Given pickup location is <pickupLocation>
    And dropoff location is <dropOffLocation>
    And date and time range is between 16 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the second <solutionType> solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I verify car details result ID
    And car type images are present in details response
    And car summary of charges is present in details
    Then car info metadata is present in details
    And search criteria info is present
    And car summary of charges is present in details
    And insurance info is present in details
    And terms of use are present in details
    And agency longitude and latitude is present in details response

  Examples:
    | solutionType | pickupLocation                 |  dropOffLocation               |
    | retail       | LPL                            |   LHR                          |
    | retail       | Liverpool, United Kingdom      |   London, United Kingdom       |
    | retail       | Liverpool, United Kingdom      |   LHR                          |
    | retail       | LHR                            |   Liverpool, United Kingdom    |


  @LIMITED
  Scenario: car details response contains debit unfriendly flag and accepted card type
    And pickup location is SFO
    And date and time range is between 16 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And debit unfriendly flag is present in search results
    And accepted card type field is present in search results
    And prepaid retail field is present in search results
    And I use result id of the first opaque solution
    When I execute car details request
    Then car details request has been executed
    And car details response is present
    And debit unfriendly flag is present in details
    And accepted card type field is present in details
    And prepaid retail field is present in details
    And agency longitude and latitude is present in details response

  @ACCEPTANCE
  Scenario Outline: car details roundtrip response contains agency lat-long for non-US location
    And pickup location is <location>
    And date and time range is between 16 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And I use result id of the first <solutionType> solution
    When I execute car details request
    Then car details request has been executed
    And car details response is present
    And agency longitude and latitude is present in details response


  Examples:
    | solutionType | location                  |
    | retail       | Rome, Italy               |
    | retail       | MXP                       |
    | retail       | London, United Kingdom    |
    | retail       | LHR                       |