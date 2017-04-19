@TOOLS @c3Car @ACCEPTANCE
Feature: Happy path guest purchase for Car in C3
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @JANKY
  Scenario: Car guest booking in C3.   RTC-1662
    Given I login into C3 with username "csrcroz1"
    And I search for car
    When I choose a car and purchase as guest
    Then I receive immediate confirmation