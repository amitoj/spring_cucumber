@TOOLS @lpg
Feature: Verify customer is able to submit LPG refund request from purchase details in My Account
  Owner: Oleksandr Zelenov

  @ACCEPTANCE @STBL
  Scenario Outline: LPG Refund request happy path. Customer can send LPG refund request within 48 hours
    Given Domestic application is accessible
    And I login as customer with known credentials
    And I search for <vertical>
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    And I go to My Trips
    And I open my last booked trip
    Then I see Low-Price Guarantee module
    And I see LPG Request refund button
    When I click LPG refund request button
    When I fill LPG refund request form
    Then I see message about successfully submitted LPG refund request

  Examples:
    | vertical |
    | car      |
    | hotel    |


  @WIP
  Scenario: Verify customer cannot send LPG refund request after 48 hours
    Given hotel purchase with expired LPG refund request
    And Domestic application is accessible
    And I login as customer with known credentials
    And I go to confirmation page by direct link
    Then I see Low-Price Guarantee module
    And I don't see LPG Request refund button