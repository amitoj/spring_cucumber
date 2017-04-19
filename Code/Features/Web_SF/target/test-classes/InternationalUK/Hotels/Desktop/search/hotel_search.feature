@US @SEARCH
Feature: Hotel search
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE @JANKY
  Scenario: user gets opaque results when searching hotel
    Given I'm from "United Kingdom" POS
    When I'm searching for a hotel in "London, United Kingdom"
    And I request quotes
    Then I get opaque results

  @ACCEPTANCE @JANKY
  Scenario Outline: Default sorting on search results. RTC-4773
    Given I'm from "<country>" POS
    When I'm searching for a hotel in "<place>"
    And I want to travel between 20 days from now and 22 days from now
    And I request quotes
    Then I will see results sorted by <sortOrder> from low to high

  Examples: For non-city searches
    | country        | place                                   | sortOrder |
    | United Kingdom | London, Heathrow Airport                | Distance  |

  Examples: For city searches
    | country        | place                           | sortOrder  |
    | United Kingdom | London, United Kingdom          | Popular    |

  @JANKY
  Scenario Outline: price is consistent when user goes from list results to the hotel details
    Given I'm from "<country>" POS
    And I'm searching for a hotel in "<place>"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    Then I see price all inclusive at mapped results
    When I choose a hotel result
    Then I see price all inclusive at details
    And price is the same I noticed on results

  Examples:
    | country        | place              |
    | United Kingdom | Brussels, Belgium  |
    | Danmark        | Bruxelles, Belgien |
    | Singapore      | Brussels, Belgium  |

  @RTC-5063 @JANKY
  Scenario: tracking of SEM keywords on the booking confirmation
    Given I have a valid MasterCard credit card
    And I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Brussels, Belgium"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation
    And google adwords marketing beacon is present

  @RTC-6499
  Scenario: non-refundable label on list results for opaque hotels according to Germany POS legal requirement
    Given I'm from "Deutschland" POS
    And I'm searching for a hotel in "Hamburg, Deutschland"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    Then I should see "nicht erstattungsfähig" label for opaque hotels on list results

  @RTC-6499 @MAP
  Scenario: non-refundable label on mapped results for opaque hotels according to Germany POS legal requirement
    Given I'm from "Deutschland" POS
    And I'm searching for a hotel in "Hamburg, Deutschland"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    Then I should see "nicht erstattungsfähig" label for opaque hotels on mapped results

  @RTC-6285 @JANKY
  Scenario Outline: Opaque results have functionality to display different hotel label for different room types.
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Akumal"
    And I want to travel between 15 days from now and 16 days from now
    When I request quotes
    And I select a hotel with <roomType> room type
    Then I see hotel with <secretType> secret type on details page
    And I process the details page
    And I see hotel with <secretType> secret type on billing page

  Examples:
    | roomType      | secretType    |
    | condo         | APARTHOTEL    |
    | all-inclusive | ALL-INCLUSIVE |

