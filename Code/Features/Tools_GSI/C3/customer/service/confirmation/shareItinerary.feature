@TOOLS @customerService
Feature: Share itinerary module testing
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: RTC-5515. Share Itinerary. Happy Path.
    Given customer hotel purchase for cancel
    Given I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I click share itinerary
    When I fill share itinerary form
    And I confirm share itinerary
    Then I see "The itinerary has been sent via email." share itinerary confirmation message

  Scenario: RTC-5510. Cancel share itinerary option
    Given customer hotel purchase for cancel
    And I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I click share itinerary
    When I cancel share itinerary
    Then share itinerary window is closed

  Scenario: RTC-5515. Share Itinerary. Happy Path. Guest purchase itinerary.
    Given guest customer purchase
    Given I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I click share itinerary
    When I fill share itinerary form
    And I confirm share itinerary
    Then I see "The itinerary has been sent via email." share itinerary confirmation message

  Scenario: RTC-5511. Check error message when trying to share itinerary with empty fields
    Given customer hotel purchase for cancel
    Given I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I click share itinerary
    When I leave fields empty and try to share itinerary
    Then I see share itinerary "Enter an email address." error message

  Scenario: RTC-5512. Check error message when trying to share itinerary with invalid email
    Given customer hotel purchase for cancel
    Given I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I click share itinerary
    When I enter invalid email to share itinerary form
    And I confirm share itinerary
    Then I see share itinerary "Email address is not valid." error message

  Scenario: RTC-5513. Share Itinerary - 1 Email from 2 is invalid
    Given guest customer purchase
    Given I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    And I click share itinerary
    When I enter invalid email to share itinerary form
    And I enter another valid email to share itinerary form
    And I confirm share itinerary
    Then I see share itinerary "Email address is not valid." error message
