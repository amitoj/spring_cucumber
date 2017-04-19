@TOOLS
Feature: Verify agent can find a hotel by combinations of hotel name and some part of hotel address

  Background:
    Given C3 application is accessible

  @ACCEPTANCE   @criticalPriority
  Scenario Outline: verify search hotel by hotel name and part of hotel address
    Given I login into C3 with username "csrcroz1"
    Given some hotel from Database
    When I search hotel by name and <hotelAddress>
    Then I see Hotel Supplier info corresponds to Database

    Examples:
      | hotelAddress |
      | state        |
      | country      |
      | address      |
      | zip code     |

  @ACCEPTANCE
   Scenario: verify search hotel by part of hotel name and country
    Given I login into C3 with username "csrcroz1"
    Given some hotel from Database
    When I search hotel by 3 letters of hotel name and country
    And I select hotel on search results page
    Then I see Hotel Supplier info corresponds to Database


  Scenario: verify search hotel by hotel name only
    Given I login into C3 with username "csrcroz1"
    Given some hotel from Database
    When I search hotel by name only
    Then I see message "Please select a state or country."


  Scenario Outline: verify search hotel by a part of hotel address only
    Given I login into C3 with username "csrcroz1"
    Given some hotel from Database
    When I search hotel by <hotelAddress>
    Then I see message "Please enter a complete or partial Hotel name."

    Examples:
    | hotelAddress |
    | state        |
    | country      |
    | address      |
    | zip code     |


