@US
Feature: Site Map
  Owner: Cristian Gonzalez Robles

  Background: 
    Given default dataset
    Given the application is accessible

  #BUG53902
  @BUG @BUG
  Scenario Outline: RTC-347:SiteMap - City guide
    Given I click on "Site Map"
    And I click on "<cityGuides>"
    Then I confirm that the page header reads "<pageHeader>"
    Then Verify content in the page: "<content1>"
    Then Verify content in the page: "<content2>"

    Examples: 
      | cityGuides           | pageHeader                | content1                  | content2                         |
      | San Francisco travel | San Francisco Hotel Deals | Alcatraz Island           | Union Square - Convention Center |
      
  @BUG @ACK
  Scenario Outline: RTC-346:Destination Guides
    Given I click on "Site Map"
    And I click on "<cityGuides>"
    Then I confirm that the page header reads "<pageHeader>"
    Then Verify content in the page: "<content1>"
    Then Verify content in the page: "<content2>"

    Examples: 
      | cityGuides | pageHeader       | content1            | content2   |
      | Rome       | Rome Hotel Deals | Vatican - Trionfale | Via Veneto |
