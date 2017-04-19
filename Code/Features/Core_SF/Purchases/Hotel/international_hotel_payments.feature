Feature: International hotel purchase with all available credit cards - Guest and Registered users
  Let customer search for and purchase hotel rooms.

  Background: 
    Given default dataset
    Given the application is accessible

  # master card
  @US @ACCEPTANCE
  Scenario Outline: Purchase a retail hotel using Master card as a guest - International hotel - EUR - Ireland
  #BUG53939
    Given I'm a master card user
    And I'm from "<pointOfSale>" POS
    And currency is <currencyCode>
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: Default location
      | pointOfSale | currencyCode | destinationLocation | startDateShift | endDateShift | resultstype |
      | Ireland     | EUR          | Dublin, Ireland     | 5              | 7            | retail      |

 # Visa card
   @US @ACCEPTANCE
  Scenario Outline: Purchase a opaque hotel using Visa card as signed in user- International hotel - EUR - London
    Given I am signed in
    And I'm a visa card user
    And I'm from "<pointOfSale>" POS
    And currency is <currencyCode>
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: Default location
      | pointOfSale | currencyCode | destinationLocation | startDateShift | endDateShift | resultstype |
      | Ireland     | EUR          | London, United Kingdom     | 5              | 7            | opaque |

  # American Exp card
  @US @ACCEPTANCE
  Scenario Outline: Purchase a retail hotel using American Exp card as signed in user- International hotel - EUR - Ireland
  #BUG53939
    Given I am signed in
    And I'm a americanexp card user
    And I'm from "<pointOfSale>" POS
    And currency is <currencyCode>
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

    Examples: Default location
      | pointOfSale | currencyCode | destinationLocation | startDateShift | endDateShift | resultstype |
      | Ireland     | EUR          | Dublin, Ireland     | 5              | 7            | retail      |

  # Maestro Exp card
  @US @ACCEPTANCE
  Scenario Outline: Purchase a opaque hotel using Maestro card as a guest user- International hotel - EUR - London
  #BUG53939
    Given I'm a maestro card user
    And I'm from "<pointOfSale>" POS
    And currency is <currencyCode>
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

 Examples: Default location
      | pointOfSale | currencyCode | destinationLocation | startDateShift | endDateShift | resultstype |
      | Ireland     | EUR          | London, United Kingdom     | 5              | 7            | opaque |

  # Diners club card
  @US @ACCEPTANCE
  Scenario Outline: Purchase a opaque hotel using Maestro card as a guest user- International hotel - EUR - London
  #BUG53939
    Given I'm a diners club card user
    And I'm from "<pointOfSale>" POS
    And currency is <currencyCode>
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request quotes
    And I choose <resultstype> hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

 Examples: Default location
      | pointOfSale | currencyCode | destinationLocation | startDateShift | endDateShift | resultstype |
      | Ireland     | EUR          | London, United Kingdom     | 5              | 7            | opaque |
