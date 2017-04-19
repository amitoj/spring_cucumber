Feature: semi-opaque room photos
  Owner: Intl team

  Background:
    Given default dataset
    And opaque room photos support is activated
    And the application is accessible

  @RTC-5832
  Scenario Outline: opaque room photos at the results page
    Given  I'm from "United Kingdom" POS
    When I'm searching for a hotel in "<destination>"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    Then I should see room photos for an opaque hotel

  Examples:
    | destination                         |
    | Manchester, England, United Kingdom |
    | Cardiff, Wales, United Kingdom      |
    | Birmingham, England, United Kingdom |
    | Paris, France                       |
    | Rome, Italy                         |