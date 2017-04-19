Feature: Airport deal Hotels
  Owner: Intl team

  Background:
    Given default dataset
    Given the application is accessible

  @RTC-5834
  Scenario: Airport deal hotels are available for top destinations
    Given I'm from "United Kingdom" POS
    When I access Airport Deal Hotels in "Paris"
    Then the Airport Deal Hotels are available