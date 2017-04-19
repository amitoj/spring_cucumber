@TOOLS  @SingleThreaded
Feature: Check travel advisory updates are filling correctly and validation works
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    Given I open travel advisory updates page

  @ACCEPTANCE  @highPriority @JANKY
  Scenario: Travel Advisories. Happy Path.
    #passed 5 times in STBL CI
    When I fill first travel advisory
    And I publish first travel advisory
    Then I see the message that advisory is applied
    When I fill second travel advisory
    And I publish second travel advisory
    Then I see the message that advisory is applied
    Given Domestic application is accessible
    Then Travel Advisory module is displayed
    And I see first travel advisory title
    And I see second travel advisory title
    Given International application is accessible
    Then Travel Advisory module is displayed
    And I see second travel advisory title
    And I don't see first travel advisory title

  Scenario: Verify Travel Advisory module is not displayed after alerts were deleted
    Given I remove second travel advisory issue
    Given I remove first travel advisory issue
    Given Domestic application is accessible
    Then Travel Advisory module is not displayed

  Scenario: Check travel advisory update form validation. Empty field validation.
    When I clean up all travel advisory fields
    And I publish first travel advisory
    Then I see the message that all fields are blank

  Scenario: Check travel advisory update form validation. Incorrect data field validation.
    When I fill first travel advisory
    And I publish first travel advisory
    Then I see the message that advisory is applied
    When I write incorrect date
    And I publish second travel advisory
    Then I see the message that date is incorrect




