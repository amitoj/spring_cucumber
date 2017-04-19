Feature: Xnet UI regression test

  @XnetUI @SMOKE
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
  @US @LIMITED @JANKY
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
    And I choose opaque hotels tab on results
    When I select the <neighborhoodName> neighborhood with <starRating> star rating and CRS <CRS Ref>
    And I book that hotel
    And I complete the booking with saved user account
    Then I receive immediate confirmation

    Examples: quotable fares parameters
      | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | neighborhoodName         | starRating | CRS Ref |
      | Santa Barbara, CA   | 1              | 2            | 1                  | 2              | 0                | Santa Barbara area hotel | 4          | XMN     |

  @XnetUI
  Scenario Outline: Retrieve Booking
    Given the xnet service is accessible
    Given I'm a valid supplier
    And my hotel id is 8880
    When I retrieve booking
    And I review bookings by <search_type> and date range <from> to <to> days from now
    Then I should get valid booking response
    And I should get rate is $25.00

    Examples: Date
      | search_type  | from | to |
      | Arrival Date | 1    | 3  |
      | Booked Date  | -2   | 0  |

  @XnetUI @ACCEPTANCE
  Scenario Outline: Update/change xnet late booking end time
    Given the xnet service is accessible
    Given I'm a valid supplier
    And my hotel id is 8880
    When I access hotel details page
    And I set late booking end time = <endTime>
    Then I should see late booking confirmation message and late booking end time being set to <endTime>

    Examples: late booking end timings
      | endTime |
      | 1:00 AM |
      | Default |

  #room type settings are read in this order -
  #bedType, maxRoom/Book, maxGuests/room, maxAdults/room, maxChildren/room, isActiveFlag
  @XnetUI @ACCEPTANCE @JANKY
  Scenario: Edit room type settings
    Given the xnet service is accessible
    Given I'm a valid supplier
    And my hotel id is 10153
    Then I should see Test in the room type list
    When I click on room type link
    And I change the following room type settings:
      #change the values of all the fields for RoomType: TEST
      | 1Q2D |
      | 3    |
      | 1    |
      | 1    |
      | 1    |
      | N    |
    Then the changes are saved successfully:
      # Asserts values set and the confirmation message.
      | 1Q2D |
      | 3    |
      | 1    |
      | 1    |
      | 1    |
      | N    |
    When I go to hotel overview page
    Then I shouldn't see Test in the room type list
    When I click on room type link
    And I change the following room type settings:
      | ROH |
      | 4   |
      | 2   |
      | 2   |
      | 2   |
      | Y   |
    Then the changes are saved successfully:
      | ROH |
      | 4   |
      | 2   |
      | 2   |
      | 2   |
      | Y   |

  @XnetUI
  Scenario Outline: Change the password
    Given the xnet service is accessible
    Given I sign in with <email> and <old_password>
    And I go to change password page
    And I change the password from <old_password> to <new_password>

    Examples: data
      | email                      | old_password | new_password |
      | xnetcustomized@hotwire.com | hotwire      | welcome      |
      | xnetcustomized@hotwire.com | welcome      | hotwire      |

  @XnetUI
  Scenario Outline: Add user permissions
    Given the xnet service is accessible
    And I'm a valid supplier
    And my hotel id is 30052
    And I want to update user permissions
    When I <addDeleteUpdate> a user with <first_name>, <last_name>, <email>, <phone> and <permission_level>
    Then I see a confirmation message
    And I see <code> affiliation of 30052 and <email> in the DB

    Examples: data
      | addDeleteUpdate | first_name | last_name      | email                      | phone      | permission_level | code |
      | add             | xnet       | customizeduser | xnetcustomized@hotwire.com | 8889898989 | All Permissions  | 3104 |
      | update          | xnet       | customizeduser | xnetcustomized@hotwire.com | 8889898989 | View Only        | 3105 |
      | update          | xnet       | customizeduser | xnetcustomized@hotwire.com | 8889898989 | Deny Access      | 3100 |
      | add             | xnet       | customizeduser | xnetcustomized@hotwire.com | 8889898989 | All Permissions  | 3104 |
      | delete          | Xnet       | customizeduser | xnetcustomized@hotwire.com | 8889898989 | Delete           | 3100 |

  @XnetUI
  Scenario: Review Changes Page
    Given the xnet service is accessible
    Given I'm a valid supplier
    And my hotel id is 8880
    When I access review changes page
    And I review the changes between 1 days from now to 2 days from now
    Then I see a review changes table
