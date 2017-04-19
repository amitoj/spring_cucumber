@TOOLS  @customerSearch
Feature: Search by customer name in C3.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE  @criticalPriority
  Scenario: Verify multiple results after searching by name. RTC-493
    Given I login into C3 with username "csrcroz1"
    Given customer with popular name
    When I want to search with given customer name
    Then I see given customer name in search results

  @ACCEPTANCE  @criticalPriority
  Scenario: Search for a Hotwire Customer by Name. Exact Match
    Given I login into C3 with username "csrcroz1"
    Given customer with unique name
    When I want to search with given customer name
    Then I see correct customer information

    @STBL @ACCEPTANCE
  Scenario: Non-Existent Traveler First and Last Name/Valid Traveler First and Last Name. RTC-545
    Given I login into C3 with username "csrcroz1"
    When I want to search customer with "1" firstname "2" lastname
    Then I see "Your search did not match any customer records." error message
    And I return from search for a customer to C3 Index page
    When I want to search customer with "Express" firstname "Hotwire" lastname
    Then I see account page for "Express Hotwire"

    @ACCEPTANCE @STBL
  Scenario Outline: Search for a Hotwire Customer - By Name - Negative. RTC-467, RTC-544, RTC-503, RTC-466
    Given I login into C3 with username "csrcroz1"
    When I want to search customer with "<firstname>" firstname "<lastname>" lastname
    Then I see "<errormessage>" error message

  Examples:
    | firstname | lastname | errormessage                                                                                                                              |
    |           | hotwire  | You have searched for a common last name. Please enter a first name. You must provide at least the first three letters of the first name. |
    | test      | hotwire  | Your full name search returned too many results. Please use another search criteria, such as email.                                       |