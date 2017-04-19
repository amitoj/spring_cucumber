Feature: opaque details page display information about hotel

  Background:
    Given the application is accessible

  @RTC-5502
  Scenario: Map with hotel neighborhood and points of interest
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Paris, France"
    When I request quotes
    And I choose opaque hotels tab on results
    And I choose a hotel result
    Then the hotel neighborhood is displayed
    When I put mouse over POI icon
    Then I see POI description

  @RTC-5502
  Scenario: No room photos block is shown when no room photos available
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Paris, France"
    When I request quotes
    And I choose opaque hotels tab on results
    And I choose a hotel result
    Then no room photos available

  @RTC-5502
  Scenario Outline: Return back to search results
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Paris, France"
    And I would place adults and children as following:
      | adultsCount | childrenCount | childrenAges |
      | 2           | 1             | 7            |
    When I request quotes
    And I choose opaque hotels tab on results
    And I choose a hotel result
    And I return back to search results using link at the <position> of the page
    Then I see search results match search criteria

  Examples:
    | position |
    | top      |
    | bottom   |

  @RTC-5502
  Scenario: User received and opened link leading to details page
  should not be able to return back to results
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Paris, France"
    And I would place adults and children as following:
      | adultsCount | childrenCount | childrenAges |
      | 2           | 1             | 7            |
    And I request quotes
    And I choose opaque hotels tab on results
    And I choose a hotel result
    When I send a link to another user
    Then the user does not see back to result links

  @RTC-5502
  Scenario: Low price guarantee offer
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Paris, France"
    And I request quotes
    And I choose opaque hotels tab on results
    When I choose a hotel result
    Then I see Low Price Guarantee offer

  @RTC-5502
  Scenario: Know before you go
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Paris, France"
    And I request quotes
    And I choose opaque hotels tab on results
    When I choose a hotel result
    Then I see Know Before You Go notice