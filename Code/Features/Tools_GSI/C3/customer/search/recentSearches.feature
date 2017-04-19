@TOOLS @customerSearch
Feature: Verify resent searches functionality in C3.
  Owner:Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE   @criticalPriority
  Scenario: No Searches - View Recent Searches. RTC-1494.
    Given I login into C3 with username "csrcroz1"
    Given customer with no recent searches
    When I want to search customer by email
    And I get recent search tab
    Then I see "There are no recent searches associated with this customer." text on page

  @STBL @ACCEPTANCE
  Scenario: View Recent Searches. Verify 'View all' link. RTC-1671.
    #TODO: BDD redesign needed (two actions in one step). SQL query update needed (investigate which searches are displayed in C3).
    Given I login into C3 with username "csrcroz1"
    Given customer with many recent searches
    When I want to search customer by email
    And I get recent search tab
    Then I verify 'view all' and 'view 15' links

  @STBL @ACCEPTANCE
  Scenario: Verify recent C3 search. RTC-1673
    Given customer with no recent searches
    Then  I login into C3 with username "csrcroz1"
    And   I want to search customer by email
    Then  I click Site search for customer
    And   I search for hotel
    Then  I return to Customer Account
    And   I get recent search tab
    When  I see recent C3 search on the page

