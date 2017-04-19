@US
Feature: Verifying existent page for incorrect url
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE @STBL
  Scenario: Hotwire 'Page Not Found' page RTC-610
    Given I verify correct processing of the URL that does not exist on our site