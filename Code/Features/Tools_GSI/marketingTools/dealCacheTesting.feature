@TOOLS
Feature: User open landing page on domestic site and see Dynamic deal module
  Owner: Oleksandr Zelenov

  Scenario Outline: Dynamic deal cache verification.RTC-5432, 5433, 5431
    Given Domestic application is accessible
    And I open <vertical> landing page
    And I see deals on page

  Examples:
    | vertical |
    | car      |
    | hotel    |
