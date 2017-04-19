@US @ACCEPTANCE @JANKY
Feature: Confirm that the farefinder on the retail hotel list page works with positive parameters entered
  Owner: Cristian Gonzalez Robles

  Background: 
    Given default dataset
    Given the application is accessible

  Scenario Outline: RTC-4661 - Farefinder positive search from retail hotel list page
    Given I have load international <site> hotwire site
    And I'm searching for a hotel in "sea"
    And I want to travel between 120 days from now and 123 days from now
    And I am looking for a hotel
    Then I confirm the search takes place since url contains searchTokenId as 1
    Then I choose "Downtown - Pike Place area hotel" hotel

    Examples: 
      | site |
      | UK   |
      | IE   |
      | HK   |
      | AU   |
