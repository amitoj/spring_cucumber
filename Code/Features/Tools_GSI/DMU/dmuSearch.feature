@TOOLS @dmu    @ACCEPTANCE
Feature: Database management tool. Happy path.
  Owner: Oleksandr Zelenov

  Background:
    Given DMU application is accessible
    #For Dev it will always use Dev01

  Scenario: Database management utility. Happy Path.
    Given I login into DMU
    Then I see DMU index page
    When I write "hotel" to DMU search box
    Then I see pop-up with matched procedures
    When I click on procedure
    Then I see procedure page

  Scenario: Login page empty fields validation.
    When I submit DMU login form
    Then I see DMU login error message

  Scenario: Login page wrong username validation.
    Given I login into DMU with wrong credentials
    Then I see DMU login error message about wrong credentials
