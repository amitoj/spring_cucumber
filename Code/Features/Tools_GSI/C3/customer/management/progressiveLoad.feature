@TOOLS     @c3Management  @SingleThreaded
Feature: Verify progressive load
  Pagination view of last purchases fragment
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario: Scroll purchase area and verify deals are loading
    Given I login into C3 with username "csrcroz1"
    And customer with many hotel purchases
    And I search for given customer email
    And I want see hotel tab
    And I see 15 results on page
    When I scroll page
    Then I see 30 results on page