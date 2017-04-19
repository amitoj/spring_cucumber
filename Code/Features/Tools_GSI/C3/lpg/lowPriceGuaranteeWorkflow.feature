@TOOLS @lpg
Feature: Verify agent can create LPG workflow for retail purchases
  Owner: Oleksandr Zelenov


  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario Outline: Verify agent can create LPG workflow for retail purchases
    #passed 5 times in STBL CI
    Given opaque <vertical> purchase for LPG claim
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I see itinerary details page
    When I click on create workflow
    And I create LPG Workflow
    Then I see message about successful LPG workflow creation
    And I see "Low Price Guarantee" in case notes frame

  Examples:
    | vertical |
    | car      |
    | hotel    |

  Scenario: Verify LPG workflow for a purchase cannot be created twice
    Given opaque car purchase with LPG claim made before
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I see itinerary details page
    When I click on create workflow
    And I create LPG Workflow
    Then I see message that such workflow was already created for itinerary