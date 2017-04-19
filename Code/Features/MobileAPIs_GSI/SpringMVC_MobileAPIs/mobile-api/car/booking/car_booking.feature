@MobileApi
Feature: Car Mobile API booking

  Scenario Outline: book opaque roundtrip car as a logged in customer
    Given pickup location is <location>
    And date and time range is between 16 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first opaque solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I am logged in as payable user
    And I use <paymentCardType> card
    When I execute the secure booking request
    Then booking result is present
    And billing info is present
    And reservation info is present
    And car summary of charges is present
    And travel advisory is present
    And car reservation details are present
    And insurance info is absent
    And agency longitude and latitude is present in booking response
    And car type images are present in booking response
    And accepted card type field is present in booking response

    @ACCEPTANCE
    Examples:
    | paymentCardType |  location                 |
    | new Visa        |  SFO                      |
#    | saved Visa      |  San Francisco, CA        |

  Examples:
    | paymentCardType |  location                 |
    | new Visa        |  CDG                      |
    | new Visa        |  LHR                      |
    | new Visa        |  Milan, Italy             |
    | new Visa        |  London, United Kingdom   |
    | new Visa        |  37.658155,-122.396959    |
    | new Visa        |  statue of liberty        |
    | new Visa        |  94111                    |



  Scenario Outline: book retail roundtrip car as a logged in customer
    Given pickup location is <location>
    And date and time range is between 10 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first retail solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I am logged in as payable user
    And I make reservation with no payment
    When I execute the secure booking request
    Then booking result is present
    And reservation info is present
    And car summary of charges is present
    And travel advisory is present
    And car reservation details are present
    And insurance info is absent
    And agency longitude and latitude is present in booking response
    And car type images are present in booking response
    And accepted card type field is present in booking response

  @ACCEPTANCE
  Examples:
    |  location                     |
    |  JFK                          |
    |  Las Vegas, NV                |
    |  LHR                          |
    |  London, United Kingdom       |
    |  MXP                          |
    |  Milan, Italy                 |


  Scenario Outline: book opaque car as a guest user without insurance
    Given pickup location is SFO
    And date and time range is between 16 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first opaque solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I use <cardType> card
    When I execute the booking request
    Then booking result is present
    And billing info is present
    And reservation info is present
    And car summary of charges is present
    And travel advisory is present
    And car reservation details are present
    And insurance info is absent
    And agency longitude and latitude is present in booking response
    And car type images are present in booking response
    And accepted card type field is present in booking response

  @LIMITED
  Examples:
    | cardType             |
    | new Visa             |


  Examples:
    | cardType             |
    | new MasterCard       |
    | new American Express |
    | new Discover         |


  @ACCEPTANCE
  Scenario: try to book car with invalid cc
    Given pickup location is JFK
    And date and time range is between 10 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first opaque solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I am logged in as payable user
    And I use invalid Visa card
    Then I get credit card invalid message for car booking request


  Scenario Outline: book retail car as a guest user without insurance, without payment
    Given pickup location is <originLocation>
    And date and time range is between 10 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first retail solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I make reservation with no payment
    When I execute the booking request
    Then booking result is present
    And reservation info is present
    And car summary of charges is present
    And travel advisory is present
    And car reservation details are present
    And insurance info is absent
    And agency longitude and latitude is present in booking response
    And car type images are present in booking response
    And accepted card type field is present in booking response

  @ACCEPTANCE
  Examples:
    | originLocation     |
    | LAS                |
    | TXL                |

  Examples:
    | originLocation        |
    | San Francisco, CA     |
    | 37.658155,-122.396959 |
    | statue of liberty     |


  Scenario Outline: book one way retail car as a guest user in US, INTL locations
    Given pickup location is <pickupLocation>
    And dropoff location is <dropOffLocation>
    And date and time range is between 10 days from now and 18 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first retail solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I make reservation with no payment
    When I execute the booking request
    Then booking result is present
    And reservation info is present
    And car summary of charges is present
    And travel advisory is present
    And car reservation details are present
    And insurance info is absent
    And agency longitude and latitude is present in booking response
    And car type images are present in booking response
    And accepted card type field is present in booking response

  @ACCEPTANCE
  Examples:
    | pickupLocation                 |  dropOffLocation               |
    | San Francisco, CA              |  Las Vegas, NV                 |

  Examples:
    | pickupLocation                 |  dropOffLocation               |
    | SFO                            |  JFK                           |
    | San Francisco, CA              |  JFK                           |
    | JFK                            |  Las Vegas, NV                 |
    | MXP                            |  FCO                           |
    | Milan, Italy                   |  Rome, Italy                   |
    | Milan, Italy                   |  FCO                           |
    | FCO                            |  Milan, Italy                  |



  Scenario Outline: book one way retail car as a guest user in UK locations
    Given pickup location is <pickupLocation>
    And dropoff location is <dropOffLocation>
    And date and time range is between 30 days from now and 38 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the <resultNumber> retail solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I make reservation with no payment
    When I execute the booking request
    Then booking result is present
    And reservation info is present
    And car summary of charges is present
    And travel advisory is present
    And car reservation details are present
    And insurance info is absent
    And agency longitude and latitude is present in booking response
    And car type images are present in booking response
    And accepted card type field is present in booking response

  Examples:
    | pickupLocation                 |  dropOffLocation               |  resultNumber  |
    | LPL                            |   LHR                          |   second       |
    | Liverpool, United Kingdom      |   London, United Kingdom       |   second       |
    | Liverpool, United Kingdom      |   LHR                          |   second       |
    | LHR                            |   Liverpool, United Kingdom    |   second       |

  @BUG
  Examples:
    | pickupLocation                 |  dropOffLocation               |  resultNumber  |
    | LPL                            |   LHR                          |   first        |
    | Liverpool, United Kingdom      |   London, United Kingdom       |   first        |


  Scenario Outline: book car as a guest user with insurance and credit card payment
    Given pickup location is SFO
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
    And I use new Visa card
    And I do select insurance
    When I execute the booking request
    Then booking result is present
    And billing info is present
    And reservation info is present
    And car summary of charges is present
    And travel advisory is present
    And car reservation details are present
    And insurance info is present
    And agency longitude and latitude is present in booking response
    And car type images are present in booking response
    And accepted card type field is present in booking response

  @LIMITED
  Examples:
    | solutionType |
    | opaque       |

  @ACCEPTANCE
  Examples:
    | solutionType |
    | retail       |


  Scenario Outline: book opaque car with non USD currency
    Given pickup location is LAX
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
    And I use new Visa card
    And my currency is EUR
    When I execute the booking request
    Then booking result is present
    And billing info is present
    And reservation info is present
    And car summary of charges is present
    And I see correct booking currency in car response

  Examples:
    | solutionType |
    | opaque       |


  Scenario: authenticated opaque car booking with Hot Dollars
    Given pickup location is LAX
    And date and time range is between 16 days from now and 17 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first opaque solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I am logged in as hot dollars user
    And I use Hot Dollars payment
    When I execute the booking request
    Then booking result is present
    And billing info is present
    And reservation info is present
    Then hot dollars info is in trip summary

  Scenario: try to book car using Hot dollars as user without them
    Given pickup location is MIA
    And date and time range is between 10 days from now and 20 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And there are no errors and warnings
    And search criteria is present
    And I use result id of the first opaque solution
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And I am logged in as random user with booked trips
    And I use Hot Dollars payment
    Then I get hot dollars invalid message for car booking request
