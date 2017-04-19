@TOOLS
Feature: Verify correct permissions for CSR types
  Owner: Oleksandr Zelenov


  Background:
    Given C3 application is accessible

  Scenario Outline: Verify appropriate permissions for CSR admin type
    #TODO: Investigate on jslave11 error "The page you requested could not be found"
    Given I login into C3 with username "<username>"
    Given the hotel ID
    When I search hotel by ID
    Then I should <negation> be able to deactivate hotel

  Examples:
    | username  | negation |
    | csrcroz26 | not      |

  @JANKY
  Examples:
    | username  | negation |
    | csrcroz25 |          |

  @ACCEPTANCE @SingleThreaded   @highPriority
  Scenario: Hotel deactivation/reactivation. Happy path
    Given I login into C3 with username "csrcroz25"
    Given the hotel ID
    And I search hotel by ID
    When I deactivate hotel
    And I fill hotel activation form
    And I submit hotel activation form
    Then I see the message that hotel deactivated
    And I reactivate hotel
    And I submit hotel activation form
    Then I see the message that hotel reactivated

  Scenario: Hotel activation form validation verification
    Given the hotel ID
    And I login into C3 with username "csrcroz25"
    And I search hotel by ID
    When I deactivate hotel
    And I submit hotel activation form
    Then I see validation message on hotel activation form

  Scenario: Checking cancel button on Hotel Activation Form
    Given the hotel ID
    And I login into C3 with username "csrcroz25"
    And I search hotel by ID
    When I deactivate hotel
    And I cancel hotel activation form
    Then hotel is active





