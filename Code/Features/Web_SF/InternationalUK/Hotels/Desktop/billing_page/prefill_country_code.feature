@US @BUG
Feature: Pre-fill country code

  Background: 
    Given default dataset
    Given the application is accessible

  Scenario Outline: RTC-4761 - Auto-fill the country code field for non logged customers
    Given I have load international <site> hotwire site
    And I'm searching for a hotel in "Ensenada, Baja California Norte, Mexico"
    And I want to travel between 5 days from now and 7 days from now
    And I am looking for a hotel
    And I choose a hotel result
    When I book that hotel
    Then The country code field should be auto-filled with <countryCode>

    Examples: 
      | site | countryCode |
      | UK   | 44          |
      | IE   | 353         |
  
  @ARCHIVE
     Examples:
	  | NO   | 47          |
      | SE   | 46          |
      | DK   | 45          |
      | AU   | 61          |
      | NZ   | 64          |
      | DE   | 49          |
      | HK   | 852         |
      | SG   | 65          |
      | MX   | 52          |
