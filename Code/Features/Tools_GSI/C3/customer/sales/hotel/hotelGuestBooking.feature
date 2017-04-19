@TOOLS    @c3Hotel   @ACCEPTANCE
Feature: Hotel guest purchase in C3
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario: Hotel guest purchase in C3 RTC-480
    Given I login into C3 with username "csrcroz1"
    And I search for hotel
    When I choose a hotel and purchase as guest
    Then I receive immediate confirmation
