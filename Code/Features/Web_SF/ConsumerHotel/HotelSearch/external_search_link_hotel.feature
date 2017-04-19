@US
Feature: Hotel Search
 Hotel external links and autocomplete tests from silktest suite
Owner: Boris Shukaylo

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario: RTC-338 Validate external link initiates Hotel search.
    Given I'm searching for a hotel in "Whistler, Canada"
    When I navigate to external link /hotel/search-options.jsp?destCity=Whistler,%20Canada&startDay=1&startMonth=1&endMonth=1&endDay=10&inputId=hotel-index
    Then the results page contains "Whistler, Canada" in location

  @ACCEPTANCE
  Scenario: RTC-339 External Hotel link without Dates
    Given I'm searching for a hotel in "Whistler, Canada"
    When I navigate to external link /hotel/search-options.jsp?destCity=Whistler,%20Canada&inputId=hotel-index
    Then the results page contains "Whistler, Canada" in location
