Feature: Hotel Search And Purchase (Happy Path) - Guest user using  all available credit cards.
  Let customer search for and purchase hotel rooms. 

  Background: 
    Given default dataset
    Given the application is accessible

  # master card
  @US @ACCEPTANCE
  Scenario Outline: Book a hotel as a guest user - Master card - Retail hotel
    Given I'm a master card user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: opaque quotable fares parameters
      | pos | state | destinationLocation | startDateShift | endDateShift | resultstype |
      | US  | CA    | Fremont, CA         | 30             | 35           | retail      |

  # American express
  @US @ACCEPTANCE
  Scenario Outline: Book a hotel as a guest user - American Express card - Opaque hotel
    Given I'm a americanexp card user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: opaque quotable fares parameters
      | pos | state | destinationLocation | startDateShift | endDateShift | resultstype |
      | US  | CA    | Fremont, CA         | 30             | 35           | opaque      |

  # Visa card
  @US @ACCEPTANCE
  Scenario Outline: Book a hotel as a guest user - Visa card - Retail hotel
    Given I'm a visa card user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: opaque quotable fares parameters
      | pos | state | destinationLocation | startDateShift | endDateShift | resultstype |
      | US  | CA    | Fremont, CA         | 30             | 35           | retail      |

  # Discover card   
  @US @ACCEPTANCE
  Scenario Outline: Book a hotel as a guest user - Discover card - Opaque hotel
    Given I'm a discover card user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: opaque quotable fares parameters
      | pos | state | destinationLocation | startDateShift | endDateShift | resultstype |
      | US  | CA    | Fremont, CA         | 30             | 35           | opaque      |

  # JCB card
  @US @ACCEPTANCE
  Scenario Outline: Book a hotel as a guest user - JCB card - Opaque hotel
    Given I'm a jcb card user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: opaque quotable fares parameters
      | pos | state | destinationLocation | startDateShift | endDateShift | resultstype |
      | US  | CA    | Fremont, CA         | 30             | 35           | opaque      |

  # master card - US POS and EUR currency
  @US @ACCEPTANCE
  Scenario Outline: Purchase a opaque hotel using Master card as a guest - US POS - EUR
  #BUG53939
    Given I'm a master card user
    And I'm from "<pointOfSale>" POS
    And I choose <currency> currency
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: Default location
      | pointOfSale | currency | destinationLocation | startDateShift | endDateShift | resultstype |
      | US          | EUR      | Fremont, CA         | 5              | 7            | opaque      |
