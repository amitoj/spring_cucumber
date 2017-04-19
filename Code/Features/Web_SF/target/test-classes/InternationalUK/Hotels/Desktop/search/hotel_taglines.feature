@US @SEARCH
Feature: Custom taglines  for particular opaque hotel at results, details and billing pages
  As a Product Owner
  I would like to have semi-opaque hotels with specific description
  So that customers will have more clear idea of the opaque hotel they are booking
  Owner: Intl team

  Background:
    Given default dataset
    Given set version test "vt.SKW01" to value "1"
    Given set version test "vt.ETL01" to value "2"
    Given the application is accessible

  @RTC-5972
  Scenario Outline: custom taglines present for opaque hotels for specified destinations
    Given I'm from "<country>" POS
    And I'm searching for a hotel in "<location>"
    And I want to travel between 30 days from now and 32 days from now
    When I request quotes
    Then I see hotel with <taglineType> tagline

  Examples:
    | country        | location                            | taglineType |
    | United Kingdom | Barcelona, Spain                    | custom      |
    | Danmark        | Manchester, England, Storbritannien | default     |

  Scenario Outline: custom taglines on mapped results page
    Given I'm from "<country>" POS
    And I'm searching for a hotel in "<location>"
    And I want to travel between 30 days from now and 32 days from now
    When I request quotes
    Then I see hotel with <taglineType> tagline

  Examples:
    | country        | location                            | taglineType |
    | United Kingdom | Barcelona, Spain                    | custom      |
    | Australia      | Munich, Germany                     | custom      |
    | New Zealand    | Manchester, England, United Kingdom | custom      |
    | Ireland        | Barcelona, Spain                    | custom      |
    | Hong Kong      | Munich, Germany                     | custom      |
    | Singapore      | Manchester, England, United Kingdom | custom      |