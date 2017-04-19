Feature: Xnet UI regression test

  @XnetUI
  Scenario: Update Xnet Inventory using valid username and password every day
    Given the xnet service is accessible
    Given I'm a valid supplier
    And my hotel id is 6591
    And date range is between 1 days from now and 5 days from now every day
    And room type is STANDARD
    And total inventory available is 4
    And rate plan is XHW
    And xnet currency is USD
    And rate per day is 25
    And extra person fee is 0
    When I update Inventory
    Then I should get No error

  # Search and book
  @US
  Scenario Outline: Find and purchase a Xnet hotel as a guest user.
    Given the application is accessible
    Given I have valid credentials
    And I authenticate myself
    And I am authenticated
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    And I choose opaque results
    When I select the <neighborhoodName> neighborhood with <starRating> star rating and CRS <CRS Ref>
    And I book that hotel
    And I complete the booking with saved user account
    Then I receive immediate confirmation

    Examples: quotable fares parameters
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | neighborhoodName         | starRating | CRS Ref |
      | Santa Barbara, CA   | 1              | 2            | 1                  | 2              | 0                | Santa Barbara area hotel | 4          | XMN     |
