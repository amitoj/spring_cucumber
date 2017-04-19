@MobileApi
Feature: Mobile API trip summary endpoint


  Scenario Outline: verify trip summary response
    Given I am logged in as random user with booked trips
    And I have unique oauth token for this customer
    When I execute trip summary request with sorting by <sortingMethod>
    Then trip summary is present
    And I see itinerary number in trip reservation details
    And I see confirmation code in trip reservation details
    And I see booking status in trip reservation details
    And I see product vertical in trip reservation details
    And I see supplier info in trip reservation details
    And I see booking dates and location in trip reservation details
    And I see sorting method is present in response
    And I see sorting method is by <sortingType> date
    And I see trips are sorted by <sortingType> date
    And I see address and latitude/longitude are present in trip summary
    And I see hotel check-in check-out time are present

  @SMOKE
  Examples:
  | sortingMethod      | sortingType       |
  | default            | travel            |

  @LIMITED
  Examples:
    | sortingMethod      | sortingType       |
    | travel date        | travel            |
    | purchase date      | purchase          |


  @ACCEPTANCE
  Scenario Outline: verify trip summary with date ranging
    Given I am logged in as random user with booked trips
    And I have unique oauth token for this customer
    When I execute trip summary request with sorting by <sortingMethod> with start date and with end date
    Then trip summary is present
    And I see itinerary number in trip reservation details
    And I see confirmation code in trip reservation details
    And I see booking status in trip reservation details
    And I see product vertical in trip reservation details
    And I see supplier info in trip reservation details
    And I see booking dates and location in trip reservation details
    And I see sorting method is present in response
    And I see sorting method is by <sortingType> date
    And I see trips are in correct date range
    And I see trips are sorted by <sortingType> date
    And I see address and latitude/longitude are present in trip summary
    And I see hotel check-in check-out time are present

  Examples:
    | sortingMethod      | sortingType       |
    | default            | travel            |
    | travel date        | travel            |
    | purchase date      | purchase          |



  @ACCEPTANCE
  Scenario Outline: verify trip summary response with excludeCanceledRefunded flag
    Given I am logged in as random user with cancelled trips
    And I have unique oauth token for this customer
    And I exclude canceled trips from the trip summary
    When I execute trip summary request with sorting by <sortingMethod>
    Then trip summary is present
    And I see itinerary number in trip reservation details
    And I see confirmation code in trip reservation details
    And I see booking status in trip reservation details
    And I see product vertical in trip reservation details
    And I see supplier info in trip reservation details
    And I see booking dates and location in trip reservation details
    And I see sorting method is present in response
    And I see sorting method is by <sortingType> date
    And I see trips are sorted by <sortingType> date
    And I see there are no cancelled trips in response
    And I see address and latitude/longitude are present in trip summary
    And I see hotel check-in check-out time are present

  Examples:
    | sortingMethod      | sortingType       |
    | default            | travel            |
    | travel date        | travel            |
    | purchase date      | purchase          |



  Scenario Outline: verify trip summary response with limit and offset
    Given I am logged in as random user with <tripsStatus> trips
    And I have unique oauth token for this customer
    And I use 2 limit and 0 offset
    When I want to get first page of trip summary with sorting by <sortingMethod>
    Then first page of trip summary response is present
    And I use 2 limit and 3 offset
    When I want to get second page of trip summary with sorting by <sortingMethod>
    Then second page of trip summary response is present
    And I see sorting method is present in both pages
    And I see sorting method in both pages is by <sortingType> date
    And I see in both pages trips are sorted by <sortingType> date

  @ACCEPTANCE
  Examples:
  | tripsStatus   | sortingMethod      | sortingType       |
  |  booked       | default            | travel            |
  |  cancelled    | default            | travel            |


  Examples:
  | tripsStatus   | sortingMethod      | sortingType       |
  |  booked       | travel date        | travel            |
  |  booked       | purchase date      | purchase          |
  |  cancelled    | travel date        | travel            |
  |  cancelled    | purchase date      | purchase          |


  Scenario Outline: verify trip summary limit and offset with date ranging
    Given I am logged in as random user with <tripsStatus> trips
    And I have unique oauth token for this customer
    And I use 2 limit and 0 offset
    When I want to get first page of trip summary with sorting by <sortingMethod> with start date and with end date
    Then first page of trip summary response is present
    And I use 2 limit and 3 offset
    When I want to get second page of trip summary with sorting by <sortingMethod> with start date and with end date
    Then second page of trip summary response is present
    And I see sorting method is present in both pages
    And I see sorting method in both pages is by <sortingType> date
    And I see in both pages trips are sorted by <sortingType> date
    And I see in both pages trips are in correct date range

  @ACCEPTANCE
  Examples:
    | tripsStatus   | sortingMethod      | sortingType       |
    |  booked       | default            | travel            |
    |  cancelled    | default            | travel            |
    |  booked       | purchase date      | purchase          |


  Examples:
    | tripsStatus   | sortingMethod      | sortingType       |
    |  booked       | travel date        | travel            |
    |  cancelled    | travel date        | travel            |
    |  cancelled    | purchase date      | purchase          |


  Scenario Outline: verify trip summary limit and offset with end date
    Given I am logged in as random user with <tripsStatus> trips
    And I have unique oauth token for this customer
    And I use 2 limit and 0 offset
    When I want to get first page of trip summary with sorting by <sortingMethod> and with end date
    Then first page of trip summary response is present
    And I use 2 limit and 3 offset
    When I want to get second page of trip summary with sorting by <sortingMethod> and with end date
    Then second page of trip summary response is present
    And I see sorting method is present in both pages
    And I see sorting method in both pages is by <sortingType> date
    And I see in both pages trips are sorted by <sortingType> date
    And I see in both pages trips are in correct date range

  @ACCEPTANCE
  Examples:
    | tripsStatus   | sortingMethod      | sortingType       |
    |  booked       | default            | travel            |
    |  cancelled    | purchase date      | purchase          |


  Examples:
    | tripsStatus   | sortingMethod      | sortingType       |
    |  cancelled    | default            | travel            |
    |  booked       | travel date        | travel            |
    |  booked       | purchase date      | purchase          |
    |  cancelled    | travel date        | travel            |



