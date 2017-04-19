@US
Feature: Verifying the back button processing
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: RTC-666 Browser back from car details to results
    And  I'm searching for a car in "SFO"
    And  I want to travel in the near future
    And  I request quotes
    And  I see car result page
    And  I choose a opaque car and hold on details
    And  I see car details page
    Then I go back to the previous page
    And  I see car result page

  @ACCEPTANCE @ARCHIVE
  Scenario: RTC-650 Verifying browser back processing between hotel and  car details pages
    When I am on hotel index page
    And  I'm searching for a hotel in "SFO"
    And  I want to travel in the near future
    And  I want 1 room(s)
    And  I will be traveling with 2 adults
    And  I will be traveling with 1 children
    And  I request quotes
    Then I will see opaque results page
    When I choose a hotel result
    Then I will see opaque details page
    When I am on car landing page
    And  I'm searching for a car in "SFO"
    And  I want to travel in the near future
    And  I request quotes
    And  I choose a opaque car and hold on details
    And  I see car details page
    Then I go back to the previous page
    And  I see car result page
    Then I go back to the previous page
    Then I go back to the previous page
    And  I see hotel details page


  @ACCEPTANCE @STBL
  Scenario: RTC-639 General check. Trying go back to MyAccount from not logged user
    Given my name is qa_dvalov@hotwire.com and my password is hotwire333
    When I authenticate myself
    Then I am authenticated
    When I access my account information
    When I logout to cookied mode
    Then I go back to the previous page
    And  I see sign in page
