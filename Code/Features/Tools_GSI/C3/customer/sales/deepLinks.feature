@TOOLS @caseNotes
Feature: Inbound call case creation from C3 Home page
  Owner: Oleksandr Zelenov

  @ACCEPTANCE @JANKY
  Scenario Outline: C3 Deep link to results page verification.
    Given C3 application is accessible
    And   I login into C3 with username "csrcroz1"
    When  I search for <vertical>
    Given I save reference number from results page
    When  I process the results page
    And   I try to proceed the interstitial page
    Then  I see details page
    Given I save reference number from details page
    When  I see case notes frame
    And   I copy latest result deeplink url
    And   I copy latest details deeplink url

    When  I delete all cookies
    And   I open saved results deeplink url
    Then  I see results page with the same search parameters

    When  I delete all cookies
    And   I open saved details deeplink url
    And   I see details page with the same search parameters

  Examples:
    | vertical | #    |
    | car      | 4830 |
    | hotel    | 4553 |

