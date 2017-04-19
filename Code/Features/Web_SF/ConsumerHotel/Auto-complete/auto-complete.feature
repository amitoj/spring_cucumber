@US
Feature: Verifying autocomplete and disambiguation layer on the hotel index and landing pages
  Owner: Komarov Igor

  Background: 
    Given default dataset
    Given the application is accessible

  @US @SingleThreaded @ACCEPTANCE
  Scenario Outline: RTC-613 Verifying auto-complete on the UHP for the Hotel destination field
    When I am on hotel index page
    Given I type "chi" hotel location
    Then I see first suggested location is "<expectedLocation>" underlined and highlighted and I click it
    And I should see "<expectedLocation>" in the Hotel location
    Given I set hotel dates between 10 days from now and 15 days from now
    When I start hotel search without specifying the destination
    Then I will see opaque results page
    Then search results contain "<expectedLocation>" in location

    Examples: 
      | expectedLocation                            |
      | Chicago, Illinois, United States of America |

  @US @JANKY
  Scenario Outline: RTC-593 Resolving hotel ambiguous issue using Disambiguation layer for city
    When I am on hotel index page
    And I'm searching for a hotel in "<disambiguationDestinationLocation>"
    And I want to travel between 21 days from now and 26 days from now
    And I want 1 room(s)
    And I will be traveling with 2 adults
    And I will be traveling with 1 children
    And I launch a search
    Given I receive error message for the incorrect location
    Then I see disambiguation layer
    When I click out of hotel disambiguation layer
    Then I don't see disambiguation layer
    And I click on the hotel destination field
    Then I see disambiguation layer
    Then I see first suggested location is "<expectedLocation>" underlined and highlighted and I click it
    When I start hotel search without specifying the destination
    Then I will see opaque results page
    Then search results contain "<expectedLocation>" in location

    Examples: 
      | disambiguationDestinationLocation | expectedLocation |
      | Islamabad                    | Islamabad, Pakistan  |

  @US  @JANKY
  Scenario Outline: RTC-594 Resolving hotel ambiguous issue using Disambiguation layer for airport
    When I am on hotel index page
    And I'm searching for a hotel in "<disambiguationDestinationLocation>"
    And I want to travel between 21 days from now and 26 days from now
    And I want 1 room(s)
    And I will be traveling with 2 adults
    And I will be traveling with 1 children
    And I launch a search
    Given I receive error message for the incorrect location
    Then I see disambiguation layer
    When I click out of hotel disambiguation layer
    Then I don't see disambiguation layer
    And I click on the hotel destination field
    Then I see disambiguation layer
    Then I see suggested location is "<expectedLocation>" and I click it
    When I start hotel search without specifying the destination
    Then I will see opaque results page
    Then search results contain "<expectedLocationResultPage>" in location

    Examples: 
      | disambiguationDestinationLocation | expectedLocation                      | expectedLocationResultPage |
      | Springfield                       | Springfield, IL - (SPI) (Springfield) | Springfield, IL - (SPI)    |

  @US @ACCEPTANCE
  Scenario Outline: RTC-580 Verify that error message match specification
    Given I am on hotel index page
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between 21 days from now and 26 days from now
    When I launch a search    
    Then the results page contains "Portland" in location

    Examples: 
      | destinationLocation |
      | Portland            |

  @US @ACCEPTANCE
  Scenario Outline: RTC-581 Verify that error message with inessential destination
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between 21 days from now and 26 days from now
    When I launch a search
    Given I receive error message for the incorrect location

    Examples: 
      | destinationLocation |
      | ZZZZZZ              |

  @US @SingleThreaded @JANKY
  Scenario Outline: RTC-565 Airport hotel location in Autocomplete
    When I am on hotel index page
    Given I type "PORTLAND" hotel location
    Then I see suggested location is "<expectedLocation1>" underlined and highlighted
    Then I see suggested location is "<expectedLocation2>" and I click it
    And I should see "<expectedLocation3>" in the Hotel location
    Given I set hotel dates between 10 days from now and 15 days from now
    When I start hotel search without specifying the destination
    Then I will see opaque results page
    Then search results contain "<expectedLocation3>" in location

    Examples: 
      | expectedLocation1 | expectedLocation2               | expectedLocation3    |
      | Portland, Oregon,      | Portland, Maine, United States of America| South Portland, Maine |

  @US @SingleThreaded  @JANKY
  Scenario Outline: RTC-566 City hotel location in Autocomplete
    When I am on hotel index page
    Given I type "PORTLAND" hotel location
    Then I see suggested location is "<expectedLocation2>"
    Then I see suggested location is "<expectedLocation1>" underlined and highlighted and I click it
    Given I set hotel dates between 10 days from now and 15 days from now
    When I start hotel search without specifying the destination
    Then I will see opaque results page
    Then search results contain "<expectedLocation3>" in location

    Examples: 
      | expectedLocation1 | expectedLocation2               | expectedLocation3    |
      | Portland, Oregon,      | Portland, Maine, United States of America| South Portland, Maine |
