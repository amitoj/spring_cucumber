@MobileApi
Feature: Hotel Mobile API booking

  Scenario Outline: book opaque hotel as a guest
    Given destination location is <originalLocation>
    And date and time range is between 12 days from now and 14 days from now
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
    And I use <paymentCardType> card
    When I execute the booking request
    Then booking result is present
    Then billing info is present
    And reservation info is present
    Then booking summary of charges is present
    Then travel advisory is present
    Then hotel reservation details are present
    Then booking number of hotel amenities is present
    Then booking number of hotel photos is present
    Then extend my stay is present
    Then room info is present

  @LIMITED
  Examples:
    | paymentCardType | originalLocation |
    | new Visa        | SFO              |


  @ACCEPTANCE
  Examples:
    | paymentCardType | originalLocation |
    | new Visa        | LHR              |
    | new Visa        | CDG              |
    | new MasterCard  | SFO              |

  Examples:
    | paymentCardType      | originalLocation |
    | new American Express |  LAX             |
    | new JCB              |  MIA             |
    | new Discover         |  JFK             |


  Scenario: book opaque hotel from UK as a guest
    Given destination location is SFO
    And date and time range is between 12 days from now and 14 days from now
    And number of rooms is 1
    And number of adults is 2
    And I set country code UK
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    And I set country code UK
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I use new Visa card
    And I set country code UK
    When I execute the booking request
    Then booking result is present
    Then billing info is present
    And reservation info is present
    Then booking summary of charges is present
    Then travel advisory is present
    Then hotel reservation details are present
    Then booking number of hotel amenities is present
    Then booking number of hotel photos is present
    Then extend my stay is present
    Then room info is present


  Scenario: book opaque hotel using valid hotel coupon code
    Given destination location is SFO
    And date and time range is between 10 days from now and 14 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the opaque solution with 4.0 star rating
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I use new Visa card
    And I use valid hotel coupon code
    And I am logged in as random user with booked trips
    When I execute the secure booking request
    Then booking result is present
    And reservation info is present
    Then booking summary of charges is present
    Then travel advisory is present
    Then hotel reservation details are present
    Then booking number of hotel amenities is present
    Then booking number of hotel photos is present
    Then extend my stay is present
    Then room info is present

  Scenario Outline: book opaque hotel as an authenticated user
    Given destination location is <originalLocation>
    And date and time range is between 10 days from now and 15 days from now
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
    And I am logged in as payable user
    And I use <paymentCardType> card
    When I execute the secure booking request
    Then booking result is present
    Then billing info is present
    And reservation info is present
    Then booking summary of charges is present
    Then travel advisory is present
    Then hotel reservation details are present
    Then booking number of hotel amenities is present
    Then booking number of hotel photos is present
    Then extend my stay is present
    Then room info is present

  @LIMITED
  Examples:
    | paymentCardType |  originalLocation |
    | new Visa        |  MIA              |

 @ACCEPTANCE
  Examples:
    | paymentCardType | originalLocation |
    | saved Visa      | SFO              |
    | saved Visa      | CDG              |
    | new Visa        | CDG              |



  Scenario Outline: book MOLO hotel as an authenticated user
    Given destination location is MIA
    And date and time range is between 12 days from now and 15 days from now
    And number of rooms is 2
    And number of adults is 4
    And I set the channel ID to <channelID>
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use the MOLO result id for details
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I see proper fields are revealed for the solution
    And I am logged in as payable user
    And I use new Visa card
    When I execute the secure booking request
    Then booking result is present
    Then billing info is present
    And reservation info is present
    Then booking summary of charges is present
    Then travel advisory is present
    Then hotel reservation details are present
    Then booking number of hotel amenities is present
    Then booking number of hotel photos is present
    Then extend my stay is present
    Then room info is present

  @LIMITED
  Examples:
    | channelID        |
    | HW_IOS           |

  @ACCEPTANCE
  Examples:
    | channelID        |
    | HW_ANDROID       |


  Scenario Outline: book MOLO hotel as guest user
    Given destination location is MIA
    And date and time range is between 12 days from now and 15 days from now
    And number of rooms is 2
    And number of adults is 4
    And I set the channel ID to <channelID>
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use the MOLO result id for details
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I see proper fields are revealed for the solution
    And I use new Visa card
    When I execute the booking request
    Then booking result is present
    Then billing info is present
    And reservation info is present
    Then booking summary of charges is present
    Then travel advisory is present
    Then hotel reservation details are present
    Then booking number of hotel amenities is present
    Then booking number of hotel photos is present
    Then extend my stay is present
    Then room info is present

  Examples:
    | channelID        |
    | HW_IOS           |
    | HW_ANDROID       |


  Scenario Outline: book retail hotel as a guest
    Given destination location is <originalLocation>
    And date and time range is between 6 days from now and 8 days from now
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
    And I use <paymentCardType> card
    When I execute the booking request
    Then booking result is present
    Then billing info is present
    And reservation info is present
    Then booking summary of charges is present
    Then travel advisory is present
    Then hotel reservation details are present
    Then booking number of hotel amenities is present
    Then extend my stay is present
    Then room info is present

  Examples:
    | paymentCardType | originalLocation |
    | new Visa        | SFO              |
    | new Visa        | LHR              |
    | saved Visa      | SFO              |
    | saved Visa      | LHR              |


  Scenario Outline: book retail hotel as an authenticated user
    Given destination location is <originalLocation>
    And date and time range is between 6 days from now and 8 days from now
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
    And I am logged in as payable user
    And I use <paymentCardType> card
    When I execute the booking request
    Then booking result is present
    Then billing info is present
    And reservation info is present
    Then booking summary of charges is present
    Then travel advisory is present
    Then hotel reservation details are present
    Then booking number of hotel amenities is present
    Then extend my stay is present
    Then room info is present

  Examples:
    | paymentCardType | originalLocation |
    | new Visa        | SFO              |
    | new Visa        | LHR              |
    | saved Visa      | SFO              |
    | saved Visa      | LHR              |

  @ACCEPTANCE
  Scenario: try to book hotel with invalid cc
    Given destination location is SFO
    And date and time range is between 12 days from now and 15 days from now
    And number of rooms is 2
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I am logged in as payable user
    And I use invalid Visa card
    Then I get credit card invalid message for hotel booking request

  Scenario Outline: book opaque hotel with non-USD currency
    Given destination location is <originalLocation>
    And date and time range is between 15 days from now and 20 days from now
    And number of rooms is 3
    And number of adults is 4
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And my currency is EUR
    And I use new Visa card
    When I execute the booking request
    Then booking result is present
    And billing info is present
    And booking summary of charges is present
    And travel advisory is present
    And hotel reservation details are present
    And I see correct booking currency in response

  Examples:
  | originalLocation |
  | SFO              |
  | LHR              |

  Scenario: try to book opaque hotel using invalid hotel coupon code
    Given destination location is SFO
    And date and time range is between 10 days from now and 14 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the opaque solution with 4.0 star rating
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I use new Visa card
    And I am logged in as random user with booked trips
    And I use invalid hotel coupon code
    Then I get coupon code invalid message for hotel booking request

  Scenario: authenticated opaque hotel booking with Hot Dollars
    Given destination location is SFO
    And date and time range is between 16 days from now and 17 days from now
    And number of rooms is 1
    And number of adults is 2
    When I execute opaque hotel search request
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I am logged in as hot dollars user
    And I use Hot Dollars payment
    When I execute the booking request
    Then booking result is present
    Then billing info is present
    Then booking summary of charges is present
    Then travel advisory is present
    Then extend my stay is present
    Then room info is present
    Then hot dollars info is in trip summary


  Scenario: try to book hotel using Hot dollars as user without them
    Given destination location is SFO
    And date and time range is between 12 days from now and 15 days from now
    And number of rooms is 2
    And number of adults is 2
    When I execute opaque hotel search request
    Then I see search results are present
    And hotel search has been executed
    And there are no errors and warnings
    And I use result id of the first solution
    When I execute opaque hotel details request
    Then hotel details request has been executed
    And hotel details response is present
    And I am logged in as random user with booked trips
    And I use Hot Dollars payment
    Then I get hot dollars invalid message for hotel booking request
