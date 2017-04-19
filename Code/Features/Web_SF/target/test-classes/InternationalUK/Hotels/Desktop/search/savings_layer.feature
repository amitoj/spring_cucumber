@ROW
Feature: Hotwire customer should see savings layer above search results
  on a second search if check in date stays more than 4 days from now.
  Owner: Intl team

  Background: Enable savings layer
    Given default dataset
    Given the application is accessible

  @SEARCH
  Scenario: Unsubscribed customer should see savings layer on list results
    Given I'm searching for a hotel in "Paris, France"
    And I want to travel between 5 days from now and 7 days from now
    When I request quotes
    Then I see savings layer

  @SEARCH
  Scenario: Subscribed customer should NOT see savings layer on list results
    Given I am a new user
    And I create an account
    When I'm searching for a hotel in "Paris, France"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    Then I do not see savings layer

  @SEARCH
  Scenario: Customer should see savings layer when doing second search minimum 4 days from today
    Given I'm searching for a hotel in "Paris, France"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    Then I see savings layer

  @ACCEPTANCE @SEARCH
  Scenario: Customer should not see savings layer if is already subscribed
    Given I am a new user
    And I create an account
    When I'm searching for a hotel in "Paris, France"
    And I'm searching for a hotel in "London, United Kingdom"
    And I request quotes
    Then I do not see savings layer