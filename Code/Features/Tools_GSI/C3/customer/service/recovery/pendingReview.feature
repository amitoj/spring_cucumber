@TOOLS
Feature: Verify Pending review logic.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario Outline: Pending review with itinerary cancelled
    Given customer <vertical> purchase for refund
    And I don't have all needed documents for refund
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I choose service recovery
    And I choose <reason> recovery reason
    When I do a full refund
    Then I see pending review confirmation message
    And I see purchase status is "Pending Review with Itinerary Cancelled"
    And I see status "30045" in Database purchase_order table
    And I see status "50045" in Database reservation table


  Examples:
    | vertical | reason                     |
    | hotel    | Medical                    |
    | hotel    | Death                      |
    | hotel    | Court Summons or Jury Duty |
    | hotel    | Military                   |
    | hotel    | Booking Made By Minor      |

  Scenario Outline: Pending review for air and cars
    Given customer <vertical> purchase for refund
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I choose service recovery
    And I choose <reason> recovery reason
    When I do a full refund
    Then I see pending review confirmation message
    And I see purchase status is "Pending Review"
    And I see status "30035" in Database purchase_order table
    And I see status "50020" in Database reservation table

  Examples:
    | vertical | reason  |
    | air      | Death   |
    | car      | Medical |