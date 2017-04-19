@US
Feature: displaying MeSo ads on Hotwire site

  The Media Solutions Ads Engineering (MeSo AdEng) team develops and maintains a library of client side (JavaScript)
  scripts that allow Expedia and partner web pages to serve ads to their users. The ad serving library is backed by
  Google's DoubleClick for Publisher (DFP) platform. The library is hosted on an Amazon Web Services (AWS) Simple
  Storage Service (S3) instance. This centralized hosting service allows for a single URL to be used to pull down
  the re-usable library for use by any Expedia or partner web site

  Background:
    Given set property "hotwire.view.MesoBanner.enabled" to value "true"
    Given default dataset
    Given I want to test session params
    Given MeSo ad block is available
    Given the application is accessible


  Scenario: Check displaying MeSo ads on homepage.
    Given I am on the homepage
    Then MeSo ads is displayed on home page

  @ARCHIVE
  Scenario: Check displaying MeSo ads on air result page.
    Given I'm a guest user
    And I'm searching for a flight from "SFO" to "JFK"
    And I want to travel between 1 weeks from now and 2 weeks from now
    And I will be flying with 1 passengers
    And I request quotes
    When I land on air results page
    Then MeSo ads is displayed on air result page

 @ARCHIVE
 Scenario: Check displaying MeSo ads on air details page.
    Given I'm a guest user
    And I'm searching for a flight from "SFO" to "JFK"
    And I want to travel between 1 weeks from now and 2 weeks from now
    And I will be flying with 1 passengers
    And I request quotes
    And I choose retail flight
    Then MeSo ads is displayed on air details page

  Scenario:
  # Roll back changes...
    Given set property "hotwire.view.MesoBanner.enabled" to value "false"



