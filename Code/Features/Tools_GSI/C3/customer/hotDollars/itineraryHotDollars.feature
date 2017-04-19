@TOOLS @c3Management   @ACCEPTANCE
Feature: HotDollars operations from view itinerary page.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @highPriority
  Scenario: Adding Hot Dollars to Customer Purchase. Happy Path.
    Given I login into C3 with username "csrcroz1"
    Given customer hotel purchase for refund
    And I search for given customer purchase
    And I add Hot Dollars to itinerary
    Then I see Hot Dollars Form
    When I add to customer account 5 Hot Dollars
    Then I see that Hot Dollars added to customer
    And I verify Hot Dollars amount in database

  @highPriority @BUG
  Scenario: HotDollars adding validation for Customer Purchase. HD Override.
    #HWTL-553 jslave only bug. Fine on dev10.
    Given I login into C3 with username "csrcroz1"
    Given customer hotel purchase for refund
    And I search for given customer purchase
    And I add Hot Dollars to itinerary
    Then I see Hot Dollars Form
    When I add to customer account more HotDollars than total cost
    Then I see error message about maximum HotDollars amount
    When I override  Hot Dollars
    Then I see that Hot Dollars added to customer
    And I verify Hot Dollars amount in database

  @highPriority   @JANKY
  Scenario: Hot Dollars subtraction from Customer Purchase.
    Given I login into C3 with username "csrcroz1"
    Given hotel purchase with Hot Dollars
    And I search for given customer purchase
    And I add Hot Dollars to itinerary
    Then I see Hot Dollars Form
  #TODO: Performance issue for some customers with many HotDollars
    When I subtract from customer account 5 Hot Dollars
    Then I see that Hot Dollars subtracted from customer
    And I verify Hot Dollars amount in database

  Scenario: Checking validation when subtraction from Customer Purchase.
    Given I login into C3 with username "csrcroz1"
    Given hotel purchase without Hot Dollars
    And I search for given customer purchase
    And I add Hot Dollars to itinerary
    Then I see Hot Dollars Form
    When I subtract from customer account 5 Hot Dollars
    Then I see Hot Dollars error message "User input for debiting hotdollars exceeded the current hotdollar amount."


  @ACCEPTANCE @STBL
  Scenario Outline: Purchase opaque-retail hotels using HotDollars entirely-partially. RTC-5181, 5367, 5182, 981, 456, 458, 460, 455
    Given domestic application is accessible
    And   I login as customer with known credentials
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    When I search for given customer email
    And  I edit customer account information
    And  I edit customer Hot Dollars
    Then I see Hot Dollars Form
    When I set to customer account <hotDollarAmount> Hot Dollars
    And  I return to Customer Account
    When I click Site search for customer
    And  I go to <POS> site
    And  I choose "USD" currency
    And  I search for hotel
    And  I switch to <productType> hotels
    When I process the results page
    And  I process the details page
    When I process the billing page with HotDollars
    And  I receive immediate confirmation
    Then I verify Hot Dollars amount in database
    And  I verify confirmation page for hotdollars purchase
    Given domestic application is accessible
    Then I go to My Trips
    And  I manage payment info on domestic site
    Then I verify remainder of hotdollars on domestic site
    Then I verify last hotdollars activity on domestic site

  Examples:
    | hotDollarAmount | productType | POS             |
    | 15000           |  opaque     | United Kingdom  |
    | 15000           |  retail     | United Kingdom  |
    | 5               |  opaque     | United Kingdom  |
    | 5               |  opaque     | US/Canada       |
    | 15000           |  opaque     | US/Canada       |


