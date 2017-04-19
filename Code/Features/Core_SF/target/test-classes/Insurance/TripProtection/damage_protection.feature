@US
Feature: Insurance module protection not available
  Owner: Juan Hernandez

  Background: 
    Given default dataset
    Given the application is accessible

  @STBL @ACCEPTANCE
  Scenario Outline: RTC-277 Insurance protection not available for certain locations -Guest user
    Given I'm a guest user
    When  I'm searching for a car in "<pickUpLocation>"
    And   I request insurance
    And   I want to travel between 1 days from now and 8 days from now
    And   I request quotes
    And   I choose a retail car
    Then  I will not see insurance in car billing

    Examples: car rental locations
      | pickUpLocation            |
      | Kingston, Jamaica - (KIN) |

  @US @ACCEPTANCE @STBL
  Scenario Outline:RTC-290 Insurance protection equivalent to 4% of Total charge
    Given I'm a guest user
    And   I'm searching for a hotel in "<destinationLocation>"
    And   I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And   I request quotes
    And   I choose <resultstype> hotels tab on results
    And   I choose a hotel result
    And   I book that hotel
    Then  the billing page trip summary will not show insurance
    When  I choose insurance
    Then  the billing page trip summary will show insurance
    And   I validate that insurance cost is 4% of total cost

    Examples: opaque quotable fares parameters
      | destinationLocation              | startDateShift | endDateShift | resultstype |
      | San Francisco, CA, United States | 30             | 35           | opaque      |

  #BUG52484
  @STBL @ACCEPTANCE @BUG
  Scenario: RTC-280 Global On Off trip protection
    Given set property "hotwire.view.web.purchase.travelInsuranceEnabled.H" to value "false"
    Given the application is accessible
    And   I'm searching for a hotel in "OAK"
    And   I want to travel in the near future
    And   I request quotes
    When  I choose a hotel result
    And   I book that hotel
    Then  I don't see trip insurance module on hotel billing page

  @STBL @ACCEPTANCE @BUG
  Scenario: Roll back changes for RTC-280
    Given set property "hotwire.view.web.purchase.travelInsuranceEnabled.H" to value "true"
    Given the application is accessible


  @STBL @ACCEPTANCE
  Scenario Outline: RTC-281 Validate that Trip Protection displays at module and has a price of 19.25 and validate with back end
    Given I am logged in
    And   I am authenticated
    And   I'm searching for a hotel in "<destinationLocation>"
    And   I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And   I request quotes
    When  I choose a hotel result
    And   I book that hotel
    And   I <choice> insurance
    And   the billing page trip summary will show insurance
    And   I validate insurance cost for hotel is <insuranceCost> on billing page
    And   I complete a hotel as user a quote
    And   I receive immediate confirmation
    And   I get the insurance cost from hotel confirmation
    Then  I validate insurance cost for hotel is correct

    Examples: 
      | destinationLocation | startDateShift | endDateShift | choice | insuranceCost |
      | Charles De Gaulle   | 3              | 5            | choose | 19.25         |
