@US
Feature: User redirection from partners using retail as gateway links

    Background:
        Given default dataset
        Given the application is accessible

    
  @US @ROW @MOBILE @ACCEPTANCE
  Scenario Outline: Users navigating to Hotwire from online partner links should land on results page with the retail result
    Given I visit Hotwire from an online partner with an <hotelType> hotel
    Then I see hotel results
    And I see the featured hotel in the results
    When I choose the retail hero hotel
    And I go back to results from the details page
    Then I see the featured hotel in the results

  Examples: hotel types
    | hotelType |
    | Ean       |
    | Expedia   |
    | Hotwire   |
