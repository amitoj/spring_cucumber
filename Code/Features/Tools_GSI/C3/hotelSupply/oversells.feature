@TOOLS
Feature: Work with oversells
  Let CSR search oversells and operate with.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @JANKY
  Scenario: Hotel Oversells. Save csv document
    #TODO: Investigate on jslave11, blank error page. Could be environment configuration issue(blocked host)
    Given I login into C3 with username "csrcroz1"
    Given hotel with oversells
    And I search hotel by ID
    And I search for reservations on Hotel Oversells page
    And I see new oversells results window
    Then I am able to click Save Document button

  @criticalPriority @ACCEPTANCE @STBL
  Scenario: Hotel Oversells. Create Workflow.RTC-4345
    Given I login into C3 with username "csrcroz1"
    Given hotel with oversells
    And I search hotel by ID
    And I search for reservations on Hotel Oversells page
    And I see new oversells results window
    When I click Create Workflow button
    Then I see "Your Workflow was successfully created." oversell confirmation message
    And I see new oversell case note was added to existing case
