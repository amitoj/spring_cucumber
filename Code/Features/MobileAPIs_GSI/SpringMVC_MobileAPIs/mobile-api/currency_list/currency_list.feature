@MobileApi
Feature: Currency list

  Scenario Outline: currency list
    Given I execute currency list request for <country>
    Then currency list response is present
    And proper currencies are present for <country>

    Examples:
      | country |
      | US      |
      | UK      |
      | MX      |
      | AU      |

