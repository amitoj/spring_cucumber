@TOOLS @caseNotes
Feature: Operating with case notes
  Let CSR open amenities case notes and operate.
  Owner: Anastasia Snitko

  Background:
    Given C3 application is accessible

  Scenario: Add/Remove a bunch of amenities
    Given I login into C3 with username "csrcroz1"
    Given the hotel ID
  #Biz error on login sometimes!!!
    And I search hotel by ID
    And I am on Hotel Amenities page
    When I change status bunch of amenities
    And I save changes
    Then I see confirmation message "Your changes have been saved."
    And I see new amenities case note was added to existing case

  @highPriority
  Scenario Outline: Add/Remove minor and major amenities
    Given the hotel ID
    Given I login into C3 with username "csrcroz1"
    And I search hotel by ID
#Timed out after 30 seconds waiting for visibility of element located by By.selector: div.commonTasksLayout div.hotelInfo
#    1914 ERROR! (JSESSIONID: 4B1D67FB7775BEAC74DE5D96313C5E2A, HOST: jslave11.hotwire.com) Hotel name: null, ID: 1137
    And I am on Hotel Amenities page
    When I "<action>" "<amenity type>" amenity
    And I save changes
    Then I see confirmation message "Your changes have been saved."
    And I see new amenities case note was added to existing case

  Examples:
    | action | amenity type |
    | add    | Minor        |
    | remove | Minor        |
    | add    | Major        |
    | remove | Major        |