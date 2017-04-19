@US
Feature: Hotel maps
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Neighborhood DB default verification. RTC-1244
     Then I verify default hotel neighborhoods values in DB