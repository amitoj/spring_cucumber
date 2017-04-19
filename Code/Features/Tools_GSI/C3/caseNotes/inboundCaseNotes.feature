@TOOLS     @caseNotes
Feature: Inbound call case creation from C3 Home page

  Background:
    Given C3 application is accessible

  Scenario: Case frame. Verify Method of contact for Inbound call case
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    And I cannot change Method of contact

  Scenario: Case frame. Verify Language drop down list
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    Then default language is English
    And I see other languages in language drop down

  Scenario: Case frame. Verify Help Desk contacted drop down list
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    And I see Help Desk Contacted dropdown with Yes/No values in it

  Scenario: Case frame. Disposition levels. General case
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    When I select HOTEL SERVICE in 1st level disposition
    Then 2nd level disposition appears in case notes frame
    When I select CONFIRM REQUEST in 2nd level disposition
    Then 3rd level disposition appears in case notes frame
    When I select RESENT ITINERARY in 3rd level disposition
    Then Outcomes appears in case notes frame

  Scenario Outline: Case frame. Disposition levels. Wrong customer
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    When I select <disposition> in 1st level disposition
    Then all other dispositions are <disposition>

  Examples:
    | disposition    |
    | WRONG CUSTOMER |
    | Z DEAD AIR     |

  Scenario: Case frame. Save note button is greyed out till note is not entered
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    And Save note button is disabled
    When I enter "testNotes" value into Notes field
    Then Save note button is available

  Scenario: Case frame. Save note and verify it displays in case note description
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    When I enter "testNotes" value into Notes field
    And I save notes
    Then notes are saved

  Scenario: Case frame. Close case button is greyed out till all required fields are not entered
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    And Close case button is greyed out
    When I enter "testNotes" value into Notes field
    Then Close case button is greyed out
    When I enter name of contact
    Then Close case button is greyed out
    When I click Terms of Use drop down list
    Then Close case button is greyed out
    When I select HOTEL SERVICE in 1st level disposition
    Then Close case button is greyed out
    When I select CONFIRM REQUEST in 2nd level disposition
    Then Close case button is greyed out
    When I select RESENT ITINERARY in 3rd level disposition
    Then Close case button is greyed out
    When I select any Outcome
    Then Close case button is available

  Scenario: Case frame. Close case frame.
    Given I login into C3 with username "csrcroz1"
    And case frame is not displayed on the page
    When I click on New inbound call case link
    And case frame is displayed on the page
    Then I see case notes frame
    When I fill case notes mandatory fields
    And case frame is displayed on the page
    And I close notes
    Then case frame is not displayed on the page

    #RTC-3890
  @ACCEPTANCE
  Scenario: Case frame. Close case and verify all data is saved / displayed correctly.
    Given I login into C3 with username "csrcroz1"
    When I click on New inbound call case link
    Then I see case notes frame
    When I fill case notes mandatory fields
    And I close notes
    Then case frame is not displayed on the page
    When I search for recent case notes
    Then I see recent case notes are closed
    And all fields are correct in case notes search results