
Feature: Store the Allowed Itinerary state before the refund


  #RefreshUtil is broken now

  #As an operations manager, I want to see in reporting whether an agent followed
  #the Allowed Recovery field, so that refunds are provided according to SRGs.
  #Owner: Vyacheslav Zyryanov

  #  Acceptance:
  #  1. When the agent COMPLETES Service Recovery, append the following text to the refund
  #  case note: "At the time of refund, Allowed Recovery displayed:" Yes, No, or, Investigate
  #  and store the state of the flag in the database
  #  2. If Allowed Recovery is not displayed for the Itinerary, add note: “At the time of refund,
  #  Allowed Recovery value was not displayed” and value "Not displayed" is stored in the Database
  #  3. Value in the database should be available to BI for reporting
  #  4. Scope is Hotel and Car

  Scenario Outline: Store the Allowed Itinerary state before the refund
    # Preparation: setup properties
    Given force property "hotwire.biz.c3.allowedRecovery.CLV.min.elite" to value "1"
    Given force property "hotwire.biz.c3.allowedRecovery.purchasesToCheck.elite" to value "1"
    Given C3 application is accessible

    # Prepare booking..
    Given I login into C3 with username "csrcroz1"
    And the customer with allowed recovery status "YES"
    And I'm looking for a hotel
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I launch search
    When I choose a hotel and purchase as user
    Then I receive immediate confirmation

    # Verify status
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I want to search for a customer
    And I want to search customer with existing confirmation number
    And Allowed Recovery status is YES
    And I choose service recovery
    And I choose Test Booking recovery reason
    When I do a full refund
    Then I see "Allowed Recovery displayed: Yes" in the notes
    Then I open itinerary details after refund via breadcrumb
    Then Allowed Recovery status is INVESTIGATE

  Examples: quotable fares parameters
    | destinationLocation         | startDateShift | endDateShift   | numberOfHotelRooms | numberOfAdults | numberOfChildren |
    | SFO                         | 30             | 35             | 1                  | 2              | 1                |

  Scenario: rollback preparation
    Given force property "hotwire.biz.c3.allowedRecovery.CLV.min.elite" to value "150"
    Given force property "hotwire.biz.c3.allowedRecovery.purchasesToCheck.elite" to value "150"
    Given C3 application is accessible