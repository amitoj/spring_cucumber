@MobileApi
Feature: version tests endpoint verifications
  As we do not have VT simulator, these scenarios currently use live VTs from
  the \phoenix\main\phoenixApp\mobileapiWebApp\WEB-INF\scenarios\mobileapi-scenarios.xml

  Scenario: send list of version scenario names and get their value
    And I use client id 1064108143041242077
    When I execute the version tests request with scenarios "PAY14, APS13"
    Then I get version scenario values applied

  Scenario: send invalid version scenario name and get 0 as response
    And I use client id 1064108143041242077
    When I execute the version tests request with scenarios "PAY13"
    Then I get 0 as version scenario value

  Scenario: request version of a test that is switched fully to a specific value
    And I use client id 1064108143041242077
    When I execute the version tests request with scenarios "PAY14"
    Then I should get one and the same scenario value when requesting it several times

  Scenario: request test that has value distribution should return either 1 or 2 for different client IDs
    When I execute the version tests request with scenarios "MST14"
    Then I should get different scenario value when requesting it several times
