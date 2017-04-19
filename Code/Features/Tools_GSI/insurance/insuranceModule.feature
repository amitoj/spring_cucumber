@TOOLS @SingleThreaded
Feature: Insurance 3d party module verification.
  Owner: Oleksandr Zelenov


  Scenario: Car static insurance module and copies verification
    Given I setup property "hotwire.biz.order.AccessAmerica.quote.timeout" to "1"
    Then I apply properties using RefreshUtil
    Given Domestic application is accessible
    And I search for car
    And I process the results page
    Then I see static insurance module

  Scenario Outline: Hotel static insurance module verification.
    Given I setup property "hotwire.view.web.purchase.hotel.insuranceSimulateRequestTimeOut" to "true"
    Given I setup property "hotwire.view.web.hotel.staticInsurance.enabled" to "true"
    Then I apply properties using RefreshUtil
    Given <site> application is accessible
    And I choose "<currency>" currency
    And I search for hotel
    And I process the results page
    And I process the details page
    Then I <negation> see static insurance module

  Examples:
    | site          | currency | negation |
    | Domestic      | USD      |          |
    | Domestic      | GBP      | don't    |
    | International | USD      | don't    |


  Scenario: Hotel static insurance. Happy path.
    Given I setup property "hotwire.view.web.purchase.hotel.insuranceSimulateRequestTimeOut" to "true"
    Given I setup property "hotwire.view.web.hotel.staticInsurance.enabled" to "true"
    Then I apply properties using RefreshUtil
    Given Domestic application is accessible
    And I choose "USD" currency
    And I search for hotel
    And I process the results page
    And I process the details page
    Then I see static insurance module
    And I verify that static insurance works correctly
    And I process the billing page
    Then I receive immediate confirmation
    And insurance information saved correctly to database


  Scenario: Reverting properties after static insurance checking.
    Given I setup property "hotwire.biz.order.AccessAmerica.quote.timeout" to "5000"
    Given I setup property "hotwire.view.web.purchase.hotel.insuranceSimulateRequestTimeOut" to "false"
    Given I setup property "hotwire.view.web.hotel.staticInsurance.enabled" to "false"
    Then I apply properties using RefreshUtil




