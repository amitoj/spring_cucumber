@US
Feature: Version test: Redirect from H.com to SForce Help Center
  Owner: Oleksandr Zelenov

  Scenario Outline: Salesforce Help Center: SFSH1 version test verification
    Given default dataset
    And set version test "vt.SFHS1" to value "<value>"
    Given the application is accessible
    When I access Help Center
    Then <hc_type> Help Center is opened
    And URL of the page contains <URL>

  Examples: quotable fares parameters
    | value | hc_type    | URL                  |
    | 1     | Salesforce | /knowledgebase       |
    | 2     | Existing   | helpcenter/index.jsp |


