@TOOLS @c3Management
Feature: HotDollars operations from customer account editing.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE  @criticalPriority
  Scenario: Adding Hot Dollars to customer. Happy Path. RTC-1636
    Given I login into C3 with username "csrcroz1"
    Given non-express customer
    When I search for given customer email
    And I edit customer account information
    And I edit customer Hot Dollars
    Then I see Hot Dollars Form
    When I add to customer account 5 Hot Dollars
    Then I see that Hot Dollars added to customer
    And I see Hot Dollars transaction pop up
    And I verify Hot Dollars amount in database

  @ACCEPTANCE  @criticalPriority @BUG
  Scenario: Hot Dollars subtraction. RTC-1641
#  Jira: https://jira/jira/browse/HWTL-463
    Given customer email with Hot Dollars
    Given I login into C3 with username "csrcroz1"
    Given I search for given customer email
    And   I edit customer account information
    And   I edit customer Hot Dollars
    Then  I see Hot Dollars Form
    When  I subtract from customer account 5 Hot Dollars
    Then  I see that Hot Dollars subtracted from customer
    And   I verify Hot Dollars amount in database

  @ACCEPTANCE  @criticalPriority @JANKY
  Scenario: Subtract more Hot Dollars than available. RTC-1642
    Given customer email with Hot Dollars
    Given I login into C3 with username "csrcroz1"
    Given I search for given customer email
    And   I edit customer account information
    And   I edit customer Hot Dollars
    Then  I see Hot Dollars Form
    When  I subtract from customer account more Hot Dollars than available
    Then  I see Hot Dollars error message "User input for debiting hotdollars exceeded the current hotdollar amount."
    And   I verify Hot Dollars amount in database

  @ACCEPTANCE  @criticalPriority
  Scenario: HotDollars overriding.
    Given I login into C3 with username "csrcroz1"
    Given non-express customer
    When  I search for given customer email
    And   I edit customer account information
    And   I edit customer Hot Dollars
    Then  I see Hot Dollars Form
#TODO: Performance issue for some customers with many HotDollars
#org.openqa.selenium.TimeoutException: Timed out after 30 seconds waiting for element to no longer be visible: By.selector: div#editHotdollarsIndicator
    When  I override 5 Hot Dollars
    Then  I see that Hot Dollars added to customer
    And   I see Hot Dollars transaction pop up
    And   I verify Hot Dollars amount in database