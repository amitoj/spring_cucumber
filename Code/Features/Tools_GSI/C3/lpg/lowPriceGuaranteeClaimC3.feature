@TOOLS    @lpg
Feature: Verify agent can make LPG refund for opaque purchases only
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @highPriority
  Scenario Outline: LPG claim. Happy Path.
    #Please ensure a valid claim amount us submitted
    Given opaque <vertical> purchase for LPG claim
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I see itinerary details page
    When I click Low Price Guarantee Claim link
    And I fill LPG form
    Then I see successful LPG message
    And I see "Low Price Guarantee Claim" in case notes frame

  @ACCEPTANCE
  Examples:
    | vertical |
    | car      |
    | hotel    |

  Scenario Outline: Verify LPG claim is not displayed for retail purchases
    Given retail <vertical> purchase for LPG claim
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I see itinerary details page
    When I don't see Low Price Guarantee Claim link

  Examples:
    | vertical |
    | car      |
    | hotel    |

  Scenario: Verify LPG module on confirmation page after LPG claim before
    Given opaque car purchase with LPG claim made before
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I see itinerary details page
    When I click Low Price Guarantee Claim link
    And I fill LPG form
    Then I see error LPG message