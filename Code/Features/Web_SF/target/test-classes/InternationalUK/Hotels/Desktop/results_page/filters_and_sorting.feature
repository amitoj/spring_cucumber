@US
Feature: Results Page / Opaque/retail shared things / Filters and sorting
  Owner: Cristian Gonzalez Robles

  Background: 
    Given default dataset
    Given the application is accessible

  @STBL @ACCEPTANCE
  Scenario Outline: RTC-5070:Free internet
    Given I have load international <site> hotwire site
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between 5 days from now and 7 days from now
    And I am looking for a hotel
    Then I will see opaque results page
    And I want to filter results by Amenities
    And I choose the amenity: "Free Internet"
    Then I verify the "Free Internet" hotel amenity in results after filtering

    Examples: 
      | site | destinationLocation                 |
      | UK   | 655 Montgomery St San Francisco, CA |
