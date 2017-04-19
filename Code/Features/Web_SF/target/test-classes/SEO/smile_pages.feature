@US
Feature: SEO Smile Pages

  Background: 
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: Add destination city to URL. RTC-443
    Given I have load URL with destination city Oakland
    Then Car farefinder contain "Oakland" in location

  Scenario: The 100 most popular destination links are SEO friendly
    Given I go to the hotels information page
    When I get top 100 destination links
    Then I see the top 100 links are SEO friendly

  Scenario: The US destinations list page links are SEO friendly
    Given I go to the hotels information page
    When I get all the United States of America destination links
    Then the default region is United States of America
    And I see the destination links are SEO friendly

  @ACCEPTANCE
  Scenario: Verifing that the Hotel deals page exists and contains all necessary elements. RTC-620
    Given I navigate to hotel deals from footer
    Then I verify the Hotel radiobutton is checked
    Then I verify the the header for Hotel deals page
    Then I verify the browser title for Hotel deals page
    Then I verify the all destination links are unique on Hotel deals page

  @ACCEPTANCE
  Scenario: RTC-621 Verifing that the Car deals page exists and contains all necessary elements
    Given I navigate to car deals from footer
    Then I verify the Car radiobutton is checked
    Then I verify the the header for Car deals page
    Then I verify the browser title for Car deals page
    Then I verify the all destination links are unique on Car deals page

  @ACCEPTANCE @ACK
  Scenario: RTC-619 Verifing that the Flight deals page exists and contains all necessary elements
    Given I navigate to flight deals from footer
    Then I verify the Flight radiobutton is checked
    Then I verify the the header for Flight deals page
    Then I verify the browser title for Flight deals page
    Then I verify the all destination links are unique on Flight deals page

  @ACCEPTANCE @ARCHIVE
  Scenario: RTC-623 Footer links "Vacations". Verify Vacations page.
    Given I navigate to vacations deals from footer
    Then I verify vacations page

  @ACCEPTANCE
  Scenario: RTC-624 Footer links "Deals". Verify Deals page.
    Given I navigate to deals deals from footer
    Then I verify deals page

  @ACCEPTANCE
  Scenario: Discount international flight deals sorted alphabetically. RTC-627
    Given I navigate to flight deals from footer
    Then I navigate to discount international flights
    Then I verify international discount flights deals sorted alphabetically
    Then I verify header for discount international flights deals

  @ACCEPTANCE
  Scenario: Verify URL structure for discount international car deals page. RTC-630
    Given I navigate to car deals from footer
    Then I navigate to discount international cars
    Then I verify URL structure for the discount international car deals page


  #https://jira/jira/browse/FUN-127 Car most popular destination list is empty
  @ACCEPTANCE @BUG
  Scenario: Verify URL structure for popular destination car deals page. RTC-603, 602
    Given I navigate to car deals from footer
    Then I choose the first link from the car most popular destinations
    Then I verify URL structure for the discount US car deals page
    Then I verify the Car radiobutton is checked
    Then I verify About Our Car module
    Then I choose the first link from the car all cities and verify URL structure

  #https://jira/jira/browse/FUN-127 Car most popular destination list is empty
  @ACCEPTANCE @BUG
  Scenario: Discount US deals general check. RTC-618
    Given I navigate to car deals from footer
    Then I verify the browser title for Car deals page
    Then I verify the the header for Car deals page
    Then I choose the first link from the car most popular destinations
    Then I verify the Car radiobutton is checked
    Then I verify URL structure for the discount US car deals page
    Then I verify discount cars deals sorted alphabetically
    Then I choose the first link from the car all cities and verify location prepopulated value and URL

  @ACCEPTANCE @JANKY @ARCHIVE
  Scenario: Verify search from hotel geo.low.level page. RTC-607, 600
    Given I navigate to hotel deals from footer
    Then I verify URL structure for the discount US hotel deals page
    Then I choose the first link from the hotel all cities and verify location prepopulated value and URL
    And I'm searching for a hotel in "San Francisco, CA - (SFO)"
    And I want to travel between 5 days from now and 10 days from now
    And I want 1 room(s)
    And I will be traveling with 2 adults
    And I will be traveling with 1 children
    And I launch a search
    And I should see hotel results for "San Francisco" location

  #https://jira/jira/browse/FUN-127 Car most popular destination list is empty
  @ACCEPTANCE @BUG
  Scenario: Verify search from car geo.low.level page. RTC-608
    And I'm searching for a car from "San Francisco, CA - (SFO)" to "Los Angeles, CA - (LAX)"
    And I want to travel between 5 days from now and 10 days from now
    And I want to pick up at 12:30pm and drop off at 12:30pm
    Given I navigate to car deals from footer
    Then I verify URL structure for the discount US car deals page
    Then I choose the first link from the car most popular destinations
    Then I choose the first link from the car all cities and verify location prepopulated value and URL
    And I launch a search
    Then I see a non-empty list of search results
    And search results contain "San Francisco, CA - (SFO)" in location

  @ACCEPTANCE @ARCHIVE
  Scenario: Verify search from flight geo.low.level page. RTC-606
    Given I'm searching for a flight from "San Francisco, CA - (SFO)" to "Los Angeles, CA - (LAX)"
    And I want to travel between 5 days from now and 10 days from now
    Then I navigate to flight deals from footer
    And I verify URL structure for the discount US flight deals page
    Then I choose the first link from the flight most popular destinations
    And I choose the first link from the flight most popular destinations
    Then I choose the first link from the flight most popular destinations
    And I launch a search
    Then I see a non-empty list of search results
    And search results contain "San Francisco, CA - (SFO) to Los Angeles, CA - (LAX)" in location

  #Owner: Juan Hernandez Iniguez
  @ACCEPTANCE @STBL
  Scenario: RTC-631:Smile pages - Flights link Breadcrump
    Given I navigate to flight deals from footer
    And I verify the the header for Flight deals page
    And I click on "Home"
    Then I see UHP page

  #Owner: Cristian Gonzalez Robles
  @ACCEPTANCE @ARCHIVE
  Scenario: RTC-633:Smile pages - Back to previous link on product overview page
    Given I navigate to flight deals from footer
    And I verify the the header for Flight deals page
    And I click on "See all states"
    And I navigate through the flights destination pagination by clicking on "Next" link
    And I verify that a result set of 20 states is displayed
    And I click on Back to previous page link
    And I verify the the header for Flight deals page

  #Owner: Cristian Gonzalez Robles
  @ACCEPTANCE @ARCHIVE
  Scenario: RTC-634:Smile pages - Browser Back button
    Given I navigate to flight deals from footer
    And I verify the the header for Flight deals page
    And I click on "See all states"
    And I navigate through the flights destination pagination by clicking on "Next" link
    When I hit the browser back button
    Then I see UHP page
