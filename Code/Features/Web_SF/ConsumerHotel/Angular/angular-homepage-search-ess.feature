@ANGULAR @US
Feature: Hotel Search to test ESS and non-ESS features from home page.

  Background:
    Given default dataset
    Given the angular application is accessible
    Given ESS is enabled
    Given the application is accessible

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Angular search with ESS enabled using dropdown and going to Angular Results page
    Given I'm searching for a hotel in "<ESSAutocomplete>"
    And I want to travel between 3 days from now and 5 days from now
    And I request angular quotes from non angular site using first result in autocomplete
    Then I see angular hotel results
    Then I verify destination location on angular results page is San Francisco

  Examples:

    | ESSAutocomplete    |
    | San Francisco      |
    | SFO                |
    #does not appear on autocomplete | Golden Gate Bridg |

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Angular search with ESS enabled using dropdown going to BEX Results page
    #hotel does not appear in autocomplete
    Given I'm searching for a hotel in "<ESSAutocomplete>"
    And I want to travel between 3 days from now and 5 days from now
    And I request angular quotes from non angular site using first result in autocomplete
    Then I verify destination location on BEX results page is San Francisco

  Examples:
    | ESSAutocomplete                   |
    | Hilton San Francisco Union Squar |

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Angular search with ESS enabled not using dropdown going to Angular Results page
    #there's a disambiguation page for the second result, will work on that later
    Given I type "<destinationLocation>" hotel location
    And I want to travel between 3 days from now and 5 days from now
    And I launch angular search
    Then I see angular hotel results
    Then I verify destination location on angular results page is San Francisco

  Examples:
    | destinationLocation                                                       |
    | San Francisco (and vicinity), California, United States of America        |
    | San Francisco, CA, United States (SFO-San Francisco Intl.)                |
    | Golden Gate Bridge, San Francisco, California, United States of America   |
    | Hilton San Francisco Union Square                                         |
      
      
    