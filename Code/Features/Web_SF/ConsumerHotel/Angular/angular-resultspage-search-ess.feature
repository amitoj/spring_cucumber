@ANGULAR @US
Feature: Hotel Search to test ESS and non-ESS features from angular results.
   
  Background: 
    Given default dataset
    Given the angular application is accessible
    Given ESS is enabled
    Given the application is accessible

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Angular search with ESS enabled using dropdown and going to Angular Results page
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    Given I'm searching for a hotel with autocomplete in "<ESSAutocomplete>"
    And I launch results page angular search
    Then I see angular hotel results
    Then I verify destination location on angular results page is San Francisco

  Examples:
    | ESSAutocomplete    | startDateShift | endDateShift |  destinationLocation   |
    | San Francisco, CA  | 3              | 5            |  Chicago, IL           |
    | SFO                | 3              | 5            |  Chicago, IL           |
    | Golden Gate Bridge | 3              | 5            |  Chicago, IL           |
      
@ANGULAR @ACCEPTANCE @US
  Scenario Outline: Angular search with ESS enabled using dropdown going to BEX Results page
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    Given I'm searching for a hotel with autocomplete in "<ESSAutocomplete>"
    And I launch results page angular search
    Then I verify destination location on BEX results page is San Francisco
    
    Examples: 
      | ESSAutocomplete                   | startDateShift | endDateShift | destinationLocation |
      | Hilton San Francisco Union Square | 3              | 5            | Chicago, IL         |
      
@ANGULAR @ACCEPTANCE @US
Scenario Outline: Angular search with ESS enabled not using dropdown going to Angular Results page
  Given I'm searching for a hotel in "<firstDestination>"
  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
  And I request angular quotes
  Then I see angular hotel results
  Given I'm searching for a hotel in "<destinationLocation>"
  And I request angular quotes from angular results
  Then I see angular hotel results
  Then I verify destination location on angular results page is San Francisco
    
     Examples: 
      | destinationLocation                                                       | startDateShift | endDateShift | firstDestination |
      | San Francisco (and vicinity), California, United States of America        | 3              | 5            | Chicago, IL      |
      | San Francisco, CA, United States (SFO-San Francisco Intl.)                | 3              | 5            | Chicago, IL      |
      | Golden Gate Bridge, San Francisco, California, United States of America   | 3              | 5            | Chicago, IL      |
      | Hilton San Francisco Union Square                                         | 3              | 5            | Chicago, IL      |
      
      
    