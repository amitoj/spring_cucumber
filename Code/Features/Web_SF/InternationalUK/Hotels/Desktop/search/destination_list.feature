@US  @ACCEPTANCE
Feature: Successful view of destination Page
  
  Owner: Juan Hernandez

  Background: 
    Given default dataset
    Given the application is accessible

  Scenario Outline: RTC-4730- Destinations page is displayed without errors
    Given I have load international <site> hotwire site
    And I'm searching for a hotel in "London, England"
    And I want to travel between 5 days from now and 7 days from now
    And I am looking for a hotel
    Then I should see Destination Page with no errors

    Examples: 
      | site |
      | UK   |
