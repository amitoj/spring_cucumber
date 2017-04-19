@US @JANKY
Feature: Hotel Search And Purchase With Discount Code
  Allow a customer to purchase an opaque hotel room with a code that gives them a discount at purchase.

  Background:
    Given default dataset

  @LIMITED
  Scenario Outline: Verify fixed amount discount notification on hotel landing page
    And a <type> hotel discount code
    And the application is accessible
    When I am on hotel landing page
    Then I <negation> get discount notification

  Examples: car rental locations
    | type    | negation |
    | valid   |          |
    | invalid | don't    |

  @LIMITED
  Scenario: Verify percent discount notification on hotel landing page
    And a 5 percent hotel discount code
    And the application is accessible
    When I am on hotel landing page
    Then I  get discount notification

  Scenario Outline: verify discount with condition of min total amount
    And a <value> <currency> discount with condition of 100 GBP as min total
    And the application is accessible
    When I am on hotel landing page
    Then I  get discount notification
    And I am notified about the discount condition

  Examples:
    | value | currency |
    | 20    | USD      |
    | 5     | percent  |

  Scenario Outline: verify discount with condition of min star rating
    And a <value> <currency> discount with condition of 4 stars as min star rating
    And the application is accessible
    When I am on hotel landing page
    Then I  get discount notification
    And I am notified about the discount condition

  Examples:
    | value | currency |
    | 20    | USD      |
    | 25    | percent  |

  Scenario Outline: verify discount with condition of max days to arrival
    And a <value> <currency> discount with condition of 5 days as max DTA
    And the application is accessible
    When I am on hotel landing page
    Then I  get discount notification
    And I am notified about the discount condition

  Examples:
    | value | currency |
    | 10    | USD      |
    | 7     | percent  |

  Scenario Outline: Verify discount notification on hotel results page
    And a <type> hotel discount code
    And the application is accessible
    And I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    When I request quotes
    Then I <negation> get discount notification banner

  Examples: Hotel locations
    | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | type    | negation |
    | San Francisco, CA   | 30             | 35           | 1                  | 2              | 1                | valid   |          |
    | San Francisco, CA   | 30             | 35           | 1                  | 2              | 1                | invalid | don't    |

  Scenario Outline: Verify discount on hotel details trip summary panel
    And a <type> hotel discount code
    And the application is accessible
    And I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
    When I choose a quote that has a Hot Rate
    Then a discount notification banner <discountStatus> appeared on the hotel details
  # New converged hotel details page does not have trip summary panel. Comment out below line until it does.
  #Then a discount <discountStatus> been applied

  Examples:
    | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | type    | discountStatus |
    | Miami, FL           | 30             | 35           | 1                  | 2              | 1                | invalid | has not        |
    | Miami, FL           | 30             | 35           | 1                  | 2              | 1                | valid   | has            |

  Scenario Outline: Verify discount on Trip Summary panel on hotel Billing page
    And a <type> hotel discount code
    And the application is accessible
    And I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want <numberOfHotelRooms> room(s)
    And I will be traveling with <numberOfAdults> adults
    And I will be traveling with <numberOfChildren> children
    And I request quotes
  # Need to fix this. "Hot Rate" ties the test that specific phrase. Maybe we should test that the code only works with Hot Rate
    And I choose a quote that has a Hot Rate
    When I proceed to hotel billing
    Then a discount <discountStatus> been applied

  Examples:
    | destinationLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | type    | discountStatus |
    | Miami, FL           | 30             | 35           | 1                  | 2              | 1                | invalid | has not        |
    | Miami, FL           | 30             | 35           | 1                  | 2              | 1                | valid   | has            |

#Scenario Outline: Find and purchase a hotel as a guest user possessing a discount code. Verify discount on hotel confirmation page
#And a <type> hotel discount code
#And the application is accessible
#And I'm a guest user
#And I'm searching for a hotel in "<destinationLocation>"
#And I want to travel between <startDateShift> days from now and <endDateShift> days from now
#And I want <numberOfHotelRooms> room(s)
#And I will be traveling with <numberOfAdults> adults
#And I will be traveling with <numberOfChildren> children
#And I request quotes
#When I choose a hotel and purchase opaque as guest a quote
#Then I receive immediate confirmation
#And the discount <discountStatus> been applied to the order

#Examples:
#| destinationLocation     | startDateShift | endDateShift   | numberOfHotelRooms | numberOfAdults | numberOfChildren | type    | discountStatus    |
#| Miami, FL               | 30             | 35             | 1                  | 2              | 1                | valid   | has               |
#| Miami, FL               | 30             | 35             | 1                  | 2              | 1                | invalid | has not           |
