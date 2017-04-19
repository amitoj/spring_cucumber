@US @ARCHIVE
Feature: Verifying punctuation processing for the hotel search
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario Outline: RTC-586/587 Verify reaction on leading/trailing delimiters
    When I am on hotel index page
    And  I'm searching for a hotel in "<location>"
    And  I want to travel between 21 days from now and 26 days from now
    And  I want 1 room(s)
    And  I will be traveling with 2 adults
    And  I will be traveling with 1 children
    And  I launch a search
    Then I will see opaque results page
    Then search results contain "<location>" in location
    When I choose a hotel result
    Then I will see opaque details page

  Examples:
    | location                |
    | , Chicago, IL     |
    |  Chicago, IL...   |