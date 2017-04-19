@US
Feature: Car purchasing with automation layer
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  Scenario: First Car aggregated purchase
    Then  Make default retail car purchase with SFO location between 20 days from now  and 25 days from now and stop on billing
    Then  Make default retail aggregated car purchase from billing

  Scenario: Second  Car aggregated purchase
    Then  Make new car purchase


  Scenario: Hotel aggregated purchase
    Then  Make default opaque hotel purchase with SFO location

  @ACCEPTANCE @STBL
  Scenario Outline:  Duplicate car booking with same identical params
    Given I have purchased a car as a registered customer <startDateShift> and <endDateShift> and  <location>
    When  I attempt to purchase a car with identical search parameters and same traveler's name
    Then  the purchase was failed due of
    """
   We're sorry, but your booking was not completed. You were not charged
   A car rental booking with the same trip details was recently booked through your Hotwire account.
   View this itinerary  If you have questions regarding this booking, please contact Hotwire Customer Care.
   In the U.S. and Canada, our associates can be reached toll-free at 1-866-HOTWIRE (468-9473).
   Outside of the U.S. and Canada, please call 1-417-520-1680 (regular toll charges apply). Error #427
    """

  Examples:
    | startDateShift   | endDateShift     | location |
    | 16 days from now | 30 days from now | SFO |


  @ACCEPTANCE @STBL
  Scenario Outline:  Duplicate car booking with different driver names
    Given I have purchased a car as a registered customer  <startDateShift> and <endDateShift> and  <location>
    When  I purchase another car with identical search parameters and NEW traveler's name
    Then I receive immediate confirmation

  Examples:
    | startDateShift   | endDateShift     | location |
    | 16 days from now | 30 days from now | SFO |

  @ACCEPTANCE @STBL
  Scenario: Failed
    Given I try to make car purchase from results
