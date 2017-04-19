@SEARCH
Feature: Converged details page

  Background:
    Given default dataset
    Given the application is accessible

  @US
  Scenario Outline: 'Facilities' term should be used on international POSa
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Dublin, Ireland"
    And I want to travel between 15 days from now and 16 days from now
    When I request quotes
    And I choose <type> hotels tab on results
    When I choose a hotel result
    Then I should see term facilities used

  Examples:
    | type   |
    | opaque |
    | retail |

  @US
  Scenario Outline: 'amenities' term should be used on US
    Given I'm searching for a hotel in "San Francisco, CA"
    And I want to travel between 3 days from now and 5 days from now
    When I request quotes
    And I choose <type> hotels tab on results
    When I choose a hotel result
    Then I should see term amenities used

  Examples:
    | type   |
    | opaque |
    | retail |

  @US
  Scenario: Info modules checklist
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Paris, France"
    And I want to travel between 15 days from now and 16 days from now
    When I request quotes
    And I choose retail hotels tab on results
    When I choose a hotel result
    Then I see LPG layer
    And I see cancellation policy panel
    And I see what trip advisor layer
    And I see features list
    And I see location description text
    And I see customer care phone number above the details is "0808 234 5903"
    And All customer care modules will be displayed in the retail details page