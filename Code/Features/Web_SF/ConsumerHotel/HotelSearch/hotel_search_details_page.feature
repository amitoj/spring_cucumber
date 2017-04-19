Feature: Change search dates from international details page.
  Hotel search from international details page.

  Background:
    Given default dataset
    Given the application is accessible

  @US @ROW @ACCEPTANCE
  Scenario: Retail details page fare finder search.
    Given I'm searching for a hotel in "Los Angeles, CA"
    And I want to travel between 3 days from now and 5 days from now
    And I want 1 room(s)
    And I request quotes
    And I choose retail hotels tab on results
    And I choose a hotel result
    And the details page fare finder is not expanded
    When I expand the details page fare finder
    And the details page fare finder is expanded
    And I want to travel between 5 days from now and 7 days from now
    And I request details quotes
    Then I get the details page travelling between 5 days from now and 7 days from now


  @US @ROW @ACCEPTANCE @ARCHIVE
  Scenario: Fare finder toggling.
    Given I'm searching for a hotel in "Los Angeles, CA"
    And I want to travel between 3 days from now and 5 days from now
    And I request quotes
    And I choose retail hotels tab on results
    And I choose a hotel result
    And the details page fare finder is not expanded
    When I expand the details page fare finder
    Then the details page fare finder is expanded
    When I collapse the details page fare finder
    Then the details page fare finder is not expanded