@US @SingleThreaded
Feature:Car Duplicate Purchase protection
  Owner:Boris Shukaylo

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE @JANKY
  Scenario: RTC-869 Duplicate booking - Retail - Airport
    Given my name is qa_dvalov@hotwire.com and my password is hotwire333
    Then I authenticate myself
    And I'm searching for a car in "Denver, CO - (DEN)"
    And I want to travel between 7 days from now and 47 days from now
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I select a retail car with cd code "SCAR"
    Then I fill in all billing information
    Then I complete purchase with agreements
    Then I receive immediate confirmation
    And  I want to logout
    Then I authenticate myself
    Then I am on home page
    And I'm searching for a car in "Denver, CO - (DEN)"
    And I want to travel between 7 days from now and 47 days from now
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I select a retail car with cd code "SCAR"
    Then I fill in all billing information
    Then I complete purchase with agreements
    Then the purchase was failed due of
    """
   We're sorry, but your booking was not completed. You were not charged
   A car rental booking with the same trip details was recently booked through your Hotwire account.
   View this itinerary  If you have questions regarding this booking, please contact Hotwire Customer Care.
   In the U.S. and Canada, our associates can be reached toll-free at 1-866-HOTWIRE (468-9473).
   Outside of the U.S. and Canada, please call 1-417-520-1680 (regular toll charges apply). Error #427
    """

  @ACCEPTANCE @JANKY
  Scenario Outline: Duplicate booking - Pick-up date differs RTC-864,856,866
    Given I have valid random credentials
    Then I authenticate myself
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I select a retail car with cd code "CCAR"
    Then I fill in all billing information
    Then I complete purchase with agreements
    Then I receive immediate confirmation
    And  I want to logout
    Then I authenticate myself
    Then I am on home page
    And I'm searching for a car in "<locationSecond>"
    And I want to travel between <startDateShiftSecond> and <endDateShiftSecond>
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I select a retail car with cd code "CCAR"
    Then I fill in all billing information
    Then I complete purchase with agreements
    And I receive immediate confirmation

  Examples:
    | #   | startDateShift  | endDateShift     | startDateShiftSecond | endDateShiftSecond | location             | locationSecond       |
    | 864 | 4 days from now | 44 days from now | 6 days from now      | 44 days from now   | Columbia, SC - (CAE) | Columbia, SC - (CAE) |
    | 865 | 4 days from now | 44 days from now | 4 days from now      | 46 days from now   | Columbia, SC - (CAE) | Columbia, SC - (CAE) |
    | 866 | 8 days from now | 48 days from now | 8 days from now      | 48 days from now   | Columbia, SC - (CAE) | Memphis, TN - (MEM)  |

  @ACCEPTANCE @JANKY
  Scenario Outline: RTC-948 Duplicate booking - 1-st one wasn't completed (agreement)
    Given I have valid random credentials
    Then I authenticate myself
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I select a retail car with cd code "CCAR"
    Then I fill in all billing information
    Then I complete purchase without agreements
    Then I receive immediate "At the bottom of the page, you must accept our Terms of Use." error message
    And  I want to logout
    Then I authenticate myself
    Then I am on home page
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I select a retail car with cd code "CCAR"
    Then I fill in all billing information
    Then I complete purchase with agreements
    And I receive immediate confirmation

  Examples:
    | #   | location             | startDateShift  | endDateShift     |
    | 948 | Columbia, SC - (CAE) | 6 days from now | 46 days from now |

  @ACCEPTANCE
  Scenario Outline: RTC-949,950 Duplicate booking - 1-st one was failed (CPV and AVS error)
    Given I have valid random credentials
    Then I authenticate myself
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I select a opaque car with cd code "CCAR"
    Then I fill in billing information to cause a <errorType> error
    Then I complete purchase with agreements
    Then I receive immediate "<errorMessage>" error message
    And  I want to logout
    Then I authenticate myself
    Then I am on home page
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I don't request insurance
    And I request quotes
    Then I see a non-empty list of search results
    And I select a opaque car with cd code "CCAR"
    Then I fill in all billing information
    Then I complete purchase with agreements
    And I receive immediate confirmation

  Examples:
    | #   | location                  | startDateShift  | endDateShift    | errorType | errorMessage                                                                                    |
    | 949 | San Francisco, CA - (SFO) | 6 days from now | 9 days from now | CPV       | The credit card number, expiration date, or security code you entered does not match what is on |
    | 950 | San Francisco, CA - (SFO) | 4 days from now | 6 days from now | AVS       | The billing address you entered does not match what is on file with your card's issuing bank.   |

  @ACCEPTANCE @JANKY
  Scenario Outline: Duplicate car booking - different first or last name. RTC-951, RTC-952
    Given I have valid random credentials
    And   I authenticate myself
    And   I'm searching for a car in "<location>"
    And   I want to travel in the near future
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And   I choose a retail car
    And   I change primary driver's name to <firstPurchaseName>
    And   I fill in all billing information
    When  I complete purchase with agreements
    Then  I receive immediate confirmation
    Given I am on car landing page
    And   I see car landing page
    And   I'm searching for a car in "<location>"
    And   I want to travel between <startDateShift> and <endDateShift>
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And   I choose a retail car
    And   I change primary driver's name to <secondPurchaseName>
    And   I fill in all billing information
    When  I complete purchase with agreements
    Then  I receive immediate confirmation

  Examples:
    | startDateShift   | endDateShift     | location             | firstPurchaseName | secondPurchaseName |
    | 12 days from now | 35 days from now | Columbia, SC - (CAE) | Johnn Lytle       | JohnG Lytle        |
    | 9 days from now  | 25 days from now | Columbia, SC - (CAE) | John Lytle        | John Lyttle        |


  Scenario Outline: Duplicate car booking with aggregation layer. RTC-951, RTC-952------AGGREGATION LAYER------
    Given I have valid random credentials
    And   I authenticate myself
    When  I make new retail car purchase with <location> location and stop on billing
    And   I change primary driver's name to <firstPurchaseName>
    When  I complete car purchase from billing
    And   I make new retail car purchase from landing with <location> location between <startDateShift> and <endDateShift> and stop on billing
    When  I change primary driver's name to <secondPurchaseName>
    Then  I complete car purchase from billing

  Examples:
    | startDateShift   | endDateShift     | location             | firstPurchaseName | secondPurchaseName |
    | 12 days from now | 35 days from now | Columbia, SC - (CAE) | Johnn Lytle       | JohnG Lytle        |

  @ACCEPTANCE @JANKY
  Scenario Outline: Duplicate car booking - the same first or last name. RTC-957
    Given I have valid random credentials
    And   I authenticate myself
    And   I'm searching for a car in "<location>"
    And   I want to travel between <startDateShift> and <endDateShift>
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And   I choose a retail car
    And   I change primary driver's name to <firstPurchaseName>
    And   I fill in all billing information
    When  I complete purchase with agreements
    Then  I receive immediate confirmation
    Given I am on home page
    And   I'm searching for a car in "<location>"
    And   I want to travel between <startDateShift> and <endDateShift>
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And   I choose a retail car
    And   I change primary driver's name to <secondPurchaseName>
    And   I fill in all billing information
    When  I complete purchase with agreements
    Then  the purchase was failed due of
    """
   We're sorry, but your booking was not completed. You were not charged
   A car rental booking with the same trip details was recently booked through your Hotwire account.
   View this itinerary  If you have questions regarding this booking, please contact Hotwire Customer Care.
   In the U.S. and Canada, our associates can be reached toll-free at 1-866-HOTWIRE (468-9473).
   Outside of the U.S. and Canada, please call 1-417-520-1680 (regular toll charges apply). Error #427
    """
  Examples:
    | startDateShift   | endDateShift     | location             | firstPurchaseName | secondPurchaseName |
    | 16 days from now | 21 days from now | Columbia, SC - (CAE) | John Lytle        | John Lytle         |

  @ACCEPTANCE @STBL
  Scenario:  RTC-3898 Duplicate booking - Opaque - Airport
    Given I have valid random credentials
    And   I authenticate myself
    And   I'm searching for a car in "Detroit, MI"
    And   I want to travel between 20 days from now and 28 days from now
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And I select a retail car with cd code "CCAR"
    And   I fill in all billing information
    When  I complete purchase with agreements
    Then  I receive immediate confirmation
    And I logout to cookied mode
    And   I'm searching for a car in "Detroit, MI"
    And   I want to travel between 20 days from now and 28 days from now
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And I select a retail car with cd code "CCAR"
    And I fill in all billing information
    When  I complete purchase with agreements
    Then  the purchase was failed due of
    """
   We're sorry, but your booking was not completed. You were not charged
   A car rental booking with the same trip details was recently booked through your Hotwire account.
   View this itinerary  If you have questions regarding this booking, please contact Hotwire Customer Care.
   In the U.S. and Canada, our associates can be reached toll-free at 1-866-HOTWIRE (468-9473).
   Outside of the U.S. and Canada, please call 1-417-520-1680 (regular toll charges apply). Error #427
    """

