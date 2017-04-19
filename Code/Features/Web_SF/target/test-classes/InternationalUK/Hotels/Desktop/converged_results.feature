@US @SEARCH
Feature: Shared results and details page for domestic and international to reuse as much functionality as possible.
  Owner: Intl team

  Background:
    Given default dataset
    And the application is accessible

  Scenario Outline: get mapped results list at international site
    Given I'm from "<country>" POS
    And I'm searching for a hotel in "<destination>"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    And I choose opaque hotels tab on results
    Then the map will be rendered correctly in the results
    When I choose a hotel result
    Then I will see opaque details page

  Examples:
    | country        | destination                        |
    | United Kingdom | Brussels, Belgium                  |
    #| Deutschland    | Hamburg, Deutschland               |
    #| Danmark        | København, Denmark                 |
    #| Australia      | Sydney, New South Wales, Australia |

  Scenario: price is consistent when user goes from mapped results to the hotel details
    Given I'm from "United Kingdom" POS
    And I'm searching for a hotel in "Brussels, Belgium"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    Then I see price all inclusive at mapped results
    And I choose a hotel result
    Then I see price all inclusive at details
    And price is the same I noticed on results