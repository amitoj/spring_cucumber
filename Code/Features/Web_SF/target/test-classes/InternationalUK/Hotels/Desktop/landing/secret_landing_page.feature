Feature: Secret landing page
  Secret landing page allows customers to book Secret Hot Rate hotels for a lower price.
  Owner: Intl team

  Background:
    Given default dataset
    And the application is accessible

  Scenario: Secret Hot Rate Hotels are available in top destinations.
    Given I am a new user
    When I access Secret Hot Rate Hotels in "London"
    Then The Secret Hot Rate Hotels are available

  Scenario: Customers are able to subscribe to Hotwire deals on a secret landing page.
    Given I am a new user
    And I access Secret Hot Rate Hotels in "London"
    When I subscribe to Hotwire deals
    Then I receive subscription confirmation