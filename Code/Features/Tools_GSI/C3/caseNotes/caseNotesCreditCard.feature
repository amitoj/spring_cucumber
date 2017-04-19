@TOOLS    @caseNotes
Feature: Check case notes credit card saving.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario: Agent sees an error message about credit card number in notes.
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    When I enter "1234 1234 5678 7895" value into Notes field
    And I fill case notes mandatory fields
    And I close notes
    Then I see error message about credit card number
    And Notes with "1234 1234 5678 7895" number are not saved

  @LIMITED
  Scenario:  Agent is not allowed to close the case with the cc number.
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    When I enter "1234567890123" value into Notes field
    And I fill case notes mandatory fields
    And I close notes
    Then I see error message about credit card number
    And Notes with "1234567890123" number are not saved

  Scenario:  Agent can save the case only after clearing the cc number.
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    When I enter "1234567890123" value into Notes field
    And I fill case notes mandatory fields
    And I save notes
    Then I see error message about credit card number
    And Notes with "1234567890123456" number are not saved
    When I clear credit card number in notes
    And I save notes
    Then notes are saved
#  Notes text isn't present. Case Notes are not saved] expected:<[tru]e> but was:<[fals]e

  @ACCEPTANCE
  Scenario:  Agent can close the case only after clearing the cc number.
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    When I enter "1234567890123456" value into Notes field
    And I fill case notes mandatory fields
    And I close notes
    Then I see error message about credit card number
    And Notes with "1234567890123456" number are not saved
    When I clear credit card number in notes
    And I close notes
    When I search for recent case notes
    Then I see recent case notes are closed

