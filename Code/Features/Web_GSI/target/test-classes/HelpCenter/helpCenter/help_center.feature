@JANKY
Feature: Help Center helps customer to find solutions of common problems.

  Background:
    Given default dataset
    Given the application is accessible

  @US @CLUSTER1 @CLUSTER3
  Scenario: Help Center is accessible to all customers
    When I access Help Center
    Then Help Center is accessible

  @US @CLUSTER3
  Scenario Outline: Q&As searching and booking is accessible to all customers
    Given I access Help Center
    When I select Q&As "<vertical>" searching and booking
    Then "<vertical>" Q&As searching and booking is accessible

  Examples: quotable verticals
    | vertical |
    | hotel    |
    | air      |
    | car      |

  @JANKY @MOBILE @CLUSTER1 @CLUSTER3
  Scenario: Help Center is accessible to all customers
    When I access Help Center
    Then Help Center is accessible