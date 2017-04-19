@US
Feature: external Car links with different parameters
  Owner: Boris Shukaylo

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: RTC-340 Validate external link initiates Car search
    When I navigate to external link /car/search-options.jsp?startSearchType=N&startLocation=SFO&selectedCarTypes=ECAR&inputId=index
    Then Car farefinder contain "SFO" in location

  @ACCEPTANCE
  Scenario: RTC-341 External Car link with all search options enabled
    Given I'm searching for a car in "LAX"
    Given I want to travel with exact pickup 09/07/15 and dropoff 09/12/15 dates
    Given I want to pick up at 3:00pm and drop off at 3:00pm
    When I navigate to external link /car/search-options.jsp?startLocation=LAX&selectStartAirportCode=LAX&startDay=7&startMonth=9&endDay=12&endMonth=9&startTime=1500&endTime=1500&isUnderageDriver=false&startSearchType=N&inputId=index&selectedCarTypes=ECAR&isDebitCardSelected=false&sid=S123&bid=B456
    Then I see the same values in FareFinder as I filled
    
  @ACCEPTANCE
  Scenario: RTC-3960 External link car search - date and card type prompt
    Given I'm searching for a car in "JFK"
    When I navigate to external link /car/search-options.jsp?startLocation=JFK&selectStartAirportCode=JFK&isUnderageDriver=false&startSearchType=N&inputId=index&selectedCarTypes=ECAR&isDebitCardSelected=false&sid=S123&bid=B456
    Then Car farefinder contain "JFK" in location

  @ACCEPTANCE
  Scenario: RTC-342 External Car link without search options
    Given I'm searching for a car in "ATL"
    When I navigate to external link /car/index.jsp?pickupLocation=ATL
    And Car farefinder contain "ATL" in location




