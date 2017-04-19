@TOOLS @c3Mobile @ACCEPTANCE
Feature: Mobile device information verification in C3.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario Outline: Mobile device verification in C3.
    Given mobile device purchase on <device>
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I see itinerary details page
    And mobile device type is correct
    And application type is correct

  Examples:
    | device  |
    | android |
    | iphone  |
    | ipad    |
    | other   |
    | tablet  |

  Scenario: Non-mobile device verification in C3.
    Given desktop customer purchase
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I see itinerary details page
    And mobile device type is correct
    And application type is correct
