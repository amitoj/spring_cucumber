@TOOLS     @caseNotes  @ACCEPTANCE
Feature: Check case notes Terms of Use drop down list.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario: Case frame. Updated Terms of Use drop down list
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    When I click Terms of Use drop down list
    Then I see "Advised full ToU" value
    And I see "Did not advise ToU" value
    And I see "Advised reduced ToU" value

  @criticalPriority
  Scenario Outline: Case frame. Create and verify Terms of Use in the new case note.
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    And I fill case notes mandatory fields
    And I see "<term of use>" value
    And I close notes
    When I search for recent case notes
    Then I see recent case notes are closed
    And I see "<term of use value>" in case notes search results

  Examples: quotable case notes parameters
    | term of use         | term of use value            |
    | Advised reduced ToU | advised caller reduced terms |
    | Advised full ToU    | advised caller               |
    | Did not advise ToU  | did not advise caller        |

  @criticalPriority
  Scenario Outline: Case frame. Create and verify new case note with "Advised reduced ToU" with Outbound call.
    Given I login into C3 with username "csrcroz1"
    And I search for customer with "g2loadtest384@hotwire.com" email
    And I select Outbound call method of contact
    And I fill case notes mandatory fields
    And I see "<term of use>" value
    When I close notes
    When I search for recent case notes
    Then I see recent case notes are closed
    And I see "<term of use value>" in case notes search results

  Examples: quotable case notes parameters
    | term of use         | term of use value            |
    | Advised reduced ToU | advised caller reduced terms |
    | Advised full ToU    | advised caller               |
    | Did not advise ToU  | did not advise caller        |
