@US
Feature: Car Domestic. Check disambiguation page results for different search phrases.
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE @STBL
  Scenario Outline: RTC-595 Resolving car ambiguous issue using Disambiguation layer for city
    When I am on car index page
    And I'm searching for a car in "<disambiguationDestinationLocation>"
    And  I want to travel in the near future
    And  I request quotes
    Given I receive error message for the incorrect location
    Then I see car disambiguation layer
    And  I click out of car disambiguation layer
    Then I don't see car disambiguation layer
    And  I click on the car destination field
    Then I see first suggested location is "<expectedLocation>" underlined and highlighted and I click it
    When I start car search without specifying search parameters
    Then I see a non-empty list of search results
    Then car search results contain expected location <expectedResultLocation>

  Examples:
    | disambiguationDestinationLocation  | expectedLocation                      |    expectedResultLocation |
    | Springfield                        | Springfield, IL - (SPI) (Springfield) |    Springfield, IL - (SPI)|


  @ACCEPTANCE @SingleThreaded @STBL
  Scenario Outline: RTC-614 Resolving of issues from auto-complete. Perform search using items from auto-complete.
    When  I am on car index page
    Given I type "<disambiguationDestinationLocation>" car location
    Then  I see first suggested location is "<expectedLocation1>" underlined and highlighted
    And   I press "ARROW_DOWN" key for 2 times on autocomplete layer
    Then  I see third suggested location is "<expectedLocation3>" underlined and highlighted and I click it
    Given I set car dates between 10 days from now and 15 days from now
    When  I start car search without specifying search parameters
    Then  I don't see car disambiguation layer
    Then  I see a non-empty list of search results
    Then  car search results contain expected location <expectedResultLocation>

  Examples:
    | disambiguationDestinationLocation  | expectedLocation1                                   | expectedLocation3                         | expectedResultLocation   |
    | SAN                                |  San Francisco, CA - (SFO) (San Francisco airport)  | San Diego, CA - (SAN) (San Diego airport) | San Diego, CA - (SAN)    |

  @US @STBL
  Scenario Outline: RTC-582 Verifying the error message on car landing page in case location is not filled in completely
    When  I am on car landing page
    Given I type "<disambiguationDestinationLocation>" car location
    Given I set car dates between 10 days from now and 15 days from now
    When  I start car search without specifying search parameters
    Given I receive error message for the incorrect location
    Then  I see car disambiguation layer
    Then  I see first suggested location is "<expectedLocation1>"
    Then  I verify Destination cities pop-up window


  Examples:
    | disambiguationDestinationLocation  | expectedLocation1 |
    | PORTLAND                           | Portland, OR      |

  @ACCEPTANCE @STBL
  Scenario Outline: RTC-583 Cars Incorrect Location
    When  I am on car landing page
    Given I type "<disambiguationDestinationLocation>" car location
    Given I set car dates between 1 days from now and 3 days from now
    When  I start car search without specifying search parameters
    Given I receive error message for the incorrect location
    Then  I see car disambiguation layer
    Then  I see first suggested location is "<expectedLocation1>"


  Examples:
    | disambiguationDestinationLocation  | expectedLocation1 |
    | ZZZZZZ                             | Cee, Spain        |

  @ACCEPTANCE
  Scenario Outline: RTC-13  A country name was entered as a location
      And I am on car landing page
      And I'm searching for a car in "Argentina"
      And I want to travel in the near future
      And I request quotes
      Then I receive immediate "<error>" error message

      Examples:
      |error |
      |Please choose your location from the list. If your location is not listed, please check your spelling or make sure it is on our destination cities list.      |

  @STBL @ACCEPTANCE
  Scenario Outline: Checking IATA code plus Airport Search phrase logic RTC-1097
    When I'm searching for a car in "<search_phrase>"
    And I want to travel in the near future
    And I request quotes
    Then I see a non-empty list of search results
    And I see the same values in FareFinder as I filled

  Examples:
    | search_phrase |
    | SFO airport   |
    | airport BDL   |

  Scenario Outline: RTC-6841 Checking Text plus Airport Search phrase logic.

    When I'm searching for a car in "<search_phrase>"
    And I want to travel in the near future
    And I request quotes
    Then I see <search_options> on disambiguation page

  Examples:
    | search_phrase          | search_options                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
    #text is airport and no cities exist
    | Toledo Express airport | Cleveland Hopkins Intl. (CLE);Columbus Port Columbus Intl. (CMH);Detroit Metro (DTW);Toledo Express (TOL);                                                                                                                                                                                                                                                                                                                                                               |
    #text is city and no airports exist
    | Bates airport          | Albany Intl. Airport (ALB);Hartford Bradley Intl. (BDL);Boise (BOI);Boston Logan Intl. (BOS);Newark Intl. (EWR);Fort Smith (FSM);Spokane Intl. (GEG);New York John F Kennedy Intl. (JFK);La Guardia Airport (LGA);Chicago Midway (MDW);Oklahoma City Will Rogers World (OKC);Chicago O'Hare Intl. (ORD);Pendleton (PDT);Portland Intl. Airport (PDX);Springfield (SPI);Saint Louis Lambert Field (STL);Tulsa Intl. (TUL);Fayetteville Northwest Arkansas Regional (XNA); |
