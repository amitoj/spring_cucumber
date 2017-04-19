@TOOLS @c3Finance
Feature: Check Overcharges Details link on Hotel Supply Details Page.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE @BUG
  Scenario: Overcharges Details link for hotel with overcharges.
  #https://jira/jira/browse/HWTL-606
    Given the hotel with overcharges
    And I login into C3 with username "csrcroz9"
    And I search hotel by ID
    Then I see "Overcharge Details" link in common tasks
    When I open overcharge details
    Then I see overcharges report


  Scenario: Overcharges Details link for hotel withOUT overcharges.
    Given the hotel without overcharges
    And I login into C3 with username "csrcroz9"
    And I search hotel by ID
    Then I don't see "Overcharge Details" link in common tasks
    And I see "There are no overcharges for this hotel" message in common tasks

