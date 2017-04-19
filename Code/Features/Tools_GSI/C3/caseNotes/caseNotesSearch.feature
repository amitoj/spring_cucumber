@TOOLS    @caseNotes @ACCEPTANCE
Feature: Verify case notes search form
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @highPriority
  Scenario: Case Notes Search by ID.
    Given I login into C3 with username "csrcroz1"
    Given purchase case note ID
    When I search for given case note ID
    Then I see case note ID in results

  @highPriority
  Scenario: Case Notes Search by itinerary number.
    Given I login into C3 with username "csrcroz1"
    Given purchase case note itinerary
    When I search for case notes by itinerary
    Then I see case note results

  @highPriority
  Scenario: Case Notes Search by Email. RTC-841
    Given I login into C3 with username "csrcroz1"
    Given customer email with case notes
    When I search for given case note email
    Then I see case note email in results

  @highPriority
  Scenario: Case Notes Search by CSR Agent. RTC-817
    #passed 5 times in STBL CI
    Given I login into C3 with username "csrcroz1"
    When I search for case notes entered by csrcroz1 for 5 days
    Then I see case note results