#   text is city and airport simultaneously
#   Primary airport + city primary airport < 5
    |Oslo airport            |Bismarck (BIS);Fargo (FAR);Grand Forks (GFK);Minneapolis/Saint Paul Intl. Airport (MSP);Oslo (OSL);Thief River Falls (TVF);                                                                                                                                                                                                                                                                                                                                               |
#   Primary airport + city primary airport > 5
    |Athens airport          |Athens (AHN);Albany Intl. Airport (ALB);Eleftherios Venizelos Intl. (ATH);Augusta (AUG);Bluefield (BLF);Burlington (BRL);Chattanooga Lovell Field (CHA);Columbus Port Columbus Intl. (CMH);Elmira/Corning (ELM);Huntsville Madison County (HSV);Los Angeles Intl. (LAX);Mendoza El Plumerillo (MDZ);Ogdensburg (OGS);Rutland (RUT);South Bend (SBN);Shreveport Regional (SHV);Springfield (SPI);Texarkana (TXK);Tyler (TYR);W. K. Kellogg Regional Airport (BTL);         |

#  --SQl queries to generate new airport&disambuguation page results values
#  --B case: text is airport and no cities exist
#  select display_name, airport_name, city_id from airport
#  where
#  REPLACE(airport_name, ' AIRPORT') not in (select city_name from city)
#  and rownum <5000;
#  --results on disambuguation page
#  select display_name,airport_code from airport where airport_code in (
#  select secondary_airport_code from airport_group g
#  join city c on c.primary_airport_code = g.primary_airport_code
#  join airport a on a.city_id = c.city_id
#  where   REPLACE(a.airport_name, ' AIRPORT') = 'TOLEDO EXPRESS'
#  ---put the airport name to previous line
#  );
#
#  --C case:  text is city and no airport exists
#  select distinct city_name from city
#  where
#  city_name not in (select REPLACE(airport_name, ' AIRPORT') from airport)
#  and primary_airport_code is not null
#  and rownum <50000;
#  --results on disambuguation page
#  --select display_name from airport where airport_code in (
#  select display_name, airport_code from airport where airport_code in (
#  select secondary_airport_code from airport_group g
#  join city c on c.primary_airport_code = g.primary_airport_code
#  where   c.city_name = 'BATES'
#  ---put the airport name to previous line
#  );
#
#  --D case:  text is city and airport simultaniously and primary airports is less than 5
#  select c.city_name from airport a
#  join city c on c.city_name = REPLACE(a.airport_name, ' AIRPORT')
#  where rownum <5000;
#
#  --results on disambuguation page
#  select distinct display_name,airport_code from airport
#  where airport_code in (
#  select airport_code from airport where airport_code in (
#  select secondary_airport_code from airport_group g
#  join city c on c.primary_airport_code = g.primary_airport_code
#  join airport a on a.city_id = c.city_id
#  where   REPLACE(a.airport_name, ' AIRPORT') = 'OSLO'
#  ---put the airport name to previous line
#  )
#  UNION
#  select secondary_airport_code from airport_group g
#  where primary_airport_code in
#  (select primary_airport_code from city where city_name = 'OSLO')
#  ---put the airport name to previous line
#  );
#  --D+ case:   text is city and airport simultaniously and primary airports is more than 5
#  select distinct display_name, airport_code from airport where airport_code in   (
#  select primary_airport_code from city where city_name='ATHENS'
#  ---put the airport name to previous line
#  UNION
#  select airport_code from airport where airport_name like 'ATHENS%'
#  ---put the airport name to previous line
#  );
